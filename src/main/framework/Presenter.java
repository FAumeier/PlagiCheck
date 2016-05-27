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
    public String backward(boolean decodeTokens) {
        StringBuilder[] stringBuilder = getTokenOutputs(decodeTokens);
        String tokenOutput1 = stringBuilder[0].toString();
        String tokenOutput2 = stringBuilder[1].toString();
        String tokenConsensus = stringBuilder[2].toString();
        return "\n" + "Original: \t" + tokenOutput1 + "\n" + "Consensus: \t" + tokenConsensus + "\n" + "Suspect: \t" + tokenOutput2;
    }

    @Override
    public String threeColumnOutput(boolean decodeTokens) {
        int width = 40;
        if (width < 10) { //Will nicht jeden kleinen Fall programmieren. Breite wird immer größer gleich 10 sein
            width = 10;
        }
        StringBuilder[] stringBuilders = getTokenOutputs(decodeTokens);
        StringBuilder tokenOutput1 = stringBuilders[0];
        StringBuilder tokenOutput2 = stringBuilders[1];
        StringBuilder tokenConsensus = stringBuilders[2];
        StringBuilder output = new StringBuilder();

        int length = tokenOutput1.length();
        if (width > length) { //Falls jemals mehr spaltenbreite als textlänge angegeben wird. Wird korrigiert
            width = length;
        }
        output.append(String.format("%-" + width + "s|%-" + width + "s|%-" + width + "s\n", "Input1:", "Consensus:", "Input2:"));
        int widthInLength = length / width; //Wie oft passt die spaltenbreite in die Sequenz länge
        int begin = 0;
        int end = width;
        /*if (widthInLength == 1) { //Damit falls abgerundet wird sicher noch das letzte zeichen mitgenommen wird. Wenns aufgeht wird eine Zeile zuviel gezeichnet
            widthInLength++;
        }*/
        for (int i = 0; i <= widthInLength; i++) {
            // Formatierte Ausgabe aller 3 Spalten
            output.append(String.format("%-" + width + "s|%-" + width + "s|%-" + width + "s\n",
                    tokenOutput1.subSequence(begin, end).toString().replace("\n", "").replace("\r", ""),
                    tokenConsensus.subSequence(begin, end).toString().replace("\n", "").replace("\r", ""),
                    tokenOutput2.subSequence(begin, end).toString().replace("\n", "").replace("\r", "")));

            begin = end;
            end = end + width;
            if (end > length) {
                end = length; //Um StringIndexOutOfbouns zu verhindern
            }
        }
        return output.toString();
    }

    private StringBuilder[] getTokenOutputs(boolean decodeTokens) {
        StringBuilder tokenOutput1 = new StringBuilder();
        StringBuilder tokenConsensus = new StringBuilder();
        StringBuilder tokenOutput2 = new StringBuilder();

        int i = matrix.getLength() - 1; //Die Breite sind die Reihen; -1 da die Matrix um 1 größer als die sequenz ist.
        int j = matrix.getWidth() - 1; //Die Länge die Spalten
        int currentS1 = s1.length() - 2;
        int currentS2 = s2.length() - 2;
        String input1, input2;

        while (i != 0 && j != 0) {
            switch (matrix.get(i, j).getDirection()) {
                case DIAGONAL_MOVE:
                    if (decodeTokens) {
                        input1 = lexer.decode(s1.getToken(i - 1)); //Hole beide Token
                        input2 = lexer.decode(s2.getToken(j - 1));
                        currentS1--;
                        currentS2--;
                    }
                    else {
                        input1 = s1.getToken(i - 1).toString();
                        input2 = s2.getToken(j - 1).toString();
                        currentS1--;
                        currentS2--;
                    }
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
                    else {
                        max = lengthOfInput1;
                    }
                    tokenOutput1.append(reverseString(input1)); //Wird reversed eingetragen da der String nicht vorangestellt werden kann. Am Ende wird der ganze Output reversed.
                    tokenOutput2.append(reverseString(input2)); //Stelle das Resultat aus input2 vor den aktuellen TokenOutput2
                    double diff = matrix.get(i, j).getValue() - matrix.get(i-1, j-1).getValue();
                    if (score.isPerfect(Math.round(diff * 100000 ) / 100000)) { //Prüfe matrix.get(i,j) mit Hilfe von score.isPerfect()
                        tokenConsensus.append(reverseString(input1)); //Resultat aus input1 vor den aktuellen Consensus
                    }
                    else if (score.isNearMatch(diff)) {
                        tokenConsensus.append(produceString(max, "+")); // Produziere einen String "++++" mit länge max und hänge ihn vor den aktuellen Consensus
                    }
                    // Tritt hier nie ein! Daher wie in Vorgabe, nearmatch Zeichen +. Hier im Diagonal Move muss es ein Perfect oder Nearmatch sein...
                    else {
                        tokenConsensus.append(produceString(max, "-")); // Produziere einen String "++++" mit länge max und hänge ihn vor den aktuellen Consensus
                    }
                    i = i - 1;
                    j = j - 1;
                    break;
                case HORIZONTAL_MOVE:
                    if (decodeTokens) {
                        input2 = lexer.decode(s2.getToken(j - 1)); //hole token aus input2
                        currentS2--;
                    }
                    else {
                        input2 = s2.getToken(j - 1).toString();
                        currentS2--;
                    }
                    tokenOutput2.append(reverseString(input2)); //stelle das resultat aus input2 vor den aktuellen tokenconsensus
                    String minusStr1 = produceString(input2.length(), "-"); //produziere einen String mit bindestrichen gleicher länge
                    tokenOutput1.append(minusStr1); //Stelle den BindestrichString vor den aktuellen tokenOuput1
                    tokenConsensus.append(minusStr1); //Stelle den BindestrichString vor den aktuellen TokenConsensus
                    j = j - 1;
                    break;
                case VERTICAL_MOVE:
                    if (decodeTokens) {
                        input1 = lexer.decode(s1.getToken(i - 1));
                        currentS1--;
                    }
                    else {
                        input1 = s1.getToken(i - 1).toString();
                        currentS1--;
                    }
                    tokenOutput1.append(reverseString(input1));
                    String minusStr2 = produceString(input1.length(), "-");
                    tokenOutput2.append(minusStr2);
                    tokenConsensus.append(minusStr2);
                    i = i - 1;
                    break;
            }
        }

        //Hänge verbleibende Tokens
        while (currentS1 >= 0) {
            if (decodeTokens) {
                input1 = lexer.decode(s1.getToken(currentS1)); //hole token aus input2
            }
            else {
                input1 = s1.getToken(currentS1).toString();
            }
            currentS1--;

            tokenOutput1.append(reverseString(input1));
            String minus = produceString(input1.length(), "-");
            tokenOutput2.append(minus);
            tokenConsensus.append(minus);
        }
        while (currentS2 >= 0) {
            if (decodeTokens) {
                input2 = lexer.decode(s2.getToken(currentS2)); //hole token aus input2
            }
            else {
                input2 = s2.getToken(currentS2).toString();
            }
            currentS2--;

            tokenOutput2.append(reverseString(input2));
            String minus = produceString(input2.length(), "-");
            tokenOutput1.append(minus);
            tokenConsensus.append(minus);
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

    private String produceString(int length, String sign) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(sign);
        }
        return stringBuilder.toString();
    }
}
