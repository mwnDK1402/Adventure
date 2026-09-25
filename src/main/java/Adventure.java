import Room.Room;

public class Adventure {

    private Room currentRoom;

    public Adventure() {

        // Startområde, evt. simpelt item
        Room room1 = new Room("Room 1", """
        You stand at the entrance of a dark cave.
        Cold, damp air flows out from the darkness ahead.
        Old footprints disappear into the cave, mixed with loose stones and patches of mud.
        A worn path disappears into the cave.""");

        // Eventuelt første enemy?
        Room room2 = new Room("Room 2", """
        A tunnel becomes narrower as you move deeper inside.
        The walls are covered in deep scratches, and loose stones cover the ground.
        You hear something moving somewhere in the darkness ahead, but you cannot tell what it is.""");

        // Items?
        Room room3 = new Room("Room 3","""
        An old camp lies hidden between rocky walls.
        A dusty tent still stands beside a burned-out campfire.
        An empty backpack and several forgotten belongings have been left behind.
        Whoever stayed here did not take everything with them...""");

        // Enemy / exploration?
        Room room4 = new Room("Room 4","""
        The tunnel opens into a large cavern.
        The ceiling disappears into the darkness, while water slowly drips from the rocks above.
        A faint metallic sound echoes from somewhere deeper inside.
        When you stop moving, the cavern becomes completely silent.""");

        // Vigtigt item, måske mere story?
        Room room5 = new Room("Room 5","""
        A massive stone chamber lies hidden beneath the cave.
        Ancient symbols cover the walls, and a large stone slab stands in the middle of the room.
        There are strange markings around the base, as if someone has tried to move it.
        The air feels usually heavy in here.""");

        // Puzzle / item / enemy?
        Room room6 = new Room("Room 6","""
        A dark lake fills most of the cavern.
        The water is completely still, despite the constant dripping from the ceiling.
        An old wooden walkway follows the edge of the lake, but parts of it have collapsed.
        Something glimmers faintly beneath the surface...""");

        // Enemies
        Room room7 = new Room("Room 7","""
        The floor is covered in old bones.
        While some are small, others are far to large to be human.
        Deep marks scar the stone floor, as if something heavy has been dragged through the chamber.
        A low growl echoes somewhere beyond the darkness.""");

        // Puzzle / key item?
        Room room8 = new Room("Room 8","""
        The remains of an old ancient stone structure stands in the middle of the room.
        A nearly faded inscription covers one of its walls.
        Among the ruins, you notice fresh marks in the dust, as if something has passed through recently. 
        A narrow passage leads deeper into the cave.""");

        // Større enemy/boss/final encounter?
        Room room9 = new Room("Room 9", """
                This is the deepest place you've reached so far.
                The chamber is enormous, and your footsteps echo between the walls. 
                In the middle of the floor are tracks left by something large. 
                You cannot see where they lead.
                Somewhere in the darkness, you hear slow, heavy breathing...""");

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
