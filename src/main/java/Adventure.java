import Room.Room;

public class Adventure {

    private Room currentRoom;

    public Adventure() {
        Room room1 = new Room("Room 1", "An empty cave");
        Room room2 = new Room("Room 2", "Description 2");
        Room room3 = new Room("Room 3","Description 3");
        Room room4 = new Room("Room 4","Description 4");
        Room room5 = new Room("Room 5","Description 5");
        Room room6 = new Room("Room 6","Description 6");
        Room room7 = new Room("Room 7","Description 7");
        Room room8 = new Room("Room 8","Description 8");
        Room room9 = new Room("Room 9","Description 9");

        currentRoom = room1;
        room1.setVisited();

        room1.setEastWest(room2, room1);
        room2.setEastWest(room3, room2);
        room6.setNorthSouth(room3, room6);
        room9.setNorthSouth(room6, room9);
        room8.setEastWest(room9, room8);
        room7.setEastWest(room8, room7);
        room8.setNorthSouth(room5, room8);
        room7.setNorthSouth(room4, room7);
        room4.setNorthSouth(room1, room4);

        setEastWestLocked(room2, room1, true);
    }

    public String look() {
        return currentRoom.getDescription();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public MoveResult moveNorth()
    {
        if (currentRoom.isNorthLocked()) return MoveResult.DoorLocked;

        Room next = currentRoom.getNorth();
        if (next == null) return MoveResult.HitWall;

        currentRoom = next;
        currentRoom.setVisited();
        return MoveResult.EnteredRoom;
    }

    public MoveResult moveEast()
    {
        if (currentRoom.isEastLocked()) return MoveResult.DoorLocked;

        Room next = currentRoom.getEast();
        if (next == null) return MoveResult.HitWall;

        currentRoom = next;
        currentRoom.setVisited();
        return MoveResult.EnteredRoom;
    }

    public MoveResult moveSouth()
    {
        if (currentRoom.isSouthLocked()) return MoveResult.DoorLocked;

        Room next = currentRoom.getSouth();
        if (next == null) return MoveResult.HitWall;

        currentRoom = next;
        currentRoom.setVisited();
        return MoveResult.EnteredRoom;
    }

    public MoveResult moveWest()
    {
        if (currentRoom.isWestLocked()) return MoveResult.DoorLocked;

        Room next = currentRoom.getWest();
        if (next == null) return MoveResult.HitWall;

        currentRoom = next;
        currentRoom.setVisited();
        return MoveResult.EnteredRoom;
    }

    public void setNorthSouthLocked(Room north, Room south, boolean locked) {
        north.setSouthLocked(locked);
        south.setNorthLocked(locked);
    }

    public void setEastWestLocked(Room east, Room west, boolean locked) {
        east.setWestLocked(locked);
        west.setEastLocked(locked);
    }
}
