import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private Adventure adventure;
    private boolean running;

    public void start() {
        this.scanner = new Scanner(System.in);
        this.adventure = new Adventure();

        running = true;

        System.out.println("You are in " + adventure.getRoomName());
        System.out.println(adventure.look());

        while (running) {
            System.out.print("Input (use 'help' for commands): ");
            InputLine input = new InputLine(scanner.nextLine());
            Command command = Command.validate(input.getCommand());

            System.out.println();

            switch (command) {
                case Go -> {
                    Direction direction = Direction.validate(input.getArg());
                    if (direction != null) {
                        handleGo(direction);
                    } else {
                        handleInvalid();
                    }
                }
                case Look -> handleLook();
                case Help -> handleHelp();
                case Exit -> handleExit();
                case Inventory -> handleInventory();
                case Health -> handleHealth();
                case Attack -> handleAttack(input.getArg());
                case Eat -> handleEat(input.getArg());
                case Drop -> handleDrop(input.getArg());
                case Take -> handleTake(input.getArg());
                case Equip -> handleEquip(input.getArg());
                case Invalid -> handleInvalid();
            }

            System.out.println();
        }

        System.out.println("You are now exiting the maze... Goodbye.");
    }

    private static void handleInvalid() {
        System.out.println("Invalid command - see help");
    }

    private void handleHelp() {
        System.out.print("""
                Commands:
                 go <direction>   - move north, south, east, or west (also: n, s, e, w)
                 look             - describe the current room
                 inventory        - list what you are carrying
                 health           - show your current HP
                 take <item>      - pick up an item from the room
                 drop <item>      - put an item down
                 equip <weapon>   - equip a weapon from your inventory or the room
                 attack [enemy]   - attack an enemy with equipped weapon (omit the name to hit the first enemy here)
                 eat <item>       - eat something
                 help             - show this list
                 exit             - quit the game
                """);
    }

    private void handleExit() {
        running = false;
    }

    private void handleInventory() {
        System.out.print(adventure.inventory());
    }

    private void handleHealth() {
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

    private void handleEquip(String intendedWeapon) {
        EquipResult result = adventure.equip(intendedWeapon);

        switch (result) {
            case NOT_EQUIPMENT -> System.out.println("You cannot equip the " + intendedWeapon);
            case NOT_FOUND ->
                    System.out.println("There is nothing like " + intendedWeapon + " to equip around here.");
            case EQUIPPED -> System.out.println("You equipped the " + intendedWeapon);
        }
    }

    private void handleTake(String intendedItem) {
        if (adventure.take(intendedItem)) {
            System.out.println("Item added to inventory");
        } else {
            System.out.println("No such item in current room");
        }
    }

    private void handleDrop(String intendedItem) {
        if (adventure.drop(intendedItem)) {
            System.out.println("Item removed from inventory");
        } else {
            System.out.println("No such item in your inventory");
        }
    }

    private void handleEat(String intendedFood) {
        if (intendedFood.isBlank()) {
            System.out.println("You can't eat nothing!");
            return;
        }

        EatOutcome outcome = adventure.eat(intendedFood);

        switch (outcome.getResult()) {
            case NOT_FOOD -> System.out.println("You cannot eat the " + intendedFood);
            case NOT_FOUND ->
                    System.out.println("There is nothing like " + intendedFood + " to eat around here");
            case EATEN -> {
                if (outcome.getHealthChange() > 0) {
                    System.out.println("You ate the " + intendedFood + ". You feel better.");
                } else if (outcome.getHealthChange() < 0) {
                    System.out.println("You ate the " + intendedFood + ". You feel worse.");
                } else {
                    System.out.println("You ate the " + intendedFood + ". You feel no different.");
                }

                System.out.println("You now have " + outcome.getHealthPostFood() + " HP");
            }
        }
        if (outcome.getEnemyOutcome() != null) {
            printEnemyAttack(outcome.getEnemyOutcome());
        }

        if (!outcome.isPlayerAlive()) {
            handleGameOver();
        }
    }

    private void handleAttack(String intendedEnemy) {
        CombatOutcome outcome = adventure.attack(intendedEnemy);
        PlayerAttackOutcome pOutcome = outcome.getPlayer();
        EnemyAttackOutcome eOutcome = outcome.getEnemy();

        switch (pOutcome.getResult()) {
            case NO_ENEMY -> {
                if (!intendedEnemy.isBlank()) {
                    System.out.println("There is no " + intendedEnemy + " in this room");
                } else {
                    System.out.println("There is no enemy in this room");
                }
            }
            case NO_WEAPON -> {
                System.out.println("You have no weapon equipped");
                printEnemyAttack(eOutcome);
                if (!eOutcome.isPlayerAlive()) {
                    handleGameOver();
                }
            }
            case CANNOT_USE -> {
                System.out.println(pOutcome.getMessage());
                printEnemyAttack(eOutcome);
                if (!eOutcome.isPlayerAlive()) {
                    handleGameOver();
                }
            }
            case ATTACKED -> {
                System.out.println("You " + pOutcome.getAttackVerb() + " the enemy for " + pOutcome.getPlayerDamageDealt() + " damage");
                if (pOutcome.isEnemyAlive()) {
                    System.out.println("The enemy now has " + pOutcome.getEnemyHealthOutcome() + " HP");
                    printEnemyAttack(eOutcome);
                } else {
                    System.out.println("You killed the " + pOutcome.getEnemyLongName());
                }

                int usesLeft = pOutcome.getUsesLeft();
                if (usesLeft > 0) {
                    System.out.println("Uses left: " + usesLeft);
                }
                else if (usesLeft == 0) {
                    System.out.println("Your weapon is out of uses");
                }

                if (eOutcome != null && !eOutcome.isPlayerAlive()) {
                    handleGameOver();
                }
            }
        }
    }

    private void handleLook() {
        System.out.print(adventure.look());
    }

    private void handleGo(Direction direction) {
        String dirString = direction.name().toLowerCase(Locale.ROOT);
        if (adventure.go(dirString)) {
            System.out.println("You are in " + adventure.getRoomName());
            System.out.print(adventure.look());
        } else {
            if (adventure.roomIsLocked()) {
                if (adventure.playerHasKey()) {
                    System.out.println("The door is locked, but you have a key.");
                    if (askYesNo("Do you want to unlock this door? (Y/N)")) {
                        adventure.unlock(dirString);
                        System.out.println("You unlock the door.");
                        handleGo(direction);
                    } else {
                        System.out.println("You leave the door locked.");
                    }
                } else {
                    System.out.println("The door is locked. You need a key!");
                }
            } else {
                System.out.println("You can't go that way!");
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

    private void handleGameOver() {
        System.out.println("Game over");
        running = false;
    }

    private static void printEnemyAttack(EnemyAttackOutcome outcome) {
        System.out.println();
        System.out.println(outcome.getEnemyLongName() + " " + outcome.getEnemyAttackVerb() + " you for " + outcome.getEnemyDamageDealt() + " HP");
        System.out.println("You now have " + outcome.getPlayerHealth() + " HP");
    }
}
