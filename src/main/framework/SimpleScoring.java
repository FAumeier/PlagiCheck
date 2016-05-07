package framework;

/**
 * Created by Matthias on 07.05.2016.
 */
public class SimpleScoring implements IScoring {
    private static double perfectScore      = 1.0;
    private static double mismatchScore     = 0.0;
    private static double nearMatchScore    = 0.5;
    private static double gapScore          = -1.0;

    private final ILexer lexer;

    public SimpleScoring(ILexer lexer) {
        this.lexer = lexer; // Lexer to decode tokens for near match check
    }

    @Override
    public double getScore(IToken tk1, IToken tk2) {
        // Currently only checks for perfect match or mismatch
        // @Todo: Implement nearmatch logic... Use lexer to decode tokens
        if (tk1.equals(tk2)) {
            return perfectScore;
        }
        else {
            return mismatchScore;
        }
    }

    @Override
    public double getGapScore() {
        return gapScore;
    }

    @Override
    public boolean isPerfect(double score) {
        return score == perfectScore;
    }

    @Override
    public boolean isMismatch(double score) {
        return score == mismatchScore;
    }

    @Override
    public boolean isNearMatch(double score) {
        return score == nearMatchScore;
    }
}
