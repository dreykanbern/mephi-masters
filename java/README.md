# Java

Мои задания по Java. Использую JDK 17 LTS.

## Уроки

- [1 — ExampleProject](lessons/1/ExampleProject/) — первый проект и работа с массивом.
- [2 — AnimalsProject](lessons/2/AnimalsProject/) — классы и объекты, переименование Cat в Dog.
- [3 — NumbersProject](lessons/3/NumbersProject/) — исправление ошибок в типах данных.

## Как запустить урок

Из папки `java`, на примере первого урока:

```bash
cd lessons/1/ExampleProject
javac -encoding UTF-8 --release 17 -d out src/*.java
java -cp out Main
```

Для другого урока заменяю номер и имя проекта. После изменения кода повторяю две последние команды.

Во втором уроке запускаю `Loader` вместо `Main`: `java -cp out Loader`.

В IntelliJ IDEA открываю проект, выбираю JDK 17 и нажимаю ▶ возле метода `main`: в первом и третьем уроках — в `Main`, во втором — в `Loader`.
