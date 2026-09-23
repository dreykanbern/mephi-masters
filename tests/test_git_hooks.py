"""Проверки настоящих Git-хуков в отдельных временных репозиториях."""

import os
from pathlib import Path
import shutil
import subprocess
import sys
import tempfile
import unittest


ROOT = Path(__file__).resolve().parents[1]


class GitHookTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.repo = Path(self.temp.name) / "repo"
        self.repo.mkdir()
        shutil.copytree(ROOT / ".githooks", self.repo / ".githooks")
        (self.repo / "scripts").mkdir()
        shutil.copy(ROOT / "scripts/git_rules.py", self.repo / "scripts/git_rules.py")
        self.git("init", "--initial-branch=lessons/1")
        self.git("config", "user.name", "Hook Test")
        self.git("config", "user.email", "test@example.invalid")
        self.git("config", "commit.gpgsign", "false")
        self.git("config", "core.hooksPath", ".githooks")

    def run_command(self, args, *, ok=True, stdin=None):
        result = subprocess.run(
            args, cwd=self.repo, text=True, input=stdin,
            stdout=subprocess.PIPE, stderr=subprocess.PIPE, timeout=15,
            env={**os.environ, "GIT_TERMINAL_PROMPT": "0"},
        )
        if ok:
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        else:
            self.assertNotEqual(result.returncode, 0, result.stdout + result.stderr)
        return result

    def git(self, *args, ok=True):
        return self.run_command(["git", *args], ok=ok)

    def stage_file(self, path, text="учебный файл\n"):
        target = self.repo / path
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_text(text, encoding="utf-8")
        self.git("add", "--", path)

    def commit(self, title, *, ok=True):
        return self.git("commit", "--allow-empty", "-m", title, ok=ok)

    def test_commit_format_and_placeholders(self):
        self.stage_file("java/lessons/1/ExampleProject/src/Main.java")
        self.stage_file("java/lessons/2/.gitkeep", "")
        self.stage_file("java/lessons/3/.gitkeep", "")
        self.stage_file("README.md")
        for title in (
            "lessons/1: первый урок",
            "lessons/2: Первый урок",
            "lessons/1 Первый урок",
            "lessons/1: Java и JVM",
        ):
            with self.subTest(title=title):
                self.commit(title, ok=False)
        self.commit("lessons/1: Первый урок по Java и JVM")
        self.commit("lessons/1: Ёмкое описание Java\n\nПодробности коммита.")

    def test_branch_guards_and_generic_branch(self):
        for branch in ("lessons/0", "lessons/01", "lessons/foo", "lessons/1/extra", "lessons", "main", "master"):
            with self.subTest(branch=branch):
                self.git("symbolic-ref", "HEAD", f"refs/heads/{branch}")
                self.commit(f"{branch}: Первый урок", ok=False)
        self.git("symbolic-ref", "HEAD", "refs/heads/docs/hooks")
        self.stage_file("README.md")
        self.commit("docs/hooks: Настройка Git")
        self.git("checkout", "--detach")
        result = self.commit("docs/hooks: Обновление", ok=False)
        self.assertIn("HEAD", result.stderr)

    def test_wrong_lesson_modification_deletion_and_rename(self):
        self.commit("lessons/1: Начало работы")
        self.git("switch", "-c", "lessons/2")
        self.stage_file("java/lessons/2/Main.java")
        self.commit("lessons/2: Второй урок")
        self.git("switch", "-c", "lessons/3")
        self.stage_file("java/lessons/2/Main.java", "изменение\n")
        self.commit("lessons/3: Изменение чужого урока", ok=False)
        self.git("reset", "--hard", "HEAD")
        self.git("rm", "java/lessons/2/Main.java")
        self.commit("lessons/3: Удаление чужого урока", ok=False)
        self.git("reset", "--hard", "HEAD")
        (self.repo / "java/lessons/3").mkdir()
        self.git("mv", "java/lessons/2/Main.java", "java/lessons/3/Main.java")
        self.commit("lessons/3: Перенос чужого урока", ok=False)

    def test_push_guard_blocks_primary_updates_and_deletions(self):
        self.commit("lessons/1: Первый урок")
        remote = Path(self.temp.name) / "remote.git"
        self.git("init", "--bare", str(remote))
        self.git("remote", "add", "origin", str(remote))
        self.git("push", "origin", "lessons/1")
        for branch in ("main", "master"):
            with self.subTest(branch=branch):
                self.git("push", "origin", f"HEAD:{branch}", ok=False)
                # Seed the destination only to verify its deletion is also blocked.
                self.git("-c", "core.hooksPath=/dev/null", "push", "origin", f"HEAD:{branch}")
                self.git("push", "origin", f":{branch}", ok=False)
        self.git("push", "origin", "HEAD:lessons/01", ok=False)
        self.git("switch", "-c", "docs/hooks")
        self.commit("docs/hooks: Описание Git")
        self.git("push", "origin", "docs/hooks")

    def test_shared_cli_validation(self):
        validator = [sys.executable, "scripts/git_rules.py"]
        self.run_command([*validator, "branch", "lessons/42"])
        self.run_command([*validator, "branch", "bad:branch"], ok=False)
        self.run_command([*validator, "title", "lessons/42", "lessons/42: Работа с API"])
        self.run_command([*validator, "title", "lessons/42", "lessons/41: Работа с API"], ok=False)


if __name__ == "__main__":
    unittest.main()
