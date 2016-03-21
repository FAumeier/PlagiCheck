package actionsPackage;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Matthias on 21.03.2016.
 */
public class StringCodingTest {

    /** Should return "0" or given start value */
    @Test
    public void shouldReturnZero() {
        assertThat(new StringCoding().getActualValue()).isEqualTo(0);
        assertThat(new StringCoding(4711).getActualValue()).isEqualTo(4711);
    }

    /** Should return given previous value */
    @Test
    public void shouldReturnPrevious() {
        assertThat(new StringCoding().actionAtKeyFound(new Integer(4711))).isEqualTo(new Integer(4711));
    }

    /** Should return next number */
    @Test
    public void shouldReturnNextNumber() {
        StringCoding sut = new StringCoding();
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(new Integer(0));
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(new Integer(1));
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(new Integer(2));
        sut = new StringCoding(4711);
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(new Integer(4711));
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(new Integer(4712));
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(new Integer(4713));
    }


}
