package pl.edu.agh.p810.compiler.lexer;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Stream;

public class Tokenizer {
    private BufferedReader reader;

    boolean inComment = false;
    boolean inStringLiteral = false;

    public Tokenizer(BufferedReader reader) {
        this.reader = reader;
    }

    public Stream<String> getTokens(){
        return reader.lines().flatMap(this::tokenizeLine);
    }

    private Stream<String> tokenizeLine(String line){
        boolean inNumber = false;
        boolean inName = false;
        boolean inSymbol = false;
        int tokenStartedAt = 0;
        List<String> tokens = new ArrayList<>();
        char[] raw = line.toCharArray();
        for (int i = 0; i < raw.length; i++) {
            if (inComment){
                if (raw[i] == '*' && i + 1 < raw.length && raw[i+1] == '/'){
                    inComment = false;
                    i++;
                }

            } else if (inStringLiteral) {
                if (raw[i] == '"') {
                    tokens.add(new String(raw, tokenStartedAt, i - tokenStartedAt +1));
                    inStringLiteral = false;
                } else if (raw[i] == '\\') {
                    if (i + 1 == raw.length) { //workaround for multiline strings
                        tokens.add(new String(raw, tokenStartedAt, i - tokenStartedAt + 1) + "\"");
                    }
                } else if (i + 1 == raw.length) {
                    tokens.add(new String(raw, tokenStartedAt, i - tokenStartedAt + 1) + "\"");
                    System.err.println("Unexpected newline inside string literal"); //todo może obsułżyć jakoś inaczej?
                }

            } else if (inNumber){
                if(isDigit(raw[i]) || isLetter(raw[i]) || raw[i] == '.'){
                    if (raw[i] == 'e' || raw[i] == 'p') {
                        if (i + 1 < raw.length && (raw[i+1] == '+' || raw[i+1] == '-')){
                            i++;
                        }
                    }
                } else {
                    tokens.add(new String(raw, tokenStartedAt, i - tokenStartedAt));
                    inNumber = false;
                    i--; //analyse again, this time not as a number
                }

            } else if (inName){
                if (!(isLetter(raw[i]) || isDigit(raw[i]))){
                    tokens.add(new String(raw, tokenStartedAt, i - tokenStartedAt));
                    inName = false;
                    i--;
                }

            } else if (inSymbol){
                if (isSymbol(raw[i])){
                    if (raw[i] == '"')  {
                        tokens.add(new String(raw, tokenStartedAt, i - tokenStartedAt));
                        inSymbol = false;
                        i--;
                    }
                    else if (raw[i] == '/' && i + 1 < raw.length){
                        if(raw[i+1] == '/'){
                            tokens.add(new String(raw, tokenStartedAt, i - tokenStartedAt));
                            inSymbol = false;
                            break;
                        }
                        if(raw[i+1] == '*') {
                            tokens.add(new String(raw, tokenStartedAt, i - tokenStartedAt));
                            inSymbol = false;
                            inComment = true;
                        }
                    }

                } else {
                    tokens.add(new String(raw, tokenStartedAt, i - tokenStartedAt));
                    inSymbol = false;
                    i--;
                }

            } else {
                if (!isWhitespace(raw[i])){
                    tokenStartedAt = i;
                    if (isDigit(raw[i])){
                        inNumber = true;
                    }
                    else if (isSymbol(raw[i])){
                        if (raw[i] == '"')  inStringLiteral = true;
                        else if (raw[i] == '/' && i + 1 < raw.length){
                            if(raw[i+1] == '/') break;
                            if(raw[i+1] == '*') {
                                inComment = true;
                            }
                        }
                        else inSymbol = true;
                    }
                    else if (isLetter(raw[i])){
                        inName = true;
                    }
                }
            }
        }
        if(inName || inNumber || inSymbol){
            tokens.add(new String(raw, tokenStartedAt, raw.length - tokenStartedAt));
        }
        tokens.add("\n"); //using newlines to allow for line counting in next steps
        return tokens.stream();
    }

    private boolean isLetter(char c){
        return c == '_' ||
                (c >= 'A' && c <= 'Z') ||
                (c >= 'a' && c <= 'z');
    }

    private boolean isDigit(char c){
        return c >= '0' && c <= '9';
    }

    private boolean isSymbol(char c){
       return c == '!' ||
               c == '"' ||
               c == '#' ||
               c == '%' ||
               c == '&' ||
               c == '\'' ||
               c == '(' ||
               c == ')' ||
               c == '*' ||
               c == '+' ||
               c == ',' ||
               c == '-' ||
               c == '.' ||
               c == '/' ||
               c == ':' ||
               c == ';' ||
               c == '<' ||
               c == '=' ||
               c == '>' ||
               c == '?' ||
               c == '[' ||
               c == '\\' ||
               c == ']' ||
               c == '^' ||
               c == '{' ||
               c == '|' ||
               c == '}' ||
               c == '~';
    }

    private boolean isWhitespace(char c){
        return c == ' ' ||
                c == '\n' ||
                c == '\r' ||
                c == '\t' ||
                c == 11;
    }
}



