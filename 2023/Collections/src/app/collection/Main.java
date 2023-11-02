package app.collection;

import app.domain.Person;
import app.domain.RandomPersonFactory;

import java.util.*;

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
        var result = new ArrayList<Person>();
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

        var count = 0;
        for (var e : collection)
            log(count++ + ") " + e);
    }

    public void print(Map<?, ?> map) {
        var count = 0;
        for (var e : map.entrySet()) {
            System.out.println(++count + ") " + e.getKey() + " => " + e.getValue());
        }
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
        var persons = getPersons(10);
        var queue = new PriorityQueue<>(Person.COMPARATOR);

        for (var p : persons) {
            queue.offer(p);
        }

        Person p;
        while ((p = queue.poll()) != null) {
            System.out.println(p);
        }
    }

    /* TODO
        Создать множество персон
     */
    void ex5() {
        var persons = getPersons(20);

        var set = new HashSet<>(persons);

        print(set);
    }

    /* TODO
        Создать множество с сохранением порядка добавления
     */
    void ex6() {
        var persons = getPersons(20);

        var set = new LinkedHashSet<>(persons);

        print(set);
    }

    /* TODO
        Создать упорядоченное множество персон
        а) анонимный класс
        б) лямбда
        в) comparing
        г) Comparable
     */
    void ex7() {
        var persons = getPersons(20);

        var set = new TreeSet<>(Comparator.comparing(Person::getSurname));
        set.addAll(persons);

        print(set);

    }

    /* TODO
        Создать отображение "день месяца рождения - персона"
        ... отсортированное
     */
    void ex9() {
        var map = new TreeMap<Integer, Person>();
        for (var p : getPersons(20)) {
            map.put(p.getBirthdate().getDayOfMonth(), p);
        }

        print(map);
    }

    /* TODO
        Создать отсортированное отображение "год рождения - список персон, родившихся в этот год"
     */
    void ex10() {
        var map = new TreeMap<Integer, List<Person>>();
        for (var p : getPersons(20)) {
            var day = p.getBirthdate().getDayOfMonth();
            map.computeIfAbsent(day, k -> new ArrayList<>()).add(p);
        }

        print(map);
    }

}
