package oop;

import oop.people.*;
import oop.storage.Item;
import oop.storage.Position;
import oop.storage.Size;
import oop.storage.StorageLocation;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("Ivan", "Ivanov");
        System.out.println(customer.getFullName());
        customer.buy(new Item(new Size(1,2,3,4), "Item for sale"));

        Person person = new Manager("Vasya", "Vasyin");

        sayNames2(customer, person, new Worker("Sergey", "Sergeev"));
    }

    private static void sayNames(Person ... persons) {
        System.out.println("========================");
        for (var person : persons)
            System.out.println(person.sayWhoAmI() + ", name: " + person.getFullName());
        System.out.println("========================");
    }

    private static void sayNames2(Nameable ... persons) {
        System.out.println("========================");
        for (var person : persons)
            System.out.println("name: " + person.getFullName());
        System.out.println("========================");
    }

    private static void testStorage() {
        var size = new Size(10, 20, 30, 1000);
        System.out.println("volume = " + size.getVolume());

        var position = new Position(10, 20, 90);
        var location = new StorageLocation(position, size);

        size.setLength(10);

        System.out.println(location);

        var item1 = new Item(size, "Item 1");
        var item2 = new Item(size, "Item 2");

        System.out.println(item1 + ", " + item2);
        System.out.println("next item id: " + Item.getNextId());

        new Size(1,2,3,4);
    }


}
