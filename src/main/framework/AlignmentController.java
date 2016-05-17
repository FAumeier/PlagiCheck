package framework;

import java.io.*;

/**
 * Created by Matthias on 19.03.2016.
 */
class AlignmentController {
    final private String original;
    final private String suspect;

    public AlignmentController(String original, String suspect) {
        this.original = original;
        this.suspect = suspect;
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
        InputStream isStreamSuspect = new FileInputStream(suspect);
        Reader readerSuspect = new InputStreamReader(isStreamSuspect, "UTF-8");
        PushbackReader inputSuspect = new PushbackReader(readerSuspect, 4);

        // Token loop for first file
        //ILexer lexer = new FilterLexer(new BaseLexer(inputOriginal));
        ILexer lexer = new BaseLexer(inputOriginal);
        IToken token = null;
        ITokenSequence originalSequence = new TokenSequence();
        do {
            token = lexer.getNextToken();
            originalSequence.add(token);
        }
        while (token.getClassCode() != ClassCodes.EOF);

        //System.out.println(lexer.toString());
        lexer.setPushBackReader(inputSuspect);  //Change reader in Lexer to get one completed lexer for both files
        token = null;
        ITokenSequence suspectSequence = new TokenSequence();
        do {
            token = lexer.getNextToken();
            suspectSequence.add(token);;
        }
        while (token.getClassCode() != ClassCodes.EOF);


        IScoring scoring = new SimpleScoring(new NearMatcher(lexer));
        ISelector selector = new SimpleSelector(originalSequence, suspectSequence);
        IRegion region = selector.getRegion();
        IAligner aligner = new Aligner(region, scoring, originalSequence, suspectSequence);
        IAlignmentMatrix matrix = aligner.forward();

        matrix.printMatrix(originalSequence, suspectSequence);

        IPresenter presenter = new Presenter(originalSequence, suspectSequence, lexer, matrix, scoring);

        System.out.println(presenter.backward(true) + "\n");
        System.out.println(presenter.backward(false) + "\n");

        System.out.println(presenter.threeColumnOutput(true));



    }
}