package app.sailor;

import app.ship.Ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Sailor {
    private final String name;
    private String avatar;
    private List<Profession> professions;
    private Ship servesOn;

    public Sailor(String name, String avatar, Profession ... professions) {
        this.name = name;
        this.avatar = avatar;
        this.professions = new ArrayList<>(Arrays.asList(professions));
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Ship getServesOn() {
        return servesOn;
    }

    public void setServesOn(Ship servesOn) {
        this.servesOn = servesOn;
    }


}
