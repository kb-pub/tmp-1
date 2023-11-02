package app.sailor;

abstract public class Profession {
    public static final double MAX_RANK = 5;

    private final String title;
    protected int rank = 1;
    private double exp = 0;

    public Profession(String title, int rank) {
        this.title = title;
        this.rank = rank;
    }

    public Profession(String title) {
        this(title, 1);
    }

    public String getTitle() {
        return title;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(int rank) {
        if (rank < 1 || rank > MAX_RANK) {
            throw new RuntimeException("rank must be in [1, %s]".formatted(MAX_RANK));
        }
        this.rank = rank;
    }

    public double getExp() {
        return exp;
    }

    public void addExp(double value) {
        assert value > 0;
        this.exp += exp;
    }
}
