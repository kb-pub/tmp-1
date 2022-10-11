package solve.domain;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class Person implements Comparable<Person> {
    private final String name;
    private final String surname;
    private final LocalDate birthdate;

    public Person(String name, String surname, LocalDate birthdate) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(surname, person.surname) &&
                Objects.equals(birthdate, person.birthdate);
    }

    @Override
    public int hashCode() {
        //return new Random().nextInt();
        return Objects.hash(name, surname, birthdate);
    }

    @Override
    public int compareTo(Person o) {
        return Comparator.comparing(Person::getName)
                .thenComparing(Person::getSurname)
                .compare(this, o);
//        var names = this.name.compareTo(o.name);
//        if (names != 0)
//            return names;
//        else {
//            return this.surname.compareTo(o.surname);
//        }
    }
}
