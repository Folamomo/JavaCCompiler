package pl.edu.agh.p810.compiler.Parser.Rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.agh.p810.compiler.Parser.AST;
import pl.edu.agh.p810.compiler.model.Token;

import java.util.List;

@Data
@AllArgsConstructor
public class ParsingResult {
    AST ast;
    List<Token> remainingTokens;

}
