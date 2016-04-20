package framework;

/**
 * Implements the IToken interface and provides class and relative code for a token.
 */
public class Token implements IToken {
    private final ClassCodes classCode;
    private final int relativeCode;


    public Token(ClassCodes classCode, int relativeCode) {
        this.classCode = classCode;
        this.relativeCode = relativeCode;
    }

    @Override
    public ClassCodes getClassCode() {
        return classCode;
    }

    @Override
    public int getRelativeCode() {
        return relativeCode;
    }
}
