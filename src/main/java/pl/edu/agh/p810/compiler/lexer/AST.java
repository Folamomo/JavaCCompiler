package pl.edu.agh.p810.compiler.lexer;

import pl.edu.agh.p810.compiler.model.Token;

import java.util.List;

public class AST {
    Token token;
    String name;
    List<AST> children;

    public AST(Token token, String name, List<AST> children) {
        this.token = token;
        this.name = name;
        this.children = children;
    }

    public AST() {
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AST> getChildren() {
        return children;
    }

    public void setChildren(List<AST> children) {
        this.children = children;
    }
}
