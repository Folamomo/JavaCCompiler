package pl.edu.agh.p810.compiler.Parser;


import pl.edu.agh.p810.compiler.Parser.Rules.Grammar;
import pl.edu.agh.p810.compiler.model.Token;

import java.util.List;
import java.util.stream.Stream;

public class Parser {
    public AST parse(List<Token> tokens){
        Grammar grammar = new Grammar();
        return grammar.getStart().parse(tokens).get(0).getAst();
    }
}
