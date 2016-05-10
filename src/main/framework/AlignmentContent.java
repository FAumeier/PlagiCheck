package framework;

/**
 * Im AlignmentContent wird der jeweilige Wert(value) der
 * Matrix festgehalten und wie dieser Erreicht wurde (direction)
 */
public class AlignmentContent implements IAlignmentContent {
    private Direction direction;
    /**
     * Wert eines Feldes
     */
    private double value;

    public AlignmentContent(Direction direction, double value) {
        this.direction = direction;
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void set(double value) {
        this.value = value;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlignmentContent that = (AlignmentContent) o;

        if (Double.compare(that.value, value) != 0) return false;
        return direction == that.direction;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = direction.hashCode();
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlignmentContent{");
        sb.append("direction=").append(direction);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
