package framework;

import java.io.IOException;
import java.io.PushbackReader;

/**
 * Created by society on 06.05.16.
 */
public class FilterLexer implements ILexer {

    BaseLexer baseLexer;

    public FilterLexer(BaseLexer baseLexer) {
        this.baseLexer = baseLexer;
    }

    @Override
    public IToken getNextToken() throws IOException {
        IToken token = baseLexer.getNextToken();
        while (token.getClassCode().equals(ClassCodes.WS)) {
            token = baseLexer.getNextToken();
        }
        return token;
    }

    @Override
    public String decode(IToken tk) {
        String token;
        IToken tokenToDecode = tk;
        token = baseLexer.decode(tokenToDecode);
        return token;
    }

    @Override
    public void setPushBackReader(PushbackReader reader) {
        baseLexer.setPushBackReader(reader);
    }
}
