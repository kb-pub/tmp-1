package app.ship;

import app.AppException;
import app.sailor.Sailor;

public class Ship {
    private final int hullStrength;
    private final int sailsStrength;
    private final Sailor[] deckhands;
    private Sailor navigator;
    private final CannoneerAndCannons[] cannoneerAndCannons;

    public Ship(int hullStrength,
                int sailsStrength,
                int deckhandCount,
                int cannoneerAndCannonsCount) {
        this.hullStrength = hullStrength;
        this.sailsStrength = sailsStrength;
        deckhands = new Sailor[deckhandCount];
        cannoneerAndCannons = new CannoneerAndCannons[cannoneerAndCannonsCount];
    }

    public int getHullStrength() {
        return hullStrength;
    }

    public int getSailsStrength() {
        return sailsStrength;
    }

    public void placeDeckhand(Sailor deckhand, int position) {
        if (position < 0 || position >= deckhands.length) {
            throw new AppException("position out of deckhand array's bounds");
        }
        if (deckhands[position] != null) {
            throw new AppException("a deckhand on position " + position + " already placed");
        }
        if (deckhand.getServesOn() != null) {
            throw new AppException("sailor + " + deckhand + " already serves on ship " + deckhand.getServesOn());
        }
        deckhands[position] = deckhand;
        deckhand.setServesOn(this);
    }

    public void removeDeckhand(int position) {
        if (position < 0 || position >= deckhands.length) {
            throw new AppException("position out of deckhand array's bounds");
        }
        deckhands[position].setServesOn(null);
        deckhands[position] = null;
    }

    public void placeNavigator(Sailor navigator) {
        if (navigator.getServesOn() != null) {
            throw new AppException("sailor + " + navigator + " already serves on ship " + navigator.getServesOn());
        }
        this.navigator = navigator;
        navigator.setServesOn(this);
    }

    public void addCannonOnLeftBoard(Cannon cannon) {

    }
}
