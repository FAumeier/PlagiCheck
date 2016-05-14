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
    }

    @Override
    public void set(int i, int j, IAlignmentContent c) {
        matrix[i][j] = c;
    }

    @Override
    public IAlignmentContent get(int i, int j) {
        return matrix[i][j];
    }

    private void printRow(IAlignmentContent[] row) {
        int columnCounter = 0;
        for (IAlignmentContent i : row) {
            System.out.print(" x:" + columnCounter + i);
            System.out.print("\t");
            columnCounter++;
        }
        System.out.println();
    }

    public void printMatrix(ITokenSequence original, ITokenSequence suspect) {
        int rowCounter = 0;
        System.out.print("\t\t\t\t\t\t    ");
        for (int i = 0; i < suspect.length() - 1; i++) {
            System.out.print("\t" + suspect.getToken(i) + "\t\t");
        }
        System.out.print("\n");
        int rows = -1;
        for(IAlignmentContent[] row : matrix) {
            System.out.print("y: " + rowCounter + " ");
            if (rows >= 0 && rows < original.length() - 1) {
                System.out.print(original.getToken(rows) + " ");;
            }
            else {
                System.out.print("      \t");
            }

            rows++;

            printRow(row);
            rowCounter++;
        }


        System.out.println("\n");
    }
}
