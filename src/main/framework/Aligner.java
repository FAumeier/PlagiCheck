package framework;

/**
 * Berechnet die AlignmentMatrix
 */
public class Aligner implements IAligner {
    private final IRegion region;
    private IScoring scoring;
    private final ITokenSequence originalTokenSequence;
    private final ITokenSequence suspectTokenSequence;

    //Konstanten für Globales Alignment
    private final double columnInitial;
    private final double rowInitial;
    private final double leftUpperCornerInitial = 0;
    private final double defaultInitial = Double.NEGATIVE_INFINITY;

    private IAlignmentMatrix alignmentMatrix;
    double gapScore;
    private int m, n;


    public Aligner(IRegion region, IScoring scoring, ITokenSequence originalTokenSequence, ITokenSequence suspectTokenSequence) {
        this.region = region;
        this.scoring = scoring;
        this.originalTokenSequence = originalTokenSequence;
        this.suspectTokenSequence = suspectTokenSequence;
        gapScore = scoring.getGapScore();
        columnInitial = gapScore;
        rowInitial = gapScore;
        m = originalTokenSequence.length() + 1;
        n = suspectTokenSequence.length() + 1; // Glaube das +1 braucht es weil es ja links oben so ne leere Ecke ist...
        alignmentMatrix = new SimpleAlignmentMatrix(m - 1, n - 1, gapScore); //FIXME: Steht in der Angabe Seite 5 so drin?
    }

    private void initialize() {



    }

    @Override
    public IAlignmentMatrix forward() {
        /*for (int i = 1; i <= m - 1; i++) {
            for (int j = 1; j <= n - 1; j++) {
                IAlignmentContent content = alignmentMatrix.get(i - 1, j);
                double valueOfContent = content.getValue();
                alignmentMatrix.get(i - 1, j).set(valueOfContent + gapScore);
                content = alignmentMatrix.get(i, j - 1);
                valueOfContent = content.getValue();
                alignmentMatrix.get(i, j - 1).set(valueOfContent + gapScore);
                content = alignmentMatrix.get(i - 1, j - 1);
                valueOfContent = content.getValue();
                alignmentMatrix.get(i - 1, j - 1).set(valueOfContent + (alignmentMatrix.get(i - 1, j).getValue() + alignmentMatrix.get(i, j - 1).getValue()));
            }
        }*/

        for (int i = 1; i < m-1; i++) {   // Rows
            for (int j = 1; j < n-1; j++) {   // Columns
                // Calculate diagonal movement for (X, Y)
                IAlignmentContent diagonal = new AlignmentContent(Direction.DIAGONAL_MOVE,
                        alignmentMatrix.get(i-1, j-1).getValue()
                                + scoring.getScore(originalTokenSequence.getToken(i-1), suspectTokenSequence.getToken(j-1)));
                // Calculate vertical movement for (X, Y)
                IAlignmentContent vertical = new AlignmentContent(Direction.HORIZONTAL_MOVE,
                        alignmentMatrix.get(i, j-1).getValue() + gapScore);
                // Calculate horizontal movement for (X, Y)
                IAlignmentContent horizontal = new AlignmentContent(Direction.VERTICAL_MOVE,
                        alignmentMatrix.get(i-1, j).getValue() + gapScore);

                // Get highest value
                IAlignmentContent best = diagonal;
                if (best.getValue() < horizontal.getValue()) {
                    best = horizontal;
                }
                if (best.getValue() < vertical.getValue()) {
                    best = vertical;
                }

                // Set highest AlignmentContent
                alignmentMatrix.set(i, j, best);

                // Commented out, shows prints out every step of caluclating the matrix
                //alignmentMatrix.printMatrix(originalTokenSequence, suspectTokenSequence);
            }
        }
        return alignmentMatrix;
    }
}
