package app.domain;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class Person implements Comparable<Person> {
    private static int idSeq = 0;

    private final int id = ++idSeq;
    private final String name;
    private final String surname;
    private final LocalDate birthdate;


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

    public String getFullName() {
        return name + " " + surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    @Override
    public String toString() {
        return "[%d] %s %s (%s)".formatted(id, name, surname, birthdate);
    }

    public static final Comparator<Person> COMPARATOR =
            Comparator.comparing(Person::getName).thenComparing(Person::getSurname);

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        return COMPARATOR.compare(this, (Person) o) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    @Override
    public int compareTo(Person other) {
        return COMPARATOR.compare(this, other);
    }
}
