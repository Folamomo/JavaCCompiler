package pl.edu.agh.p810.compiler.Generator.x86assembly;

import lombok.SneakyThrows;
import pl.edu.agh.p810.compiler.Generator.AbstractGenerator;
import pl.edu.agh.p810.compiler.Generator.Identifier;
import pl.edu.agh.p810.compiler.Generator.VariableIdentifier;
import pl.edu.agh.p810.compiler.Parser.AST;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GlobalVariableGenerator extends AbstractGenerator {
    private List<String> typeSpecifiers;
    private List<String> storageClassSpecifiers;
    private List<String> typeQualifiers;
    public GlobalVariableGenerator(AbstractGenerator parent) {
        super(parent);
        typeSpecifiers = new ArrayList<>();
        storageClassSpecifiers = new ArrayList<>();
        typeQualifiers = new ArrayList<>();
    }

    @Override
    @SneakyThrows
    public void visit(AST ast)  {
        switch (ast.getSymbol().name){
            case "Declaration" -> declareVariables(ast);
            case "VOID", "CHAR", "SHORT", "INT", "LONG",
                    "FLOAT", "DOUBLE", "SIGNED", "UNSIGNED" -> typeSpecifiers.add(ast.getSymbol().name);
            case "StructOrUnionSpecifier", "EnumSpecifier" -> err.write("Not yet implemented\n");
            case "CONST", "VOLATILE" -> typeQualifiers.add(ast.getSymbol().name);
            case "TYPEDEF", "EXTERN", "STATIC", "AUTO", "REGISTER" -> storageClassSpecifiers.add(ast.getSymbol().name);
            case "InitDeclarator" -> visitInitDeclarator(ast);
            default -> ast.getChildren().forEach(child -> child.accept(this));
        }
    }

    @SneakyThrows
    private void declareVariables(AST declaration)  {
        declaration.getChildren().get(0).accept(this);
        fixTypeSpecifiers();
        declaration.getChildren().get(1).accept(this);

    }

    private void fixTypeSpecifiers() {
        //TODO
    }

    private void visitInitDeclarator(AST initDeclarator) {
        declare(initDeclarator.getChildren().get(0));
        if (initDeclarator.getChildren().size()==3){
            initialize(initDeclarator.getChildren().get(2));
        }
    }

    private void initialize(AST initializer) {

    }

    @SneakyThrows
    private void declare(AST declarator){
        int pointerCount = 0;
        String name = "";
        AST directDeclarator = declarator;
        while (directDeclarator.getChildren().size() == 2){
            ++pointerCount;
            directDeclarator = directDeclarator.getChildren().get(1);
        }

        if (declarator.getChildren().size()==1){
            name = declarator.getChildren().get(0).getValue();
        }
        Identifier newIdentifier = new VariableIdentifier(
                String.join(" ", typeSpecifiers),
                pointerCount, typeQualifiers.contains("CONST"),
                typeQualifiers.contains("VOLATILE"),
                name,
                name);

        parent.getIdentifierTable().add(newIdentifier);
        out.write(name + ":\t.zero\t" + newIdentifier.sizeOf());
    }
}
