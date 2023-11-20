public enum CharClass {
    LETTER(0),
    DIGIT(1),
    UNKNOWN(99),
    EOF(-1);

    private final int code;

    CharClass(final int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}