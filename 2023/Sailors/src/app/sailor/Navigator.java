package app.sailor;

public class Navigator extends Profession {
    public Navigator(int rank) {
        super("Штурман", rank);
    }

    public double getSpeedIncreasePercent() {
        return switch (rank) {
            case 1 -> 0;
            case 2 -> 15;
            case 3 -> 25;
            case 4 -> 35;
            default -> 50;
        };
    }
}
