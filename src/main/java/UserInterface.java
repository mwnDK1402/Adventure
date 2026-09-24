import Room.Room;

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
            System.out.print("\nInput: ");
            input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "north" -> {
                    Room nextRoom = adventure.moveNorth();

                    if (nextRoom == null) {
                        System.out.println("You cannot go that way.");
                    } else {
                        if(!nextRoom.isVisited()) {
                            System.out.println("You are in " + nextRoom.getName());
                            System.out.println(adventure.look());
                            nextRoom.setVisited();
                        }
                        else {
                            System.out.println("You are in " + nextRoom.getName());
                        }
                    }
                }
                case "east" ->
                {
                    Room nextRoom = adventure.moveEast();

                    if (nextRoom == null)
                    {
                        System.out.println("You cannot go that way.");
                    } else
                    {
                        if (!nextRoom.isVisited())
                        {
                            System.out.println("You are in " + nextRoom.getName());
                            System.out.println(adventure.look());
                            nextRoom.setVisited();
                        } else
                        {
                            System.out.println("You are in " + nextRoom.getName());
                        }
                    }
                }
                case "south" -> {
                    Room nextRoom = adventure.moveSouth();

                    if (nextRoom == null) {
                        System.out.println("You cannot go that way.");
                    } else {
                        if(!nextRoom.isVisited()) {
                            System.out.println("You are in " + nextRoom.getName());
                            System.out.println(adventure.look());
                            nextRoom.setVisited();
                        }
                        else {
                            System.out.println("You are in " + nextRoom.getName());
                        }
                    }
                }
                case "west" -> {
                    Room nextRoom = adventure.moveWest();

                    if (nextRoom == null) {
                        System.out.println("You cannot go that way.");
                    } else {
                        if(!nextRoom.isVisited()) {
                            System.out.println("You are in " + nextRoom.getName());
                            System.out.println(adventure.look());
                            nextRoom.setVisited();
                        }
                        else {
                            System.out.println("You are in " + nextRoom.getName());
                        }
                    }
                }
                case "look" -> {
                    System.out.println(adventure.look());
                }
                case "help" -> {
                    System.out.println("Available commands:");
                    System.out.println("north - Move north");
                    System.out.println("south - Move south");
                    System.out.println("east - Move east");
                    System.out.println("west - Move west");
                    System.out.println("look - Show description of current room");
                    System.out.println("help - Show this list of commands");
                    System.out.println("exit - Exit the game");
                }
            }
        } while (!input.equals("exit")); {
            System.out.println("You're exiting the maze...");
            System.out.println("Farewell");
        }
    }
}
