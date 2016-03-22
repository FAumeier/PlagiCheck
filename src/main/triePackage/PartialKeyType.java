package triePackage;

/**
 * Created by Matthias on 19.03.2016.
 */
public class PartialKeyType implements Comparable {
    private final int key;

    public PartialKeyType(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    @Override
    public int compareTo(final Object other) throws ClassCastException {
        //TODO: Check if implemented correctly according to conventions
        //Should return 0; 1; -1
        if (other instanceof PartialKeyType) { //always check downcasting with instanceof
            PartialKeyType otherKey = (PartialKeyType) other;
            return this.key - otherKey.getKey();
        } else {
            throw new ClassCastException("Comparison of partialKeyType failed");
        }

    }

    @Override
    public boolean equals(Object o) {
        boolean isEqual = false;
        if (o != null && o instanceof PartialKeyType) {
            PartialKeyType partialKeyType = (PartialKeyType) o;
            isEqual = partialKeyType.getKey() == this.getKey();
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return key;
    }

    @Override
    public String toString() {
        return "" + (char) key;
    }
}
