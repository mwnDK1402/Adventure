import Room.Room;

import java.util.Locale;
import java.util.Scanner;

public class UserInterface
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Adventure adventure = new Adventure();

        System.out.println("Welcome to the maze!\n");
        System.out.println("You're currently in " + adventure.getCurrentRoom() + ": " + adventure.look());
        System.out.print("\nWhere do you want to go?");

        String input;

        do {
            System.out.println("\nInput: ");
            input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "north" -> {
                    Room nextRoom = adventure.moveNorth();

                    if (nextRoom == null) {
                        System.out.println("You cannot go that way.");
                    } else {
                        System.out.println("You are in " + nextRoom.getName());
                        System.out.println(adventure.look());
                    }
                }
                case "east" -> {
                    Room nextRoom = adventure.moveEast();

                    if (nextRoom == null) {
                        System.out.println("You cannot go that way.");
                    } else {
                        System.out.println("You are in " + nextRoom.getName());
                        System.out.println(adventure.look());
                    }
                }
                case "south" -> {
                    Room nextRoom = adventure.moveSouth();
                    if (nextRoom == null) {
                        System.out.println("You cannot go that way.");
                    } else {
                        System.out.println("You are in " + nextRoom.getName());
                        System.out.println(adventure.look());
                    }
                }
                case "west" -> {
                    Room nextRoom = adventure.moveWest();
                    if (nextRoom == null) {
                        System.out.println("You cannot go that way.");
                    } else {
                        System.out.println("You are in " + nextRoom.getName());
                        System.out.println(adventure.look());
                    }
                }
            }
        } while (!input.equals("exit")); {
            System.out.println("You're exiting the maze...");
        }
    }
}
