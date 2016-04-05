package framework;

/**
 * Created by Flo on 05.04.2016.
 */
public class Token implements IToken {
    private final int IDENTIFIER;
    private final int INTCONS;
    //private static final int DATE;
   // private static final int PMARK;

    public Token(int classCode, int relativeCode) {
        IDENTIFIER = classCode;
        INTCONS = relativeCode;
    }

    public int getClassCode() {
        return IDENTIFIER;
    }

    public int getRelativeCode() {
        return INTCONS;
    }
}
