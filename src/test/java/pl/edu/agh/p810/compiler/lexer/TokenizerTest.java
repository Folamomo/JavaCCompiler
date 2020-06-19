package pl.edu.agh.p810.compiler.lexer;

import org.junit.jupiter.api.Test;
import pl.edu.agh.p810.compiler.Parser.AST;
import pl.edu.agh.p810.compiler.Parser.RecursiveDescentParser;
import pl.edu.agh.p810.compiler.Generator.x86assembly.TranslationUnitGenerator;
import pl.edu.agh.p810.compiler.Parser.Rules.Grammar;
import pl.edu.agh.p810.compiler.model.Token;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

class TokenizerTest {
    @Test public void someRandomCodeIsTokenizedCorrectly() {
        Tokenizer tokenizer = new Tokenizer(new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("simpleCode.c"))));
        List<String> result = tokenizer.getTokens().collect(Collectors.toList());
        int a = 0;
    }

    @Test public void lexerReturnedStreamOfTokens(){
        Tokenizer tokenizer = new Tokenizer(new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("simpleCode.c"))));
        Lexer lexer = new Lexer();
        List<Token> result = lexer.getTokensStream(tokenizer.getTokens()).collect(Collectors.toList());
        int a = 0;
    }

    @Test public void parserReturnedAST(){
        Tokenizer tokenizer = new Tokenizer(new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("simpleCode.c"))));
        Lexer lexer = new Lexer();
        List<Token> result = lexer.getTokensStream(tokenizer.getTokens()).collect(Collectors.toList());
        int a = 0;
        RecursiveDescentParser parser = new RecursiveDescentParser(new Grammar());
        AST ast = parser.parse(result);
        int b = 0;
        TranslationUnitGenerator generator = new TranslationUnitGenerator(System.out, System.err);
        generator.visit(ast);
    }

    @Test public void visitor(){
        Tokenizer tokenizer = new Tokenizer(new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("simpleCode.c"))));
        Lexer lexer = new Lexer();
        List<Token> result = lexer.getTokensStream(tokenizer.getTokens()).collect(Collectors.toList());
        RecursiveDescentParser parser = new RecursiveDescentParser(new Grammar());
        AST ast = parser.parse(result);
        TranslationUnitGenerator translationUnitGenerator = new TranslationUnitGenerator(System.out, System.err);
        translationUnitGenerator.visit(ast);
    }
}
