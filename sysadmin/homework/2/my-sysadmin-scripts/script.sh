#!/usr/bin/env bash
set -e  # Остановить скрипт, если команда завершилась с ошибкой.

INTERVAL=5  # Пауза между снимками в секундах.

while true; do
    {
        date '+--- %Y-%m-%d %H:%M:%S ---'
        free -h
        df -h
        uptime
        echo
    } >> monitor.log

    sleep "$INTERVAL"
done
