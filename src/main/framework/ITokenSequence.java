package framework;

/**
 * Created by society on 06.05.16.
 */
public interface ITokenSequence {
    void add(IToken tk);
    int length();
    IToken getToken(int i);
}
