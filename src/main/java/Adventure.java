import java.util.ArrayList;

public class Adventure
{
    private Player player;
    private Map map;

    public Adventure()
    {
        this.map = new Map();
        map.buildMap();
        this.player = new Player(map.getInitialRoom());
    }

    public String look()
    {
        return player.getCurrentRoom().getDescription() + System.lineSeparator() + this.itemsInRoom();
    }

    public String getRoomName()
    {
        return player.getCurrentRoom().getName();
    }

    public boolean go(String direction)
    {
        return player.move(direction);
    }

    public boolean roomIsLocked(){
        return player.isLocked();
    }

    public String inventory()
    {
        ArrayList<Item> inventory = player.getInventory();
        String items = "Inventory: " + System.lineSeparator();

        if(inventory.isEmpty())
        {
            return "Your inventory is empty";
        }

        for(Item item : inventory)
        {
            items += "- " + item.getShortName() + System.lineSeparator();
        }
        return items;
    }

    public String itemsInRoom()
    {
        // Low cohesion between Player and the items of the room, why we allow them to communicate even though strangers.
        ArrayList<Item> inventory = player.getCurrentRoom().getItems();

        if(!inventory.isEmpty())
        {
            String items = "Here you see: ";

            for(int i = 0; i < inventory.size(); i++)
            {
                items += inventory.get(i).getLongName().toLowerCase();

                if (i < inventory.size() - 1)
                {
                    items += ", ";
                }
            }
            return items;
        }
        else
        {
            return "";
        }
    }

    public boolean take(String shortName)
    {
        return player.takeItem(shortName);
    }

    public boolean drop(String shortName) {
        return player.dropItem(shortName);
    }
}
