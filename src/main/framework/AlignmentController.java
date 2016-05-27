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
        PushbackReader inputOriginal = new PushbackReader(readerOriginal, 10);

        // Read second file
        InputStream isStreamSuspect = new FileInputStream(suspect);
        Reader readerSuspect = new InputStreamReader(isStreamSuspect, "UTF-8");
        PushbackReader inputSuspect = new PushbackReader(readerSuspect, 10);

        // Token loop for first file
        //ILexer lexer = new FilterLexer(new BaseLexer(inputOriginal));
        ILexer lexer = new BaseLexer(inputOriginal);
        IToken token = null;
        ITokenSequence originalSequence = new TokenSequence();
        do {
            token = lexer.getNextToken();
            if (token.getClassCode() != ClassCodes.ERROR) {
                originalSequence.add(token);
            }
        }
        while (token.getClassCode() != ClassCodes.EOF);

        //System.out.println(lexer.toString());
        lexer.setPushBackReader(inputSuspect);  //Change reader in Lexer to get one completed lexer for both files
        token = null;
        ITokenSequence suspectSequence = new TokenSequence();
        int test = 0;
        do {
            token = lexer.getNextToken();
            if (token.getClassCode() != ClassCodes.ERROR) {
                suspectSequence.add(token);
            }
            test++;
        }
        while (token.getClassCode() != ClassCodes.EOF);

        // Kontrollausgaben
        // 1. Tries für alle ClassCodes ausgeben
        System.out.println("-------------------------- Alle Tries ------------------------------");
        System.out.println(lexer);
        // 2. Tokensequenzen ausgeben
        System.out.println("----------------------- TokenSequenzen (Token & decoded Token ) ----");
        String seq1 = "Original Sequenz: ";
        String seq1decode = "Original Sequenz: ";
        for (int i = 0; i < originalSequence.length() - 1; i++) {
            IToken currentToken = originalSequence.getToken(i);
            seq1 += currentToken;
            seq1decode += lexer.decode(currentToken);
        }
        String seq2 = "Suspect Sequenz:  ";
        String seq2decode = "Suspect Sequenz:  ";
        for (int i = 0; i < suspectSequence.length() - 1; i++) {
            IToken currentToken = suspectSequence.getToken(i);
            seq2 += currentToken;
            seq2decode += lexer.decode(currentToken);
        }
        System.out.println(seq1);
        System.out.println(seq2 + "\n");
        System.out.println(seq1decode);
        System.out.println(seq2decode + "\n");

        IScoring scoring = new SimpleScoring(new NearMatcher(lexer));
        ISelector selector = new SimpleSelector(originalSequence, suspectSequence);
        IRegion region = selector.getRegion();
        IAligner aligner = new Aligner(region, scoring, originalSequence, suspectSequence);
        IAlignmentMatrix matrix = aligner.forward();

        // 3. Scoring
        System.out.println("------------------------ Scoring -----------------------------------");
        System.out.println(scoring + "\n");

        // 4. Alignment Matrix
        System.out.println("------------------------ Alignment Matrix --------------------------");
        matrix.printMatrix(originalSequence, suspectSequence);

        IPresenter presenter = new Presenter(originalSequence, suspectSequence, lexer, matrix, scoring);

        // 5. Drei-Zeilen-Layout
        System.out.println("----------------- 3-Zeilen-Layout (Klartext & Token) ---------------");
        System.out.println(presenter.backward(true) + "\n");
        System.out.println(presenter.backward(false) + "\n");

        // 6. Drei-Spalten-Layout
        System.out.println("--------------------- 3-Spalten-Layout -----------------------------");
        System.out.println(presenter.threeColumnOutput(true));

    }
}