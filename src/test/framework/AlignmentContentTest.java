package framework;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by society on 10.05.16.
 */
public class AlignmentContentTest {
    IAlignmentContent alignmentContent;

    @Before
    public void setUp() throws Exception {
        alignmentContent = new AlignmentContent(0, 0, Direction.VERTICAL_MOVE, 1.00);
    }

    @Test
    public void getValue() throws Exception {
        assertEquals(1D, alignmentContent.getValue(), 0D);
    }

    @Test
    public void getDirection() throws Exception {
        assertEquals(Direction.VERTICAL_MOVE, alignmentContent.getDirection());
    }

    @Test
    public void equalsTest() throws Exception {
        IAlignmentContent otherContent = new AlignmentContent(1, 0, Direction.VERTICAL_MOVE, 1D);
        assertNotEquals(true, alignmentContent.equals(otherContent));
        otherContent = new AlignmentContent(0, 0, Direction.VERTICAL_MOVE, 1D);
        assertEquals(true, alignmentContent.equals(otherContent));
    }
}