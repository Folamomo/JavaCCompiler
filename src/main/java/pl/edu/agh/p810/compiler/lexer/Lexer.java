package pl.edu.agh.p810.compiler.lexer;

import pl.edu.agh.p810.compiler.model.Token;
import pl.edu.agh.p810.compiler.model.TokenType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Lexer {
    final static Map<String, TokenType> dictionary = new HashMap<>();
    static {
        dictionary.put("[", TokenType.LEFT_BRACKET);
        dictionary.put("]", TokenType.RIGHT_BRACKET);
        dictionary.put("{", TokenType.LEFT_BRACE);
        dictionary.put("}", TokenType.RIGHT_BRACE);
        dictionary.put("(", TokenType.LEFT_PARENTHESIS);
        dictionary.put(")", TokenType.RIGHT_PARENTHESIS);
        dictionary.put("<", TokenType.LOWER);
        dictionary.put(">", TokenType.GREATER);
        dictionary.put("<=", TokenType.LOWER_OR_EQUAL);
        dictionary.put(">=", TokenType.GREATER_OR_EQUAL);
        dictionary.put(".", TokenType.DOT);
        dictionary.put("++", TokenType.PLUS_PLUS);
        dictionary.put("--", TokenType.MINUS_MINUS);
        dictionary.put("&", TokenType.AMPERSAND);
        dictionary.put("*", TokenType.STAR);
        dictionary.put("+", TokenType.PLUS);
        dictionary.put("-", TokenType.MINUS);
        dictionary.put("!", TokenType.LOGIC_NOT);
        dictionary.put("/", TokenType.DIVIDE);
        dictionary.put("%", TokenType.MODULO);
        dictionary.put("<<", TokenType.BITWISE_LEFT);
        dictionary.put(">>", TokenType.BITWISE_RIGHT);
        dictionary.put("==", TokenType.EQUAL_TO);
        dictionary.put("!=", TokenType.NOT_EQUAL_TO);
        dictionary.put("^", TokenType.BITWISE_XOR);
        dictionary.put("|", TokenType.BITWISE_OR);
        dictionary.put("&&", TokenType.LOGIC_AND);
        dictionary.put("||", TokenType.LOGIC_OR);
        dictionary.put("?", TokenType.TERNARY_CONDITIONAL_LEFT);
        dictionary.put(":", TokenType.TERNARY_CONDITIONAL_RIGHT);
        dictionary.put(";", TokenType.SEMICOLON);
        dictionary.put("...", TokenType.ELLIPSIS);
        dictionary.put("=", TokenType.DIRECT_ASSIGNMENT);
        dictionary.put("*=", TokenType.PRODUCT_ASSIGNMENT);
        dictionary.put("/=", TokenType.QUOTIENT_ASSIGNMENT);
        dictionary.put("%=", TokenType.REMAINDER_ASSIGNMENT);
        dictionary.put("+=", TokenType.SUM_ASSIGNMENT);
        dictionary.put("-=", TokenType.DIFFERENCE_ASSIGNMENT);
        dictionary.put("<<=", TokenType.BITWISE_LEFT_ASSIGNMENT);
        dictionary.put(">>=", TokenType.BITWISE_RIGHT_ASSIGNMENT);
        dictionary.put("&=", TokenType.BITWISE_AND_ASSIGNMENT);
        dictionary.put("^=", TokenType.BITWISE_XOR_ASSIGNMENT);
        dictionary.put("|=", TokenType.BITWISE_OR_ASSIGNMENT);
        dictionary.put(",", TokenType.COMMA);
        dictionary.put("#", TokenType.HASHTAG);
        dictionary.put("##", TokenType.DOUBLE_HASHTAG);

        dictionary.put("auto", TokenType.AUTO);
        dictionary.put("break", TokenType.BREAK);
        dictionary.put("case", TokenType.CASE);
        dictionary.put("char", TokenType.CHAR);
        dictionary.put("const", TokenType.CONST);
        dictionary.put("continue", TokenType.CONTINUE);
        dictionary.put("default", TokenType.DEFAULT);
        dictionary.put("do", TokenType.DO);
        dictionary.put("double", TokenType.DOUBLE);
        dictionary.put("else", TokenType.ELSE);
        dictionary.put("enum", TokenType.ENUM);
        dictionary.put("extern", TokenType.EXTERN);
        dictionary.put("float", TokenType.FLOAT);
        dictionary.put("for", TokenType.FOR);
        dictionary.put("goto", TokenType.GOTO);
        dictionary.put("if", TokenType.IF);
        dictionary.put("int", TokenType.INT);
        dictionary.put("long", TokenType.LONG);
        dictionary.put("register", TokenType.REGISTER);
        dictionary.put("return", TokenType.RETURN);
        dictionary.put("short", TokenType.SHORT);
        dictionary.put("signed", TokenType.SIGNED);
        dictionary.put("sizeof", TokenType.SIZEOF);
        dictionary.put("static", TokenType.STATIC);
        dictionary.put("struct", TokenType.STRUCT);
        dictionary.put("switch", TokenType.SWITCH);
        dictionary.put("typedef", TokenType.TYPEDEF);
        dictionary.put("union", TokenType.UNION);
        dictionary.put("unsigned", TokenType.UNSIGNED);
        dictionary.put("void", TokenType.VOID);
        dictionary.put("volatile", TokenType.VOLATILE);
        dictionary.put("while", TokenType.WHILE);
    }
    int lineNr;

    public Lexer() {
        lineNr = 0;
    }

    public Stream<Token> getTokensStream(Stream<String> symbols){
        return symbols.map(this::createToken).filter(token -> !token.getValue().equals("\n"));
    }

    private Token createToken(String symbol){
        if(symbol.equals("\n")){
            lineNr++;
        }
        TokenType tokenType = dictionary.get(symbol) != null ? dictionary.get(symbol) : getTokenType(symbol);
        Token token = new Token(tokenType, symbol,lineNr);
        return token;
    }

    private TokenType getTokenType(String symbol){
        Pattern stringPattern = Pattern.compile("\".*\"");
        Matcher stringMatcher = stringPattern.matcher(symbol);
        if(stringMatcher.matches()) {
            return TokenType.STRING_LITERAL;
        }
        Pattern intPattern = Pattern.compile("\\d+");
        Matcher intMatcher = intPattern.matcher(symbol);
        if(intMatcher.matches()) {
           return TokenType.INT_LITERAL;
        }
        Pattern floatPattern = Pattern.compile("\\d+\\.\\d+");
        Matcher floatMatcher = floatPattern.matcher(symbol);
        if(floatMatcher.matches()){
            return TokenType.FLOAT_LITERAL;
        }
        return TokenType.IDENTIFIER;
    }
}
