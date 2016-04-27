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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (relativeCode != token.relativeCode) return false;
        return classCode == token.classCode;

    }

    @Override
    public int hashCode() {
        int result = classCode.hashCode();
        result = 31 * result + relativeCode;
        return result;
    }
}
