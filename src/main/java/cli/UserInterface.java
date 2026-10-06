package cli;

import model.*;

import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public final class UserInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final Adventure adventure = new Adventure();

    private void confirmDirection(String direction) {
        if (adventure.go(direction)) {
            System.out.printf("""
                    You are in %s
                    %s
                    
                    """, adventure.getRoomName(), adventure.look());
        } else {
            if (adventure.roomIsLocked()) {
                System.out.println("""
                        
                        The door is locked. You need a key!
                        """);
            } else {
                System.out.println("""
                        
                        You can't go that way!
                        """);
            }
        }
    }

    public void start() {
        boolean running = true;

        System.out.printf("""
                You are in %s
                %s
                """, adventure.getRoomName(), adventure.look());

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
                    System.out.printf("Health: %d. ", adventure.health());
                    if (adventure.health() >= 100) {
                        System.out.println("You are in perfect health!");
                    } else if (adventure.health() >= 50) {
                        System.out.println("You are in good health, but avoid fighting right now.");
                    } else if (adventure.health() >= 25) {
                        System.out.println("You are wounded - find something healthy to eat.");
                    } else if (adventure.health() >= 1) {
                        System.out.println("You are barely alive.");
                    } else {
                        System.out.println("You should be dead.");
                    }
                }
                default -> {
                    if (choice.startsWith("attack ")) {
                        var enemyInput = choice.substring(7);
                        if (enemyInput.isBlank()) {
                            System.out.println("You must specify a target when attacking.");
                            break;
                        }
                        var intendedEnemy = parseEnemyNoun(enemyInput);
                        if (intendedEnemy.isEmpty()) {
                            System.out.printf("%s does not exist here.%n", enemyInput);
                            break;
                        }
                        AttackOutcome outcome = adventure.attack(intendedEnemy.get());

                        switch (outcome) {
                            case AttackOutcome.Attacked attacked -> {
                                System.out.printf("""
                                        You %s the %s for %d damage
                                        Enemy HP: %d
                                        """, resolveWeaponVerb(attacked.verb()), resolveEnemyNoun(attacked.noun()), attacked.damage(), attacked.enemyRemainingHealth());

                                if (attacked.usesLeft().isPresent()) {
                                    System.out.printf("Uses left: %d%n", attacked.usesLeft().getAsInt());
                                }
                            }
                            case AttackOutcome.CannotUse cannotUse ->
                                    System.out.printf(resolveWeaponStatus(cannotUse.status()));
                            case AttackOutcome.Missed ignored -> throw new RuntimeException("Not implemented");
                            case AttackOutcome.NoWeapon ignored -> System.out.println("You have no weapon equipped");
                            case AttackOutcome.NoEnemy ignored ->
                                    System.out.printf("%s does not exist here.%n", resolveEnemyNoun(intendedEnemy.get()));
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

                        switch (outcome) {
                            case EatOutcome.Eaten eaten -> {
                                System.out.printf("You ate the %s. ", intendedItem);
                                if (eaten.healthChange() > 0) {
                                    System.out.println("You feel better.");
                                } else if (eaten.healthChange() < 0) {
                                    System.out.println("You feel worse.");
                                } else {
                                    System.out.println("You feel no different.");
                                }

                                System.out.printf("Health: %d%n", adventure.health());
                            }
                            case EatOutcome.NotFood ignored -> {
                                System.out.printf("You cannot eat the %s%n", intendedItem);
                            }
                            case EatOutcome.NotFound ignored -> {
                                System.out.printf("There is nothing like %s to eat around here%n", intendedItem);
                            }
                        }
                    } else if (choice.startsWith("equip ")) {
                        String intendedWeapon = choice.substring(6);
                        EquipResult result = adventure.equip(intendedWeapon);

                        switch (result) {
                            case NOT_EQUIPMENT -> System.out.printf("You cannot equip the %s%n", intendedWeapon);
                            case NOT_FOUND ->
                                    System.out.printf("There is nothing like %s to equip around here.%n", intendedWeapon);
                            case EQUIPPED -> System.out.printf("You equipped the %s%n", intendedWeapon);
                        }
                    } else {
                        System.out.println("Invalid command - see help");
                    }
                }
            }
        }

        System.out.println("You are now exiting the maze... Goodbye.");
    }

    private static String resolveWeaponVerb(WeaponVerb verb) {
        return switch (verb) {
            case Sword -> "slash";
            case Slingshot, Bow -> "shoot";
        };
    }

    private static Optional<EnemyNoun> parseEnemyNoun(String noun) {
        //noinspection SwitchStatementWithTooFewBranches
        return switch (noun.toLowerCase(Locale.ROOT)) {
            case "skeleton" -> Optional.of(EnemyNoun.Skeleton);
            default -> Optional.empty();
        };
    }

    private static String resolveEnemyNoun(EnemyNoun noun) {
        return switch (noun) {
            case Skeleton -> "skeleton";
        };
    }

    private static String resolveWeaponStatus(UnusableStatus status) {
        return switch (status) {
            case OutOfAmmo -> "You are out of ammunition.%n";
            case OutOfCharges -> "You are out of charges.%n";
        };
    }
}
