package pl.edu.agh.p810.compiler.lexer;

import pl.edu.agh.p810.compiler.model.Token;
import pl.edu.agh.p810.compiler.model.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Parser {
    Optional<AST> declaration(List<Token> tokenList){
        if(tokenList.get(tokenList.size()-1).getTokenType() == TokenType.SEMICOLON){
            Token semicolon = tokenList.remove(tokenList.size()-1);
            if (declaration_specifiers(tokenList) != null) {
                 AST ast = new AST();
                 ast.setChildren(new ArrayList<>());
                 ast.getChildren().add(declaration_specifiers(tokenList).get());
                 return Optional.of(ast);
            } else {
                for (int i = tokenList.size(); i >= 0; --i) {
                    if (declaration_specifiers(tokenList.subList(0, i)) != null) {
                        if (init_declarator_list(tokenList.subList(i, tokenList.size())) != null) {
                            AST ast = new AST(null, "declaration",  new ArrayList<>());
                            ast.getChildren().add(declaration_specifiers(tokenList.subList(0, i)).get());
                            ast.getChildren().add(init_declarator_list(tokenList.subList(i, tokenList.size())).get());
                            ast.getChildren().add(new AST(semicolon, "semicolon",  null));
                            return Optional.of(ast);
                        }
                    }
                }
            }
        }
        return null;
    }
    Optional<AST> declaration_specifiers(List<Token> tokenList){
        if(type_specifier(tokenList) != null){
            AST ast = new AST(null, "declaration_specifiers", new ArrayList<>());
            ast.getChildren().add(type_specifier(tokenList).get());
            return Optional.of(ast);
        }
        return null;
    }
    Optional<AST> type_specifier(List<Token> tokenList){
        if(tokenList.size() == 1){
            Token token = tokenList.get(0);
            if(token.getTokenType() == TokenType.INT){
                return Optional.of(new AST(token, "int", null));
            }
        }
        return null;
    }
    Optional<AST> init_declarator_list(List<Token> tokenList){
        if(init_declarator(tokenList) != null){
            AST ast = new AST(null, "init_declarator_list", new ArrayList<>());
            ast.getChildren().add(init_declarator(tokenList).get());
            return Optional.of(ast);
        }
        return null;
    }
    Optional<AST> init_declarator(List<Token> tokenList){
        if(declarator(tokenList) != null){
            AST ast = new AST(null, "init_declarator", new ArrayList<>());
            ast.getChildren().add(declarator(tokenList).get());
            return Optional.of(ast);
        } else {
            int direct_assignment_index = 0;
            for (Token token: tokenList){
                if(token.getTokenType() == TokenType.DIRECT_ASSIGNMENT) {
                    break;
                }
                direct_assignment_index++;
            }
            if(direct_assignment_index != tokenList.size() && direct_assignment_index != 0){
                if(declarator(tokenList.subList(0, direct_assignment_index)) != null){
                    if(initializer(tokenList.subList(direct_assignment_index+1, tokenList.size())) != null){
                        AST ast = new AST(null, "init_declarator", new ArrayList<>());
                        ast.getChildren().add(declarator(tokenList.subList(0, direct_assignment_index)).get());
                        ast.getChildren().add(new AST(tokenList.get(direct_assignment_index), "direct_assignment", null));
                        ast.getChildren().add(initializer(tokenList.subList(direct_assignment_index+1, tokenList.size())).get());
                        return Optional.of(ast);
                    }
                }
            }
        }
        return null;
    }
    Optional<AST> declarator(List<Token> tokenList){
        if(direct_declarator(tokenList) != null){
            AST ast = new AST(null, "declarator", new ArrayList<>());
            ast.getChildren().add(direct_declarator(tokenList).get());
            return Optional.of(ast);
        }
        return null;
    }
    Optional<AST> initializer(List<Token> tokenList){
        if(assignment_expression(tokenList) != null){
            AST ast = new AST(null, "initializer", new ArrayList<>());
            ast.getChildren().add(assignment_expression(tokenList).get());
            return Optional.of(ast);
        }
        return null;
    }
    Optional<AST> direct_declarator(List<Token> tokenList){
        if(tokenList.size() == 1){
            Token token = tokenList.get(0);
            if(token.getTokenType() == TokenType.IDENTIFIER){
                return Optional.of(new AST(token, "identifier", null));
            }
        }
        return null;
    }
    //uproszczone w tym miejscu
    Optional<AST> assignment_expression(List<Token> tokenList){
        if(tokenList.size() == 1){
            Token token = tokenList.get(0);
            if(token.getTokenType() == TokenType.INT_LITERAL){
                return Optional.of(new AST(token, "int_literal", null));
            }
        }
        return null;
    }

}
