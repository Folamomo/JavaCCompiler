package pl.edu.agh.p810.compiler.Generator;

import pl.edu.agh.p810.compiler.Parser.AST;

public class X86Generator implements ASTVisitor {

    @Override
    public void visit(AST ast) {
        switch (ast.getSymbol().name){
//            case "TranslationUnit" -> visitTranslationUnit(ast);
            case "EOF" -> visitEOF(ast);
            case "FunctionDefinition" -> visitFunctionDefinition(ast);
            case "CompoundStatement" -> visitCompoundStatement(ast);
            case "ExternalDeclaration" -> visitExternalDeclaration(ast);
            case "Declaration" -> visitDeclaration(ast);
            case "DeclarationSpecifiers" -> visitDeclarationSpecifiers(ast);
            case "TypeSpecifier" -> visitTypeSpecifier(ast);
            case "Assignment" -> visitAssignment(ast);
            case "Expression" -> visitExpression(ast);
            case "AdditiveExpression" -> visitAdditiveExpression(ast);
            case "MultiplicativeExpression" -> visitMultiplicativeExpression(ast);
            case "CastExpression" -> visitCastExpression(ast);
//            case "ParameterTypeList" ->
            default -> ast.getChildren().forEach(child -> child.accept(this));
        }
    }

    public void visitTranslationUnit(AST node){
        node.getChildren().forEach(child -> child.accept(this));
    }

    private void visitEOF(AST node) {
        System.out.println("EOF");
    }

    private void visitExternalDeclaration(AST node) {
        node.getChildren().forEach(child -> child.accept(this));
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

    private void visitDeclaration(AST node) {
    }

    private void visitDeclarator(AST node){
    }

    private void visitCompoundStatement(AST node){
    }

    private void visitDeclarationSpecifiers(AST node){

    }

    private void visitDirectDeclarator(AST node){
    }

    private void visitCastExpression(AST ast) {
    }

    private void visitMultiplicativeExpression(AST ast) {
    }

    private void visitAdditiveExpression(AST ast) {
    }

    private void visitExpression(AST ast) {
    }

    private void visitAssignment(AST ast) {
    }


    private void visitTypeSpecifier(AST ast) {
    }
}
