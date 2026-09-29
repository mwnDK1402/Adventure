import java.util.ArrayList;

public class Player
{
    private Room currentRoom;
    private boolean isLocked;
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

        if (desiredRoom == null)
        {
            isLocked = false;
            return false;
        }

        if (desiredRoom.getLock())
        {
            isLocked = true;
            return false;
        }

        else
        {
            currentRoom = desiredRoom;
            return true;
        }
    }
    public boolean isLocked()
    {
        return isLocked;
    }

    public boolean removeItemFromInventory(Item item)
    {
        return inventory.remove(item);
    }

    // Return a copy so it can't be modified
    public ArrayList<Item> getInventory()
    {
        return new ArrayList<>(inventory);
    }

    public boolean takeItem(String intendedItem)
    {
        Item item = currentRoom.findItem(intendedItem);

        if(item == null)
        {
            return false;
        }

        inventory.add(item);
        currentRoom.removeItem(item);
        return true;
    }
}
