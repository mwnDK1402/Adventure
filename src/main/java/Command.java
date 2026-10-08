import java.util.Arrays;

enum Command {
    Invalid,
    Go,
    North,
    South,
    East,
    West,
    N,
    S,
    E,
    W,
    Look,
    Help,
    Exit,
    Inventory,
    Health,
    Attack,
    Eat,
    Drop,
    Take,
    Equip;

    public static Command validate(String command) {
        return Arrays.stream(Command.values())
                .filter(cmd -> cmd.name().equalsIgnoreCase(command))
                .findFirst()
                .orElse(Invalid);
    }
}
