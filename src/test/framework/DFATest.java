package framework;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the DFA.
 */
public class DFATest {
    private final DFA sut = new DFA();

    @Test
    public void shouldRecognizeWhitespace() {
        assertThat(sut.trans(DFAStates.START, ' ')).isEqualTo(DFAStates.WS);
        assertThat(sut.trans(DFAStates.WS, '3')).isEqualTo(DFAStates.FAILURE);

        assertThat(sut.trans(DFAStates.START, ' ')).isEqualTo(DFAStates.WS);
        assertThat(sut.trans(DFAStates.WS, ' ')).isEqualTo(DFAStates.WS);
    }

    @Test
    public void shouldRecognizePunctuationMark() {
        assertThat(sut.trans(DFAStates.START, ',')).isEqualTo(DFAStates.PM);
        assertThat(sut.trans(DFAStates.PM, '3')).isEqualTo(DFAStates.FAILURE);

        assertThat(sut.trans(DFAStates.START, ',')).isEqualTo(DFAStates.PM);
        assertThat(sut.trans(DFAStates.PM, ',')).isEqualTo(DFAStates.FAILURE);
    }

    @Test
    public void shouldRecognizeIdentifier() {
        assertThat(sut.trans(DFAStates.START, 'a')).isEqualTo(DFAStates.IDENTIFIER);
        assertThat(sut.trans(DFAStates.IDENTIFIER, 'b')).isEqualTo(DFAStates.IDENTIFIER);
        assertThat(sut.trans(DFAStates.IDENTIFIER, 'c')).isEqualTo(DFAStates.IDENTIFIER);
        assertThat(sut.trans(DFAStates.IDENTIFIER, ' ')).isEqualTo(DFAStates.FAILURE);
    }

    @Test
    public void shouldRecognizeIntegerConstant() {
        assertThat(sut.trans(DFAStates.START, '1')).isEqualTo(DFAStates.FIRST_OF_DAY);
        assertThat(sut.trans(DFAStates.FIRST_OF_DAY, '3')).isEqualTo(DFAStates.SECOND_OF_DAY);
        assertThat(sut.trans(DFAStates.SECOND_OF_DAY, ' ')).isEqualTo(DFAStates.FAILURE);

        assertThat(sut.trans(DFAStates.START, '1')).isEqualTo(DFAStates.FIRST_OF_DAY);
        assertThat(sut.trans(DFAStates.FIRST_OF_DAY, '3')).isEqualTo(DFAStates.SECOND_OF_DAY);
        assertThat(sut.trans(DFAStates.SECOND_OF_DAY, '3')).isEqualTo(DFAStates.INTCONS);
    }

    @Test
    public void shouldRecognizeDate() {
        //13.10.1990
        assertThat(sut.trans(DFAStates.START, '1')).isEqualTo(DFAStates.FIRST_OF_DAY);
        assertThat(sut.trans(DFAStates.FIRST_OF_DAY, '3')).isEqualTo(DFAStates.SECOND_OF_DAY);
        assertThat(sut.trans(DFAStates.SECOND_OF_DAY, '.')).isEqualTo(DFAStates.DAY_STATE);
        assertThat(sut.trans(DFAStates.DAY_STATE, '1')).isEqualTo(DFAStates.FIRST_OF_MONTH);
        assertThat(sut.trans(DFAStates.FIRST_OF_MONTH, '0')).isEqualTo(DFAStates.SECOND_OF_MONTH);
        assertThat(sut.trans(DFAStates.SECOND_OF_MONTH, '.')).isEqualTo(DFAStates.MONTH_STATE);
        assertThat(sut.trans(DFAStates.MONTH_STATE, '1')).isEqualTo(DFAStates.FIRST_OF_YEAR);
        assertThat(sut.trans(DFAStates.FIRST_OF_YEAR, '9')).isEqualTo(DFAStates.DATE_STATE);
        assertThat(sut.trans(DFAStates.DATE_STATE, '9')).isEqualTo(DFAStates.THIRD_OF_YEAR);
        assertThat(sut.trans(DFAStates.THIRD_OF_YEAR, '0')).isEqualTo(DFAStates.DATE_STATE);
        assertThat(sut.trans(DFAStates.DATE_STATE, ' ')).isEqualTo(DFAStates.FAILURE);

        //13.10.99
        assertThat(sut.trans(DFAStates.START, '1')).isEqualTo(DFAStates.FIRST_OF_DAY);
        assertThat(sut.trans(DFAStates.FIRST_OF_DAY, '3')).isEqualTo(DFAStates.SECOND_OF_DAY);
        assertThat(sut.trans(DFAStates.SECOND_OF_DAY, '.')).isEqualTo(DFAStates.DAY_STATE);
        assertThat(sut.trans(DFAStates.DAY_STATE, '1')).isEqualTo(DFAStates.FIRST_OF_MONTH);
        assertThat(sut.trans(DFAStates.FIRST_OF_MONTH, '0')).isEqualTo(DFAStates.SECOND_OF_MONTH);
        assertThat(sut.trans(DFAStates.SECOND_OF_MONTH, '.')).isEqualTo(DFAStates.MONTH_STATE);
        assertThat(sut.trans(DFAStates.MONTH_STATE, '9')).isEqualTo(DFAStates.FIRST_OF_YEAR);
        assertThat(sut.trans(DFAStates.FIRST_OF_YEAR, '9')).isEqualTo(DFAStates.DATE_STATE);
        assertThat(sut.trans(DFAStates.DATE_STATE, ' ')).isEqualTo(DFAStates.FAILURE);
    }

