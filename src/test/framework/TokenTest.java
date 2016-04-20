package framework;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by on 20.04.2016.
 */
public class TokenTest {
    @Test
    public void shouldReturnCorrectClassCode() {
        assertThat(new Token(ClassCodes.IDENTIFIER, 4711).getClassCode()).isEqualTo(ClassCodes.IDENTIFIER);
    }

    public void shouldReturnCorrectRelativeCode() {
        assertThat(new Token(ClassCodes.IDENTIFIER, 4711).getRelativeCode()).isEqualTo(4711);
    }
}
