package Room;

public class Room {

    private String name;
    private String description;

    private Room north;
    private Room east;
    private Room south;
    private Room west;

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

    public void setNorthSouth(Room northRoom, Room southRoom) {
        setNorth(northRoom);
        northRoom.setSouth(southRoom);

    }

    public void setEastWest(Room eastRoom, Room westRoom) {
        setEast(eastRoom);
        eastRoom.setWest(westRoom);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}