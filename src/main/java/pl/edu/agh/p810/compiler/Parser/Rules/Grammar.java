package pl.edu.agh.p810.compiler.Parser.Rules;

import lombok.Getter;
import pl.edu.agh.p810.compiler.model.TokenType;

import java.io.InvalidClassException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Grammar {
    private Map<String, Symbol> symbols;
    private Symbol start;

    public Grammar(){
        symbols = Stream.of(TokenType.values()).collect(Collectors.toMap(Enum::name, Terminal::new));

        addProduction("CastExpression", "INT_LITERAL");
        addProduction("CastExpression", "IDENTIFIER");

        addProduction("MultiplicativeExpression", "CastExpression");
        addProduction("MultiplicativeExpression", "CastExpression", "STAR", "MultiplicativeExpression");
        addProduction("MultiplicativeExpression", "CastExpression", "DIVIDE", "MultiplicativeExpression");
        addProduction("MultiplicativeExpression", "CastExpression", "MODULO", "MultiplicativeExpression");

        addProduction("AdditiveExpression", "MultiplicativeExpression");
        addProduction("AdditiveExpression", "MultiplicativeExpression", "PLUS", "AdditiveExpression");
        addProduction("AdditiveExpression", "MultiplicativeExpression", "MINUS", "AdditiveExpression");

        addProduction("Expression", "AdditiveExpression");
        addProduction("Expression", "IDENTIFIER");
        addProduction("Expression", "STRING_LITERAL");
        addProduction("Expression", "INT_LITERAL");
        addProduction("Expression", "FLOAT_LITERAL");

        addProduction("Assignment", "IDENTIFIER", "DIRECT_ASSIGNMENT", "Expression");

        addProduction("TypeSpecifier", "INT");

        addProduction("DeclarationSpecifiers", "TypeSpecifier");

        addProduction("Declaration", "DeclarationSpecifiers", "IDENTIFIER", "SEMICOLON");
        addProduction("Declaration", "DeclarationSpecifiers", "Assignment", "SEMICOLON");

        addProduction("DirectDeclarator", "IDENTIFIER");
        addProduction("DirectDeclarator", "IDENTIFIER", "LEFT_PARENTHESIS", "VOID", "RIGHT_PARENTHESIS"); //TODO VOID zamienić na Declarator i dopisać inne opcje

        addProduction("Declarator", "DirectDeclarator");

        addProduction("ExternalDeclaration", "Declaration");

        addProduction("CompoundStatement", "LEFT_BRACE", "RIGHT_BRACE");
        addProduction("CompoundStatement", "LEFT_BRACE", "ExternalDeclaration",  "RIGHT_BRACE");

        addProduction("FunctionDefinition", "DeclarationSpecifiers", "Declarator", "CompoundStatement");
        addProduction("FunctionDefinition", "Declarator", "CompoundStatement");

        addProduction("ExternalDeclaration", "FunctionDefinition");

        addProduction("TranslationUnit", "ExternalDeclaration", "EOF");
        addProduction("TranslationUnit", "ExternalDeclaration", "TranslationUnit");

        start = symbols.get("TranslationUnit");
    }

    public void addProduction(String left, String... right ){
        symbols.compute(left, (key, old) -> tryAdding(key, old, right));
    }

    private Symbol tryAdding(String key, Symbol old, String... right){
        if (old == null){
            old = new Nonterminal(key, new ArrayList<>());
        }
        if (old.getClass() != Nonterminal.class){
            throw new RuntimeException();
        }

        Nonterminal nonterminal = (Nonterminal) old;

        if (right.length != 0) nonterminal.productions.add(
                Arrays.stream(right).map(name -> symbols.get(name)).collect(Collectors.toList()));
        return nonterminal;
    }
}
