package framework;

/**
 * Created by society on 10.05.16.
 */
public class SimpleAlignmentMatrix implements IAlignmentMatrix {

    private IAlignmentContent[][] matrix;
    private final int length;
    private final int width;

    public SimpleAlignmentMatrix(int length, int width) {
        this.length = length;
        this.width = width;
        matrix = new IAlignmentContent[length][width];
        initializeMatrix();
    }

    private void initializeMatrix() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = new AlignmentContent(Direction.DIAGONAL_MOVE, Double.NEGATIVE_INFINITY);
            }
        }
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
