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
        if (alignmentMatrix != null) {
            alignmentMatrix.get(0, 0).set(gapScore);
            for (int i = 1; i <= m - 1; i++) {
                alignmentMatrix.get(0, i).set((-i) * rowInitial);
            }
            for (int j = 1; j <= n - 1; j++) {
                alignmentMatrix.get(j, 0).set((-j) * columnInitial);
            }
            //Eigentlich müssten laut Angabe hier die restlichen Values auf negative infinity gesetzt werden. Dies geschieht aber schon im Konstruktor
            //der SimpleAlignmentMatrix
        }
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

        for (int y = 1; y < m; y++) {   // Rows
            for (int x = 1; x < m; x++) {   // Columns
                //@Fixme: this score is only the direct comparison of the tokens corresponding to the cell, but you
                // have to calculate every one of the three ways -> Horizontal, vertical, diagonal and then pick the
                // best one and summarize it with value from which it came from...
                double score = scoring.getScore(originalTokenSequence.getToken(y), suspectTokenSequence.getToken(x));
                alignmentMatrix.get(y, x).set(score);
            }
        }
        return null;
    }
}
