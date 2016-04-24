package framework;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.LinkedList;

/**
 * Tests the base lexer
 */
public class BaseLexerTest {
    private final String testMessage = "Am 01.05.15 wissen wir,dass 1 und 1 gleich   2 ist.";
    private final String date = "01.05.15";

    @Test
    public void shouldDetermineTokensCorrectly() {
        BaseLexer sut = new BaseLexer(new PushbackReader(new StringReader(date)));
        LinkedList<IToken> tokens = new LinkedList<>();
        try {
            for (int i = 0; i < 24; i++) {
                IToken token = sut.getNextToken();
                System.out.println("Token ClassCode: " + token.getClassCode() + " RelativeCode: " + token.getRelativeCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
