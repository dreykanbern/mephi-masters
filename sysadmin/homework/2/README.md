# М3–М4

Продолжил мониторинг из М1–М2: скрипт пишет `monitor.log`, теперь его можно прочитать через HTTPS.

1. Собрал образ и запустил контейнер через Docker и Compose: [сборка](results/docker/png/01-docker-build.png), [запуск](results/docker/png/02-docker-run.png).
2. Сделал RAID 1 в `/mnt/raid` и LVM в `/mnt/logs` на трёх файлах по 512 MiB: [проверка томов](results/storage/png/03-raid-lvm.png). Лог хранится в `/mnt/logs/my-app`.
3. Python отдаёт лог на `localhost:8080`, Nginx принимает HTTPS на 443: [проверка](results/nginx/png/04-nginx-https.png). Запросы видны в [журналах](results/logs/png/06-request-logs.png).
4. Настроил systemd: `my-app` работает и включён в автозапуск: [статус](results/systemd/png/05-systemd.png). Восстановление томов и приложения после перезагрузки проверил на отдельной VM: [результат](results/bootstrap/png/08-vm-reboot.png).
5. Для бонуса расширил LVM и ext4 с 200 до 300 MiB, файлы сохранились: [до и после](results/storage/png/07-lvm-expansion.png). Команда: `sudo ./scripts/expand-lvm.sh`.
6. Написал `bootstrap.sh` и проверил его на чистой Ubuntu: [результат](results/bootstrap/png/09-vm-clean-bootstrap.png). Повторный запуск сохраняет данные.

На том же VPS с Ubuntu 22.04 у меня давно работает панель VPN со своим сайтом Nginx на порту 80.
Для М4 порт 80 тоже используется: `listen 80`, `server_name my-app.local`.
По IP остаётся панель VPN, а запрос по имени `my-app.local` получает редирект 301 на HTTPS.
Проверяю это на сервере командой `curl -I --resolve my-app.local:80:127.0.0.1 http://my-app.local/`.

Запуск на Ubuntu с systemd и правами sudo:

```bash
git clone --branch sysadmin/2 https://github.com/dreykanbern/mephi-masters.git
cd mephi-masters/sysadmin/homework/2/my-sysadmin-scripts
sudo ./scripts/bootstrap.sh
```

Проверка HTTPS: `curl -kI https://77.238.228.246/monitor.log` (сертификат самоподписанный).

1) [Скрипты](my-sysadmin-scripts/scripts/)
2) [Конфигурации](my-sysadmin-scripts/configs/)
3) [Скриншоты и выводы команд](results/README.md)
