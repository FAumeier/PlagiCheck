package framework;

/**
 * Im AlignmentContent wird der jeweilige Wert(value) der
 * Matrix festgehalten und wie dieser Erreicht wurde (direction)
 */
public class AlignmentContent implements IAlignmentContent {
    /**
     * Zeilenindex
     */
    private int i;
    /**
     * Spaltenindex
     */
    private int j;
    private Direction direction;
    /**
     * Wert eines Feldes
     */
    private double value;

    public AlignmentContent(int i, int j, Direction direction, double value) {
        this.i = i;
        this.j = j;
        this.direction = direction;
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlignmentContent that = (AlignmentContent) o;

        if (i != that.i) return false;
        if (j != that.j) return false;
        if (Double.compare(that.value, value) != 0) return false;
        return direction == that.direction;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = i;
        result = 31 * result + j;
        result = 31 * result + direction.hashCode();
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlignmentContent{");
        sb.append("i=").append(i);
        sb.append(", j=").append(j);
        sb.append(", direction=").append(direction);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }

    public int getRowIndex() {
        return i;
    }

    public int getColumnIndex() {
        return j;
    }
}
