package oop.people;

public interface Nameable {
    String getName();
    String getSurname();

    static String getSomething() {
        return "something";
    }

    default String getFullName() {
        return getName() + " " + getSurname() + " from Nameable";
    }
}
