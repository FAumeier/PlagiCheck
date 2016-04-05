package triePackage;

import actionsPackage.IActionAtInsert;
import actionsPackage.StringCoding;
import mapPackage.TreeMapFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Matthias on 21.03.2016.
 */
public class TrieTest {

    ITrie trie;
    IActionAtInsert stringCoding;

    /** Setup test objects for Trie tests */
    @Before
    public void setUp() throws Exception {
        trie = new Trie(new TreeMapFactory());
        stringCoding = new StringCoding();
    }

    /**
     * Insert different words into trie. And check return values.
     * Multiple tests in one Unit Tests, due to the fact that the trie has to be built up with different words.
     * @TODO: Create seperate tests for every aspect and keep big test as "complete trie test"
     *
     * sut1: Empty trie, everything is new
     * sut2: Same word, but starts with uppercase, new trie-branch
     * sut3: extends branch of sut2 "Alf..."
     * sut4: extends branch of sut2 "Al..."
     * sut5: Already inserted, has to be found with same keyNodeValue
     * sut6: Word ends in middle of branch of sut2 "Al", has to be changed to keyNode with new value
     * sut7: As sut6 but in branch of sut4 "Alpha"
     */
    @Test
    public void shouldCreateCorrectTrie() {
        ITrieReference sut1 = trie.insert("alf", stringCoding);
        assertThat(sut1.getFound()).isFalse();
        assertThat(sut1.getValue()).isEqualTo(0);
        ITrieReference sut2 = trie.insert("Alf", stringCoding);
        assertThat(sut2.getFound()).isFalse();
        assertThat(sut2.getValue()).isEqualTo(1);
        ITrieReference sut3 = trie.insert("Alfons", stringCoding);
        assertThat(sut3.getFound()).isFalse();
        assertThat(sut3.getValue()).isEqualTo(2);
        ITrieReference sut4 = trie.insert("Alphabet", stringCoding);
        assertThat(sut4.getFound()).isFalse();
        assertThat(sut4.getValue()).isEqualTo(3);
        ITrieReference sut5 = trie.insert("Alf", stringCoding);
        assertThat(sut5.getFound()).isTrue();
        assertThat(sut5.getValue()).isEqualTo(1);
        ITrieReference sut6 = trie.insert("Al", stringCoding);  // Key is already there, but not as KeyNode!
        assertThat(sut6.getFound()).isTrue();
        assertThat(sut6.getValue()).isEqualTo(4);
        ITrieReference sut7 = trie.insert("Alpha", stringCoding);  // Key is already there, but not as KeyNode!
        assertThat(sut7.getFound()).isTrue();
        assertThat(sut7.getValue()).isEqualTo(5);

        assertThat(trie.toString()).isEqualTo(
                "A\n" +
                ".l |-> 4\n" +
                "..f |-> 1\n" +
                "...o\n" +
                "....n\n" +
                ".....s |-> 2\n" +
                "..p\n" +
                "...h\n" +
                "....a |-> 5\n" +
                ".....b\n" +
                "......e\n" +
                ".......t |-> 3\n" +
                "a\n" +
                ".l\n" +
                "..f |-> 0\n");
    }

    /** Should get correct iterator */
    @Test
    public void shouldGetCorrectIterator() {
        // Given
        Iterator<Character> iter = Trie.stringIterator("Alfons");
        // When & Then
        assertThat(iter.hasNext()).isTrue();
        assertThat(iter.next()).isEqualTo('A');
        assertThat(iter.next()).isEqualTo('l');
        assertThat(iter.next()).isEqualTo('f');
        assertThat(iter.next()).isEqualTo('o');
        assertThat(iter.next()).isEqualTo('n');
        assertThat(iter.next()).isEqualTo('s');
        assertThat(iter.hasNext()).isFalse();
    }
}
