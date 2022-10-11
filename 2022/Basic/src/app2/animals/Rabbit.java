package app2.animals;

public class Rabbit extends BaseAnimal {
    public final static int BITE_LIMIT = 10;

    private final boolean angry;

    public Rabbit(String name, boolean angry) {
        super(Rabbit.class.getSimpleName(), name);
        this.angry = angry;
    }

    @Override
    public String getName() {
        return (angry ? "angry " : "") + super.getName();
    }

    public String bite(Animal a) {
        a.incBiteCount();
        var result = getName() + " bites " + a.getFullName();
        if (a instanceof Rabbit) {
            result += "! STRIKE BACK!";
            incBiteCount();
        }
        return result;
    }
}
