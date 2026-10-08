import java.util.ArrayList;

public class Adventure {
    private final Player player;
    private final Map map;

    public Adventure() {
        this.map = new Map();
        map.buildMap();
        this.player = new Player(map.getInitialRoom(), 100);
    }

    public String look() {
        return player.getCurrentRoom().getDescription() + System.lineSeparator() + this.itemsInRoom() + this.enemiesInRoom();
    }

    public String getRoomName() {
        return player.getCurrentRoom().getName();
    }

    public boolean go(String direction) {
        return player.move(direction);
    }

    public boolean roomIsLocked() {
        return player.isLocked();
    }

    public String inventory() {
        ArrayList<Item> inventory = player.getInventory();
        String items = "Inventory: " + System.lineSeparator();

        if (inventory.isEmpty()) {
            return "Your inventory is empty";
        }

        for (Item item : inventory) {
            items += "- " + item.getInventoryText();
            if (item instanceof Weapon weapon && player.getEquipped() == weapon) {
                items += " (equipped)";
            }
            items += System.lineSeparator();
        }
        return items;
    }

    private String enemiesInRoom() {
        String enemiesString = "Here lurks: ";
        Room room = player.getCurrentRoom();
        ArrayList<Enemy> enemies = room.getEnemies();

        if (!enemies.isEmpty()) {
            for (int i = 0; i < room.getEnemySize(); i++) {
                enemiesString += enemies.get(i).getLongName().toLowerCase();

                if (i < enemies.size() - 1) {
                    enemiesString += ", ";
                }
            }
            for (Enemy enemy : enemies) {
                enemiesString += System.lineSeparator() + enemy.getDescription();
            }
            return enemiesString;
        } else {
            return "";
        }
    }

    private String itemsInRoom() {
        // Low cohesion between Player and the items of the room, why we allow them to communicate even though strangers.
        ArrayList<Item> inventory = player.getCurrentRoom().getItems();

        if (!inventory.isEmpty()) {
            String items = "Here you see: ";

            for (int i = 0; i < inventory.size(); i++) {
                items += inventory.get(i).getLongName().toLowerCase();

                if (i < inventory.size() - 1) {
                    items += ", ";
                }
            }
            return items;
        } else {
            return "";
        }
    }

    public boolean take(String shortName) {
        return player.takeItem(shortName);
    }

    public boolean drop(String shortName) {
        return player.dropItem(shortName);
    }

    public int health() {
        return player.getHealth();
    }

    public EatOutcome eat(String shortName) {
        return player.eat(shortName);
    }

    public EquipResult equip(String shortName) {
        return player.equip(shortName);
    }

    public CombatOutcome attack(String shortName) {
        Enemy enemy = player.getCurrentRoom().findEnemy(shortName);
        if (enemy == null) {
            return new CombatOutcome(new PlayerAttackOutcome(AttackResult.NO_ENEMY, 0, null, null, -1, -1, null), null);
        }
        PlayerAttackOutcome outcome = player.attack(enemy);
        EnemyAttackOutcome enemyOutcome = null;

        if (enemy.isAlive()) {
            enemyOutcome = enemy.attack(player);
        }

        return new CombatOutcome(outcome, enemyOutcome);
    }
}
