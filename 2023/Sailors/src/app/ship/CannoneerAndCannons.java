package app.ship;

import app.sailor.Sailor;

public class CannoneerAndCannons {
    private Sailor cannoneer;
    private Cannon cannonLeft;
    private Cannon cannonRight;
    private final Ship shipPlacedOn;

    public CannoneerAndCannons(Sailor cannoneer, Cannon cannonLeft, Cannon cannonRight, Ship shipPlacedOn) {
        this.cannoneer = cannoneer;
        this.cannonLeft = cannonLeft;
        this.cannonRight = cannonRight;
        this.shipPlacedOn = shipPlacedOn;
    }

    public Sailor getCannoneer() {
        return cannoneer;
    }

    public void setCannoneer(Sailor cannoneer) {
        this.cannoneer = cannoneer;
    }

    public Cannon getCannonLeft() {
        return cannonLeft;
    }

    public void setCannonLeft(Cannon cannonLeft) {
        this.cannonLeft = cannonLeft;
    }

    public Cannon getCannonRight() {
        return cannonRight;
    }

    public void setCannonRight(Cannon cannonRight) {
        this.cannonRight = cannonRight;
    }

    public Ship getShipPlacedOn() {
        return shipPlacedOn;
    }
}
