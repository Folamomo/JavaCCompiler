package pl.edu.agh.p810.compiler.Parser.Rules;

import pl.edu.agh.p810.compiler.Parser.AST;
import pl.edu.agh.p810.compiler.model.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Nonterminal extends Symbol {
    protected List<List<Symbol>> productions;

    public Nonterminal(String name, List<List<Symbol>> productions) {
        super(name);
        this.productions = productions;
    }

    @Override
    public List<ParsingResult> parse(List<Token> tokens) {
        return productions.stream()
                .flatMap(production -> parseProduction(production, tokens))
                .collect(Collectors.toList());
    }

    protected Stream<ParsingResult> parseProduction(List<Symbol> production, List<Token> tokens){
        List<ParsingResult> results = List.of(new ParsingResult(new AST(this, name, new ArrayList<>()), tokens));
        for (Symbol symbol: production){
            List<ParsingResult> newResults = new ArrayList<>();
            for (var situation : results){
                for (var newResult : symbol.parse(situation.remainingTokens)){
                    AST newAst = new AST(this, name, new ArrayList<>());
                    newAst.getChildren().addAll(situation.getAst().getChildren());
                    newAst.getChildren().add(newResult.getAst());
                    newResults.add(new ParsingResult(newAst, newResult.remainingTokens));
                }
            }
            results = newResults;
        }
        return results.stream();
    }
}
