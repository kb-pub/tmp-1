package oop.people;

import oop.storage.Item;

public class Customer extends Person {
    public Customer(String name, String surname) {
        super(name, surname);
    }

    public void buy(Item item) {
        System.out.println(getFullName() + " bought " + item);
    }

    @Override
    public String sayWhoAmI() {
        return "i am a customer " + getFullName();
    }
}
