package app2.animals;

public interface Animal {
    String getType();
    String getName();
    int getBiteCount();
    void incBiteCount();

    default String getFullName() {
        return getType() + " " + getName();
    }
}
