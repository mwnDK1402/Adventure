package Room;

public class Room {

    private String name;
    private String description;

    private Room north;
    private Room east;
    private Room south;
    private Room west;

    private boolean northLocked;
    private boolean eastLocked;
    private boolean southLocked;
    private boolean westLocked;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Room getNorth() {
        return north;
    }

    public Room getEast() {
        return east;
    }

    public Room getSouth() {
        return south;
    }

    public Room getWest() {
        return west;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public boolean isNorthLocked() {
        return northLocked;
    }

    public boolean isEastLocked() {
        return eastLocked;
    }

    public boolean isSouthLocked() {
        return southLocked;
    }

    public boolean isWestLocked() {
        return westLocked;
    }

    public void setNorthLocked(boolean northLocked) {
        this.northLocked = northLocked;
    }

    public void setEastLocked(boolean eastLocked) {
        this.eastLocked = eastLocked;
    }

    public void setSouthLocked(boolean southLocked) {
        this.southLocked = southLocked;
    }

    public void setWestLocked(boolean westLocked) {
        this.westLocked = westLocked;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}