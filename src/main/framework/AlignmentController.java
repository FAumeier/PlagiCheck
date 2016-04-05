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
        InputStream isStreamOriginal = new FileInputStream(original);
        Reader readerOriginal = new InputStreamReader(isStreamOriginal, "UTF-8");
        BufferedReader inputOriginal = new BufferedReader(readerOriginal);

        //@TODO: Zweites File einlesen

        //ILexer lexer = new SimpleLexer(inputOriginal);
        //IToken token = lexer.getNextToken();
        SimpleLexer lexer = new SimpleLexer(inputOriginal);
        String token = lexer.getNextStringToken();
        while (token != null) {
            System.out.println("Gelesen: " + token);
            //token = lexer.getNextToken();
            token = lexer.getNextStringToken();
        }
        System.out.println(lexer.toString());
        //@TODO: Lexer für zweiten Input einbinden + Leseschleife
    }
}