package app.battle;

import app.sailor.Sailor;

import java.util.Map;

public class ShipBattleState {
    private int hullMaxStrength, hullCurrentStrength;
    private int sailsMaxStrength, sailsCurrentStrength;
    private Map<Sailor, Boolean> woundedSailors;
}
