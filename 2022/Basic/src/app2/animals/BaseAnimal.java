package app2.animals;

abstract public class BaseAnimal implements Animal {
    private final String type, name;
    private int biteCount;

    public BaseAnimal(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getBiteCount() {
        return biteCount;
    }

    @Override
    public void incBiteCount() {
        biteCount++;
    }
}
