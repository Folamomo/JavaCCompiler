package pl.edu.agh.p810.compiler.lexer;

import org.junit.jupiter.api.Test;
import pl.edu.agh.p810.compiler.Parser.AST;
import pl.edu.agh.p810.compiler.Parser.Parser;
import pl.edu.agh.p810.compiler.model.Token;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

class TokenizerTest {
    @Test public void someRandomCodeIsTokenizedCorrectly() {
        Tokenizer tokenizer = new Tokenizer(new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("randomCode.c"))));
        List<String> result = tokenizer.getTokens().collect(Collectors.toList());
        int a = 0;
    }

    @Test public void lexerReturnedStreamOfTokens(){
        Tokenizer tokenizer = new Tokenizer(new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("randomCode.c"))));
        Lexer lexer = new Lexer();
        List<Token> result = lexer.getTokensStream(tokenizer.getTokens()).collect(Collectors.toList());
        int a = 0;
    }

    @Test public void parserReturnedAST(){
        Tokenizer tokenizer = new Tokenizer(new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("simpleCode.c"))));
        Lexer lexer = new Lexer();
        List<Token> result = lexer.getTokensStream(tokenizer.getTokens()).collect(Collectors.toList());
        Parser parser = new Parser();
        AST ast = parser.parse(result);
        int a = 0;
    }
}
