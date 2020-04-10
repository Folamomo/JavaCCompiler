package pl.edu.agh.p810.compiler.lexer;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
    @Test public void someRandomCodeIsTokenizedCorrectly() {
        Tokenizer tokenizer = new Tokenizer(new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("randomCode.c"))));
        List<String> result = tokenizer.getTokens().collect(Collectors.toList());
        int a = 0;
    }
}