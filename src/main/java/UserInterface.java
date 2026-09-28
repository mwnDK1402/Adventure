import java.util.Scanner;

public class UserInterface
{
    private Scanner scanner;
    private Adventure adventure;

    private void confirmDirection(String direction)
    {
        if (adventure.go(direction))
        {

            System.out.println("You are in " + adventure.getRoomName());
            System.out.println(adventure.look());
            System.out.println();
        }
        else
        {
            System.out.println();
            System.out.println("You can't go that way!");
            System.out.println();
        }
    }

    public void start()
    {
        this.scanner = new Scanner(System.in);
        this.adventure = new Adventure();

        boolean running = true;

        System.out.println("You are in " + adventure.getRoomName());
        System.out.println(adventure.look());
        System.out.println();

        while(running)
        {
            System.out.print("Where do you want to go?: ");
            String choice = scanner.nextLine();

            switch (choice.trim().toLowerCase())
            {
                case "go north", "north", "go n", "n" -> confirmDirection("north");
                case "go south", "south", "go s", "s" -> confirmDirection("south");
                case "go east", "east", "go e", "e" -> confirmDirection("east");
                case "go west", "west", "go w", "w" -> confirmDirection("west");
                case "look" -> System.out.println(adventure.look());
                case "help" -> System.out.println("""
                        instructions
                        """);
                case "exit" -> running = false;
                //case "inventory" -> System.out.println(
                //case "take" ->;
                //case "drop" ->;
                default -> System.out.println("Invalid command - see help");
            }

        }
        System.out.println("You are now exiting the maze... Goodbye.");
    }

}
