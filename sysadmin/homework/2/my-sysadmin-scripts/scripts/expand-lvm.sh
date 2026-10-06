#!/usr/bin/env bash
# Бонус: расширить уже созданный LV и ext4 с 200 до 300 MiB без потери логов.
set -euo pipefail
[[ $EUID -eq 0 ]] || { echo 'Запустите через sudo' >&2; exit 1; }
cd -- "$(dirname -- "${BASH_SOURCE[0]}")"
./storage.sh

echo 'До расширения:'
lvs vg_data/lv_logs
df -h /mnt/logs
size=$(lvs --noheadings --units b --nosuffix -o lv_size vg_data/lv_logs | xargs)
if (( ${size%.*} < 300 * 1024 * 1024 )); then
    lvextend --yes -L 300M /dev/vg_data/lv_logs
fi
# Отдельная команда позволяет повторить шаг, если расширение ФС было прервано.
resize2fs /dev/vg_data/lv_logs
echo 'После расширения:'
lvs vg_data/lv_logs
df -h /mnt/logs
