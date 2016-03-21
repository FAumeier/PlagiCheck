package mapPackage;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Matthias on 21.03.2016.
 */
public class TreeMapFactoryTest {

    /** Should return empty Tree Map */
    @Test
    public  void shouldReturnEmptyTreeMap() {
        IMapFactory mapFactory = new TreeMapFactory();
        assertThat(mapFactory.create().isEmpty()).isTrue();
    }
}
