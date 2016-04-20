package framework;

import org.junit.Test;

import java.io.PushbackReader;
import java.io.StringReader;

/**
 * Tests the base lexer
 */
public class BaseLexerTest {
    private final String testMessage = "Am 01.05.15 wissen wir,dass 1 und 1 gleich   2 ist.";

    @Test
    public void shouldDetermineTokensCorrectly() {
        BaseLexer sut = new BaseLexer(new PushbackReader(new StringReader(testMessage)));
    }
}
