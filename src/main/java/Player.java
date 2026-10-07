import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private boolean isLocked;
    private final ArrayList<Item> inventory;
    private int health;
    private Weapon equipped;

    public Player(Room currentRoom, int health) {
        this.currentRoom = currentRoom;
        this.inventory = new ArrayList<>();
        this.health = health;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public boolean move(String direction) {
        Room desiredRoom = switch (direction) {
            case "north" -> currentRoom.getNorth();
            case "south" -> currentRoom.getSouth();
            case "east" -> currentRoom.getEast();
            case "west" -> currentRoom.getWest();
            default -> null;
        };

        if (desiredRoom == null) {
            isLocked = false;
            return false;
        }

        if (desiredRoom.getLock()) {
            isLocked = true;
            return false;
        } else {
            currentRoom = desiredRoom;
            return true;
        }
    }

    public boolean isLocked() {
        return isLocked;
    }

    // Return a copy so it can't be modified
    public ArrayList<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public boolean takeItem(String intendedItem) {
        Item item = currentRoom.findItem(intendedItem);

        if (item == null) return false;

        inventory.add(item);
        currentRoom.removeItem(item);
        return true;
    }

    public boolean dropItem(String intendedItem) {
        Item item = findItem(intendedItem);

        if (item == null) return false;
        currentRoom.addItem(item);
        inventory.remove(item);
        if (item instanceof Weapon weapon && equipped == weapon) {
            equipped = null;
        }
        return true;
    }

    private Item findItem(String shortName) {
        for (Item item : inventory) {
            if (item.getInventoryText().equalsIgnoreCase((shortName))) {
                return item;
            }
        }
        return null;
    }

    public int getHealth() {
        return health;
    }

    public EatOutcome eat(String shortName) {
        Item item = findItem(shortName);
        if (item == null) {
            item = currentRoom.findItem(shortName);
        }
        if (item == null) {
            return new EatOutcome(EatResult.NOT_FOUND, shortName, 0);
        }
        if (!(item instanceof Food food)) {
            return new EatOutcome(EatResult.NOT_FOOD, shortName, 0);
        }

        health += food.getHealthPoints();

        if (!inventory.remove(food)) {
            currentRoom.removeItem(food);
        }

        return new EatOutcome(EatResult.EATEN, shortName, food.getHealthPoints());
    }

    public EquipResult equip(String shortName) {
        Item item = findItem(shortName);
        boolean takeItem = false;
        if (item == null) {
            item = currentRoom.findItem(shortName);
            takeItem = item != null;
        }
        if (item == null) {
            return EquipResult.NOT_FOUND;
        }
        if (!(item instanceof Weapon weapon)) {
            return EquipResult.NOT_EQUIPMENT;
        }

        equipped = weapon;
        if (takeItem) {
            inventory.add(item);
            currentRoom.removeItem(item);
        }
        return EquipResult.EQUIPPED;
    }

    public Weapon getEquipped() {
        return equipped;
    }

    public AttackOutcome attack(Enemy enemy) {
        if (equipped == null) {
           return new AttackOutcome(AttackResult.NO_WEAPON, 0, null, null, -1, -1, null);
        }

        if (!equipped.canUse()) {
            return new AttackOutcome(AttackResult.CANNOT_USE, 0, equipped.getCannotUseMessage(), null, equipped.getUsesLeft(), -1, null);
        }

        equipped.use();
        return new AttackOutcome(AttackResult.ATTACKED, equipped.getDamage(), null, equipped.getAttackVerb(), equipped.getUsesLeft(), enemy.getEnemyHealth(), enemy.getLongName());
    }

    public void hit(int damage) {
        this.health -= damage;
    }
}
