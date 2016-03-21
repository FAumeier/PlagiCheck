package framework;

import java.io.IOException;

/**
 * Created by Matthias on 19.03.2016.
 */
public interface ILexer {
    IToken getNextToken() throws IOException;
    String decode(IToken tk);
}