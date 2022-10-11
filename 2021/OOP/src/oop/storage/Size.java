package oop.storage;

public class Size {
    private int length, width, height;
    private int[] sizes = new int[3];
    private int mass;

    public Size(int length, int width, int height, int mass) {
        if (length <= 0 || width <= 0 || height <= 0 || mass <= 0)
            throw new IllegalArgumentException("length <= 0 || width <= 0 || height <= 0 || mass <= 0");
        this.length = length;
        this.width = width;
        this.height = height;
        this.mass = mass;
    }

    public boolean isNotGreaterThan(Size other) {
        if (this.mass > other.mass)
            return false;
        else if (this.length > other.length || this.width > other.width || this.height > other.height)
            return false;
        else
            return true;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        if (length <= 0)
            throw new IllegalArgumentException("length <= 0");
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        // ...
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        // ...
        this.height = height;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        // ...
        this.mass = mass;
    }

    public long getVolume() {
        return (long) length * width * height;
    }

    @Override
    public String toString() {
        return "Size{" +
                "length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", mass=" + mass +
                '}';
    }
}
