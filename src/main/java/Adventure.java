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

    public MoveResult moveNorth() {
        MoveResult result = currentRoom.moveNorth();
        switch (result) {
            case EnteredRoomFirstTime, EnteredRoomAgain ->
                    currentRoom = currentRoom.getNorth();
        }
        return result;
    }

    public MoveResult moveEast() {
        MoveResult result = currentRoom.moveEast();
        switch (result) {
            case EnteredRoomFirstTime, EnteredRoomAgain ->
                    currentRoom = currentRoom.getEast();
        }
        return result;
    }

    public MoveResult moveSouth() {
        MoveResult result = currentRoom.moveSouth();
        switch (result) {
            case EnteredRoomFirstTime, EnteredRoomAgain ->
                    currentRoom = currentRoom.getSouth();
        }
        return result;
    }

    public MoveResult moveWest() {
        MoveResult result = currentRoom.moveWest();
        switch (result) {
            case EnteredRoomFirstTime, EnteredRoomAgain ->
                    currentRoom = currentRoom.getWest();
        }
        return result;
    }

    public Room getCurrentRoom() {
        return currentRoom;
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
