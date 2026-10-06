import java.util.ArrayList;

public class Room {
    private final String name;
    private final String description;
    private final ArrayList<Item> items;
    private final ArrayList<Enemy> enemies;

    private boolean lock;
    private Room north;
    private Room south;
    private Room east;
    private Room west;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.enemies = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public void setNorth(Room room) {
        this.north = room;
    }

    public void setSouth(Room room) {
        this.south = room;
    }

    public void setWest(Room room) {
        this.west = room;
    }

    public void setEast(Room room) {
        this.east = room;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public Room getNorth() {
        return north;
    }

    public Room getSouth() {
        return south;
    }

    public Room getEast() {
        return east;
    }

    public Room getWest() {
        return west;
    }

    public void setWestEast(Room west, Room east) {
        west.setEast(east);
        east.setWest(west);
    }

    public void setNorthSouth(Room north, Room south) {
        north.setSouth(south);
        south.setNorth(north);
    }

    public boolean getLock() {
        return lock;
    }

    public void setLock(boolean bool) {
        lock = bool;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    // Return a copy so it can't be modified
    public ArrayList<Item> getItems() {
        return new ArrayList<>(items);
    }

    public Item findItem(String shortName) {
        for (Item item : items) {
            if (item.getInventoryText().equalsIgnoreCase((shortName))) {
                return item;
            }
        }
        return null;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public ArrayList<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }

    public Enemy findEnemy(EnemyNoun noun) {
        for (Enemy enemy : enemies) {
            if (enemy.getNoun() == noun) {
                return enemy;
            }
        }
        return null;
    }

    public int getEnemySize() {
        return enemies.size();
    }
}
