package pl.edu.agh.p810.compiler.model;

public class Token {
    private TokenType tokenType;
    private String value;
    private int lineNr;

    public Token(TokenType tokenType, String value, int lineNr) {
        this.tokenType = tokenType;
        this.value = value;
        this.lineNr = lineNr;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }

    public int getLineNr() {
        return lineNr;
    }
}
