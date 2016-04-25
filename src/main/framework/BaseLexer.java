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
                    String tokenString = tokenBuffer.substring(0, lastFinalPosition + 1); //end muss inklusive der lastfinalposition sein TODO: Check this!
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
                    /*char[] temp = new char[tokenBuffer.length()]; //TODO: This a waste of Memory
                    int end = lastFinalPosition + 1;
                    if (position > end) {
                        tokenBuffer.getChars(end, position, temp, 0); //TODO: use this in a smarter way
                    } else if (position < end) {
                        tokenBuffer.getChars(position, end, temp, 0); //TODO: use this in a smarter way
                    } else {
                        temp[0] = tokenBuffer.charAt(position);
                    }
                    int numberOfActualCharsInArray = 0; //This is here to count the actual chars in the temp array
                    for (int counter = 0; counter < temp.length; counter++) {
                        if (temp[counter] != 0) {
                            numberOfActualCharsInArray++;
                        }
                    }
                    char[] charsToPushBack = new char[numberOfActualCharsInArray]; //This is the real array which stores all the chars we want to pushback onto the PushbackReader
                    //now add chars to push back to new array
                    for (int counter = 0; counter < numberOfActualCharsInArray; counter++) {
                        if (temp[counter] != 0) {
                            charsToPushBack[counter] = temp[counter];
                        }
                    }
                    reader.unread(charsToPushBack);*/
                    reader.unread(tokenBuffer.substring(lastFinalPosition +1, tokenBuffer.length()).toCharArray());

                    int relativeCode = (int) trieReference.getValue();
                    return new Token(classCode, relativeCode);
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

    @Override
    public String decode(IToken tk) {
        return null;
    }
}
