package pl.edu.agh.p810.compiler.Parser;

import pl.edu.agh.p810.compiler.Parser.Rules.Grammar;
import pl.edu.agh.p810.compiler.model.Token;

import java.util.List;

public interface Parser {
    AST parse (List<Token> tokens);
}
