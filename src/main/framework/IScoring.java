package framework;

/**
 * Created by Matthias on 07.05.2016.
 */
public interface IScoring {
    /**
     * Matches given tokens ans returns the corresponding score.
     * @param tk1 First token...
     * @param tk2 Second token to match against
     * @return the corresponding score
     */
    double getScore(IToken tk1, IToken tk2);

    /**
     * Returns the score of a gap.
     * @return the gap score
     */
    double getGapScore();

    /**
     * Checks whether a score corresponds to a perfect match.
     * @param score given score
     * @return whether it corresponds to a perfect match or not
     */
    boolean isPerfect(double score);

    /**
     * Checks whether a score corresponds to a mismatch.
     * @param score given score
     * @return whether it corresponds to a mismatch or not
     */
    boolean isMismatch(double score);

    /**
     * Checks whether a score corresponds to a near match.
     * @param score given score
     * @return whether it corresponds to a near match or not
     */
    boolean isNearMatch(double score);
}
