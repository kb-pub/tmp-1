package app.collection;

import app.domain.Person;
import app.domain.PersonFactory;

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
        var list = new ArrayList<Person>();
        for (int i = 0; i < size; i++)
            list.add(PersonFactory.get());
        return list;
    }

    void print(Collection<?> collection) {
        int cnt = 1;
        for (var elem : collection)
            System.out.println(cnt++ + ") " + elem);
    }

    private void log(Object o) {
        System.out.println(o);
    }

    /* TODO
        накопить список персон
        распечатать for, foreach, итератором
        распечатать в обратном порядке
     */
    void ex2() {
        var persons = new ArrayList<>(getPersons(10));
//        for (int i = 0; i < persons.size(); i++)
//            log(persons.get(i));

//        for (var p : persons)
//            log(p);

        var iterator = persons.iterator();
//        var cnt = 0;
        while (iterator.hasNext()) {
            log(iterator.next());
//            if (++cnt == 4)
//                persons.remove(cnt);
        }
    }

    /* TODO
        удалить всех Васисуалиев из списка
     */
    void ex3() {
        var persons = getPersons(10);
//        var i = persons.iterator();
//        while (i.hasNext()) {
//            var p = i.next();
//            if (p.getName().equals("Васисуалий"))
//                i.remove();
//        }
        persons.removeIf(p -> p.getName().equals("Васисуалий"));
        print(persons);
    }

    /* TODO
        Создать очередь персон
     */
    void ex4() {
        var queue = new ArrayDeque<>(getPersons(10));
        Person p;
        while ((p = queue.poll()) != null)
            log(p);
    }

    /* TODO
        Создать множество персон
     */
    void ex5() {
        var set = new HashSet<>(getPersons(30));
        print(set);
    }

    /* TODO
        Создать множество с сохранением порядка добавления
     */
    void ex6() {
        var set = new LinkedHashSet<>(getPersons(30));
        print(set);
    }

    /* TODO
        Создать упорядоченное множество персон
     */
    void ex7() {
//        var set = new TreeSet<>(getPersons(30));
//        var set = new TreeSet<>(new Comparator<Person>() {
//            @Override
//            public int compare(Person o1, Person o2) {
////                return Integer.compare(o1.getId(), o2.getId());
////                return o1.getName().compareTo(o2.getName());
//                return (o1.getName() + o1.getSurname()).compareTo(o2.getName() + o2.getSurname());
//            }
//        });
        Comparator<Person> comp = (o1, o2) ->
                (o1.getName() + o1.getSurname()).compareTo(o2.getName() + o2.getSurname());
        var set = new TreeSet<>(comp);
        set.addAll(getPersons(30));
        print(set);
    }

    /* TODO
        Создать упорядоченное множество персон с передачей собственного компаратора
     */
    void ex8() {

    }

    /* TODO
        Создать отображение "дата рождения - персона"
     */
    void ex9() {
        var persons = getPersons(10);
        var map = new HashMap<Integer, Person>();
        for (var p : persons)
            map.put(p.getId(), p);
        print(map.entrySet());
    }

    /* TODO
        Создать отображение "год рождения - список персон, родившихся в этот год"
     */
    void ex10() {
        var persons = getPersons(20);
        var map = new HashMap<Integer, TreeSet<Person>>();
//        for (var p : persons) {
//            var year = p.getBirthdate().getYear();
//            var set = map.get(year);
//            if (set == null) {
//                map.put(year, new TreeSet<>());
//                set = map.get(year);
//            }
//            set.add(p);
//        }
        persons.forEach(p -> map.computeIfAbsent(
                    p.getBirthdate().getYear(), k -> new TreeSet<>()).add(p));
        print(map.entrySet());
    }

}
