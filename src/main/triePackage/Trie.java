package triePackage;

import actionsPackage.IActionAtInsert;
import mapPackage.IMapFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Matthias on 19.03.2016.
 */
public class Trie implements ITrie {
    /**
     * Logger
     */
    private final static Logger LOG = LogManager.getLogger(Trie.class.getName()); //Use for example: LOG.debug("any string");

    private final ITrieNode root;
    private final IMapFactory mapFactory;

    private final IActionAtInsert ownActionAtInsert;

    public Trie(IMapFactory mapFactory, IActionAtInsert actionAtInsert) {
        this.mapFactory = mapFactory;
        root = new TrieNode(mapFactory);
        ownActionAtInsert = actionAtInsert;
        LOG.debug("New Trie created");
    }

    public Trie(IMapFactory mapFactory) {
        this(mapFactory, null);
    }

    @Override
    public ITrieReference insert(Iterator key, IActionAtInsert value) {
        return root.recursiveInsert(key, value);
    }

    @Override
    public ITrieReference insert(String str, IActionAtInsert value) {
        LOG.debug("Wird in Trie eingefügt: " + str);
        Iterator<Character> iter = stringIterator(str);
        return insert(iter, value);
    }

    @Override
    public ITrieReference insert(String str) throws IllegalStateException {
        if (ownActionAtInsert == null) {
            throw new IllegalStateException(
                    "No own ActionAtInsert injected, can´t use insert-method without ActionAtInsert.");
        }
        return insert(str, ownActionAtInsert);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    /**
     * Iterator for String.
     *
     * @param string String to get Iterator for.
     * @return Iterator for given String.
     */
    public static Iterator<Character> stringIterator(final String string) {
        // Ensure the error is found as soon as possible.
        if (string == null)
            throw new NullPointerException();

        return new Iterator<Character>() {
            private int index = 0;

            public boolean hasNext() {
                return index < string.length();
            }

            public Character next() {
      /*
       * Throw NoSuchElementException as defined by the Iterator contract,
       * not IndexOutOfBoundsException.
       */
                if (!hasNext())
                    throw new NoSuchElementException();
                return string.charAt(index++);
            }

            public void remove() {
                //TODO: Implement
                throw new UnsupportedOperationException();
            }
        };
    }
}
