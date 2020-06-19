package pl.edu.agh.p810.compiler.Generator;

import pl.edu.agh.p810.compiler.Parser.AST;

import java.io.IOException;

public interface ASTVisitor {
    void visit(AST ast);
}
