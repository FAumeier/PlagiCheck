package framework;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the base lexer
 */
public class BaseLexerTest {

    private ILexer lexer;
    private String sentence;
    @Before
    public void setUp() throws Exception {
        sentence = "Bla dies ist ein, Satz geschrieben am 12.10.16";
        lexer = new BaseLexer(new PushbackReader(new StringReader(sentence)));
    }

    @Test
    public void shouldDetermineTokensCorrectly() {
        ILexer sut = new BaseLexer(new PushbackReader(
                new StringReader("Am 01.05.15 wissen wir,dass 1 und 1 gleich   2 ist."), 4));
        try {
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 0))); // "Am"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));         // " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.DATE, 0)));       // "01.05.15"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));         // " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 1))); // "wissen"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));         // " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 2))); // "wir"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.PMARK, 0)));      // ","
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 3))); // "dass"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));         // " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.INTCONS, 0)));    // "1"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));         // " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 4))); // "und"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));         // " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.INTCONS, 0)));    // "1"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));         // " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 5))); // "gleich"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 1)));         // "   "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.INTCONS, 1)));    // "2"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));         // " "
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 6))); // "ist"
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.PMARK, 1)));      // "."
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldTokenizeTestSentence() {
        ILexer sut = new BaseLexer(new PushbackReader(new StringReader("Hallo 242  Test "), 4));
        try {
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 0)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.INTCONS, 0)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 1)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 1)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));
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
                assertThat(sut.getNextToken().equals(new Token(ClassCodes.DATE, 0)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDistiguishIntconsFromDate() {
        ILexer sut = new BaseLexer(new PushbackReader(new StringReader("02.04 nodate 02."), 4));
        try {
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.INTCONS, 0)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.PMARK, 0)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.INTCONS, 1)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 0)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.WS, 0)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.INTCONS, 0)));
            assertThat(sut.getNextToken().equals(new Token(ClassCodes.PMARK, 0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDecode() {

        // Token loop for first file

        IToken token = null;
        do {
            try {
                token = lexer.getNextToken();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while (token.getClassCode() != ClassCodes.EOF);

        token = new Token(ClassCodes.IDENTIFIER, 0);
        assertThat(lexer.decode(token).equals("Bla"));
    }
}
