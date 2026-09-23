#!/usr/bin/env bash
set -euo pipefail

cd "$(git rev-parse --show-toplevel)"

if ! command -v python3 >/dev/null; then
    echo "Для Git-хуков нужен Python 3." >&2
    exit 1
fi

existing_hooks=$(git config --get core.hooksPath || true)
if [[ -n "$existing_hooks" && "$existing_hooks" != ".githooks" ]]; then
    echo "Уже настроены другие хуки: $existing_hooks. Настройку не меняю." >&2
    exit 1
fi

chmod +x .githooks/pre-commit .githooks/commit-msg .githooks/pre-push
git config --local core.hooksPath .githooks
echo "Git-хуки включены для этого репозитория."
