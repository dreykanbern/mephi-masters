#!/usr/bin/env bash
set -euo pipefail

cd "$(git rev-parse --show-toplevel)"

if ! command -v gh >/dev/null; then
    echo "Для публикации нужен GitHub CLI: gh. После установки выполните gh auth login." >&2
    exit 1
fi

branch=$(git symbolic-ref --quiet --short HEAD) || {
    echo "Сначала переключитесь на рабочую ветку." >&2
    exit 1
}
python3 scripts/git_rules.py branch "$branch"

if [[ -n "$(git status --porcelain)" ]]; then
    echo "Сначала закоммитьте изменения, которые нужно опубликовать." >&2
    exit 1
fi

commit_title=$(git log -1 --format=%s)
python3 scripts/git_rules.py title "$branch" "$commit_title"
prefix="$branch: "
title=${commit_title#"$prefix"}

remote_url=$(git remote get-url origin)
repository=$(gh repo view "$remote_url" --json nameWithOwner --jq .nameWithOwner)
base_branch=$(gh repo view "$repository" --json defaultBranchRef --jq .defaultBranchRef.name)
if [[ "$branch" == "$base_branch" ]]; then
    echo "Публиковать нужно отдельную ветку, а не $base_branch." >&2
    exit 1
fi

git fetch origin "$base_branch:refs/remotes/origin/$base_branch"
if git diff --quiet "origin/$base_branch...HEAD"; then
    echo "В ветке нет изменений относительно $base_branch." >&2
    exit 1
fi

git push --set-upstream origin "HEAD:refs/heads/$branch"

pr_url=$(gh pr list --repo "$repository" --head "$branch" --base "$base_branch" \
    --state open --json url --jq '.[0].url // empty')
if [[ -n "$pr_url" ]]; then
    echo "Изменения отправлены в существующий PR: $pr_url"
    exit 0
fi

body_file=$(mktemp)
trap 'rm -f "$body_file"' EXIT
{
    printf 'Изменения из ветки `%s` для `%s`.\n\n' "$branch" "$base_branch"
    git log --reverse --format='- %s' "origin/$base_branch..HEAD"
} > "$body_file"

gh pr create --repo "$repository" --base "$base_branch" --head "$branch" \
    --title "$title" --body-file "$body_file"
