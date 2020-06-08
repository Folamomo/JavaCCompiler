package pl.edu.agh.p810.compiler.Generator;

import pl.edu.agh.p810.compiler.Parser.AST;

public interface Visitor {
    void visit(AST ast);

}
