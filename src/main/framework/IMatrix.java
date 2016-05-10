package framework;

/**
 * Created by society on 10.05.16.
 */
public interface IMatrix<T> {
    void set(int i, int j, T c);
    T get(int i, int j);
}
