package triePackage;


import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Matthias on 20.03.2016.
 */
public class PartialKeyTypeTest {

    /** Should return given value */
    @Test
    public void shouldReturnGivenValue() {
        assertThat(new PartialKeyType(10).getKey()).isEqualTo(10);
    }

    /** Should sort correctly, checks the implementation of Comparable interface */
    @Test
    public void shouldSortCorrectly() {
        PartialKeyType[] keys = new PartialKeyType[5];
        keys[0] = new PartialKeyType('a');  // ASCII key = 97
        keys[1] = new PartialKeyType('+');  // ASCII key = 43
        keys[2] = new PartialKeyType(']');  // ASCII key = 93
        keys[3] = new PartialKeyType('X');  // ASCII key = 88
        keys[4] = new PartialKeyType('y');  // ASCII key = 121
        Arrays.sort(keys);
        assertThat(keys[0].getKey()).isEqualTo('+');
        assertThat(keys[1].getKey()).isEqualTo('X');
        assertThat(keys[2].getKey()).isEqualTo(']');
        assertThat(keys[3].getKey()).isEqualTo('a');
        assertThat(keys[4].getKey()).isEqualTo('y');
    }

    /** Should print toString correctly */
    @Test
    public void shouldPrintToString() {
        assertThat(new PartialKeyType('X').toString()).isEqualTo("X");
    }
}
