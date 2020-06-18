package pl.edu.agh.p810.compiler.Parser.Rules;

import java.util.List;

public class Nonterminal extends Symbol {
    protected List<List<Symbol>> productions;

    public Nonterminal(String name, List<List<Symbol>> productions) {
        super(name);
        this.productions = productions;
    }

    public List<List<Symbol>> getProductions() {
        return productions;
    }
}
