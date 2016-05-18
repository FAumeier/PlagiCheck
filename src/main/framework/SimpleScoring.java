package framework;

/**
 * Created by Matthias on 07.05.2016.
 */
public class SimpleScoring implements IScoring {
    private static double perfectScore      = 1.0;
    private static double mismatchScore     = 0.0;
    private static double nearMatchScore    = 0.5;
    private static double gapScore = -0.1;

    private final INearMatcher nearMatcher;

    @Override
    public String toString() {
        return String.format("{SimpleScoring: Perfect Score: %f MismatchScore: %f nearMatchScore: %f gapScore: %f " +
                "NearMatcher: %s}", perfectScore, mismatchScore, nearMatchScore, gapScore, nearMatcher);
    }

    public SimpleScoring(INearMatcher nearMatcher) {
        this.nearMatcher = nearMatcher;
    }

    @Override
    public double getScore(IToken tk1, IToken tk2) {
        // Currently only checks for perfect match or mismatch
        if (tk1.equals(tk2)) {
            return perfectScore;
        }
        else if (nearMatcher.isNearMatch(tk1, tk2)) {
            return nearMatchScore;
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
