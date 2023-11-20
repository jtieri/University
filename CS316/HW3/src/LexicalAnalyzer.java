import java.io.BufferedReader;
import java.io.IOException;

public class LexicalAnalyzer {
    private CharClass charClass;
    private char nextChar;
    private Token nextToken;
    private String lexeme;
    private BufferedReader input;

    public LexicalAnalyzer(BufferedReader input) {
        this.input = input;
        this.lexeme = "";
        this.nextToken = null;
        this.nextChar = 0;
        this.charClass = null;
    }

    /* lookup - a function to lookup operators and parentheses and return the token */
    private void lookup(final char ch) {
        switch (ch) {
            case '(':
                addChar();
                nextToken = Token.LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = Token.RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = Token.ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = Token.SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = Token.MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = Token.DIV_OP;
                break;
            default:
                addChar();
                nextToken = Token.EOF;
                break;
        }
    }

    private Token keywordCheck(String lexeme) {
        switch (lexeme) {
            case "for":
                return Token.FOR_CODE;
            case "if":
                return Token.IF_CODE;
            case "else":
                return Token.ELSE_CODE;
            case "while":
                return Token.WHILE_CODE;
            case "do":
                return Token.DO_CODE;
            case "int":
                return Token.INT_CODE;
            case "float":
                return Token.FLOAT_CODE;
            case "switch":
                return Token.SWITCH_CODE;
            default:
                return null;
        }
    }

    /* addChar - a function to add nextChar to lexeme */
    private void addChar() {
        lexeme += nextChar;
    }

    /* getChar - a function to get the next character of input and determine its character class */
    public void getChar() {
        int i = -1;
        try { i = input.read(); } catch (IOException e) { e.printStackTrace(); }
        if (i != -1) {
            nextChar = (char) i;
            if (Character.isLetter(nextChar)) {
                charClass = CharClass.LETTER;
            } else if (Character.isDigit(nextChar)) {
                charClass = CharClass.DIGIT;
            } else {
                charClass = CharClass.UNKNOWN;
            }
        } else {
            charClass = CharClass.EOF;
        }
    }

    /* getNonBlank - a function to call getChar until it returns a non-whitespace character */
    private void getNonBlank() {
        while (Character.isWhitespace(nextChar)) {
            getChar();
        }
    }

    /* lex - a simple lexical analyzer for arithmetic expressions */
    public void lex() {
        getNonBlank();

        switch (charClass) {
            /* Parse identifiers */
            case LETTER:
                addChar();
                getChar();
                while (charClass == CharClass.LETTER || charClass == CharClass.DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = Token.IDENT;
                break;

            /* Parse integer literals */
            case DIGIT:
                addChar();
                getChar();
                while (charClass == CharClass.DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = Token.INT_LIST;
                break;

            /* Parentheses and operators */
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;

            /* EOF */
            case EOF:
                nextToken = Token.EOF;
                lexeme = "EOF";
                break;
        }

        /* Check for keyword */
        Token temp = keywordCheck(lexeme);
        if (temp != null) {
            nextToken = temp;
        }

        System.out.printf("Next token is: %d, Next lexeme is %s\n", nextToken.code(), lexeme);
        lexeme = "";
    }
}
