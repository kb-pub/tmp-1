package app.collection;

import app.domain.Person;
import app.domain.RandomPersonFactory;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("enter ex num> ");
        var num = new Scanner(System.in).nextLine();
        Main.class.getDeclaredMethod("ex" + num).invoke(new Main());
    }

    // TODO Проверить фабрику персон
    void ex1() {
        print(getPersons(10));
    }

    private Collection<Person> getPersons(int size) {
        var factory = new RandomPersonFactory();
        var result = new LinkedHashSet<Person>();
        for (int i = 0; i < size; i++) {
            result.add(factory.get());
        }
        return result;
    }

    /* TODO
        распечатать for, foreach, итератором
     */
    void print(Collection<?> collection) {
//        var list = new LinkedList<>(collection);
//        for (int i = 0; i < list.size(); i++) {
//            log(list.get(i));
//        }

//        var iter = collection.iterator();
//        while (iter.hasNext()) {
//            log(iter.next());
//        }

        for (var e : collection)
            log(e);
    }

    private void log(Object o) {
        System.out.println(o);
    }

    /* TODO
        накопить список персон
        распечатать for, foreach, итератором
     */
    void ex2() {

    }

    /* TODO
        удалить всех Василиев из списка
     */
    void ex3() {
        var persons = getPersons(10);
        for (var p : persons) {
            if (p.getName().equals("Боря")) {
                persons.remove(p); // error!
            }
        }

//        var iter = persons.iterator();
//        while (iter.hasNext()) {
//            var p = iter.next();
//            if (p.getName().equals("Боря")) {
//                iter.remove();
//            }
//        }

//        persons.removeIf(p -> p.getName().equals("Боря"));
        print(persons);
    }

    /* TODO
        Создать очередь персон
     */
    void ex4() {

    }

    /* TODO
        Создать множество персон
     */
    void ex5() {

    }

    /* TODO
        Создать множество с сохранением порядка добавления
     */
    void ex6() {

    }

    /* TODO
        Создать упорядоченное множество персон
        а) анонимный класс
        б) лямбда
        в) comparing
        г) Comparable
     */
    void ex7() {

    }

    /* TODO
        Создать отображение "день месяца рождения - персона"
        ... отсортированное
     */
    void ex9() {

    }

    /* TODO
        Создать отсортированное отображение "год рождения - список персон, родившихся в этот год"
     */
    void ex10() {

    }

}
