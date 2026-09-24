import Room.Room;

import java.util.Scanner;

public class UserInterface
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Adventure adventure = new Adventure();

        System.out.println("Welcome to the maze!\n");
        System.out.println("You're currently in " + adventure.getCurrentRoom().getName() + ": " + adventure.look());
        System.out.print("\nWhere do you want to go?");

        String input;

        while (true) {
            System.out.print("\nInput: ");
            input = scanner.nextLine().toLowerCase();

            if (input.equals("exit")) break;

            MoveResult moveResult = switch (input) {
                case "north" -> adventure.moveNorth();
                case "east" -> adventure.moveEast();
                case "south" -> adventure.moveSouth();
                case "west" -> adventure.moveWest();
                default -> null;
            };

            switch (moveResult) {
                case EnteredRoom -> {
                    Room room = adventure.getCurrentRoom();
                    System.out.println("You are in " + room.getName());
                    if (!room.isVisited()) {
                        System.out.println(room.getDescription());
                        room.setVisited();
                    }
                }
                case HitWall -> System.out.println("You cannot go that way.");
                case DoorLocked -> System.out.println("The door is locked.");
                case null -> System.out.printf("%s is not a valid direction", input);
            }
        }

        System.out.println("You're exiting the maze...");
    }
}
