public enum Token {
    INT_LIST(10),
    IDENT(11),
    ASSIGN_OP(20),
    ADD_OP(21),
    SUB_OP(22),
    MULT_OP(23),
    DIV_OP(24),
    LEFT_PAREN(25),
    RIGHT_PAREN(26),
    FOR_CODE(30),
    IF_CODE(31),
    ELSE_CODE(32),
    WHILE_CODE(33),
    DO_CODE(34),
    INT_CODE(35),
    FLOAT_CODE(36),
    SWITCH_CODE(37),
    EOF(-1);

    private final int code;

    Token(final int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}