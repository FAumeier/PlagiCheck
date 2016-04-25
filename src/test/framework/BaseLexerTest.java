package framework;

import org.junit.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the base lexer
 */
public class BaseLexerTest {
    private final String testMessage = "Am 01.05.15 wissen wir,dass 1 und 1 gleich   2 ist.";
    private final String date = "01.05.15";
    private final String digitwithWhiteSpace = " 1";

    @Test
    public void shouldDetermineTokensCorrectly() {
        BaseLexer sut = new BaseLexer(new PushbackReader(new StringReader(digitwithWhiteSpace), 4));
        LinkedList<IToken> tokens = new LinkedList<>();
        try {
            for (int i = 0; i < 4; i++) {
                IToken token = sut.getNextToken();
                System.out.println("Token ClassCode: " + token.getClassCode() + " RelativeCode: " + token.getRelativeCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldTokenizeTestSentence() {
        ILexer sut = new BaseLexer(new PushbackReader(new StringReader("Hallo 242  Test"), 4));
        try {
            IToken token = sut.getNextToken();
            assertThat(token.getClassCode()).isEqualTo(ClassCodes.IDENTIFIER);
            assertThat(token.getRelativeCode()).isEqualTo(0);
            token = sut.getNextToken();
            assertThat(token.getClassCode()).isEqualTo(ClassCodes.WS);
            assertThat(token.getRelativeCode()).isEqualTo(0);
            token = sut.getNextToken();
            assertThat(token.getClassCode()).isEqualTo(ClassCodes.INTCONS);
            assertThat(token.getRelativeCode()).isEqualTo(0);
            token = sut.getNextToken();
            assertThat(token.getClassCode()).isEqualTo(ClassCodes.WS);
            assertThat(token.getRelativeCode()).isEqualTo(1);
            token = sut.getNextToken();
            assertThat(token.getClassCode()).isEqualTo(ClassCodes.IDENTIFIER);
            assertThat(token.getRelativeCode()).isEqualTo(1);
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
                IToken token = sut.getNextToken();
                assertThat(token.getClassCode()).isEqualTo(ClassCodes.DATE);
                assertThat(token.getRelativeCode()).isEqualTo(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDistiguishIntconsFromDate() {
        ILexer sut = new BaseLexer(new PushbackReader(new StringReader("02.04 nodate"), 4));
        try {
            IToken token = sut.getNextToken();
            assertThat(token.getClassCode()).isEqualTo(ClassCodes.INTCONS);
            assertThat(token.getRelativeCode()).isEqualTo(0);
            token = sut.getNextToken();
            assertThat(token.getClassCode()).isEqualTo(ClassCodes.PMARK);
            assertThat(token.getRelativeCode()).isEqualTo(0);
            token = sut.getNextToken();
            assertThat(token.getClassCode()).isEqualTo(ClassCodes.INTCONS);
            assertThat(token.getRelativeCode()).isEqualTo(1);
            token = sut.getNextToken();
            assertThat(token.getClassCode()).isEqualTo(ClassCodes.WS);
            assertThat(token.getRelativeCode()).isEqualTo(0);
            token = sut.getNextToken();
            assertThat(token.getClassCode()).isEqualTo(ClassCodes.IDENTIFIER);
            assertThat(token.getRelativeCode()).isEqualTo(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
