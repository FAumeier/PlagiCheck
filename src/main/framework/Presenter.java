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
        StringBuilder[] stringBuilder = getTokenOutputs();
        String tokenOutput1 = stringBuilder[0].toString();
        String tokenOutput2 = stringBuilder[1].toString();
        String tokenConsensus = stringBuilder[2].toString();
        return "\n" + "Original: \t" + tokenOutput1 + "\n" + "Consensus: \t" + tokenConsensus + "\n" + "Suspect: \t" + tokenOutput2;
    }

    @Override
    public String threeColumnOutput() {
        int breadth = 11;
        if (breadth < 10) { //Will nicht jeden kleinen Fall programmieren. Breadth wird immer größer gleich 10 sein
            breadth = 10;
        }
        StringBuilder[] stringBuilders = getTokenOutputs();
        StringBuilder tokenOutput1 = stringBuilders[0];
        StringBuilder tokenOutput2 = stringBuilders[1];
        StringBuilder tokenConsensus = stringBuilders[2];
        StringBuilder output = new StringBuilder();
        int padding = 0;
        int length = tokenOutput1.length();
        if (breadth > length) { //Falls jemals mehr spaltenbreite als textlänge angegeben wird. Wird korrigiert
            breadth = length;
        }
        output = buildHeader(output, breadth - 10); //den header aufbauen und anhängen
        output.append('\n');
        int breadthsInLength = length / breadth; //Wie oft passt die spaltenbreite in die Sequenz länge
        int begin = 0;
        int end = breadth;
        if (breadthsInLength == 1) { //Damit falls abgerundet wird sicher noch das letzte zeichen mitgenommen wird. Wenns aufgeht wird eine Zeile zuviel gezeichnet
            breadthsInLength++;
        }
        for (int i = 0; i < breadthsInLength; i++) {
            if (i + 1 == breadthsInLength) {
                if ((end - begin) < breadth) {
                    padding = breadth - (end - begin); //Anzahl der Spaces die die Spaltenbreite auffüllen
                }
                output.append(tokenOutput1.subSequence(begin, end));
                output = fill(output, padding);
                output.append('|');
                output.append(tokenConsensus.subSequence(begin, end));
                output = fill(output, padding);
                output.append('|');
                output.append(tokenOutput2.subSequence(begin, end));
                output.append('\n');
            } else {
                output.append(tokenOutput1.subSequence(begin, end));
                output = fill(output, padding);
                output.append('|');
                output.append(tokenConsensus.subSequence(begin, end));
                output = fill(output, padding);
                output.append('|');
                output.append(tokenOutput2.subSequence(begin, end));
                output.append('\n');
            }
            begin = end;
            end = end + breadth;
            if (end > length) {
                end = length; //Um StringIndexOutOfbouns zu verhindern
            }
        }
        return output.toString();
    }

    private StringBuilder buildHeader(StringBuilder builder, int length) {
        String input1 = "Input 1:";//8
        String input2 = "Input 2:";
        String consensus = "Consensus:";
        StringBuilder builder1 = builder;
        builder.append(input1);
        builder = fill(builder, length + 2); //+2 da consensus 2 buchstaben mehr hat. Soll den Header auf eine Einheitliche Länge bringen.
        builder.append('|');
        builder.append(consensus);
        builder = fill(builder, length);
        builder.append('|');
        builder.append(input2);
        builder = fill(builder, length + 2);
        return builder;
    }

    private StringBuilder fill(StringBuilder stringBuilder, int length) {
        StringBuilder stringBuilder1 = stringBuilder;
        for (int i = 0; i < length; i++) {
            stringBuilder.append(' ');
        }
        return stringBuilder;
    }

    private StringBuilder[] getTokenOutputs() {
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
                    }
                    else if (lengthOfInput2 > lengthOfInput1) {
                        delta = lengthOfInput2 - lengthOfInput1;
                        max = lengthOfInput2;
                        input1 = normalizeStrings(input1, delta);
                    }
                    tokenOutput1.append(reverseString(input1)); //Wird reversed eingetragen da der String nicht vorangestellt werden kann. Am Ende wird der ganze Output reversed.
                    tokenOutput2.append(reverseString(input2)); //Stelle das Resultat aus input2 vor den aktuellen TokenOutput2
                    double diff = matrix.get(i, j).getValue() - matrix.get(i-1, j-1).getValue();
                    if (score.isPerfect(Math.round(diff * 100000 ) / 100000)) { //Prüfe matrix.get(i,j) mit Hilfe von score.isPerfect()
                        tokenConsensus.append(input1); //Resultat aus input1 vor den aktuellen Consensus
                    }
                    else if (score.isNearMatch(diff)) {
                        tokenConsensus.append(produceString(max, "*")); // Produziere einen String "****" mit länge max und hänge ihn vor den aktuellen Consensus
                    }
                    else {
                        tokenConsensus.append(produceString(max, "+")); // Produziere einen String "++++" mit länge max und hänge ihn vor den aktuellen Consensus
                    }
                    i = i - 1;
                    j = j - 1;
                    break;
                case HORIZONTAL_MOVE:
                    input2 = lexer.decode(s2.getToken(j - 1)); //hole token aus input2
                    tokenOutput2.append(reverseString(input2)); //stelle das resultat aus input2 vor den aktuellen tokenconsensus
                    String minusStr1 = produceMinusString(input2.length()); //produziere einen String mit bindestrichen gleicher länge
                    tokenOutput1.append(minusStr1); //Stelle den BindestrichString vor den aktuellen tokenOuput1
                    tokenConsensus.append(minusStr1); //Stelle den BindestrichString vor den aktuellen TokenConsensus
                    j = j - 1;
                    break;
                case VERTICAL_MOVE:
                    input1 = lexer.decode(s1.getToken(i - 1));
                    tokenOutput1.append(reverseString(input1));
                    String minusStr2 = produceMinusString(input1.length());
                    tokenOutput2.append(minusStr2);
                    tokenConsensus.append(minusStr2);
                    i = i - 1;
                    break;
            }
        }


        StringBuilder[] outputs = new StringBuilder[3];
        outputs[0] = tokenOutput1.reverse(); //Hier werden die Strings nun endgültig gedreht um sie in die richtige Reihenfolge zu bringen.
        outputs[1] = tokenOutput2.reverse();
        outputs[2] = tokenConsensus.reverse();
        return outputs;
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

    private String reverseString(String input) {
        StringBuilder reverseString = new StringBuilder();
        reverseString.append(input);
        reverseString.reverse();
        String reversedString = reverseString.toString();
        return reversedString;
    }

    private String producePlusString(int length) {
        return produceString(length, "+");
    }

    private String produceMinusString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append("-");
        }
        return stringBuilder.toString();
    }

    private String produceString(int length, String sign) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= length; i++) { //FIXME: Maybe produces more then needed
            stringBuilder.append(sign);
        }
        return stringBuilder.toString();
    }
}
