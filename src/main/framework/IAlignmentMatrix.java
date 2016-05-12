package framework;

/**
 * Created by society on 10.05.16.
 */
public interface IAlignmentMatrix {
    void set(int i, int j, IAlignmentContent c);
    IAlignmentContent get(int i, int j);
    void printMatrix();
}
