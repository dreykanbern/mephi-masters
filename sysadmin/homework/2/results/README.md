# Результаты М3–М4

6 октября 2026: сервер `niderlands` (`v7890.hosted-by-vdsina.com`) и отдельная VM `miphy-hw2`, Ubuntu 22.04.

1. Сборка Docker: [скриншот](docker/png/01-docker-build.png), [вывод](docker/txt/01-docker-build.txt).
2. Запуск контейнера: [скриншот](docker/png/02-docker-run.png), [вывод](docker/txt/02-docker-run.txt).
3. RAID и LVM: [скриншот](storage/png/03-raid-lvm.png), [вывод](storage/txt/03-raid-lvm.txt).
4. Nginx, HTTP 301 и HTTPS 200: [скриншот](nginx/png/04-nginx-https.png), [вывод](nginx/txt/04-nginx-https.txt).
5. Systemd и автозапуск: [скриншот](systemd/png/05-systemd.png), [вывод](systemd/txt/05-systemd.txt).
6. Запрос в журналах: [скриншот](logs/png/06-request-logs.png), [вывод](logs/txt/06-request-logs.txt).
7. Расширение LVM: [скриншот](storage/png/07-lvm-expansion.png), [вывод](storage/txt/07-lvm-expansion.txt).
8. Проверка после перезагрузки VM: [скриншот](bootstrap/png/08-vm-reboot.png), [вывод](bootstrap/txt/08-vm-reboot.txt).
9. Запуск с нуля в VM: [скриншот](bootstrap/png/09-vm-clean-bootstrap.png), [вывод](bootstrap/txt/09-vm-clean-bootstrap.txt).

Полный вывод bootstrap: [сервер](bootstrap/txt/bootstrap-server.txt), [чистая VM](bootstrap/txt/bootstrap-vm.txt).
