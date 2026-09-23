"""Публикация в локальный remote без обращения к GitHub."""

import json
import os
from pathlib import Path
import shutil
import subprocess
import sys
import tempfile
import unittest


ROOT = Path(__file__).resolve().parents[1]
PR_URL = "https://github.com/student/studies/pull/1"

FAKE_GH = r'''
import json
import os
from pathlib import Path
import subprocess
import sys

state_path = Path(os.environ["PUBLISH_TEST_GH_STATE"])
state = json.loads(state_path.read_text())
args = sys.argv[1:]
event = {"args": args}
state["events"].append(event)

def save():
    state_path.write_text(json.dumps(state))

def option(name):
    return args[args.index(name) + 1]

if args[:2] == ["repo", "view"]:
    field = option("--json")
    if field == "nameWithOwner":
        print("student/studies")
    elif field == "defaultBranchRef":
        print(state["default_branch"])
    else:
        save()
        sys.exit("Unexpected repository field: " + field)
elif args[:2] in (["pr", "list"], ["pr", "create"]):
    branch = option("--head")
    published = subprocess.run(
        ["git", "--git-dir=" + os.environ["PUBLISH_TEST_REMOTE"],
         "rev-parse", "refs/heads/" + branch],
        text=True, capture_output=True,
    )
    event["published_head"] = published.stdout.strip()
    if published.returncode or event["published_head"] != os.environ["PUBLISH_TEST_HEAD"]:
        save()
        sys.exit("PR operation happened before the current commit was pushed")
    if args[1] == "list":
        print(state.get("pr_url", ""))
    else:
        event["body"] = Path(option("--body-file")).read_text()
        if state.get("fail_next_create"):
            state["fail_next_create"] = False
            save()
            sys.exit("Simulated GitHub failure")
        state["pr_url"] = "https://github.com/student/studies/pull/1"
        print(state["pr_url"])
else:
    save()
    sys.exit("Unexpected gh call: " + repr(args))
save()
'''


