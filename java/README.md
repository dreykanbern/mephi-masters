# Java

Мои задания по Java. Использую JDK 17 LTS.

## Уроки

- [1 — ExampleProject](lessons/1/ExampleProject/) — первый проект и работа с массивом.
- [2 — AnimalsProject](lessons/2/AnimalsProject/) — классы и объекты, переименование Cat в Dog.
- [3 — NumbersProject](lessons/3/NumbersProject/) — исправление ошибок в типах данных.
- [4 — ClassProject и VariableProject](lessons/4/) — исправление объявления класса и имени переменной.
- 5 — [CarProject](lessons/5/CarProject/) и [NewCarProject](lessons/5/NewCarProject/) — создание класса, объектов и вызов метода.

## Как запустить урок

Из папки `java`, на примере первого урока:

```bash
cd lessons/1/ExampleProject
javac -encoding UTF-8 --release 17 -d out src/*.java
java -cp out Main
```

Для другого урока заменяю номер и имя проекта. После изменения кода повторяю две последние команды.

Во втором уроке запускаю `Loader` вместо `Main`: `java -cp out Loader`.

В IntelliJ IDEA выбираю JDK 17. Каждый учебный проект подключаю как отдельный Java-модуль, а его папку `src` отмечаю как Sources Root. Нажимаю ▶ возле метода `main` в классе `Main` (во втором уроке — `Loader`). Настройки IDEA локальные и не хранятся в Git, поэтому после клонирования настраиваю модули заново.
