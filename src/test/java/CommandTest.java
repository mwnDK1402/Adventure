import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {
    @ParameterizedTest
    @MethodSource("commandCases")
    void testCommand(String input, String expectedCommand, String[] expectedArgs) {
        var cmd = new Command(input);
        assertEquals(expectedCommand, cmd.getCommand());
        assertArrayEquals(expectedArgs, cmd.getArgs());
    }

    public static Stream<Arguments> commandCases() {
        return Stream.of(
                Arguments.of(
                        " remove    these spaces   ",
                        "remove",
                        new String[] {"these", "spaces"}
                ),
                Arguments.of(
                        "onearg",
                        "onearg",
                        new String[] {}
                )
        );
    }
}