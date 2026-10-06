package model;

import java.util.OptionalInt;

class MeleeWeapon extends Weapon {

    public MeleeWeapon(WeaponVerb verb, String shortName, String longName, int damage) {
        super(verb, shortName, longName, damage);
    }

    @Override
    public WeaponStatus getStatus() {
        return WeaponStatus.Usable;
    }

    @Override
    public OptionalInt getUsesLeft() {
        return OptionalInt.empty();
    }

    @Override
    public void use() {
    }
}
