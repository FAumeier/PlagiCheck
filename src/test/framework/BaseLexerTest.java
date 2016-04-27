package framework;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the base lexer
 */
public class BaseLexerTest {

    ILexer lexer;
    String sentence;
    @Before
    public void setUp() throws Exception {
        sentence = "Bla dies ist ein, Satz geschrieben am 12.10.16";
        lexer = new BaseLexer(new PushbackReader(new StringReader(sentence)));
    }

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
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 0))); // "Am"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));// " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.DATE, 0)));// "01.05.15"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));// " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 1)));// "wissen"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));// " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 2)));// "wir"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.PMARK, 0)));// ","
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 3)));// "dass"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));// " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.INTCONS, 0)));// "1"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));// " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 4)));// "und"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));// " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.INTCONS, 0)));// "1"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));// " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 5)));// "gleich"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 1)));// "   "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.INTCONS, 1)));// "2"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));// " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 6)));// "ist"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.PMARK, 1)));// "."
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
/**
    @Test
    public void testDecode() {
        try {
            for (int i = 0; i < sentence.length(); i++) {
                IToken nextToken = lexer.getNextToken();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String correctToken = "Bla";
        IToken token = new Token(ClassCodes.IDENTIFIER, 0);
        assertThat(lexer.decode(token).equals(correctToken));
    }
    **/
}
