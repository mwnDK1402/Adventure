public class Item
{
    private String shortName;
    private String longName;

    public Item(String shortName, String longName)
    {
        this.shortName = shortName;
        this.longName = longName;
    }

    Item item1 = new Item("Lamp", "A shiny brass lamp");
    Item item2 = new Item("Key", "An old rusty key");

    public String getShortName()
    {
        return this.shortName;
    }

    public String getLongName()
    {
        return this.longName;
    }
}
