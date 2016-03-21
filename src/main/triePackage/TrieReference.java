package triePackage;

/**
 * Created by Matthias on 20.03.2016.
 */
public class TrieReference implements ITrieReference {
    private final boolean found;
    private final Object value;
    private final ITrieNode node;

    public TrieReference(boolean found, Object value, ITrieNode node) {
        this.found = found;
        this.value = value;
        this.node = node;
    }

    @Override
    public boolean getFound() {
        return found;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public ITrieNode getNode() {
        return node;
    }
}
