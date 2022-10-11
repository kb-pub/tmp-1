package app.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Person implements Comparable<Person> {
    private static int idCount = 0;

    private final String name;
    private final String surname;
    private final LocalDate birthdate;

    private final int id = ++idCount;

    public Person(String name, String surname, LocalDate birthdate) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(birthdate, person.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthdate);
    }

    @Override
    public int compareTo(Person other) {
        return (name + surname).compareTo(other.name + other.surname);
//        int nameComparing = this.name.compareTo(other.name);
//        if (nameComparing != 0)
//            return nameComparing;
//        else {
//            return this.surname.compareTo(other.surname);
//        }
    }
}
