# StaticCarProject

Шестой урок — вызываю метод `build()` класса `Car` без создания объекта.

[Car](src/Car.java) · [Main](src/Main.java) · [Как запустить](../../../README.md)

В объявление метода добавляю `static`:

```java
public static void build() {
    System.out.println("You must build a new car");
}
```

Статический метод принадлежит классу. В `Main.main` вызываю его через имя класса:

```java
Car.build();
```

Создавать объект через `new Car()` больше не нужно. Имя `build` сохраняю со строчной буквы, как в исходном коде задания.

Результат: `You must build a new car`.
