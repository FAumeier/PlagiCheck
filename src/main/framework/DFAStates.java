package framework;

/**
 * Different DFA states mapped as integer values.
 */
public enum DFAStates {
    EOF(-1, "End of File"),
    START(0, "Start state"),
    FAILURE(1, "Failure state"),
    WS(2, "Whitespace state"),
    PM(3, "Punctuation mark state"),
    IDENTIFIER(4, "Identifier state"),
    INTCONS(5, "Integer constant state"),
    FIRST_OF_DAY(6, "First of day state"),
    SECOND_OF_DAY(7, "Second of day state"),
    DAY_STATE(8, "Day state"),
    FIRST_OF_MONTH(9, "First of month state"),
    SECOND_OF_MONTH(10, "Second of month state"),
    MONTH_STATE(11, "Month state"),
    FIRST_OF_YEAR(12, "First of year state"),
    THIRD_OF_YEAR(13, "Third of year state"),   // If you are in DATE_STATE and the year is in Format YYYY instead of YY
    DATE_STATE(15, "Date state"),
    DEFAULT_STATE(16, "Default");   // Collects random garbage

    private final int id;
    private final String description;


    DFAStates(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
