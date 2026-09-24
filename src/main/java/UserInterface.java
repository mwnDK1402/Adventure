import java.util.Scanner;

public class UserInterface
{
    private static String input;
    private static Adventure adventure;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        adventure = new Adventure();

        System.out.println("Welcome to the maze!\n");
        System.out.println("You're currently in " + adventure.getCurrentRoom().getName() + ": " + adventure.look());
        System.out.print("\nWhere do you want to go?");

        while (true) {
            System.out.print("\nInput: ");
            input = scanner.nextLine().toLowerCase();

            if (input.equals("exit")) break;

            switch (input) {
                case "go north", "move north" -> performMove(adventure.moveNorth());
                case "go east", "move east" -> performMove(adventure.moveEast());
                case "go south", "move south" -> performMove(adventure.moveSouth());
                case "go west", "move west" -> performMove(adventure.moveWest());
                case "look" -> System.out.println(adventure.look());
                case "help" -> {
                    System.out.println("Available commands:");
                    System.out.println("go north - Move north");
                    System.out.println("go south - Move south");
                    System.out.println("go east - Move east");
                    System.out.println("go west - Move west");
                    System.out.println("look - Show description of current room");
                    System.out.println("help - Show this list of commands");
                    System.out.println("exit - Exit the game");
                }
                default -> System.out.printf("%s is not a valid command.", input);
            }
        }

        System.out.println("You're exiting the maze...");
    }

    private static void performMove(MoveResult moveResult) {
        switch (moveResult) {
            case EnteredRoom -> {
                Room room = adventure.getCurrentRoom();
                System.out.println("You are in " + room.getName());
                if (!room.isVisited()) {
                    System.out.println(room.getDescription());
                }
            }
            case HitWall -> System.out.println("You cannot go that way.");
            case DoorLocked -> System.out.println("The door is locked.");
            case null -> System.out.printf("%s is not a valid direction", input);
        }
    }
}
