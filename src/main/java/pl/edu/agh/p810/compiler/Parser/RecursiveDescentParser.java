package pl.edu.agh.p810.compiler.Parser;


import pl.edu.agh.p810.compiler.Parser.Rules.*;
import pl.edu.agh.p810.compiler.model.Token;
import pl.edu.agh.p810.compiler.model.TokenType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecursiveDescentParser implements Parser{
    private Grammar grammar;

    public RecursiveDescentParser(Grammar grammar) {
        this.grammar = grammar;
    }

    public AST parse(List<Token> tokens){
        tokens.add(new Token(TokenType.EOF, null, 0));
        return parseSymbol(grammar.getStart(), tokens).get(0).getAst();
    }

    public List<ParsingContext> parseSymbol(Symbol symbol, List<Token> tokens){
        if (symbol instanceof Terminal terminal){
            return parseTerminal(terminal, tokens, terminal);
        } else if (symbol instanceof Nonterminal nonterminal){
            return parseNonterminal(nonterminal, tokens, nonterminal);
        } return List.of();
    }

    private List<ParsingContext> parseNonterminal(Nonterminal symbol, List<Token> tokens, Nonterminal nonterminal) {
        List<ParsingContext> result = new ArrayList<>();
        for (var production : symbol.getProductions()){
            result.addAll(parseProduction(symbol, production, tokens));
        }
        return result;
    }

    private List<ParsingContext> parseProduction(Symbol parent, List<Symbol> production, List<Token> tokens) {
        List<ParsingContext> results = List.of(new ParsingContext(new AST(parent, parent.name, new ArrayList<>()), tokens));
        for (Symbol symbol: production){
            List<ParsingContext> newResults = new ArrayList<>();
            for (var situation : results){
                for (var newResult : parseSymbol(symbol, situation.getRemainingTokens())){
                    AST newAst = new AST(parent, parent.name, new ArrayList<>());
                    newAst.getChildren().addAll(situation.getAst().getChildren());
                    newAst.getChildren().add(newResult.getAst());
                    newResults.add(new ParsingContext(newAst, newResult.getRemainingTokens()));
                }
            }
            results = newResults;
        }
        return results;
    }

    private List<ParsingContext> parseTerminal(Terminal symbol, List<Token> tokens, Terminal terminal) {
        if (!tokens.isEmpty() && tokens.get(0).getTokenType() == terminal.type){
            return List.of(new ParsingContext(new AST(symbol, tokens.get(0).getValue(), null), tokens.subList(1, tokens.size())));
        } else {
            return List.of();
        }
    }
}
