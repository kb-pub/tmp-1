package app.ship;

import java.util.function.Function;

public class Cannon {
    private final String name;
    private final String description;
    private final int damage;
    private final Function<Double, Double> hitChanceByDistance;
    private Ship shipPlacedOn;

    public Cannon(String name, String description, int damage, Function<Double, Double> hitChanceByDistance) {
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.hitChanceByDistance = hitChanceByDistance;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDamage() {
        return damage;
    }

    public Function<Double, Double> getHitChanceByDistance() {
        return hitChanceByDistance;
    }

    public Ship getShipPlacedOn() {
        return shipPlacedOn;
    }

    public void setShipPlacedOn(Ship shipPlacedOn) {
        this.shipPlacedOn = shipPlacedOn;
    }
}
