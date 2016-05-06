package framework;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static framework.DFAStates.*;

/**
 * Deterministic state automate.
 */
public class DFA implements IDFA {
    private static final Set<Character> pm = new HashSet<>(Arrays.asList('.', ',', ':', ';', '!', '?'));

    @Override
    public DFAStates initial() {
        return DFAStates.START;
    }

    @Override
    public DFAStates trans(DFAStates state, int nextChar) {
        switch (state) {
            case EOF:
                break;
            case START:
                if (Character.isWhitespace(nextChar)) {
                    return WS;
                }
                else if (pm.contains((char) nextChar)) {
                    return PM;
                }
                else if (Character.isDigit(nextChar)) {
                    return FIRST_OF_DAY;
                }
                else if (Character.isAlphabetic(nextChar)) {
                    return IDENTIFIER;
                }
                else if (nextChar == '\uFFFF' ) { //EOF seems to be this strange Character... Not -1...
                    return EOF;
                }
                else {
                    return DEFAULT_STATE;
                }
            case FAILURE:
                break;
            case WS:
                if (Character.isWhitespace(nextChar)) {
                    return WS;
                }
                else {
                    return FAILURE;
                }
            case PM:
                return FAILURE;
            case IDENTIFIER:
                if (Character.isAlphabetic(nextChar)) {
                    return IDENTIFIER;
                }
                else {
                    return FAILURE;
                }
            case INTCONS:
                if (Character.isDigit(nextChar)) {
                    return INTCONS;
                }
                else {
                    return FAILURE;
                }
            case FIRST_OF_DAY:
                if (Character.isDigit(nextChar)) {
                    return SECOND_OF_DAY; //Laut Diagramm ist hier schon INTCONS erreicht!
                }
                else {
                    return FAILURE;
                }
            case SECOND_OF_DAY:
                if (nextChar == '.') {
                    return DAY_STATE;
                }
                else if (Character.isDigit(nextChar)) {
                    return INTCONS;
                }
                else {
                    return FAILURE;
                }
            case DAY_STATE:
                if (Character.isDigit(nextChar)) {
                    return FIRST_OF_MONTH;
                }
                else {
                    return FAILURE;
                }
            case FIRST_OF_MONTH:
                if (Character.isDigit(nextChar)) {
                    return SECOND_OF_MONTH;
                }
                else {
                    return FAILURE;
                }
            case SECOND_OF_MONTH:
                if (nextChar == '.') {
                    return MONTH_STATE;
                }
                else {
                    return FAILURE;
                }
            case MONTH_STATE:
                if (Character.isDigit(nextChar)) {
                    return FIRST_OF_YEAR;
                }
                else {
                    return FAILURE;
                }
            case FIRST_OF_YEAR:
                if (Character.isDigit(nextChar)) {
                    return DATE_STATE;
                }
                else {
                    return FAILURE;
                }
            case THIRD_OF_YEAR:
                if (Character.isDigit(nextChar)) {
                    return DATE_STATE;
                }
                else {
                    return FAILURE;
                }
            case DATE_STATE:
                if (Character.isDigit(nextChar)) {
                    return THIRD_OF_YEAR;
                }
                return FAILURE;
            case DEFAULT_STATE:
                if (pm.contains((char) nextChar) || Character.isWhitespace(nextChar) || (nextChar == '\uFFFF')) {
                    return FAILURE;
                }
                return DEFAULT_STATE;
        }
        return null;
    }

    @Override
    public boolean isFinal(DFAStates state) {
        return state == WS
                || state == PM
                || state == DFAStates.IDENTIFIER
                || state == DFAStates.INTCONS
                || state == DFAStates.DATE_STATE
                || state == DFAStates.FIRST_OF_DAY
                || state == DFAStates.SECOND_OF_DAY;
    }

    @Override
    public boolean isStop(DFAStates state) {
        return state == DFAStates.EOF || state == DFAStates.FAILURE;
    }

    @Override
    public Set getTokenClasses() {
        //@TODO: Was gehört hier denn rein?!
        return null;
    }
}
