package triePackage;

import mapPackage.TreeMapFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Matthias on 21.03.2016.
 */
public class TrieReferenceTest {

    /** Should return given values */
    @Test
    public void shouldReturnGivenValues() {
        ITrieNode testNode = new TrieNode(null, new TreeMapFactory(), new PartialKeyType('a'));
        ITrieReference sut = new TrieReference(true, 4711, testNode);
        assertThat(sut.getFound()).isTrue();
        assertThat(sut.getNode()).isEqualTo(testNode);
        assertThat(sut.getValue()).isEqualTo(4711);
    }
}
