import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private Adventure adventure;
    private boolean running;

    private void confirmDirection(String direction) {
        if (adventure.go(direction)) {
            System.out.println("You are in " + adventure.getRoomName());
            System.out.println(adventure.look());
            System.out.println();
        } else {
            if (adventure.roomIsLocked()) {
                System.out.println();
                if (adventure.playerHasKey()) {
                    System.out.println("The door is locked, but you have a key.");
                    if (askYesNo("Do you want to unlock this door? (Y/N)")) {
                        adventure.unlock(direction);
                        System.out.println("You unlock the door.");
                        System.out.println();
                        confirmDirection(direction);
                    } else {
                        System.out.println("You leave the door locked.");
                        System.out.println();
                    }
                } else {
                    System.out.println("The door is locked. You need a key!");
                    System.out.println();
                }
            } else {
                System.out.println();
                System.out.println("You can't go that way!");
                System.out.println();
            }
        }
    }

    private boolean askYesNo(String question) {
        while (true) {
            System.out.print(question + " ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no")) {
                return false;
            }
            System.out.println("Please answer Y or N.");
        }
    }

    public void start() {
        this.scanner = new Scanner(System.in);
        this.adventure = new Adventure();

        running = true;

        System.out.println("You are in " + adventure.getRoomName());
        System.out.println(adventure.look());
        System.out.println();

        while (running) {
            System.out.print("Input (use 'help' for commands): ");
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
                    if (choice.startsWith("attack ")) {
                        String intendedEnemy = choice.substring(7);
                        CombatOutcome outcome = adventure.attack(intendedEnemy);

                        switch (outcome.getPlayer().getResult()) {
                            case NO_WEAPON -> {
                                System.out.println("You have no weapon equipped");
                                printEnemyAttack(outcome.getEnemy());
                                checkGameOver(outcome.getEnemy());
                            }
                            case CANNOT_USE -> {
                                System.out.println(outcome.getPlayer().getMessage());
                                printEnemyAttack(outcome.getEnemy());
                                checkGameOver(outcome.getEnemy());
                            }
                            case NO_ENEMY -> System.out.println("There is no " + intendedEnemy + " in this room");
                            case ATTACKED -> {
                                if (outcome.getPlayer().getEnemyHealthOutcome() > 0) {
                                    System.out.println();
                                    System.out.println("You " + outcome.getPlayer().getAttackVerb() + " the enemy for " + outcome.getPlayer().getPlayerDamageDealt() + " damage");
                                    System.out.println("The enemy now has " + outcome.getPlayer().getEnemyHealthOutcome() + " HP");
                                    System.out.println();
                                    if (outcome.getPlayer().getEnemyHealthOutcome() > 0) {
                                        printEnemyAttack(outcome.getEnemy());
                                    }
                                    else {
                                        System.out.println("You killed the " + outcome.getPlayer().getEnemyLongName());
                                        System.out.println();
                                    }

                                } else if (outcome.getPlayer().getEnemyHealthOutcome() <= 0) {
                                    System.out.println(outcome.getPlayer().getEnemyLongName() + " has been slain.");
                                }

                                if (outcome.getPlayer().getUsesLeft() > 0){
                                    System.out.println("Uses left: " + outcome.getPlayer().getUsesLeft());
                                }
                                else {
                                    System.out.println("Your weapon is out of uses");
                                }
                                checkGameOver(outcome.getEnemy());
                            }
                        }
                    } else if (choice.startsWith("take ")) {
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

                                System.out.println("You now have " + outcome.getHealthPostFood() + " HP");
                            }
                        }
                        if (outcome.getEnemyOutcome() != null) {
                            printEnemyAttack(outcome.getEnemyOutcome());
                            checkGameOver(outcome.getEnemyOutcome());
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

    private void checkGameOver(EnemyAttackOutcome outcome) {
        if (outcome.getPlayerHealth() < 0) {
            System.out.println("Game over");
            running = false;
        }
    }

    private static void printEnemyAttack(EnemyAttackOutcome outcome) {
        System.out.println();
        System.out.println(outcome.getEnemyLongName() + " " + outcome.getEnemyAttackVerb() + " you for " + outcome.getEnemyDamageDealt() + " HP");
        System.out.println("You now have " + outcome.getPlayerHealth() + " HP");
        System.out.println();
    }

}
