package framework;

/**
 * Created by Matthias on 14.05.2016.
 */
public class Presenter implements IPresenter {
    private final ITokenSequence s1, s2;
    private final ILexer lexer;
    private final IAlignmentMatrix matrix;
    private final IScoring score;

    public Presenter(ITokenSequence s1, ITokenSequence s2, ILexer lexer, IAlignmentMatrix matrix, IScoring score) { //TODO: Implement with REGION
        this.s1 = s1;
        this.s2 = s2;
        this.lexer = lexer;
        this.matrix = matrix;
        this.score = score;
    }

    @Override
    public String backward() {
        StringBuilder tokenOutput1 = new StringBuilder();
        StringBuilder tokenConsensus = new StringBuilder();
        StringBuilder tokenOutput2 = new StringBuilder();

        int i = matrix.getWidth(); //Die Breite sind die Reihen
        int j = matrix.getLength(); //Die Länge die Spalten
        while (i >= 0 && j >= 0) {
            switch (matrix.get(i, j).getDirection()) {

                case DIAGONAL_MOVE:
                    String input1 = lexer.decode(s1.getToken(i - 1));
                    String input2 = lexer.decode(s2.getToken(j - 1));
                    int lengthOfInput1 = input1.length();
                    int lengthOfInput2 = input2.length();
                    int delta = 0;
                    if (lengthOfInput1 > lengthOfInput2) {
                        delta = lengthOfInput1 - lengthOfInput2;
                        input2 = normalizeStrings(input2, delta);
                    } else if (lengthOfInput2 > lengthOfInput1) {
                        delta = lengthOfInput2 - lengthOfInput1;
                        input1 = normalizeStrings(input1, delta);
                    }
                    tokenOutput1.append(reverseSring(input1)); //Wird reversed eingetragen da der String nicht vorangestellt werden kann. Am Ende wird der ganze Output reversed.
                    tokenOutput2.append(reverseSring(input2)); //""
                    if (score.isPerfect(matrix.get(i, j).getValue())) {
                        tokenConsensus.append(tokenOutput1); //Resultat aus input1 vor den aktuellen Consensus
                    } else {
                        tokenConsensus.append(producePlusString(delta)); // Produziere einen String "++++" mit länge max und hänge ihn vor den aktuellen consensus
                    }
                    i = i - 1;
                    j = j - 1;
                    break;
                case HORIZONTAL_MOVE:
                    break;
                case VERTICAL_MOVE:
                    input1 = lexer.decode(s1.getToken(i - 1));
                    tokenOutput1.append(reverseSring(input1));
                    StringBuilder minusString = produceMinusString(input1.length());
                    tokenOutput2.append(minusString);
                    tokenConsensus.append(minusString);
                    i = i - 1;
                    break;
            }
        }
        return null;
    }

    @Override
    public String threeColumnOutput() {
        return null;
    }

    private String normalizeStrings(String input, int delta) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(input);
        for (int i = 0; i < delta; i++) {
            stringBuilder.append(" "); //append spaces to make length equal
        }
        String normalizedString = stringBuilder.toString();
        return normalizedString;
    }

    private String reverseSring(String input) {
        StringBuilder reverseString = new StringBuilder();
        reverseString.append(input);
        reverseString.reverse();
        String reversedString = reverseString.toString();
        return reversedString;
    }

    private String producePlusString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append("+");
        }
        String ouput = stringBuilder.toString();
        return ouput;
    }

    private StringBuilder produceMinusString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append("-");
        }
        return stringBuilder;
    }
}
