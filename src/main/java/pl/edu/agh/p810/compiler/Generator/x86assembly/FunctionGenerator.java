package pl.edu.agh.p810.compiler.Generator.x86assembly;

import pl.edu.agh.p810.compiler.Generator.AbstractGenerator;
import pl.edu.agh.p810.compiler.Parser.AST;

public class FunctionGenerator extends AbstractGenerator {
    public FunctionGenerator(AbstractGenerator parent) {
        super(parent);
    }

    @Override
    public void visit(AST ast) {
        switch (ast.getSymbol().name){
            case "FunctionDefinition" -> visitFunctionDefinition(ast);
            case "Default" -> {}
        }

    }

    private void visitFunctionDefinition(AST node) {
        node.getChildren().forEach(child -> {
            if(child.getSymbol().name.equals("Declarator")){
                System.out.println(child
                        .getChildren()
                        .get(0)
                        .getChildren()
                        .get(0)
                        .getValue() + ':');
            }
        });
        System.out.println("push    rbp");
        System.out.println("mov     rbp, rsp");
        node.getChildren().forEach(child -> {
            if(child.getSymbol().name.equals("Declarator")){
                child.accept(this);
            }
        });
        node.getChildren().forEach(child ->{
            if (child.getSymbol().name.equals("CompoundStatement")){
                child.accept(this);
            }
        });
        System.out.println("pop    rbp");
        System.out.println("ret");
    }
}
