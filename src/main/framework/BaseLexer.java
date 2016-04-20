package framework;

import java.io.IOException;
import java.io.PushbackReader;

/**
 * Base lexer which works with Class Codes and Relative Codes.
 */
public class BaseLexer implements ILexer {
    private final PushbackReader reader;

    private int position;
    private StringBuilder tokenBuffer;
    private int lastFinalPosition;
    private DFAStates lastFinalState;
    private DFAStates currentState;

    public BaseLexer(PushbackReader reader) {
        this.reader = reader;
    }
    /*
    der lexer nutzt den DFA
    - er merkt sich den aktuellen state
    - liest das nächste zeichen aus dem PushbackReader
    - merkt sich bereits passende Tokenstrings
    - trägt tokenstrings in passende tries
    - zurückschieben von zuviel gelesenen zeichen auf den pushbackreader
    - generieren eines tokens aus dem klassencode - geliefert vom DFA und dem aus dem Trie gelieferten Relativcode
    - Rückgabe des tokens an den Aufrufer

    Landet immer im fehlerzustand, liest dann jedoch weiter bis er einen TOken mit Final/Endzustand erkannt hat.

    Stop-Zustand ist nicht Endzustand!
    Finalzustand merkt sich nur aktuell erkannten token, macht dann weiter (bspw. Integer kann zu date werden...)
     */


    @Override
    public IToken getNextToken() throws IOException {
        return null;
    }

    @Override
    public String decode(IToken tk) {
        return null;
    }
}
