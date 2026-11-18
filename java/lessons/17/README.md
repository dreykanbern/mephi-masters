# Урок 17 — Дата и время

## Задание 1. Вывод времени в UTC

Измените код класса `Time`, чтобы получить следующий вывод.

```text
Thu Jan 01 00:00:01 UTC 1970
```

Вывод `Date.toString()` зависит от системного часового пояса JVM. Чтобы получить строку в UTC с английскими сокращениями, задайте в форматтере часовой пояс `UTC` и локаль `Locale.ENGLISH`.

```java
import java.util.Calendar;

public class Time {
    int millis = 999;

    public void getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        System.out.println(calendar.getTime());
    }
}
```

<details>
<summary>Посмотреть ответ</summary>

Задайте `millis = 1000`, чтобы получить первую секунду после начала эпохи Unix. Шаблон `EEE MMM dd HH:mm:ss z yyyy` включает сокращения дня недели, месяца и часового пояса. [Код решения](1/src/Time.java)

</details>

## Задание 2. Форматирование даты

Допишите код класса `Time`, чтобы получить следующую строку.

```text
1970 Jan 01 00:00:00
```

```java
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time {
    String date = "";

    public void getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("паттерн форматирования");
        date = dateFormat.format(calendar.getTime());
        System.out.println(date);
    }
}
```

<details>
<summary>Посмотреть ответ</summary>

Используйте шаблон `yyyy MMM dd HH:mm:ss`, чтобы вывести год, месяц, день и время с точностью до секунды. Локаль `Locale.ENGLISH` даёт сокращение `Jan`, а часовой пояс `UTC` обеспечивает вывод `00:00:00` для заданного момента. [Код решения](2/src/Time.java)

</details>

## Запуск решений

У каждого задания свой класс `Time`. Из каталога `lessons/17` скомпилируйте их в отдельные папки.

```bash
javac -encoding UTF-8 --release 17 -d out/1 1/src/*.java
java -cp out/1 Time

javac -encoding UTF-8 --release 17 -d out/2 2/src/*.java
java -cp out/2 Time
```
