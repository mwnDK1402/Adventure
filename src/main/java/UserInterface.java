import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private Adventure adventure;

    private void confirmDirection(String direction) {
        if (adventure.go(direction)) {
            System.out.println("You are in " + adventure.getRoomName());
            System.out.println(adventure.look());
            System.out.println();
        } else {
            if (adventure.roomIsLocked()) {
                System.out.println();
                System.out.println("The door is locked. You need a key!");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("You can't go that way!");
                System.out.println();
            }
        }
    }

    public void start() {
        this.scanner = new Scanner(System.in);
        this.adventure = new Adventure();

        boolean running = true;

        System.out.println("You are in " + adventure.getRoomName());
        System.out.println(adventure.look());
        System.out.println();

        while (running) {
            System.out.print("Where do you want to go?: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "go north", "north", "go n", "n" -> confirmDirection("north");
                case "go south", "south", "go s", "s" -> confirmDirection("south");
                case "go east", "east", "go e", "e" -> confirmDirection("east");
                case "go west", "west", "go w", "w" -> confirmDirection("west");
                case "look" -> System.out.println(adventure.look());
                case "help" -> System.out.println("""
                        instructions
                        """);
                case "exit" -> running = false;
                case "inventory" -> System.out.println(adventure.inventory());
                case "health" -> {
                    if (adventure.health() >= 100) {
                        System.out.println("Health: " + adventure.health() + ". You are in perfect health!");
                    } else if (adventure.health() >= 50) {
                        System.out.println("Health: " + adventure.health() + ". You are in good health, but avoid fighting right now.");
                    } else if (adventure.health() >= 25) {
                        System.out.println("Health: " + adventure.health() + ". You are wounded - find something healthy to eat.");
                    } else if (adventure.health() >= 1) {
                        System.out.println("Health: " + adventure.health() + ". You are barely alive.");
                    } else {
                        System.out.println("Health: " + adventure.health() + ". You should be dead.");
                    }
                }
                default -> {
                    if (choice.startsWith("take ")) {
                        String intendedItem = choice.substring(5);
                        if (adventure.take(intendedItem)) {
                            System.out.println("Item added to inventory");
                        } else {
                            System.out.println("No such item in current room");
                        }
                    } else if (choice.startsWith("drop ")) {
                        String intendedItem = choice.substring(5);
                        if (adventure.drop(intendedItem)) {
                            System.out.println("Item removed from inventory");
                        } else {
                            System.out.println("No such item in your inventory");
                        }
                    } else if (choice.startsWith("eat ")) {
                        String intendedItem = choice.substring(4);
                        EatOutcome outcome = adventure.eat(intendedItem);

                        switch (outcome.getResult()) {
                            case NOT_FOOD -> System.out.println("You cannot eat the " + intendedItem);
                            case NOT_FOUND ->
                                    System.out.println("There is nothing like " + intendedItem + " to eat around here");
                            case EATEN -> {
                                if (outcome.getHealthChange() > 0) {
                                    System.out.println("You ate the " + intendedItem + ". You feel better.");
                                } else if (outcome.getHealthChange() < 0) {
                                    System.out.println("You ate the " + intendedItem + ". You feel worse.");
                                } else {
                                    System.out.println("You ate the " + intendedItem + ". You feel no different.");
                                }

                                System.out.println("Health: " + adventure.health());
                            }
                        }
                    } else if (choice.startsWith("equip ")) {
                        String intendedWeapon = choice.substring(6);
                        EquipResult result = adventure.equip(intendedWeapon);

                        switch (result) {
                            case NOT_EQUIPMENT -> System.out.println("You cannot equip the " + intendedWeapon);
                            case NOT_FOUND ->
                                    System.out.println("There is nothing like " + intendedWeapon + " to equip around here.");
                            case EQUIPPED -> System.out.println("You equipped the " + intendedWeapon);
                        }
                    } else {
                        System.out.println("Invalid command - see help");
                    }
                }
            }
        }

        System.out.println("You are now exiting the maze... Goodbye.");
    }

}