class PublishTests(unittest.TestCase):
    def setUp(self):
        temporary = tempfile.TemporaryDirectory(prefix="mephi-publish-test-")
        self.addCleanup(temporary.cleanup)
        self.directory = Path(temporary.name)
        self.repository = self.directory / "worktree"
        self.repository.mkdir()
        self.remote = self.directory / "remote.git"
        self.state_path = self.directory / "gh-state.json"
        self.state_path.write_text(json.dumps({"events": [], "default_branch": "master"}))
        fake_bin = self.directory / "bin"
        fake_bin.mkdir()
        gh = fake_bin / "gh"
        gh.write_text(f"#!{sys.executable}\n" + FAKE_GH)
        gh.chmod(0o755)
        self.env = os.environ.copy()
        self.env.update({
            "PATH": str(fake_bin) + os.pathsep + self.env["PATH"],
            "GIT_CONFIG_NOSYSTEM": "1",
            "GIT_CONFIG_GLOBAL": os.devnull,
            "GIT_TERMINAL_PROMPT": "0",
            "PUBLISH_TEST_GH_STATE": str(self.state_path),
            "PUBLISH_TEST_REMOTE": str(self.remote),
        })
        # IDE or hook processes may export repository-specific Git settings.
        for name in (
            "GIT_DIR", "GIT_WORK_TREE", "GIT_INDEX_FILE", "GIT_COMMON_DIR",
            "GIT_OBJECT_DIRECTORY", "GIT_ALTERNATE_OBJECT_DIRECTORIES",
        ):
            self.env.pop(name, None)
        self.git("init", "--initial-branch=master")
        self.git("config", "user.name", "Test Student")
        self.git("config", "user.email", "student@example.invalid")
        self.git("config", "commit.gpgsign", "false")
        hooks = self.directory / "empty-hooks"
        hooks.mkdir()
        self.git("config", "core.hooksPath", str(hooks))
        scripts = self.repository / "scripts"
        scripts.mkdir()
        for name in ("publish.sh", "git_rules.py"):
            shutil.copy2(ROOT / "scripts" / name, scripts / name)
        self.lesson = self.repository / "lesson.txt"
        self.lesson.write_text("Начальное состояние\n")
        self.git("add", ".")
        self.git("commit", "-m", "Начало")
        self.base_head = self.git("rev-parse", "HEAD").stdout.strip()
        self.git("init", "--bare", "--initial-branch=master", str(self.remote))
        self.git("remote", "add", "origin", str(self.remote))
        self.git("push", "origin", "master")
        self.git("checkout", "-b", "lessons/1")
        self.commit_lesson("lessons/1: Первый урок Java")

    def git(self, *args, check=True):
        return subprocess.run(
            ["git", *args], cwd=self.repository, env=self.env,
            text=True, capture_output=True, check=check,
        )

    def commit_lesson(self, title):
        with self.lesson.open("a") as lesson:
            lesson.write(title + "\n")
        self.git("add", "lesson.txt")
        self.git("commit", "-m", title)
        self.head = self.git("rev-parse", "HEAD").stdout.strip()
        self.env["PUBLISH_TEST_HEAD"] = self.head

    def publish(self):
        return subprocess.run(
            ["bash", "scripts/publish.sh"], cwd=self.repository,
            env=self.env, text=True, capture_output=True,
        )

    def state(self):
        return json.loads(self.state_path.read_text())

    def remote_head(self, branch):
        result = self.git(
            "--git-dir=" + str(self.remote), "rev-parse", "--verify",
            "refs/heads/" + branch, check=False,
        )
        return result.stdout.strip() if result.returncode == 0 else None

    def assert_rejected_without_push(self):
        result = self.publish()
        self.assertNotEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertIsNone(self.remote_head("lessons/1"))
        self.assertEqual(self.remote_head("master"), self.base_head)
        self.assertEqual(self.state()["events"], [])

    def test_pushes_current_commit_before_creating_pr(self):
        result = self.publish()
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertEqual(self.remote_head("lessons/1"), self.head)
        events = self.state()["events"]
        creates = [event for event in events if event["args"][:2] == ["pr", "create"]]
        self.assertEqual(len(creates), 1)
        event = creates[0]
        self.assertEqual(event["published_head"], self.head)
        args = event["args"]
        for option, expected in (
            ("--repo", "student/studies"), ("--base", "master"),
            ("--head", "lessons/1"), ("--title", "Первый урок Java"),
        ):
            self.assertEqual(args[args.index(option) + 1], expected)
        self.assertIn("lessons/1: Первый урок Java", event["body"])
        self.assertIn(PR_URL, result.stdout)
        upstream = self.git("rev-parse", "--abbrev-ref", "@{upstream}").stdout.strip()
        self.assertEqual(upstream, "origin/lessons/1")

    def test_next_commit_updates_existing_pr(self):
        first = self.publish()
        self.assertEqual(first.returncode, 0, first.stdout + first.stderr)
        self.commit_lesson("lessons/1: Добавлен вывод результата")
        second = self.publish()
        self.assertEqual(second.returncode, 0, second.stdout + second.stderr)
        self.assertEqual(self.remote_head("lessons/1"), self.head)
        creates = [event for event in self.state()["events"]
                   if event["args"][:2] == ["pr", "create"]]
        self.assertEqual(len(creates), 1)
        self.assertIn(PR_URL, second.stdout)

    def test_dirty_worktree_does_not_push(self):
        self.lesson.write_text("Ещё не сохранено в коммите\n")
        self.assert_rejected_without_push()

    def test_master_does_not_push(self):
        self.git("checkout", "master")
        self.commit_lesson("master: Изменение основной ветки")
        self.assert_rejected_without_push()

    def test_uses_repository_default_branch(self):
        self.git("branch", "-m", "master", "main")
        self.git("--git-dir=" + str(self.remote), "branch", "-m", "master", "main")
        state = self.state()
        state["default_branch"] = "main"
        self.state_path.write_text(json.dumps(state))
        result = self.publish()
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        pr_events = [event for event in self.state()["events"]
                     if event["args"][:2] in (["pr", "list"], ["pr", "create"])]
        self.assertEqual(len(pr_events), 2)
        for event in pr_events:
            args = event["args"]
            self.assertEqual(args[args.index("--base") + 1], "main")

    def test_pr_title_strips_only_branch_prefix(self):
        self.commit_lesson("lessons/1: Первый урок: Работа с Java")
        result = self.publish()
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        create = next(event for event in self.state()["events"]
                      if event["args"][:2] == ["pr", "create"])
        args = create["args"]
        self.assertEqual(args[args.index("--title") + 1], "Первый урок: Работа с Java")

    def test_invalid_commit_title_does_not_push(self):
        self.git("commit", "--amend", "-m", "lessons/1: lowercase English title")
        self.assert_rejected_without_push()

    def test_pr_failure_can_be_retried_after_successful_push(self):
        state = self.state()
        state["fail_next_create"] = True
        self.state_path.write_text(json.dumps(state))
        failed = self.publish()
        self.assertNotEqual(failed.returncode, 0)
        self.assertIn("Simulated GitHub failure", failed.stderr)
        self.assertEqual(self.remote_head("lessons/1"), self.head)
        self.assertNotIn("pr_url", self.state())
        retried = self.publish()
        self.assertEqual(retried.returncode, 0, retried.stdout + retried.stderr)
        self.assertEqual(self.state()["pr_url"], PR_URL)
        creates = [event for event in self.state()["events"]
                   if event["args"][:2] == ["pr", "create"]]
        self.assertEqual(len(creates), 2)
        self.assertEqual(self.git("status", "--porcelain").stdout, "")


if __name__ == "__main__":
    unittest.main()
