package app.stream;

import app.domain.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("enter ex num> ");
        var num = new Scanner(System.in).nextLine();
        Main.class.getDeclaredMethod("ex" + num).invoke(new Main());
    }

    // TODO Нагенерировать поток чисел (Stream.of, generate, iterate)
    void ex1() {
//        IntStream.of(1, 2, 3, 4, 5)
//                .peek(i -> log("i'm In progress!"))
//                .mapToObj(i -> i + ")")
//                .map(s -> s + " hello!")
//                .forEach(this::log);
//        Stream.of(1, 2, 3, 4, 5)
//                .peek(i -> log("i'm In progress!"))
//                .map(i -> i + ")")
//                .map(s -> s + " hello!")
//                .forEach(this::log);

//        var rand = new Random();
//        final var count = new long[1];
//        try {
//            Stream.generate(() -> rand.nextInt(1000))
//                    .peek(x -> count[0]++)
//                    .limit(20)
//                    .sorted()
//                    .forEach(this::log);
//        }
//        catch (Throwable e) {
//            log("count = " + count[0]);
//        }

//        var rand = new Random();
//        var count = new int[1];
//        Stream.iterate(0, x -> count[0]++ < 20, x -> rand.nextInt())
//                .forEach(this::log);
//        IntStream.range(0, 10)
//                .forEach(this::log);

//        getStream().forEach(this::log);
//        getStream().forEach(this::log);
    }

    IntStream getIntStream() {
        return IntStream.range(1, 20);
    }

    // TODO Отфильтровать поток чисел (filter)
    void ex2() {

    }

    // TODO Ограничить поток чисел (skip, limit)
    void ex3() {

    }

    // TODO Преобразовать в случайных персон (map)
    void ex4() {

    }

    // TODO Создать поток персон
    void ex5() {

    }

    Stream<Person> getPersonStream() {
        return null;
    }

    // TODO Собрать персон из нескольких потоков (flatMap)
    void ex6() {

    }

    // TODO Оставить только уникальные, отсортировать
    void ex7() {

    }

    // TODO Простое сведЕние: max, min, findFirst, findAny, anyMatch, allMatch, noneMatch
    void ex8() {

    }

    // TODO Перемножить числа из потока (reduce)
    void ex9() {
    }

    // TODO Склеить ФИО персон через запятую (reduce)
    void ex10() {

    }

    // TODO Накопить персоны в разных коллекциях (collect)
    void ex11() {

    }

    // TODO Сгруппировать по одинаковому имени (groupingBy)
    void ex12() {

    }

    // TODO ... во множество (toSet)
    void ex13() {

    }

    // TODO ... по количеству таких персон (counting)
    void ex14() {

    }

    // TODO ... накопить только фамилии без повторений (mapping)
    void ex15() {

    }

    void log(Object o) {
        System.out.println(o);
    }
}
