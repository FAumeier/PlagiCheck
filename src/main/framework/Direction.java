package framework;

/**
 * Created by society on 10.05.16.
 */
//TODO: Implement this!
public enum Direction {
    DIAGONAL_MOVE(0), HORIZONTAL_MOVE(1), VERTICAL_MOVE(2);
    private int code;

    Direction(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}