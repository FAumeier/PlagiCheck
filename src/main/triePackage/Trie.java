package triePackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.NoSuchElementException;

import actionsPackage.IActionAtInsert;
import mapPackage.IMapFactory;

/**
 * Created by Matthias on 19.03.2016.
 */
public class Trie implements ITrie {
    /**
     * Logger
     */
    final static Logger LOG = LogManager.getLogger(Trie.class.getName()); //Use for example: LOG.debug("any string");

    ITrieNode root;
    IMapFactory mapFactory;

    public Trie(IMapFactory mapFactory) {
        this.mapFactory = mapFactory;
        root = new TrieNode(mapFactory);
        LOG.debug("New Trie created");
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
