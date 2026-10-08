import java.util.Arrays;
import java.util.Locale;

class Command {
    private final String command;
    private final String[] args;

    public Command(String inputLine) {
        String lowerCase = inputLine.toLowerCase(Locale.ROOT);
        var words = Arrays.stream(lowerCase.split(" ")).filter(w -> !w.isBlank()).toArray(String[]::new);

        command = words.length > 0 ? words[0] : null;
        args = Arrays.stream(words, 1, words.length).toArray(String[]::new);
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args; // Yup, I know it leaks, but there's only ever one consumer (and owner)
    }
}
