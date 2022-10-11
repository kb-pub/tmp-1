package oop.storage;

public class Item {
    private static int idCount = 1;

    private final int id = idCount++;
    private final Size size;
    private final String description;

    public static int getNextId() {
         return idCount;
    }

    public Item(Size size, String description) {
        this.size = size;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Size getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", size=" + size +
                ", description='" + description + '\'' +
                '}';
    }
}
