#!/usr/bin/env bash
set -euo pipefail

pids=()
cleanup() {
    # Оба процесса завершаются вместе; init в Docker подбирает дочерние процессы.
    if (( ${#pids[@]} )); then
        kill "${pids[@]}" 2>/dev/null || true
        wait "${pids[@]}" 2>/dev/null || true
    fi
}
trap cleanup EXIT
trap 'exit 0' TERM INT

/usr/local/bin/script.sh &
pids+=("$!")
python3 -u -m http.server 8080 --bind 0.0.0.0 &
pids+=("$!")

# Любое неожиданное завершение мониторинга или HTTP-сервера — сбой контейнера.
wait -n "${pids[@]}" || true
exit 1
