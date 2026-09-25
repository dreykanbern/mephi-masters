# HelloWorldProject

Седьмой урок — исправляю порядок вызовов методов, чтобы получить `Hello world!`.

[Main](src/Main.java) · [Как запустить](../../../README.md)

В методе `main` сначала вызываю `printHello()`, затем `printSpace()` и `printWorld()`:

```java
printHello();
printSpace();
printWorld();
```

Методы выполняются в порядке вызова. `System.out.print` выводит текст без перевода строки, поэтому три вызова складываются в одну строку.

Результат: `Hello world!`.
