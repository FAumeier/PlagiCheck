package triePackage;

import org.junit.Before;
import org.junit.Test;

import mapPackage.TreeMapFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Matthias on 21.03.2016.
 */
public class TrieReferenceTest {
    ITrieNode testNode;
    ITrieReference sut;
    @Before
    public void setUp() throws Exception {
        testNode = new TrieNode(null, new TreeMapFactory(), new PartialKeyType('a'));
        sut = new TrieReference(true, 4711, testNode);
    }

    /** Should return given values */
    @Test
    public void shouldReturnGivenValues() {
        assertThat(sut.getFound()).isTrue();
        assertThat(sut.getNode()).isEqualTo(testNode);
        assertThat(sut.getValue()).isEqualTo(4711);
    }
}
