package actionsPackage;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Matthias on 21.03.2016.
 */
public class StringCodingTest {
    StringCoding sut;
    @Before
    public void setUp() throws Exception {
        sut = new StringCoding();
    }

    /** Should return "0" or given start value */
    @Test
    public void shouldReturnZero() {
        assertThat(new StringCoding().getActualValue()).isEqualTo(0);
        assertThat(new StringCoding(4711).getActualValue()).isEqualTo(4711);
    }

    /** Should return given previous value */
    @Test
    public void shouldReturnPrevious() {
        assertThat(new StringCoding().actionAtKeyFound(4711)).isEqualTo(4711);
    }

    /** Should return next number */
    @Test
    public void shouldReturnNextNumber() {
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(0);
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(1);
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(2);
        sut = new StringCoding(4711);
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(4711);
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(4712);
        assertThat(sut.actionAtKeyNotFound()).isEqualTo(4713);
    }

    /** Should return next free number */
    @Test
    public void shouldReturnNextFreeNumber() {
        StringCoding sut = new StringCoding();
        sut.actionAtKeyNotFound();
        sut.actionAtKeyNotFound();
        sut.actionAtKeyNotFound();
        assertThat(sut.getActualValue()).isEqualTo(3);
        sut = new StringCoding(4711);
        sut.actionAtKeyNotFound();
        sut.actionAtKeyNotFound();
        sut.actionAtKeyNotFound();
        assertThat(sut.getActualValue()).isEqualTo(4714);
    }


}
