import java.util.ArrayList;

public class Player
{
    private Room currentRoom;
    private ArrayList<Item> inventory;

    public Player(Room currentRoom)
    {
        this.currentRoom = currentRoom;
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

    public ArrayList<Item> getItemsInventory()
    {
        return inventory;
    }
}
