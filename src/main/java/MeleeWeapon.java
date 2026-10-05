public class MeleeWeapon extends Weapon {

    public MeleeWeapon(String shortName, String longName, int damage) {
        super(shortName, longName, damage);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void use() {
    }

    @Override
    public String getCannotUseMessage(){
        return null;
    }

    @Override
    public String getAttackVerb() {
        return "slashed";
    }
}
