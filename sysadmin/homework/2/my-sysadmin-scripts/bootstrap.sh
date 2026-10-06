#!/usr/bin/env bash
# М3–М4. Запуск на учебной Ubuntu 22.04/24.04: sudo ./bootstrap.sh
set -euo pipefail

fail() { echo "Ошибка: $*" >&2; exit 1; }
[[ $EUID -eq 0 ]] || fail "запустите через sudo"
[[ -d /run/systemd/system ]] || fail "нужна Ubuntu с работающим systemd"
cd -- "$(dirname -- "${BASH_SOURCE[0]}")"

# Не подменяем одноимённые файлы или контейнер другого проекта.
for file in /etc/systemd/system/my-app.service /etc/systemd/system/my-app-storage.service \
    /etc/nginx/sites-available/my-app /opt/my-app/storage.sh; do
    # При повторном запуске узнаём и маркер уже установленной версии.
    [[ ! -e "$file" ]] || grep -Fqx -e '# my-app' -e '# ДЗ2: my-app' "$file" || fail "$file уже занят"
done
if command -v docker >/dev/null && docker container inspect my-app &>/dev/null; then
    [[ $(docker inspect -f '{{index .Config.Labels "com.docker.compose.project"}}' my-app) == my-app ]] \
        || fail "контейнер my-app принадлежит другому проекту"
fi

export DEBIAN_FRONTEND=noninteractive
apt-get update
packages=(mdadm lvm2 nginx openssl curl)
command -v docker >/dev/null || packages+=(docker.io)
docker compose version &>/dev/null || packages+=(docker-compose-v2)
apt-get install -y --no-install-recommends "${packages[@]}"
systemctl enable --now docker
docker compose version

# Отдельное имя позволяет оставить работающие сайты (например, VPN) на порту 80.
shopt -s nullglob
for file in /etc/nginx/sites-enabled/* /etc/nginx/conf.d/*.conf; do
    if [[ "$file" == /etc/nginx/sites-enabled/my-app ]]; then
        [[ $(readlink -f "$file") == /etc/nginx/sites-available/my-app ]] || fail "$file уже занят"
    elif grep -Eq 'server_name.*[[:space:]]my-app\.local([[:space:];]|$)' "$file"; then
        fail "имя my-app.local уже используется в $file"
    fi
done

install -d /opt/my-app
install -m 755 storage.sh /opt/my-app/storage.sh
install -m 644 configs/my-app-storage.service /etc/systemd/system/my-app-storage.service
install -m 644 configs/my-app.service /etc/systemd/system/my-app.service
systemctl daemon-reload
systemctl enable my-app-storage my-app
systemctl start my-app-storage
# Повторный запуск проверяет и уже активное хранилище.
./storage.sh

# Compose создаёт контейнер. Дальнейшим запуском и перезапуском управляет systemd.
systemctl stop my-app
docker compose --project-name my-app up -d --build
docker compose --project-name my-app stop

if [[ ! -f /etc/ssl/private/my-app.key && ! -f /etc/ssl/certs/my-app.crt ]]; then
    openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
        -keyout /etc/ssl/private/my-app.key -out /etc/ssl/certs/my-app.crt \
        -subj '/CN=my-app.local' -addext 'subjectAltName=DNS:my-app.local,IP:127.0.0.1'
    chmod 600 /etc/ssl/private/my-app.key
fi
[[ -f /etc/ssl/private/my-app.key && -f /etc/ssl/certs/my-app.crt ]] \
    || fail "для TLS нужны оба файла: my-app.key и my-app.crt"
install -m 644 configs/nginx-my-app.conf /etc/nginx/sites-available/my-app
ln -sfn /etc/nginx/sites-available/my-app /etc/nginx/sites-enabled/my-app
nginx -t
systemctl enable --now nginx
systemctl reload nginx
systemctl start my-app
sleep 3

# systemctl start не ждёт готовности HTTP-сервера внутри контейнера.
ready=false
for attempt in {1..20}; do
    if curl -fsS --max-time 2 http://127.0.0.1:8080/monitor.log >/dev/null; then
        ready=true
        break
    fi
    sleep 1
done
[[ "$ready" == true ]] || fail "приложение не готово: journalctl -u my-app -n 30"
curl -fsSI --max-time 5 --resolve my-app.local:80:127.0.0.1 http://my-app.local/
curl -fksSI --max-time 5 --resolve my-app.local:443:127.0.0.1 https://my-app.local/monitor.log
systemctl --no-pager status my-app
echo 'Готово. Проверка: curl -k --resolve my-app.local:443:127.0.0.1 https://my-app.local/monitor.log'
