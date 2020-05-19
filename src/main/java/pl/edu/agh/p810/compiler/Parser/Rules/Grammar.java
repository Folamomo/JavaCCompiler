package pl.edu.agh.p810.compiler.Parser.Rules;

import lombok.Getter;
import pl.edu.agh.p810.compiler.model.TokenType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Grammar {
    private Map<String, Symbol> symbols;
    private Symbol start;
    public Grammar(){
        symbols = Stream.of(TokenType.values()).collect(Collectors.toMap(Enum::name, Terminal::new));

        Symbol expression = new Nonterminal("Expression", List.of(
                List.of(symbols.get("IDENTIFIER")),
                List.of(symbols.get("STRING_LITERAL")),
                List.of(symbols.get("INT_LITERAL")),
                List.of(symbols.get("FLOAT_LITERAL")),
                List.of(symbols.get("INT_LITERAL"), symbols.get("PLUS"), symbols.get("INT_LITERAL"))));//TODO inna implementacja


        Symbol assignment = new Nonterminal("Assignment", List.of(List.of(symbols.get("IDENTIFIER"), symbols.get("DIRECT_ASSIGNMENT"), expression)));

        Symbol declaration = new Nonterminal("Declaration", List.of(
                List.of(symbols.get("INT"), symbols.get("IDENTIFIER"), symbols.get("SEMICOLON")),
                List.of(symbols.get("INT"), assignment, symbols.get("SEMICOLON"))
        ));
        symbols.put(declaration.name, declaration);
        symbols.put(expression.name, expression);
        symbols.put(assignment.name, assignment);
        start = declaration;
    }
}
