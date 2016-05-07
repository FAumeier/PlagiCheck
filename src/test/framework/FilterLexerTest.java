package framework;

import org.junit.Before;
import org.junit.Test;

import java.io.PushbackReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by society on 07.05.16.
 */
public class FilterLexerTest {
    ILexer lexer;
    String sentence;

    @Before
    public void setUp() throws Exception {
        sentence = "Am 01.05.15 wissen wir,dass 1 und 1 gleich   2 ist.";
        lexer = new FilterLexer(new BaseLexer(new PushbackReader(new StringReader(sentence))));
    }

    @Test
    public void shouldDetermineTokensCorrectly() throws Exception {
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 0))); // "Am"
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.DATE, 0)));       // "01.05.15"
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 1))); // "wissen"
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 2))); // "wir"
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.PMARK, 0)));      // ","
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 3))); // "dass"
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.INTCONS, 0)));    // "1"
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 4))); // "und"
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.INTCONS, 0)));    // "1"
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 5))); // "gleich"
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.INTCONS, 1)));    // "2"
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.IDENTIFIER, 6))); // "ist"
        assertThat(lexer.getNextToken().equals(new Token(ClassCodes.PMARK, 1)));      // "."
    }
}
