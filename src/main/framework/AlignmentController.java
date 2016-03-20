package framework;

import java.io.*;

/**
 * Created by Matthias on 19.03.2016.
 */
public class AlignmentController {
    final private String original;
    final private String suspect;

    public AlignmentController(String original, String suspect) {
        this.original = original;
        this.suspect = suspect;
    }

    public void run() throws Exception {
        // Read first file
        InputStream istreamOriginal = new FileInputStream(original);
        Reader readerOriginal = new InputStreamReader(istreamOriginal);
        BufferedReader inputOriginal = new BufferedReader(readerOriginal);

        //@TODO: Zweites File einlesen

        ILexer lexer = new SimpleLexer(inputOriginal);
        IToken token = lexer.getNextToken();
        while (token != null) {
            System.out.println("Gelesen: " + token);
            token = lexer.getNextToken();
        }

        //@TODO: Lexer für zweiten Input einbinden + Leseschleife
    }
}