public class Room {

    private String name;
    private String description;
    private boolean visited;

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
        this.visited = false;
    }

    public MoveResult moveNorth()
    {
        if (northLocked) return MoveResult.DoorLocked;

        Room next = north;
        if (next == null) return MoveResult.HitWall;

        next.visited = true;
        return MoveResult.EnteredRoom;
    }

    public MoveResult moveEast() {
        if (eastLocked) return MoveResult.DoorLocked;

        Room next = east;
        if (next == null) return MoveResult.HitWall;

        next.visited = true;
        return MoveResult.EnteredRoom;
    }

    public MoveResult moveSouth() {
        if (southLocked) return MoveResult.DoorLocked;

        Room next = south;
        if (next == null) return MoveResult.HitWall;

        next.visited = true;
        return MoveResult.EnteredRoom;
    }

    public MoveResult moveWest()
    {
        if (westLocked) return MoveResult.DoorLocked;

        Room next = west;
        if (next == null) return MoveResult.HitWall;

        next.visited = true;
        return MoveResult.EnteredRoom;
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

    public void setVisited() {
        visited = true;
    }

    public boolean isVisited() {
        return visited;
    }
}