import java.util.Scanner;

public class UserInterface
{
    private static String input;
    private static Adventure adventure;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        adventure = new Adventure();

        Room initialRoom = adventure.getCurrentRoom();

        System.out.printf("Welcome to the maze!%n%n");
        System.out.printf("You're currently in %s:%n%s%n", initialRoom.getName(), initialRoom.getDescription());
        System.out.printf("%nWhere do you want to go?%n");

        while (true) {
            System.out.printf("%nInput: ");
            input = scanner.nextLine().toLowerCase();

            if (input.equals("exit")) break;

            switch (input) {
                case "go north", "move north" -> performMove(adventure.moveNorth());
                case "go east", "move east" -> performMove(adventure.moveEast());
                case "go south", "move south" -> performMove(adventure.moveSouth());
                case "go west", "move west" -> performMove(adventure.moveWest());
                case "look" -> {
                    System.out.println(adventure.getCurrentRoom().getDescription());
                    System.out.println(adventure.getCurrentRoom().getDoorDescription());
                }
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
                case "xyzzy" -> {
                    adventure.caseXyzzy();
                    Room room = adventure.getCurrentRoom();
                    System.out.println("You are in " + room.getName());
                }
                default -> System.out.printf("%s is not a valid command.%n", input);
            }
        }

        System.out.println("You're exiting the maze...");
    }

    private static void performMove(MoveResult moveResult) {
        switch (moveResult) {
            case EnteredRoomFirstTime -> {
                Room room = adventure.getCurrentRoom();
                System.out.printf("You are in %s%n%s%n", room.getName(), room.getDescription());
            }
            case EnteredRoomAgain -> {
                Room room = adventure.getCurrentRoom();
                System.out.printf("You are in %s%n", room.getName());
            }
            case HitWall -> System.out.println("You cannot go that way.");
            case DoorLocked -> System.out.println("The door is locked.");
            case null -> System.out.printf("%s is not a valid direction.%n", input);
        }
    }
}
