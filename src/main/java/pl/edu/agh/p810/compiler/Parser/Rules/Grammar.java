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

        addProduction("Expression", "IDENTIFIER");
        addProduction("Expression", "STRING_LITERAL");
        addProduction("Expression", "INT_LITERAL");
        addProduction("Expression", "FLOAT_LITERAL");
        addProduction("Expression", "INT_LITERAL", "PLUS", "INT_LITERAL");

        addProduction("Assignment", "IDENTIFIER", "DIRECT_ASSIGNMENT", "Expression");

        addProduction("Declaration", "INT", "IDENTIFIER", "SEMICOLON");
        addProduction("Declaration", "INT", "Assignment", "SEMICOLON");

        addProduction("ExternalDeclaration", "Declaration");
        addProduction("ExternalDeclaration", "FunctionDefinition");

        addProduction("Declarator", "DirectDeclarator");

        addProduction("DirectDeclarator", "IDENTIFIER");
        addProduction("DirectDeclarator", "LEFT_PARENTHESIS", "VOID", "RIGHT_PARENTHESIS"); //TODO VOID zamienić na Declarator i dopisać inne opcje


        addProduction("TranslationUnit", "ExternalDeclaration", "EOF");
        addProduction("TranslationUnit", "ExternalDeclaration", "TranslationUnit");

        addProduction("FunctionDefinition", "Declarator", "CompoundStatement");

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

        nonterminal.productions.add(
                Arrays.stream(right).map(name -> symbols.get(name)).collect(Collectors.toList()));
        return nonterminal;
    }
}
