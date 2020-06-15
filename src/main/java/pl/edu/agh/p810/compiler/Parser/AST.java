package pl.edu.agh.p810.compiler.Parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.agh.p810.compiler.Generator.Visitor;
import pl.edu.agh.p810.compiler.Parser.Rules.Symbol;
import pl.edu.agh.p810.compiler.model.Token;

import java.util.List;

@Data
@AllArgsConstructor
public class AST {
    Symbol symbol;
    String value;
    List<AST> children;
    public void accept(Visitor visitor){
        visitor.visit(this);
        int a;
    }
}
