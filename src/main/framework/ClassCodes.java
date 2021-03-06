package framework;

/**
 * Class Codes for Tokenizer
 */
public enum ClassCodes {
    IDENTIFIER(0, "Identifier"),
    DATE(1, "Date"),
    WS(2, "Whitespace"),
    PMARK(3, "Punktuationmark"),
    INTCONS(4, "Integer constant"),
    EOF(5, "End of File"),
    ERROR(6, "Error");

    private final int id;
    private final String description;


    ClassCodes(int id, String description) {
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

    public String toShort() {
        switch (this) {
            case IDENTIFIER:
                return "ID";
            case DATE:
                return "DA";
            case WS:
                return "WS";
            case PMARK:
                return "PM";
            case INTCONS:
                return "IC";
            case EOF:
                return "EO";
            case ERROR:
                return "ER";
        }
        return "--";
    }
}
