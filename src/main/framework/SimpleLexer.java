package framework;

import actionsPackage.IActionAtInsert;
import actionsPackage.StringCoding;
import mapPackage.IMapFactory;
import mapPackage.TreeMapFactory;
import triePackage.ITrie;
import triePackage.Trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by Matthias on 19.03.2016.
 */
public class SimpleLexer implements ILexer {
    final private BufferedReader reader;
    final private IMapFactory mapFactory = new TreeMapFactory();    // DIC
    final private IActionAtInsert action = new StringCoding(4711);  // DIC
    final private ITrie trie;                                       // DIC
    //final private MapTokenToString tokenToString; decoding
    private String line;
    private StringTokenizer tk = null;

    public SimpleLexer(BufferedReader reader) throws Exception {
        this.reader = reader;
        line = reader.readLine();
        if (line != null) {
            tk = new StringTokenizer(line);
        }
        this.trie = new Trie(mapFactory);                           // DIC
        //this.tokenToString = new MapTokenToString(); decoding
    }

    public IToken getNextToken() throws IOException {
        return null;
        /*
        Log.println(Log.URGENT, "--> next token");
        ITrieReference ref = null;
        IToken result = null;
        boolean foundToken = false;
        boolean noMoreToken = false;
        do {    // Invariante: Es gibt einen Tokenizer; tk != null
            // Schleife dient nur zum Lesen der Zeilen
            result = null;
            if (tk != null) {
                if (tk.haseMoreTokens()) {
                    String intermediate = tk.nextToken();
                    Log.println(Log.URGENT, "--- next token: " + intermediate);
                    //@TODO: Später wenn Klassencode bekannt den richtigen Trie ansteuern
                    ref = trie.insert(intermediate, action);        // DIC
                    //@TODO: Später Extraktion des relative Codes aus ref und bilden des Tokens aus Klassencode und Relativcode
                    result = new Token(-1, -1); //@FIXME: Dummy!!!
                    foundToken = true;
                }
                else {  // Neue Zeile lesen
                    tk = null;
                    line = reader.readLine();
                    if (line != null) {
                        tk = new StringTokenizer(line);
                    }
                }
            }
            else {
                noMoreToken = true;
            }
        }
        while (!foundToken && !noMoreToken);
        Log.println(Log.URGENT, "<-- result token: " + result);
        return result;
        */
    }

    public String decode(IToken tk) throws UnsupportedOperationException {
        //@FIXME
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public String toString() {
        return "\nResult Trie \n" + trie;
    }
}