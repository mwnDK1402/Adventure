import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {
    @ParameterizedTest
    @MethodSource("commandCases")
    void testCommand(String input, String expectedCommand, String expectedArg) {
        var cmd = new Command(input);
        assertEquals(expectedCommand, cmd.getCommand());
        assertEquals(expectedArg, cmd.getArg());
    }

    public static Stream<Arguments> commandCases() {
        return Stream.of(
                Arguments.of(
                        "",
                        "",
                        ""
                ),
                Arguments.of(
                        "drop",
                        "drop",
                        ""
                ),
                Arguments.of(
                        "eat burger",
                        "eat",
                        "burger"
                ),
                Arguments.of(
                        " take    bow & arrow   ",
                        "take",
                        "bow & arrow"
                ),
                Arguments.of(
                        "DROP WOODEN SWORD",
                        "drop",
                        "wooden sword"
                )
        );
    }
}