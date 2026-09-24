import Room.Room;

public class Adventure
{

    private Room currentRoom;

    public Adventure()
    {

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

        room1.setEast(room2);
        room1.setSouth(room4);
        room2.setWest(room1);
        room2.setEast(room3);
        room3.setWest(room2);
        room3.setSouth(room6);
        room4.setNorth(room1);
        room4.setSouth(room7);
        room5.setSouth(room8);
        room6.setNorth(room3);
        room6.setSouth(room9);
        room7.setNorth(room4);
        room7.setEast(room8);
        room8.setNorth(room5);
        room8.setWest(room7);
        room8.setEast(room9);
        room9.setNorth(room6);
        room9.setWest(room8);

        setEastWestLocked(room2, room1, true);
    }

    public String look()
    {
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
        return MoveResult.EnteredRoom;
    }

    public MoveResult moveEast()
    {
        if (currentRoom.isEastLocked()) return MoveResult.DoorLocked;

        Room next = currentRoom.getEast();
        if (next == null) return MoveResult.HitWall;

        currentRoom = next;
        return MoveResult.EnteredRoom;
    }

    public MoveResult moveSouth()
    {
        if (currentRoom.isSouthLocked()) return MoveResult.DoorLocked;

        Room next = currentRoom.getSouth();
        if (next == null) return MoveResult.HitWall;

        currentRoom = next;
        return MoveResult.EnteredRoom;
    }

    public MoveResult moveWest()
    {
        if (currentRoom.isWestLocked()) return MoveResult.DoorLocked;

        Room next = currentRoom.getWest();
        if (next == null) return MoveResult.HitWall;

        currentRoom = next;
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
