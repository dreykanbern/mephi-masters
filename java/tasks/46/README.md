# Задание 46 — Квадратная доска с координатами

Задача 19 из блока о массивах и строках.

Заполнить публичный массив `strings` координатами доски `size × size`. Столбцы обозначаются буквами, номера строк убывают от `size` до `1`. При `size = 4` первая строка — `A4 B4 C4 D4`, последняя — `A1 B1 C1 D1`.

Исходное значение `size = 9` и модификаторы полей сохранены. Для столбцов после `Z` метод `columnName` формирует `AA`, `AB` и далее.

Решение на Java 17. Исходный код: [`Main.java`](src/Main.java).

Запуск из этого каталога:

```bash
javac -encoding UTF-8 --release 17 -d out src/*.java
java -cp out Main
```

Результат:

```text
A9 B9 C9 D9 E9 F9 G9 H9 I9
A8 B8 C8 D8 E8 F8 G8 H8 I8
A7 B7 C7 D7 E7 F7 G7 H7 I7
A6 B6 C6 D6 E6 F6 G6 H6 I6
A5 B5 C5 D5 E5 F5 G5 H5 I5
A4 B4 C4 D4 E4 F4 G4 H4 I4
A3 B3 C3 D3 E3 F3 G3 H3 I3
A2 B2 C2 D2 E2 F2 G2 H2 I2
A1 B1 C1 D1 E1 F1 G1 H1 I1
```
