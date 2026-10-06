package model;

import java.util.OptionalInt;

abstract class Weapon extends Item {
    private final WeaponVerb verb;
    private final int damage;

    public Weapon(WeaponVerb verb, String shortName, String longName, int damage) {
        super(shortName, longName);
        this.verb = verb;
        this.damage = damage;
    }

    public final WeaponVerb getVerb() {
        return verb;
    }

    public final int getDamage() {
        return damage;
    }

    public abstract WeaponStatus getStatus();

    public abstract OptionalInt getUsesLeft();

    public abstract void use();
}
