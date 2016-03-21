package actionsPackage;

/**
 * Created by Matthias on 19.03.2016.
 */
public class StringCoding implements IActionAtInsert {
    private int counter = 0;    // Counts words in dictionary, counter identifies next available number

    public StringCoding(int start) {
        counter = start;
    }
    public StringCoding() {
        this(0);
    }

    /**
     * Does nothing and returns given argument
     * @param previous the given value
     * @return the given argument
     */
    @Override
    public Object actionAtKeyFound(Object previous) {
        return previous;
    }

    /**
     * Increases counter and returns new value
     *
     * @return new value for new token
     */
    @Override
    public Object actionAtKeyNotFound() {
        return counter++;
    }

    public int getActualValue() {
        return counter;
    }

    public String toString() {
        return "Counter = " + counter;
    }
}
