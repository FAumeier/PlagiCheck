package framework;

import java.util.Set;

/**
 * Interface for the deterministic state automate.
 */
public interface IDFA {
    /**
     * Returns the beginning state. Tells the lexer where to start.
     * @return the state to begin with
     */
    DFAStates initial();

    /**
     * Tells you the state you reach with the given character symbol.
     * @param state your current state
     * @param symbol your next symbol
     * @return the state you reach
     */
    DFAStates trans(DFAStates state, int symbol);

    /**
     * Only the DFA knows what a final state is.
     * @param state the state to check
     * @return whether the given state is final or not
     */
    boolean isFinal(DFAStates state);

    /**
     * Tells you if your state is a stop state. (error or EOF)
     * @param state the state to check
     * @return whether the given state is a stop state or not
     */
    boolean isStop(DFAStates state);

    /**
     * Returns the determined token class (class code). Should contain only one element!
     * @return a set with the determined token classes
     */
    Set getTokenClasses();
}
