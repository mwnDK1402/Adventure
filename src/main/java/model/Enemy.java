package model;

class Enemy {
    private EnemyNoun noun;
    private String longName;
    private String description;
    private int health;
    private Weapon weapon;
    private Room room;

    public Enemy(EnemyNoun noun, String longName, String description, int health, Weapon weapon) {
        this.noun = noun;
        this.longName = longName;
        this.description = description;
        this.health = health;
        this.weapon = weapon;
    }

    public EnemyNoun getNoun() {
        return noun;
    }

    public String getLongName() {
        return longName;
    }

    public String getDescription() {
        return description;
    }

    public int getEnemyHealth() {
        return health;
    }

    public void attack(Player player) {}

    public void hit(int damage) {
        this.health -= damage;
    }
}
