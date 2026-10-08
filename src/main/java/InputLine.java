import java.util.Arrays;
import java.util.Locale;

class InputLine {
    private final String command;
    private final String arg;

    public InputLine(String inputLine) {
        var sanitized = inputLine.toLowerCase(Locale.ROOT).trim();

        String[] parts = Arrays.stream(sanitized.split(" ", 2))
                .map(String::trim)
                .toArray(String[]::new);

        String first = parts.length > 0
                ? parts[0]
                : "";
        String rest = parts.length > 1
                ? parts[1]
                : "";

        if (rest.isBlank() && Direction.validate(first) != null) {
            command = "go";
            arg = first;
        } else {
            command = first;
            arg = rest;
        }
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }
}
