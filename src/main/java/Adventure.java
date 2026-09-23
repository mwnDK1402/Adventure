import Room.Room;

public class Adventure
{

    private Room currentRoom;

    public void generateMaps()
    {

        Room room1 = new Room("Room 1", "An empty cave");
        Room room2 = new Room("Room 2", "New room 2");
        Room room3 = new Room("","");
        Room room4 = new Room("","");
        Room room5 = new Room("","");
        Room room6 = new Room("","");
        Room room7 = new Room("","");
        Room room8 = new Room("","");
        Room room9 = new Room("","");

        currentRoom = room1;

        room1.setWest(room2);
        room1.setSouth(room4);
        room2.setEast(room1);
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

        Room next = currentRoom.getNorth();

        if (next != null) {
            currentRoom = next;
        } else {
            System.out.println("You cannot go this way.");
        }
    }

    public String look()
    {
        return currentRoom.getDescription();
    }

}
