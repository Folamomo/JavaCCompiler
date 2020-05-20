package pl.edu.agh.p810.compiler.Parser;


import pl.edu.agh.p810.compiler.Parser.Rules.Grammar;
import pl.edu.agh.p810.compiler.model.Token;
import pl.edu.agh.p810.compiler.model.TokenType;

import java.util.List;
import java.util.stream.Stream;

public class Parser {
    public AST parse(List<Token> tokens){
        tokens.add(new Token(TokenType.EOF, null, 0));
        Grammar grammar = new Grammar();
        return grammar.getStart().parse(tokens).get(0).getAst();
    }
}
