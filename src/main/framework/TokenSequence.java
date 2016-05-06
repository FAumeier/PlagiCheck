package framework;

import java.util.LinkedList;

/**
 * Created by society on 06.05.16.
 */
public class TokenSequence implements ITokenSequence {

    private LinkedList<IToken> tokenLinkedList;

    public TokenSequence() {
        this.tokenLinkedList = new LinkedList<>();
    }

    @Override
    public void add(Token tk) {
        tokenLinkedList.add(tk);
    }

    @Override
    public int length() {
        return tokenLinkedList.size();
    }

    @Override
    public IToken getToken(int i) {
        return tokenLinkedList.get(i);
    }
}
