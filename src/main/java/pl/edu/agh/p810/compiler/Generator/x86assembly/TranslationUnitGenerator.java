package pl.edu.agh.p810.compiler.Generator.x86assembly;

import lombok.SneakyThrows;
import pl.edu.agh.p810.compiler.Generator.ASTVisitor;
import pl.edu.agh.p810.compiler.Generator.AbstractGenerator;
import pl.edu.agh.p810.compiler.Generator.IdentifierTable;
import pl.edu.agh.p810.compiler.Parser.AST;

import java.io.IOException;
import java.io.OutputStream;

public class TranslationUnitGenerator extends AbstractGenerator {
    private IdentifierTable global;
    public TranslationUnitGenerator(OutputStream out, OutputStream err) {
        super(out, err);
        global = new IdentifierTable();
    }

    @SneakyThrows
    @Override
    public void visit(AST ast) {
        switch (ast.getSymbol().name){
            case "Declaration" -> ast.accept(new GlobalVariableGenerator(this));
            case "FunctionDefinition" -> ast.accept(new FunctionGenerator(this));
            case "EOF" -> out.write(";EOF");
            default -> ast.getChildren().forEach(child -> child.accept(this));
        }
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return global;
    }
}
