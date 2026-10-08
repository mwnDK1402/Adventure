enum Direction {
    North,
    South,
    East,
    West;

    public static Direction validate(String direction) {
        return switch (direction) {
            case "north", "n" -> North;
            case "south", "s" -> South;
            case "east", "e" -> East;
            case "west", "w" -> West;
            default -> null;
        };
    }
}
