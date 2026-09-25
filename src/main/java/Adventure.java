public class Adventure {

    private Room currentRoom;
    private Room xyzzyTempRoom;
    private int counter;

    private Room room1;

    public Adventure() {

        // Startområde, evt. simpelt item? (Introduktion)
        room1 = new Room("Room 1", """
        You stand at the entrance of a dark cave.
        Cold, damp air flows out from the darkness ahead.
        Old footprints disappear into the cave, mixed with loose stones and patches of mud.
        A worn path disappears into the cave.""");

        // Eventuelt første lille enemy? (Narrow tunnel)
        Room room2 = new Room("Room 2", """
        A tunnel becomes narrower as you move deeper inside.
        The walls are covered in deep scratches, and loose stones cover the ground.
        You hear something moving somewhere in the darkness ahead, but you cannot tell what it is.""");

        // Items? (Abandoned camp)
        Room room3 = new Room("Room 3","""
        An old camp lies hidden between rocky walls.
        A dusty tent still stands beside a burned-out campfire.
        An empty backpack and several forgotten belongings have been left behind.
        Whoever stayed here did not take everything with them...""");

        // Enemy / udfordring / puzzle? (Deep cavern)
        Room room4 = new Room("Room 4","""
        The tunnel opens into a large cavern.
        The ceiling disappears into the darkness, while water slowly drips from the rocks above.
        A faint metallic sound echoes from somewhere deeper inside.
        When you stop moving, the cavern becomes completely silent.""");

        // Final enemy (Final chamber)
        Room room5 = new Room("Room 5","""
        This is the deepest place you've reached so far.
        The chamber is enormous, and your footsteps echo between the walls.
        In the middle of the floor are tracks left by something large.
        You cannot see where they lead.
        Somewhere in the darkness, you hear slow, heavy breathing and you notice something move.
        To glowing eyes slowly open...""");

        // Puzzle + item? (Underground lake)
        Room room6 = new Room("Room 6","""
        A dark lake fills most of the cavern.
        The water is completely still, despite the constant dripping from the ceiling.
        An old wooden walkway follows the edge of the lake, but parts of it have collapsed.
        Something glimmers faintly beneath the surface...""");

        // Sværere enemies? (Bone chamber)
        Room room7 = new Room("Room 7","""
        The floor is covered in old bones.
        While some are small, others are far to large to be human.
        Deep marks scar the stone floor, as if something heavy has been dragged through the chamber.
        A low growl echoes somewhere beyond the darkness.""");

        // Puzzle / key item? (Ancient ruins)
        Room room8 = new Room("Room 8","""
        The remains of an old ancient stone structure stands in the middle of the room.
        A nearly faded inscription covers one of its walls.
        Among the ruins, you notice fresh marks in the dust, as if something has passed through recently.
        A narrow passage leads deeper into the cave.""");

        // Vigtigt item, måske nødvendigt for at komme ind i room 5? (Forgotten passage)
        Room room9 = new Room("Room 9","""
        The passage ends in a small chamber hidden deep withing the cave.
        At the far end stands an ancient stone door covered in strange symbols.
        In front of it, you find a heavy object resting on a stone pedestal.
        Whatever this item is, it seems to have been placed here for a reason.
        Beyond the door, you can hear a faint, distant sound.""");

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

    public Room caseXyzzy() {
        counter++;

        if (counter % 2 == 1) {
            xyzzyTempRoom = currentRoom;
            return currentRoom = room1;
        }

        else return currentRoom = xyzzyTempRoom;
    }
}
