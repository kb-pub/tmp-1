package solve.collection;

import solve.domain.Person;
import solve.domain.PersonFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("enter ex num> ");
        var num = new Scanner(System.in).nextLine();
        Main.class.getDeclaredMethod("ex" + num).invoke(new Main());
    }

    // TODO Проверить фабрику персон
    void ex1() {

    }

    /* TODO
        накопить список персон
        распечатать for, foreach, итератором
        распечатать в обратном порядке
     */
    void ex2() {

    }

    /* TODO
        удалить всех Васисуалиев из списка
     */
    void ex3() {

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
     */
    void ex7() {

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

    }

    /* TODO
        Создать отображение "год рождения - список персон, родившихся в этот год"
     */
    void ex10() {

    }


    Collection<Person> getPersons(int count) {
        var coll = new ArrayList<Person>();
        for (int i = 0; i < count; i++) {
            coll.add(PersonFactory.get());
        }
        return coll;
    }

    void print(Collection<?> collection) {
        int cnt = 1;
        for (var elem : collection)
            System.out.println(cnt++ + ") " + elem);
    }
}
