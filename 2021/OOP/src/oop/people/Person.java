package oop.people;

import java.io.Closeable;
import java.io.IOException;

abstract public class Person extends Object implements Nameable, Closeable {
    private String name;
    private String surname;

    public Person(String name, String surname) {
        if (name == null || surname == null)
            throw new IllegalArgumentException("name == null || surname == null");
        this.name = name;
        this.surname = surname;
    }

    @Override
    public void close() throws IOException {
        System.out.println("i am closed now");
    }

    abstract public String sayWhoAmI();

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
