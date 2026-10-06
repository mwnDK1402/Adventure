import java.util.OptionalInt;

public class RangedWeapon extends Weapon {
    private int ammunition;

    public RangedWeapon(WeaponVerb verb, String shortName, String longName, int damage, int ammunition){
        super(verb, shortName, longName, damage);
        this.ammunition = ammunition;
    }

    @Override
    public WeaponStatus getStatus(){
        return ammunition > 0 ? WeaponStatus.Usable : WeaponStatus.OutOfAmmo;
    }

    @Override
    public OptionalInt getUsesLeft() {
        return OptionalInt.of(ammunition);
    }

    @Override
    public void use(){
        ammunition--;
    }
}
