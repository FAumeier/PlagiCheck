package triePackage;

import actionsPackage.IActionAtInsert;
import actionsPackage.StringCoding;
import mapPackage.TreeMapFactory;
import org.junit.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Matthias on 21.03.2016.
 */
public class TrieTest {

    @Test
    public void testTrie() {
        ITrie trie = new Trie(new TreeMapFactory());
        IActionAtInsert stringCoding = new StringCoding();
        ITrieReference alfRef = trie.insert("alf", stringCoding);
        assertThat(alfRef.getFound()).isFalse();
        ITrieReference alfonsRef = trie.insert("alfons", stringCoding);
        assertThat(alfonsRef.getFound()).isFalse();
        ITrieReference alfRef2 = trie.insert("alf", stringCoding);
        assertThat(alfRef2.getFound()).isTrue();
        ITrieReference alfphabetRef = trie.insert("alphabet", stringCoding);
        trie.insert("all", stringCoding);
        trie.insert("alles", stringCoding);

        System.out.println(trie);
    }

    /** Should get correct iterator */
    @Test
    public void shouldGetCorrectIterator() {
        Iterator<Character> iter = Trie.stringIterator("Alfons");
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
