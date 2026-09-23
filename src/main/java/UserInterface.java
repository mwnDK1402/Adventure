import java.util.Scanner;

public class UserInterface
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Adventure adventure = new Adventure();

        System.out.println("Welcome");
        System.out.println();
        System.out.println(adventure.look());
        System.out.println();
        System.out.print("Where do you want to go?: ");
        String input = scanner.nextLine();
    }
}
