# Домашнее задание 2 Яндекс Погода

Отправляю GET на `/v2/forecast` с параметрами `lat`, `lon` и `limit` и заголовком
`X-Yandex-Weather-Key`. Вывожу весь ответ JSON как есть, текущую температуру
из `fact.temp` и среднее арифметическое `parts.day.temp_avg` для первых `limit`
элементов массива `forecasts`. Дни без дневной температуры пропускаю и указываю,
сколько суток вошло в расчёт. [Документация REST API](https://yandex.ru/dev/weather/doc/ru/concepts/forecast-rest)

## Запуск

Использую JDK 17 и Maven. В папке `java/homework/2` я использую локальный файл `.env`
со строкой `YANDEX_WEATHER_KEY=значение_ключа` без кавычек. Можно задать ключ через
переменную окружения `YANDEX_WEATHER_KEY`. Значение из `.env` имеет приоритет.
Запускаю из папки задания.

```bash
mvn -q compile exec:java
```

По умолчанию запрашиваю прогноз для Санкт-Петербурга на 3 суток.
Свои параметры можно передать в порядке `lat lon limit`.

```bash
mvn -q compile exec:java -Dexec.args='59.93 30.31 5'
```

В IntelliJ IDEA открываю `java/homework/2/pom.xml` как Maven-проект и запускаю
`Main` из модуля `weather-homework`. В поле `Working directory` указываю папку
`java/homework/2`, где лежит `.env`.
