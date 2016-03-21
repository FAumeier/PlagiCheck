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
    public int compareTo(final Object other) {
        PartialKeyType otherKey = (PartialKeyType) other;
        return this.key - otherKey.getKey();
    }
}
