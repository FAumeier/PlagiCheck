package framework;

import java.util.Set;

/**
 * Deterministic state automate.
 */
public class DFA implements IDFA {
    @Override
    public DFAStates initial() {
        return DFAStates.START;
    }

    @Override
    public DFAStates trans(DFAStates state, int symbol) {
        return null;
    }

    @Override
    public boolean isFinal(DFAStates state) {
        if (state == DFAStates.WS
                || state == DFAStates.PM
                || state == DFAStates.IDENTIFIER
                || state == DFAStates.INTCONS
                || state == DFAStates.DATE_STATE) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isStop(DFAStates state) {
        if (state == DFAStates.EOF || state == DFAStates.FAILURE) {
            return true;
        }
        return false;
    }

    @Override
    public Set getTokenClasses() {
        return null;
    }
}
