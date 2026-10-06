package model;

import java.util.ArrayList;

public final class Adventure {
    private final Player player;

    public Adventure() {
        Map map = new Map();
        map.buildMap();
        this.player = new Player(map.getInitialRoom(), 100);
    }

    public java.util.ArrayList<Item> getInventory() {
        return player.getInventory();
    }

    public String look() {
        return String.format("%s%n%s%s", player.getCurrentRoom().getDescription(), this.itemsInRoom(), this.enemiesInRoom());
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

        if (inventory.isEmpty()) return "Your inventory is empty";

        StringBuilder items = new StringBuilder(128);
        items.append("Inventory: ").append(System.lineSeparator());
        for (Item item : inventory) {
            items.append("- ").append(item.getInventoryText());
            if (item instanceof Weapon weapon && player.getEquipped() == weapon){
                items.append(" (equipped)");
            }
            items.append(System.lineSeparator());
        }
        return items.toString();
    }

    private String enemiesInRoom() {
        StringBuilder enemiesString = new StringBuilder(128);
        enemiesString.append("Here lurks: ");
        Room room = player.getCurrentRoom();
        ArrayList<Enemy> enemies = room.getEnemies();

        if (enemies.isEmpty()) return "";

        for (int i = 0; i < room.getEnemySize(); i++) {
            enemiesString.append(enemies.get(i).getLongName().toLowerCase());

            if (i < enemies.size() - 1) {
                enemiesString.append(", ");
            }
        }
        return enemiesString.toString();
    }

    private String itemsInRoom() {
        // Low cohesion between Player and the items of the room, why we allow them to communicate even though strangers.
        ArrayList<Item> inventory = player.getCurrentRoom().getItems();

        if (inventory.isEmpty()) return "";

        StringBuilder items = new StringBuilder(128);
        items.append("Here you see: ");

        for (int i = 0; i < inventory.size(); i++) {
            items.append(inventory.get(i).getLongName().toLowerCase());

            if (i < inventory.size() - 1) {
                items.append(", ");
            }
        }
        return items.toString();
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

    public AttackOutcome attack(EnemyNoun noun) {
        Enemy enemy = player.getCurrentRoom().findEnemy(noun);
        if (enemy == null) return new AttackOutcome.NoEnemy();
        return player.attack(enemy);
    }
}
