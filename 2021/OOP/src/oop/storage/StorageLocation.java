package oop.storage;

public class StorageLocation {
    private final Position position;
    private final Size size;
    private Item item;

    public StorageLocation(Position position, Size size) {
        this.position = position;
        this.size = size;
    }

    public boolean canPlace(Item item) {
        return item.getSize().isNotGreaterThan(size);
    }

    public void setItem(Item item) {
        if ( ! canPlace(item))
            throw new IllegalArgumentException("item size is too big");
        this.item = item;
    }

    public String toString() {
        return "storage : " + position.toString() + ", " + size.toString();
    }
}
