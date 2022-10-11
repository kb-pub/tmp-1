package solve.stream;

import app.domain.Person;
import app.domain.PersonFactory;

import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("enter ex num> ");
        var num = new Scanner(System.in).nextLine();
        Main.class.getDeclaredMethod("ex" + num).invoke(new Main());
    }

    // TODO Нагенерировать числа (Stream.of, generate, iterate)
    void ex1() {
        numberStream()
                .forEach(System.out::println);
    }

    Stream<Integer> numberStream() {
//        return Stream.of(1,2,3,4,5);

//        var random = new Random();
//        return Stream.generate(random::nextInt);

        return Stream.iterate(0, x -> x < 100, x -> x + 1);

//        return List.of(1,2,3,4,5,6,7,8,9,0).stream();
    }

    // TODO Отфильтровать поток чисел (filter)
    void ex2() {
        numberStream()
                .filter(x -> x % 4 == 0)
                .forEach(this::log);
    }

    // TODO Ограничить поток чисел (skip, limit)
    void ex3() {
        numberStream()
//                .skip(5)
//                .limit(10)
                .filter(x -> x % 4 == 0)
                .skip(5)
                .limit(10)
                .forEach(this::log);
    }

    // TODO Преобразовать в случайных персон (map)
    void ex4() {
        numberStream()
                .limit(10)
                .map(number -> PersonFactory.get())
                .forEach(this::log);
    }

    Stream<Person> personStream(int count) {
        return Stream.generate(PersonFactory::get)
                .limit(count);
    }

    void ex5() {
        personStream(10)
                .forEach(this::log);
    }

    // TODO Собрать персон из нескольких потоков (flatMap)
    void ex6() {
        numberStream()
                .limit(5)
                .flatMap(this::personStream)
                .forEach(this::log);
    }

    // TODO Оставить только уникальные, отсортировать
    void ex7() {
        personStream(10)
                .distinct()
                .sorted(Comparator.comparing(Person::getSurname))
                .forEach(this::log);
    }

    // TODO Простое сведЕние: max, min, findFirst, findAny, anyMatch, allMatch, noneMatch
    void ex8() {
        personStream(10)
                .max(Comparator.comparing(Person::getName).thenComparing(Person::getSurname))
                .ifPresent(this::log);
    }

    // TODO Перемножить числа из потока (reduce)
    void ex9() {
        numberStream()
                .skip(10)
                .filter(x -> x % 2 != 0)
                .reduce((x, y) -> x * y)
                .ifPresent(this::log);
    }

    // TODO Склеить ФИО персон через запятую (reduce)
    void ex10() {
        var result = personStream(10)
                .parallel()
                .reduce("",
                        (str, person) -> str + ", " + person.getName() + " " + person.getSurname(),
                        (str1, str2) -> {
                            log("in third arg");
                            return str1 + ", " + str2;
                        });
        log(result);
    }

    // TODO Накопить персоны в разных коллекциях (collect)
    void ex11() {
//        var set = personStream(10)
//                .collect(() -> new TreeSet<>(Comparator.comparing(Person::getName)),
//                        TreeSet::add, TreeSet::addAll);
//        var set = personStream(10)
//                .collect(() -> new TreeSet(),
//                        (treeSet, x) -> treeSet.add(x),
//                        (set1, set2) -> set1.addAll(set2));
        var set = personStream(10)
                .collect(Collectors.toSet());
        log(set);
    }

    // TODO Сгруппировать по одинаковому имени (groupingBy)
    void ex12() {
        var map = personStream(10)
                .collect(Collectors.groupingBy(Person::getName));
        map.forEach((k, v) -> log(k + ": " + v));
    }

    // TODO ... во множество (toSet)
    void ex13() {
        var map = personStream(10)
                .collect(Collectors.groupingBy(Person::getName, Collectors.toSet()));
        map.forEach((k, v) -> log(k + ": " + v));
    }

    // TODO ... по количеству таких персон (counting)
    void ex14() {
        var map = personStream(10)
                .collect(Collectors.groupingBy(Person::getName, Collectors.counting()));
        map.forEach((k, v) -> log(k + ": " + v));
    }

    // TODO ... накопить только фамилии без повторений (mapping)
    void ex15() {
        var map = personStream(10)
                .collect(Collectors.groupingBy(Person::getName,
                        Collectors.mapping(Person::getSurname, toSet())));
        map.forEach((k, v) -> log(k + ": " + v));
    }

    void ex16() {
        var map = personStream(10)
                .collect(Collectors.groupingBy(Person::getName,
                        Collectors.mapping(Person::getSurname,
                                Collectors.joining(", "))));
        map.forEach((k, v) -> log(k + ": " + v));
    }

    void log(Object o) {
        System.out.println(o);
    }
}
