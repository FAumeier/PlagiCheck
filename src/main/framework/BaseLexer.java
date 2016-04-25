package framework;

import actionsPackage.StringCoding;
import mapPackage.TreeMapFactory;
import triePackage.ITrie;
import triePackage.ITrieReference;
import triePackage.Trie;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Base lexer which works with Class Codes and Relative Codes.
 */
public class BaseLexer implements ILexer {
    private final PushbackReader reader;
    private Map<ClassCodes, ITrie> tries = new HashMap<>();

    private int position;
    private StringBuilder tokenBuffer;
    private int lastFinalPosition;
    private DFAStates lastFinalState;
    private DFAStates currentState;

    public BaseLexer(PushbackReader reader) {
        this.reader = reader;
        tries.put(ClassCodes.IDENTIFIER, new Trie(new TreeMapFactory(), new StringCoding()));
        tries.put(ClassCodes.INTCONS, new Trie(new TreeMapFactory(), new StringCoding()));
        tries.put(ClassCodes.PMARK, new Trie(new TreeMapFactory(), new StringCoding()));
        tries.put(ClassCodes.WS, new Trie(new TreeMapFactory(), new StringCoding()));
        tries.put(ClassCodes.DATE, new Trie(new TreeMapFactory(), new StringCoding()));
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
        position = -1;
        tokenBuffer = new StringBuilder();
        lastFinalPosition = -1;
        lastFinalState = null;
        DFA dfa = new DFA();
        currentState = dfa.initial();
        while (!dfa.isStop(currentState)) { //Solange bis Fehler- oder EOF- Zustand erreicht.

            if (dfa.isFinal(currentState)) {
                lastFinalPosition = position;
                lastFinalState = currentState;
            }
            char currentChar = (char) reader.read();
            if (currentState != DFAStates.EOF) {
                tokenBuffer.append(currentChar);
                position++;
            }
            currentState = dfa.trans(currentState, currentChar);
            if (currentState == DFAStates.FAILURE) {
                if (lastFinalPosition > -1) {
                    // Get ClassCode for last final state
                    ClassCodes classCode = getClassCodeFromState(lastFinalState);
                    // Insert final identified token into corresponding trie
                    ITrieReference trieReference = tries.get(classCode).
                            insert(tokenBuffer.substring(0, lastFinalPosition + 1));
                    // Push back characters which have been read to far
                    reader.unread(tokenBuffer.substring(lastFinalPosition +1, tokenBuffer.length()).toCharArray());
                    // Create and return token
                    return new Token(classCode, (int) trieReference.getValue());
                } else if (lastFinalPosition == -1) {
                    final IToken ERROR_TOKEN = new Token(ClassCodes.ERROR, 0);
                    return ERROR_TOKEN;
                }
            }
            if (currentState == DFAStates.EOF) {
                //end of file
                return new Token(ClassCodes.EOF, -2);
            }
        }

        return null; //TODO: maybe this is not the proper way
    }

    private ClassCodes getClassCodeFromState(DFAStates state) {
        switch (state) {
            case EOF:
                return ClassCodes.ERROR;
            case START:
                return ClassCodes.ERROR;
            case FAILURE:
                return ClassCodes.ERROR;
            case WS:
                return ClassCodes.WS;
            case PM:
                return ClassCodes.PMARK;
            case IDENTIFIER:
                return ClassCodes.IDENTIFIER;
            case INTCONS:
                return ClassCodes.INTCONS;
            case FIRST_OF_DAY:
                return ClassCodes.INTCONS;
            case SECOND_OF_DAY:
                return ClassCodes.INTCONS;
            case DAY_STATE:
                return ClassCodes.ERROR; // Can´t be the final state of a token either INTCONS or full DATE
            case FIRST_OF_MONTH:
                return ClassCodes.ERROR; // Can´t be the final state of a token either INTCONS or full DATE
            case SECOND_OF_MONTH:
                return ClassCodes.ERROR; // Can´t be the final state of a token either INTCONS or full DATE
            case MONTH_STATE:
                return ClassCodes.ERROR; // Can´t be the final state of a token either INTCONS or full DATE
            case FIRST_OF_YEAR:
                return ClassCodes.ERROR; // Can´t be the final state of a token either INTCONS or full DATE
            case SECOND_OF_YEAR:
                return ClassCodes.ERROR; // Can´t be the final state of a token either INTCONS or full DATE
            case DATE_STATE:
                return ClassCodes.DATE;
            default:
                return ClassCodes.ERROR;
        }
    }

    @Override
    public String decode(IToken tk) {
        return null;
    }
}
