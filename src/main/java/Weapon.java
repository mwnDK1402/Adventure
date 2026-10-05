public abstract class Weapon extends Item {
    public abstract boolean canUse();

    public abstract void use();

    public abstract String getCannotUseMessage();

    public abstract String getAttackVerb();

    public abstract int getUsesLeft();

    private int damage;

    public Weapon(String shortName, String longName, int damage) {
        super(shortName, longName);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
