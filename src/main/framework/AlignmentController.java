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
        Reader readerOriginal = new InputStreamReader(isStreamOriginal); //FIXME: Bug: Found a call to a method which will perform a byte to String (or String to byte) conversion, and will assume that the default platform encoding is suitable. This will cause the application behaviour to vary between platforms. Use an alternative API and specify a charset name or Charset object explicitly.
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