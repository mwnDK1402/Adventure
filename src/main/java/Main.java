import Room.Room;

public class Main {

    static void main (String[] args) {
        Room room1 = new Room("Room 1", "An empty cave");
        Room room2 = new Room("Room 2", "New room 2");

        Room currentRoom = new Room("","");

        room1.setWest(room2);
        room2.setEast(room1);



        Room next = currentRoom.getNorth();
    }
}