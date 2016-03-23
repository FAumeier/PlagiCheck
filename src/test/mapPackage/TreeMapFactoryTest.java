package mapPackage;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Matthias on 21.03.2016.
 */
public class TreeMapFactoryTest {
    IMapFactory mapFactory;
    @Before
    public void setUp() throws Exception {
        mapFactory = new TreeMapFactory();
    }

    /** Should return empty Tree Map */
    @Test
    public  void shouldReturnEmptyTreeMap() {
        assertThat(mapFactory.create().isEmpty()).isTrue();
    }
}
