import java.util.ArrayList;

public class Player
{
    private Room currentRoom;
    private ArrayList<Item> inventory;

    public Player(Room currentRoom)
    {
        this.currentRoom = currentRoom;
        this.inventory = new ArrayList<>();
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public boolean move(String direction)
    {
        Room desiredRoom = switch (direction)
        {
            case "north" -> currentRoom.getNorth();
            case "south" -> currentRoom.getSouth();
            case "east" -> currentRoom.getEast();
            case "west" -> currentRoom.getWest();
            default -> null;
        };

        if (desiredRoom != null)
        {
            currentRoom = desiredRoom;
            return true;
        } else
        {
            return false;
        }
    }

    public void addItemToInventory(Item item)
    {
        inventory.add(item);
    }

    public boolean removeItemToInventory(Item item)
    {
        return inventory.remove(item);
    }

    // Return a copy so it can't be modified
    public ArrayList<Item> getInventory()
    {
        return new ArrayList<>(inventory);
    }
}
