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

    public EnemyAttackOutcome attack(Player player) {
        player.hit(weapon.getDamage());
        return new EnemyAttackOutcome(weapon.getDamage(), player.getHealth(), weapon.getAttackVerb(), longName);
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
