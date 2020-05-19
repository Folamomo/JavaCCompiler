package pl.edu.agh.p810.compiler.Parser.Rules;

import pl.edu.agh.p810.compiler.model.Token;

import java.util.List;

public abstract class Symbol {
    public final String name;

    protected Symbol(String name) {
        this.name = name;
    }
    public abstract List<ParsingResult> parse(List<Token> tokens);
}
