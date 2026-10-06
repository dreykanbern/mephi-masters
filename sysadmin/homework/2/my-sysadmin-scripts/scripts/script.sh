#!/usr/bin/env bash
set -euo pipefail

trap 'exit 0' TERM INT

while true; do
    {
        date '+--- %Y-%m-%d %H:%M:%S ---'
        free -h
        df -h
        uptime
        echo
    } | tee -a monitor.log

    sleep 5
done
