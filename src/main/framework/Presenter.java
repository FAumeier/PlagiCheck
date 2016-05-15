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

        int i = matrix.getLength() - 1; //Die Breite sind die Reihen; -1 da die Matrix um 1 größer als die sequenz ist.
        int j = matrix.getWidth() - 1; //Die Länge die Spalten
        while (i != 0 && j != 0) {
            switch (matrix.get(i, j).getDirection()) {

                case DIAGONAL_MOVE:
                    String input1 = lexer.decode(s1.getToken(i - 1)); //Hole beide Token
                    String input2 = lexer.decode(s2.getToken(j - 1));
                    int lengthOfInput1 = input1.length();
                    int lengthOfInput2 = input2.length();
                    int delta = 0;
                    int max = 0;
                    //mache diese Strings gleich lang:
                    if (lengthOfInput1 > lengthOfInput2) {
                        delta = lengthOfInput1 - lengthOfInput2;
                        max = lengthOfInput1;
                        input2 = normalizeStrings(input2, delta);
                    } else if (lengthOfInput2 > lengthOfInput1) {
                        delta = lengthOfInput2 - lengthOfInput1;
                        max = lengthOfInput2;
                        input1 = normalizeStrings(input1, delta);
                    }
                    tokenOutput1.append(reverseSring(input1)); //Wird reversed eingetragen da der String nicht vorangestellt werden kann. Am Ende wird der ganze Output reversed.
                    tokenOutput2.append(reverseSring(input2)); //Stelle das Resultat aus input2 vor den aktuellen TokenOutput2
                    if (score.isPerfect(Math.round(matrix.get(i, j).getValue() - matrix.get(i-1, j-1).getValue()))) { //Prüfe matrix.get(i,j) mit Hilfe von score.isPerfect()
                        tokenConsensus.append(input1); //Resultat aus input1 vor den aktuellen Consensus
                    } else {
                        tokenConsensus.append(producePlusString(max)); // Produziere einen String "++++" mit länge max und hänge ihn vor den aktuellen Consensus
                    }
                    i = i - 1;
                    j = j - 1;
                    break;
                case HORIZONTAL_MOVE:
                    input2 = lexer.decode(s2.getToken(j - 1)); //hole token aus input2
                    tokenOutput2.append(reverseSring(input2)); //stelle das resultat aus input2 vor den aktuellen tokenconsensus
                    StringBuilder minusString = produceMinusString(input2.length()); //produziere einen String mit bindestrichen gleicher länge
                    tokenOutput1.append(minusString); //Stelle den BindestrichString vor den aktuellen tokenOuput1
                    tokenConsensus.append(minusString); //Stelle den BindestrichString vor den aktuellen TokenConsensus
                    j = j - 1;
                    break;
                case VERTICAL_MOVE:
                    input1 = lexer.decode(s1.getToken(i - 1));
                    tokenOutput1.append(reverseSring(input1));
                    minusString = produceMinusString(input1.length());
                    tokenOutput2.append(minusString);
                    tokenConsensus.append(minusString);
                    i = i - 1;
                    break;
            }
        }
        tokenOutput1.reverse(); //Hier werden die Strings nun endgültig gedreht um sie in die richtige Reihenfolge zu bringen.
        tokenOutput2.reverse();
        tokenConsensus.reverse();
        return "\n" + "Original: \t" + tokenOutput1 + "\n" + "Consensus: \t" + tokenConsensus + "\n" + "Suspect: \t" + tokenOutput2;
    }

    @Override
    public String threeColumnOutput() {
        //TODO: Implement
        int breadth = 30;
        return null;
    }

    private String normalizeStrings(String input, int delta) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(input);
        for (int i = 0; i < delta; i++) {
            stringBuilder.append("."); //append spaces to make length equal
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
        for (int i = 0; i <= length; i++) { //FIXME: Maybe produces more + then needed
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
