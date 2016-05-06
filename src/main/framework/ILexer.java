package framework;

import java.io.IOException;
import java.io.PushbackReader;

/**
 * Created by Matthias on 19.03.2016.
 */
interface ILexer {
    IToken getNextToken() throws IOException;
    String decode(IToken tk);
    void setPushBackReader(PushbackReader reader);
}
