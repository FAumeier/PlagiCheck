package framework;

import org.junit.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the base lexer
 */
public class BaseLexerTest {

    /** Helper to assert equality of class and relative code for a token */
    private void assertToken(IToken token, ClassCodes classCode, int relativeCode) {
        assertThat(token.getClassCode()).isEqualTo(classCode);
        assertThat(token.getRelativeCode()).isEqualTo(relativeCode);
    }

    @Test
    public void shouldDetermineTokensCorrectly() {
        ILexer sut = new BaseLexer(new PushbackReader(
                new StringReader("Am 01.05.15 wissen wir,dass 1 und 1 gleich   2 ist."), 4));
        try {
            assertToken(sut.getNextToken(), ClassCodes.IDENTIFIER, 0);  // "Am"
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);          // " "
            assertToken(sut.getNextToken(), ClassCodes.DATE, 0);        // "01.05.15"
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);          // " "
            assertToken(sut.getNextToken(), ClassCodes.IDENTIFIER, 1);  // "wissen"
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);          // " "
            assertToken(sut.getNextToken(), ClassCodes.IDENTIFIER, 2);  // "wir"
            assertToken(sut.getNextToken(), ClassCodes.PMARK, 0);       // ","
            assertToken(sut.getNextToken(), ClassCodes.IDENTIFIER, 3);  // "dass"
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);          // " "
            assertToken(sut.getNextToken(), ClassCodes.INTCONS, 0);     // "1"
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);          // " "
            assertToken(sut.getNextToken(), ClassCodes.IDENTIFIER, 4);  // "und"
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);          // " "
            assertToken(sut.getNextToken(), ClassCodes.INTCONS, 0);     // "1"
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);          // " "
            assertToken(sut.getNextToken(), ClassCodes.IDENTIFIER, 5);  // "gleich"
            assertToken(sut.getNextToken(), ClassCodes.WS, 1);          // "   "
            assertToken(sut.getNextToken(), ClassCodes.INTCONS, 1);     // "2"
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);          // " "
            assertToken(sut.getNextToken(), ClassCodes.IDENTIFIER, 6);  // "ist"
            assertToken(sut.getNextToken(), ClassCodes.PMARK, 1);       // "."
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldTokenizeTestSentence() {
        ILexer sut = new BaseLexer(new PushbackReader(new StringReader("Hallo 242  Test "), 4));
        try {
            assertToken(sut.getNextToken(), ClassCodes.IDENTIFIER, 0);
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);
            assertToken(sut.getNextToken(), ClassCodes.INTCONS, 0);
            assertToken(sut.getNextToken(), ClassCodes.WS, 1);
            assertToken(sut.getNextToken(), ClassCodes.IDENTIFIER, 1);
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldTokenizeDate() {
        String[] dates = {"02.04.16", "02.04.2016"};
        try {
            for (int i = 0; i < dates.length; i++) {
                ILexer sut = new BaseLexer(new PushbackReader(new StringReader(dates[i]), 4));
                assertToken(sut.getNextToken(), ClassCodes.DATE, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDistiguishIntconsFromDate() {
        ILexer sut = new BaseLexer(new PushbackReader(new StringReader("02.04 nodate 02."), 4));
        try {
            assertToken(sut.getNextToken(), ClassCodes.INTCONS, 0);
            assertToken(sut.getNextToken(), ClassCodes.PMARK, 0);
            assertToken(sut.getNextToken(), ClassCodes.INTCONS, 1);
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);
            assertToken(sut.getNextToken(), ClassCodes.IDENTIFIER, 0);
            assertToken(sut.getNextToken(), ClassCodes.WS, 0);
            assertToken(sut.getNextToken(), ClassCodes.INTCONS, 0);
            assertToken(sut.getNextToken(), ClassCodes.PMARK, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
