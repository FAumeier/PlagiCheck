package triePackage;


import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Matthias on 20.03.2016.
 */
public class PartialKeyTypeTest {

    @Test
    public void shouldReturnGivenValue() {
        assertThat(new PartialKeyType(10).getKey()).isEqualTo(10);
    }
}
