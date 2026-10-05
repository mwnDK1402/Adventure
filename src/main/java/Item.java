public class Item {
    private final String shortName;
    private final String longName;

    public Item(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    public String getInventoryText() {
        return this.shortName;
    }

    public String getLongName() {
        return this.longName;
    }
}
