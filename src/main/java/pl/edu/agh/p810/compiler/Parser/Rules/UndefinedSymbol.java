package pl.edu.agh.p810.compiler.Parser.Rules;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UndefinedSymbol extends RuntimeException {
    public UndefinedSymbol(String message) {
        super(message);
    }

    public UndefinedSymbol(String left, String[] right, String missing) {
        super("Missing symbol " + missing + " in production " + left + ": " + String.join(", ", right));
    }
}
