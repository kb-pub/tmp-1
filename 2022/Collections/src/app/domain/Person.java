package app.domain;

import java.time.LocalDate;

public class Person {
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    @Override
    public String toString() {
        return "%d -> %s %s, %s".formatted(id, name, surname, birthdate);
    }
}
