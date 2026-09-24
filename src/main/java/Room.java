import java.util.ArrayList;

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

    private boolean northDoorTried;
    private boolean eastDoorTried;
    private boolean southDoorTried;
    private boolean westDoorTried;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.visited = false;
    }

    public MoveResult moveNorth() {
        northDoorTried = true;
        if (northLocked) return MoveResult.DoorLocked;

        Room next = north;
        if (next == null) return MoveResult.HitWall;

        MoveResult result = next.visited
                ? MoveResult.EnteredRoomAgain
                : MoveResult.EnteredRoomFirstTime;
        next.visited = true;
        next.southDoorTried = true;
        return result;
    }

    public MoveResult moveEast() {
        eastDoorTried = true;
        if (eastLocked) return MoveResult.DoorLocked;

        Room next = east;
        if (next == null) return MoveResult.HitWall;

        MoveResult result = next.visited
                ? MoveResult.EnteredRoomAgain
                : MoveResult.EnteredRoomFirstTime;
        next.visited = true;
        next.westDoorTried = true;
        return result;
    }

    public MoveResult moveSouth() {
        southDoorTried = true;
        if (southLocked) return MoveResult.DoorLocked;

        Room next = south;
        if (next == null) return MoveResult.HitWall;

        MoveResult result = next.visited
                ? MoveResult.EnteredRoomAgain
                : MoveResult.EnteredRoomFirstTime;
        next.visited = true;
        next.northDoorTried = true;
        return result;
    }

    public MoveResult moveWest() {
        westDoorTried = true;
        if (westLocked) return MoveResult.DoorLocked;

        Room next = west;
        if (next == null) return MoveResult.HitWall;

        MoveResult result = next.visited
                ? MoveResult.EnteredRoomAgain
                : MoveResult.EnteredRoomFirstTime;
        next.visited = true;
        next.eastDoorTried = true;
        return result;
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

    public String getDoorDescription() {
        ArrayList<String> triedDoors = getTriedDoors();

        return switch (triedDoors.size()) {
            case 0 -> ""; // This should never happen: every Room we look at has been entered
            // North
            case 1 -> "There is a door to the " +
                    triedDoors.getFirst() +
                    ".";
            // North and West
            // North, South, and West
            // North, East, South, and West
            default -> {
                var sb = new StringBuilder(52) // Max length, no resizing
                        .append("There are doors to the ")
                        .append(triedDoors.getFirst());
                for (int i = 1; i < triedDoors.size() - 1; i++) {
                    sb
                            .append(", ")
                            .append(triedDoors.get(i));
                }
                if (triedDoors.size() > 2) sb.append(",");
                sb
                        .append(" and ")
                        .append(triedDoors.getLast())
                        .append(".");
                yield sb.toString();
            }
        };
    }

    private ArrayList<String> getTriedDoors() {
        boolean[] tried = {
                northDoorTried && north != null,
                southDoorTried && south != null,
                eastDoorTried && east != null,
                westDoorTried && west != null
        };

        ArrayList<String> triedDoors = new ArrayList<>(4);
        for (int i = 0; i < tried.length; i++) {
            if (tried[i]) {
                triedDoors.add(switch (i) {
                    case 0 -> "North";
                    case 1 -> "South";
                    case 2 -> "East";
                    case 3 -> "West";
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                });
            }
        }
        return triedDoors;
    }

    public void setVisited() {
        visited = true;
    }
}