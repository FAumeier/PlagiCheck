package framework;

import actionsPackage.IActionAtInsert;
import actionsPackage.StringCoding;
import mapPackage.TreeMapFactory;
import triePackage.ITrie;
import triePackage.ITrieReference;
import triePackage.Trie;

import java.io.IOException;
import java.io.PushbackReader;

/**
 * Base lexer which works with Class Codes and Relative Codes.
 */
public class BaseLexer implements ILexer {
    private final PushbackReader reader;
    private ITrie identifier, intcons, pmark, ws, date;
    IActionAtInsert stringCoding;

    private int position;
    private StringBuilder tokenBuffer;
    private int lastFinalPosition;
    private DFAStates lastFinalState;
    private DFAStates currentState;

    public BaseLexer(PushbackReader reader) {
        this.reader = reader;
        identifier = new Trie(new TreeMapFactory());
        intcons = new Trie(new TreeMapFactory());
        pmark = new Trie(new TreeMapFactory());
        ws = new Trie(new TreeMapFactory());
        date = new Trie(new TreeMapFactory());
        stringCoding = new StringCoding();
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
        position = 0;
        tokenBuffer = new StringBuilder();
        lastFinalPosition = 0;
        lastFinalState = null;
        DFA dfa = new DFA();
        currentState = dfa.initial();
        while (!dfa.isStop(currentState)) {
            char currentChar = (char) reader.read();
            currentState = dfa.trans(currentState, currentChar);
            if (dfa.isStop(currentState)) {
                lastFinalPosition = position;
                lastFinalState = currentState;
            }
            if (currentState == DFAStates.EOF) {
                //end of file
                return new Token(ClassCodes.EOF, -2);
            } else if (currentState == DFAStates.FAILURE) {
                if (lastFinalPosition > -1) {
                    String tokenString = tokenBuffer.substring(0, lastFinalPosition); //end muss inklusive der lastfinalposition sein TODO: Check this!
                    ITrieReference trieReference = null;
                    ClassCodes classCode = null;
                    switch (lastFinalState) {
                        case IDENTIFIER:
                            trieReference = identifier.insert(tokenString, stringCoding);
                            classCode = ClassCodes.IDENTIFIER;
                            break;
                        case INTCONS:
                            trieReference = intcons.insert(tokenString, stringCoding);
                            classCode = ClassCodes.INTCONS;
                            break;
                        case PM:
                            trieReference = pmark.insert(tokenString, stringCoding);
                            classCode = ClassCodes.PMARK;
                            break;
                        case WS:
                            trieReference = ws.insert(tokenString, stringCoding);
                            classCode = ClassCodes.WS;
                            break;
                        case DATE_STATE:
                            trieReference = date.insert(tokenString, stringCoding);
                            classCode = ClassCodes.DATE;
                            break;
                    }
                    //Unread der letzten Zeichen
                    int lengthOfTokenBuffer = position - (lastFinalPosition + 1);
                    for (int i = lengthOfTokenBuffer; i >= 0; i++) { //TODO: Check if length is correct
                        reader.unread(tokenBuffer.charAt(i));
                        tokenBuffer.deleteCharAt(i);
                    }

                    int relativeCode = (int) trieReference.getValue();
                    return new Token(classCode, relativeCode);
                } else if (lastFinalPosition == -1) {
                    final IToken ERROR_TOKEN = new Token(ClassCodes.ERROR, 0);
                    return ERROR_TOKEN;
                }
            } else {
                tokenBuffer.append(currentChar);
                position++;
            }
        }

        return null; //TODO: maybe this is not the proper way
    }

    @Override
    public String decode(IToken tk) {
        return null;
    }
}
