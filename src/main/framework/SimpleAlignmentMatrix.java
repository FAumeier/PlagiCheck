package framework;

/**
 * Created by society on 10.05.16.
 */
public class SimpleAlignmentMatrix implements IAlignmentMatrix {

    private IAlignmentContent[][] matrix;
    private final int length;
    private final int width;


    public SimpleAlignmentMatrix(int length, int width, double gapScore) {
        this.length = length;
        this.width = width;
        matrix = new IAlignmentContent[length][width];
        initializeMatrix(gapScore);
    }

    private void initializeMatrix(double gapScore) {
        //Fill with negative infinity
        for (int y = 0; y < length; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][x] = new AlignmentContent(Direction.DIAGONAL_MOVE, Double.NEGATIVE_INFINITY);

                //Fill first row with gap scores
                if (y == 0) {
                    matrix[y][x] = new AlignmentContent(Direction.HORIZONTAL_MOVE, (double) x * gapScore );
                }
                //Fill first column with gap scores
                if (x == 0) {
                    matrix[y][x] = new AlignmentContent(Direction.VERTICAL_MOVE, (double) y * gapScore );
                }
            }
        }
        //set constants
    }

    @Override
    public void set(int i, int j, IAlignmentContent c) {
        matrix[i][j] = c;
    }

    @Override
    public IAlignmentContent get(int i, int j) {
        return matrix[i][j];
    }
}
