package framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

import actionsPackage.IActionAtInsert;
import actionsPackage.StringCoding;
import mapPackage.IMapFactory;
import mapPackage.TreeMapFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import triePackage.ITrie;
import triePackage.ITrieReference;
import triePackage.Trie;
import triePackage.TrieNode;

/**
 * Created by Matthias on 19.03.2016.
 */
public class SimpleLexer implements ILexer {
    static final Logger LOG = LogManager.getLogger(SimpleLexer.class.getName()); //Use for example: LOG.debug("any string");

    final private BufferedReader reader;
    final private IMapFactory mapFactory = new TreeMapFactory();    // DIC
    final private IActionAtInsert action = new StringCoding();  // DIC
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

        LOG.debug("--> next token");
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
                    LOG.debug("--- next token: " + intermediate);
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
        LOG.debug("<-- result token: " + result);
        return result;

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
