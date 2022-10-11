package app.stream;

import app.domain.Person;
import app.domain.PersonFactory;

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
        getIntStream()
                .filter(i -> i % 2 != 0)
                .filter(i -> i % 3 == 0)
                .forEach(this::log);
    }

    // TODO Ограничить поток чисел (skip, limit)
    void ex3() {
        getIntStream()
                .skip(5)
                .limit(10)
                .forEach(this::log);
    }

    // TODO Преобразовать в случайных персон (map)
    void ex4() {
        getIntStream()
                .mapToObj(i -> PersonFactory.get())
                .forEach(this::log);
    }

    // TODO Создать поток персон
    void ex5() {

    }

    Stream<Person> getPersonStream() {
        return getIntStream()
                .mapToObj(i -> PersonFactory.get());
    }

    // TODO Собрать персон из нескольких потоков (flatMap)
    void ex6() {
        Stream.of(getPersonStream(),
                        getPersonStream(),
                        getPersonStream())
                .flatMap(stream -> stream)
                .forEach(this::log);
    }

    // TODO Оставить только уникальные, отсортировать
    void ex7() {
        getPersonStream()
                .distinct()
                .sorted()
                .forEach(this::log);
    }

    // TODO Простое сведЕние: max, min, findFirst, findAny, anyMatch, allMatch, noneMatch
    void ex8() {
        var x = getIntStream()
                .anyMatch(i -> i % 10 == 0)
                ;
        log(x);
    }

    // TODO Перемножить числа из потока (reduce)
    void ex9() {
        var val = getIntStream()
                .reduce(1, (x ,y) -> x * y);
//                .reduce((x ,y) -> x * y);
        log(val);
    }

    // TODO Склеить ФИО персон через запятую (reduce)
    void ex10() {
        var str = getPersonStream()
//        var str = Stream.<Person>empty()
                .map(p -> p.getName() + " " + p.getSurname())
                .distinct()
                .sorted(Comparator.comparing(String::length))
                .reduce((p1, p2) -> p1 + ", " + p2);
//        log(str.orElse("<no person>"));
        str.ifPresent(this::log);
    }

    // TODO Накопить персоны в разных коллекциях (collect)
    void ex11() {
        var list = getPersonStream()
                .collect(() -> new TreeSet<Person>(),
                        TreeSet::add,
                        TreeSet::addAll);
        log(list);
    }

    // TODO Сгруппировать по одинаковому имени (groupingBy)
    void ex12() {
        var map = getPersonStream()
                .collect(Collectors.groupingBy(Person::getName));
        map.forEach((name, person) ->
                log(String.format("%s -> %s\n", name, person)));
    }

    // TODO ... во множество (toSet)
    void ex13() {
        var map = getPersonStream()
                .collect(Collectors.groupingBy(
                        Person::getName, Collectors.toSet()));
        map.forEach((name, person) ->
                log(String.format("%s -> %s\n", name, person)));
    }

    // TODO ... по количеству таких персон (counting)
    void ex14() {
        var map = getPersonStream()
                .collect(Collectors.groupingBy(
                        Person::getName, Collectors.counting()));
        map.forEach((name, person) ->
                log(String.format("%s -> %s\n", name, person)));
    }

    // TODO ... накопить только фамилии без повторений (mapping)
    void ex15() {
        var map = getPersonStream()
                .collect(Collectors.groupingBy(
                        Person::getName, Collectors.mapping(
                                Person::getSurname,
                                Collectors.toSet())));
        map.forEach((name, person) ->
                log(String.format("%s -> %s\n", name, person)));
    }

    void ex16() {
        record TestSet(int elems, int iters) {
        }

        record Sort_TestSet(String sort, List<TestSet> setList) {}

        var fullSet = List.of(
                new TestSet(100, 100000),
                new TestSet(1000, 10000),
                new TestSet(10000, 1000),
                new TestSet(100000, 100),
                new TestSet(1000000, 10));
        var reducedSet = List.of(
                new TestSet(100, 100000),
                new TestSet(1000, 10000),
                new TestSet(10000, 1000),
                new TestSet(100000, 100));

        var mainMap = Set.of(
                new Sort_TestSet("bubble", reducedSet),
                new Sort_TestSet("quick", fullSet));

        mainMap.stream();
    }

    void log(Object o) {
        System.out.println(o);
    }
}
