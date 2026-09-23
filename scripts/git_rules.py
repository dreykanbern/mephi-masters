#!/usr/bin/env python3
"""Общие проверки веток и заголовков коммитов для Git-хуков и публикации."""

import re
import subprocess
import sys


class RuleError(Exception):
    pass


def git(*args):
    return subprocess.check_output(["git", *args], stderr=subprocess.PIPE)


def validate_branch(branch):
    if branch in {"main", "master"}:
        raise RuleError("Работайте в отдельной ветке; изменения в main/master идут через pull request.")
    if not branch or branch.startswith("-"):
        raise RuleError("Некорректное название ветки.")
    result = subprocess.run(
        ["git", "check-ref-format", f"refs/heads/{branch}"],
        stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL,
    )
    if result.returncode:
        raise RuleError(f"Некорректное название ветки: {branch}.")
    if (branch == "lessons" or branch.startswith("lessons/")) and not re.fullmatch(
        r"lessons/[1-9][0-9]*", branch
    ):
        raise RuleError("Ветка урока должна называться lessons/1, lessons/2 и т. д., без ведущих нулей.")


def current_branch():
    try:
        return git("symbolic-ref", "--quiet", "--short", "HEAD").decode().strip()
    except subprocess.CalledProcessError as exc:
        raise RuleError("Сначала переключитесь на ветку: сейчас HEAD не привязан к ветке.") from exc


def validate_title(branch, title):
    validate_branch(branch)
    prefix = f"{branch}: "
    if not title.startswith(prefix):
        raise RuleError(f"Заголовок коммита должен начинаться с «{prefix}».")
    description = title[len(prefix):]
    if not re.match(r"[А-ЯЁ]", description):
        raise RuleError("Описание после двоеточия должно начинаться с заглавной русской буквы; далее можно использовать Java, JVM и другие английские слова.")
    if "\n" in title or "\r" in title:
        raise RuleError("Заголовок коммита должен занимать одну строку.")


def pre_commit():
    branch = current_branch()
    validate_branch(branch)
    paths = git("diff", "--cached", "--name-only", "--no-renames", "-z").split(b"\0")
    for raw_path in paths:
        path = raw_path.decode("utf-8", errors="surrogateescape")
        match = re.match(r"java/lessons/([^/]+)/", path)
        if not match or path.rsplit("/", 1)[-1] == ".gitkeep":
            continue
        number = match.group(1)
        if not re.fullmatch(r"[1-9][0-9]*", number):
            raise RuleError(f"Папка урока должна иметь номер без ведущих нулей: {path}.")
        if branch != f"lessons/{number}":
            raise RuleError(f"Изменение {path} относится к ветке lessons/{number}, текущая ветка — {branch}.")


def commit_msg(filename):
    with open(filename, encoding="utf-8") as message:
        title = message.readline().rstrip("\r\n")
    validate_title(current_branch(), title)


def pre_push():
    for line in sys.stdin:
        fields = line.split()
        if len(fields) != 4:
            raise RuleError("Не удалось прочитать список веток для отправки.")
        remote_ref = fields[2]
        if remote_ref.startswith("refs/heads/"):
            validate_branch(remote_ref[len("refs/heads/"):])


def main():
    args = sys.argv[1:]
    if len(args) == 2 and args[0] == "branch":
        validate_branch(args[1])
    elif len(args) == 3 and args[0] == "title":
        validate_title(args[1], args[2])
    elif args == ["pre-commit"]:
        pre_commit()
    elif len(args) == 2 and args[0] == "commit-msg":
        commit_msg(args[1])
    elif args and args[0] == "pre-push":
        pre_push()
    else:
        raise RuleError("Использование: git_rules.py branch ВЕТКА | title ВЕТКА ЗАГОЛОВОК | pre-commit | commit-msg ФАЙЛ | pre-push.")


if __name__ == "__main__":
    try:
        main()
    except (RuleError, OSError, UnicodeError, subprocess.CalledProcessError) as exc:
        print(f"Ошибка Git-проверки: {exc}", file=sys.stderr)
        sys.exit(1)