    @Test
    public void shouldReturnStart() {
        assertThat(sut.initial()).isEqualTo(DFAStates.START);
    }

    @Test
    public void shouldRecognizeStopStates() {
        // Stop states
        assertThat(sut.isStop(DFAStates.EOF)).isTrue();
        assertThat(sut.isStop(DFAStates.FAILURE)).isTrue();

        // No stop states
        assertThat(sut.isStop(DFAStates.DATE_STATE)).isFalse();
        assertThat(sut.isStop(DFAStates.IDENTIFIER)).isFalse();
        assertThat(sut.isStop(DFAStates.INTCONS)).isFalse();
        assertThat(sut.isStop(DFAStates.PM)).isFalse();
        assertThat(sut.isStop(DFAStates.WS)).isFalse();
    }

    @Test
    public void shouldRecognizeFinalStates() {
        // Valid final states
        assertThat(sut.isFinal(DFAStates.WS)).isTrue();
        assertThat(sut.isFinal(DFAStates.PM)).isTrue();
        assertThat(sut.isFinal(DFAStates.IDENTIFIER)).isTrue();
        assertThat(sut.isFinal(DFAStates.INTCONS)).isTrue();
        assertThat(sut.isFinal(DFAStates.FIRST_OF_DAY)).isTrue();
        assertThat(sut.isFinal(DFAStates.SECOND_OF_DAY)).isTrue();
        assertThat(sut.isFinal(DFAStates.DATE_STATE)).isTrue();

        // No final states
        assertThat(sut.isFinal(DFAStates.EOF)).isFalse();
        assertThat(sut.isFinal(DFAStates.START)).isFalse();
        assertThat(sut.isFinal(DFAStates.FAILURE)).isFalse();
        assertThat(sut.isFinal(DFAStates.DAY_STATE)).isFalse();
        assertThat(sut.isFinal(DFAStates.FIRST_OF_MONTH)).isFalse();
        assertThat(sut.isFinal(DFAStates.SECOND_OF_MONTH)).isFalse();
        assertThat(sut.isFinal(DFAStates.MONTH_STATE)).isFalse();
        assertThat(sut.isFinal(DFAStates.FIRST_OF_YEAR)).isFalse();
    }

    @Test
    public void shouldRecognizeRandomGarbage() {
        assertThat(sut.trans(DFAStates.START, '$')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '$')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '2')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '2')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, 'a')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '.')).isEqualTo(DFAStates.FAILURE);

        assertThat(sut.trans(DFAStates.START, '&')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '$')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '2')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '2')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, 'a')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, ',')).isEqualTo(DFAStates.FAILURE);

        assertThat(sut.trans(DFAStates.START, '$')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '$')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '2')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '2')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, 'a')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, ' ')).isEqualTo(DFAStates.FAILURE);

        assertThat(sut.trans(DFAStates.START, '$')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '$')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '2')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '2')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, 'a')).isEqualTo(DFAStates.DEFAULT_STATE);
        assertThat(sut.trans(DFAStates.DEFAULT_STATE, '\uFFFF')).isEqualTo(DFAStates.FAILURE);
    }
}
