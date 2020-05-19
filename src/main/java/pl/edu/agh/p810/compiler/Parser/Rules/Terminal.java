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

    @Override
    public List<ParsingResult> parse(List<Token> tokens) {
        if (tokens.get(0).getTokenType()==type){
            return List.of(new ParsingResult(makeAST(tokens.get(0)), tokens.subList(1, tokens.size())));
        } else {
            return List.of();
        }
    }

    private AST makeAST(Token token){
        return new AST(this, token.getValue(), null);
    }
}
