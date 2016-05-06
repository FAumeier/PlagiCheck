package framework;

import java.io.*;

/**
 * Created by Matthias on 19.03.2016.
 */
class AlignmentController {
    final private String original;
    final private String suspect;
    final private ISelector selector;

    public AlignmentController(String original, String suspect) {
        this.original = original;
        this.suspect = suspect;

        selector = new SimpleSelector(new Region(0, getSize(original), 0, getSize(suspect)));
    }

    private int getSize(String filename) {
        File thisFile = new File(filename);
        return (int) thisFile.length();
    }

    public void run() throws Exception {
        // Read first file
        InputStream isStreamOriginal = new FileInputStream(original);
        Reader readerOriginal = new InputStreamReader(isStreamOriginal, "UTF-8");
        PushbackReader inputOriginal = new PushbackReader(readerOriginal, 4);

        // Read second file
        InputStream isStreamSuspect = new FileInputStream(original);
        Reader readerSuspect = new InputStreamReader(isStreamSuspect, "UTF-8");
        PushbackReader inputSuspect = new PushbackReader(readerSuspect, 4);

        // Token loop for first file
        ILexer lexer = new BaseLexer(inputOriginal);
        IToken token;
        do {
            token = lexer.getNextToken();
        }
        while (token.getClassCode() != ClassCodes.EOF);

        System.out.println(lexer.toString());
        //@TODO: Lexer für zweiten Input einbinden + Leseschleife
    }
}