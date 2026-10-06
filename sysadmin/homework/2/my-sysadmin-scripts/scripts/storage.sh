#!/usr/bin/env bash
# my-app
# Учебные диски — только файлы в /mnt/raid-lab. Повторный запуск сохраняет данные.
set -euo pipefail

fail() { echo "Ошибка: $*" >&2; exit 1; }
[[ $EUID -eq 0 ]] || fail "запустите через sudo"
LAB=/mnt/raid-lab

if [[ ! -f "$LAB/.my-app-storage" ]]; then
    [[ ! -e "$LAB" ]] || fail "$LAB уже существует и не принадлежит этой работе"
    [[ ! -b /dev/md0 ]] || fail "/dev/md0 уже занят"
    ! vgs vg_data &>/dev/null || fail "vg_data уже существует"
    mkdir -p "$LAB"
    touch "$LAB/.my-app-storage"
fi

# --nooverlap повторно использует уже подключённый файл, --show возвращает loopN.
for number in 1 2 3; do
    if [[ ! -f "$LAB/disk$number.img" ]]; then
        dd if=/dev/zero of="$LAB/disk$number.img" bs=1M count=512 status=none
    fi
done
LOOP1=$(losetup --find --show --nooverlap "$LAB/disk1.img")
LOOP2=$(losetup --find --show --nooverlap "$LAB/disk2.img")
LOOP3=$(losetup --find --show --nooverlap "$LAB/disk3.img")
# После загрузки udev может собирать сохранённый RAID; дождёмся окончания.
udevadm settle --timeout=30
printf 'RAID: %s + %s; LVM: %s\n' "$LOOP1" "$LOOP2" "$LOOP3"

if mdadm --detail /dev/md0 &>/dev/null; then
    # Массив с таким именем должен состоять ровно из двух наших loop-устройств.
    mapfile -t members < <(find /sys/block/md0/slaves -mindepth 1 -maxdepth 1 -printf '%f\n')
    [[ ${#members[@]} -eq 2 && -e /sys/block/md0/slaves/${LOOP1##*/} \
        && -e /sys/block/md0/slaves/${LOOP2##*/} ]] || fail "/dev/md0 принадлежит другим дискам"
elif mdadm --examine "$LOOP1" &>/dev/null || mdadm --examine "$LOOP2" &>/dev/null; then
    mdadm --assemble /dev/md0 "$LOOP1" "$LOOP2"
else
    [[ -z $(blkid -s TYPE -o value "$LOOP1" || true) \
        && -z $(blkid -s TYPE -o value "$LOOP2" || true) ]] || fail "на RAID-дисках уже есть данные"
    # --run пропускает вопрос о размещении метаданных на учебных дисках.
    mdadm --create /dev/md0 --level=1 --raid-devices=2 --run "$LOOP1" "$LOOP2"
fi

if vgs vg_data &>/dev/null; then
    mapfile -t pvs_in_group < <(pvs --noheadings -o pv_name --select vg_name=vg_data | xargs -n1)
    [[ ${#pvs_in_group[@]} -eq 1 && ${pvs_in_group[0]} == "$LOOP3" ]] \
        || fail "vg_data принадлежит другим дискам"
else
    if ! pvs "$LOOP3" &>/dev/null; then
        [[ -z $(blkid -s TYPE -o value "$LOOP3" || true) ]] || fail "на LVM-диске уже есть данные"
        pvcreate --yes "$LOOP3"
    fi
    [[ -z $(pvs --noheadings -o vg_name "$LOOP3" | xargs) ]] || fail "$LOOP3 уже в другой VG"
    vgcreate vg_data "$LOOP3"
fi
vgchange -ay vg_data
if ! lvs vg_data/lv_logs &>/dev/null; then
    lvcreate --yes -L 200M -n lv_logs vg_data
fi

mount_volume() {
    local device=$1 directory=$2 filesystem
    filesystem=$(blkid -s TYPE -o value "$device" || true)
    if [[ -z "$filesystem" ]]; then
        mkfs.ext4 "$device"
    elif [[ "$filesystem" != ext4 ]]; then
        fail "$device содержит $filesystem, ожидалась ext4"
    fi
    mkdir -p "$directory"
    if mountpoint -q "$directory"; then
        [[ $(readlink -f "$(findmnt -n -o SOURCE --mountpoint "$directory")") == "$(readlink -f "$device")" ]] \
            || fail "$directory уже занят другим томом"
    else
        mount "$device" "$directory"
    fi
}

mount_volume /dev/md0 /mnt/raid
mount_volume /dev/vg_data/lv_logs /mnt/logs
mkdir -p /mnt/logs/my-app
cat /proc/mdstat
lvs vg_data
df -h /mnt/raid /mnt/logs
