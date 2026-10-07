public class Enemy {

    private String shortName;
    private String longName;
    private String description;
    private int health;
    private Weapon weapon;
    private Room room;

    public Enemy(String shortName, String longName, String description, int health, Weapon weapon, Room room) {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.health = health;
        this.weapon = weapon;
        this.room = room;
    }

    public void attack(Player player) {

    }

    public void hit(int damage) {
        if (getEnemyHealth() >= 0) {
            this.health -= damage;
        } else {
            room.removeEnemy(this);
        }
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
}
