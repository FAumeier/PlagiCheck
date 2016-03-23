package triePackage;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import actionsPackage.IActionAtInsert;
import actionsPackage.StringCoding;
import mapPackage.TreeMapFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Matthias on 21.03.2016.
 */
public class TrieTest {

    ITrie trie;
    IActionAtInsert stringCoding;
    Iterator<Character> iter;

    @Before
    public void setUp() throws Exception {
        trie = new Trie(new TreeMapFactory());
        stringCoding = new StringCoding();
        iter = Trie.stringIterator("Alfons");
    }

    @Test
    public void testTrie() {
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
        System.out.println(trie);
    }

    /** Should get correct iterator */
    @Test
    public void shouldGetCorrectIterator() {
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
