public class Enemy {

    private String shortName;
    private String longName;
    private String description;
    private int health;
    private Weapon weapon;
    private Room room;
    //private boolean isAlive;

    public Enemy(String shortName, String longName, String description, int health, Weapon weapon, Room room) {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.health = health;
        this.weapon = weapon;
        this.room = room;
    }

    public void attack(Player player, AttackOutcome outcome) {
        outcome.setEnemyAttackVerb(weapon.getAttackVerb());
        outcome.setEnemyDamageDealt(weapon.getDamage());
        player.hit(weapon.getDamage());
        outcome.setPlayerHealth(player.getHealth());
    }

    public void hit(int damage) {
        if (!isAlive()) {
            return;
        }
        this.health -= damage;

        if (!isAlive()) {
            room.addItem(getWeapon());
            room.removeEnemy(this);
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public String getLongName() {
        return longName;
    }

    public int getEnemyHealth() {
        return health;
    }

    public Room getEnemyRoom() {
        return room;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
