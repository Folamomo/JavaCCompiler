package pl.edu.agh.p810.compiler.Parser.Rules;

import pl.edu.agh.p810.compiler.Parser.AST;
import pl.edu.agh.p810.compiler.model.Token;
import pl.edu.agh.p810.compiler.model.TokenType;

import java.util.List;

public class Terminal extends Symbol {
    public final TokenType type;

    public Terminal(TokenType type) {
        super(type.name());
        this.type = type;
    }
}
