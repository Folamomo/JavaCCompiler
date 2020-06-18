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

        addProduction("ExternalDeclaration");
        addProduction("Declaration");
        addProduction("EqualityExpression");
        addProduction("Initializer");
        addProduction("InitDeclaratorList");
        addProduction("CastExpression");
        addProduction("TypeName");
        addProduction("AssignmentExpression");
        addProduction("TypeSpecifier");
        addProduction("TypeQualifier");
        addProduction("AbstractDeclarator");
        addProduction("Expression");
        addProduction("CompoundStatement");
        addProduction("Statement");
        addProduction("ConditionalExpression");
        addProduction("Declarator");
        addProduction("ParameterTypeList");

        addProduction("UnaryOperator", "AMPERSAND");
        addProduction("UnaryOperator", "STAR");
        addProduction("UnaryOperator", "PLUS");
        addProduction("UnaryOperator", "MINUS");
        addProduction("UnaryOperator", "TILDA");
        addProduction("UnaryOperator", "LOGIC_NOT");

        addProduction("PrimaryExpression", "IDENTIFIER");
        addProduction("PrimaryExpression", "STRING_LITERAL");
        addProduction("PrimaryExpression", "INT_LITERAL"); //TODO uproszczone
        addProduction("PrimaryExpression", "LEFT_PARENTHESIS", "Expression", "RIGHT_PARENTHESIS");

        addProduction("ArgumentExpressionList", "AssignmentExpression");
        addProduction("ArgumentExpressionList", "AssignmentExpression", "COMMA" , "ArgumentExpressionList");

        addProduction("PostfixExpression", "PrimaryExpression");
        addProduction("PostfixExpression", "LEFT_BRACKET", "Expression", "RIGHT_BRACKET", "PostfixExpression");
        addProduction("PostfixExpression", "LEFT_PARENTHESIS", "RIGHT_PARENTHESIS", "PostfixExpression");
        addProduction("PostfixExpression", "LEFT_PARENTHESIS", "ArgumentExpressionList", "RIGHT_PARENTHESIS", "PostfixExpression");
        addProduction("PostfixExpression", "DOT", "IDENTIFIER");
        addProduction("PostfixExpression", "ARROW", "IDENTIFIER");
        addProduction("PostfixExpression", "PLUS_PLUS");
        addProduction("PostfixExpression", "MINUS_MINUS");

        addProduction("UnaryExpression", "PostfixExpression");
        addProduction("UnaryExpression", "PLUS_PLUS", "UnaryExpression");
        addProduction("UnaryExpression", "MINUS_MINUS", "UnaryExpression");
        addProduction("UnaryExpression", "UnaryOperator" , "CastExpression");
        addProduction("UnaryExpression", "SIZEOF", "UnaryExpression");
        addProduction("UnaryExpression", "SIZEOF", "LEFT_PARENTHESIS", "TypeName", "RIGHT_PARENTHESIS");

        addProduction("SpecifierQualifierList", "TypeSpecifier");
        addProduction("SpecifierQualifierList", "TypeSpecifier", "SpecifierQualifierList");
        addProduction("SpecifierQualifierList", "TypeQualifier");
        addProduction("SpecifierQualifierList", "TypeQualifier", "SpecifierQualifierList");

        addProduction("DirectAbstractDeclarator", "LEFT_PARENTHESIS", "AbstractDeclarator", "RIGHT_PARENTHESIS");
        addProduction("DirectAbstractDeclarator", "LEFT_BRACKET", "RIGHT_BRACKET");
        addProduction("DirectAbstractDeclarator", "LEFT_BRACKET", "ConditionalExpression", "RIGHT_BRACKET");
        addProduction("DirectAbstractDeclarator", "LEFT_BRACKET", "RIGHT_BRACKET", "DirectAbstractDeclarator");
        addProduction("DirectAbstractDeclarator", "LEFT_BRACKET", "ConditionalExpression", "RIGHT_BRACKET", "DirectAbstractDeclarator");
        addProduction("DirectAbstractDeclarator", "LEFT_PARENTHESIS", "RIGHT_PARENTHESIS");
        addProduction("DirectAbstractDeclarator", "LEFT_PARENTHESIS", "ParameterTypeList", "RIGHT_PARENTHESIS");
        addProduction("DirectAbstractDeclarator", "LEFT_PARENTHESIS", "RIGHT_PARENTHESIS", "DirectAbstractDeclarator");
        addProduction("DirectAbstractDeclarator", "LEFT_PARENTHESIS", "ParameterTypeList", "RIGHT_PARENTHESIS", "DirectAbstractDeclarator");

        addProduction("TypeQualifierList", "TypeQualifier");
        addProduction("TypeQualifierList", "TypeQualifier", "TypeQualifierList");

        addProduction("Pointer", "STAR");
        addProduction("Pointer", "STAR", "TypeQualifierList");
        addProduction("Pointer", "STAR", "Pointer");
        addProduction("Pointer", "STAR", "TypeQualifierList", "Pointer");

        addProduction("AbstractDeclarator", "Pointer");
        addProduction("AbstractDeclarator", "DirectAbstractDeclarator");
        addProduction("AbstractDeclarator", "Pointer", "DirectAbstractDeclarator");

        addProduction("TypeName", "SpecifierQualifierList");
        addProduction("TypeName", "SpecifierQualifierList", "AbstractDeclarator");

        addProduction("CastExpression", "UnaryExpression");
        addProduction("CastExpression", "LEFT_PARENTHESIS", "TypeName", "RIGHT_PARENTHESIS", "CastExpression");

        addProduction("MultiplicativeExpression", "CastExpression");
        addProduction("MultiplicativeExpression", "CastExpression", "STAR", "MultiplicativeExpression");
        addProduction("MultiplicativeExpression", "CastExpression", "DIVIDE", "MultiplicativeExpression");
        addProduction("MultiplicativeExpression", "CastExpression", "MODULO", "MultiplicativeExpression");

        addProduction("AdditiveExpression", "MultiplicativeExpression");
        addProduction("AdditiveExpression", "MultiplicativeExpression", "PLUS", "AdditiveExpression");
        addProduction("AdditiveExpression", "MultiplicativeExpression", "MINUS", "AdditiveExpression");

        addProduction("Expression", "AssignmentExpression");
        addProduction("Expression", "AssignmentExpression", "COMMA", "Expression");

        addProduction("Assignment", "IDENTIFIER", "DIRECT_ASSIGNMENT", "Expression");

        addProduction("StructDeclarator", "Declarator");
        addProduction("StructDeclarator", "TERNARY_CONDITIONAL_RIGHT", "ConditionalExpression");
        addProduction("StructDeclarator", "Declarator", "TERNARY_CONDITIONAL_RIGHT", "ConditionalExpression");

        addProduction("StructDeclaratorList", "StructDeclarator");
        addProduction("StructDeclaratorList", "StructDeclarator", "COMMA", "StructDeclaratorList");

        addProduction("StructDeclaration", "SpecifierQualifierList", "StructDeclaratorList", "SEMICOLON");

        addProduction("StructDeclarationList", "StructDeclaration");
        addProduction("StructDeclarationList", "StructDeclaration", "StructDeclarationList");

        addProduction("StructOrUnion", "STRUCT");
        addProduction("StructOrUnion", "UNION");

        addProduction("StructOrUnionSpecifier", "StructOrUnion", "IDENTIFIER", "LEFT_BRACE", "StructDeclarationList", "RIGHT_BRACE");
        addProduction("StructOrUnionSpecifier", "StructOrUnion", "LEFT_BRACE", "StructDeclarationList", "RIGHT_BRACE");
        addProduction("StructOrUnionSpecifier", "StructOrUnion", "IDENTIFIER");

        addProduction("Enumerator", "IDENTIFIER");
        addProduction("Enumerator", "IDENTIFIER", "DIRECT_ASSIGNMENT", "ConditionalExpression");

        addProduction("EnumeratorList", "Enumerator");
        addProduction("EnumeratorList", "Enumerator", "COMMA", "EnumeratorList");

        addProduction("EnumSpecifier", "ENUM", "LEFT_BRACE", "EnumeratorList", "RIGHT_BRACE");
        addProduction("EnumSpecifier", "ENUM", "IDENTIFIER", "LEFT_BRACE", "EnumeratorList", "RIGHT_BRACE");
        addProduction("EnumSpecifier", "ENUM", "IDENTIFIER");

        addProduction("TypeSpecifier", "INT");
        addProduction("TypeSpecifier", "VOID");
        addProduction("TypeSpecifier", "CHAR");
        addProduction("TypeSpecifier", "SHORT");
        addProduction("TypeSpecifier", "INT");
        addProduction("TypeSpecifier", "LONG");
        addProduction("TypeSpecifier", "FLOAT");
        addProduction("TypeSpecifier", "DOUBLE");
        addProduction("TypeSpecifier", "SIGNED");
        addProduction("TypeSpecifier", "UNSIGNED");
        addProduction("TypeSpecifier", "StructOrUnionSpecifier");
        addProduction("TypeSpecifier", "EnumSpecifier");
//        addProduction("TypeSpecifier", "TYPE_NAME);

        addProduction("TypeQualifier", "CONST");
        addProduction("TypeQualifier", "VOLATILE");

        addProduction("StorageClassSpecifier", "TYPEDEF");
        addProduction("StorageClassSpecifier", "EXTERN");
        addProduction("StorageClassSpecifier", "STATIC");
        addProduction("StorageClassSpecifier", "AUTO");
        addProduction("StorageClassSpecifier", "REGISTER");

        addProduction("DeclarationSpecifiers", "TypeSpecifier");
        addProduction("DeclarationSpecifiers", "TypeSpecifier", "DeclarationSpecifiers");
        addProduction("DeclarationSpecifiers", "TypeQualifier");
        addProduction("DeclarationSpecifiers", "TypeQualifier", "DeclarationSpecifiers");
        addProduction("DeclarationSpecifiers", "StorageClassSpecifier");
        addProduction("DeclarationSpecifiers", "StorageClassSpecifier", "DeclarationSpecifiers");

        addProduction("DeclarationList", "Declaration");
        addProduction("DeclarationList", "Declaration", "DeclarationList");

        addProduction("ParameterDeclaration", "DeclarationSpecifiers", "Declarator");
        addProduction("ParameterDeclaration", "DeclarationSpecifiers", "AbstractDeclarator");
        addProduction("ParameterDeclaration", "DeclarationSpecifiers");

        addProduction("ParameterList", "ParameterDeclaration");
        addProduction("ParameterList", "ParameterDeclaration", "COMMA", "ParameterList");

        addProduction("ParameterTypeList", "ParameterList");
        addProduction("ParameterTypeList", "ParameterList", "COMMA", "ELLIPSIS");

        addProduction("IdentifierList", "IDENTIFIER");
        addProduction("IdentifierList", "IDENTIFIER", "COMMA", "IdentifierList");

        addProduction("DirectDeclarator", "IDENTIFIER");
        addProduction("DirectDeclarator", "LEFT_PARENTHESIS", "Declarator", "RIGHT_PARENTHESIS");
        addProduction("DirectDeclarator", "LEFT_BRACKET", "ConditionalExpression", "RIGHT_BRACKET", "DirectDeclarator");
        addProduction("DirectDeclarator", "LEFT_BRACKET", "RIGHT_BRACKET", "DirectDeclarator");
        addProduction("DirectDeclarator", "LEFT_PARENTHESIS", "ParameterTypeList", "RIGHT_PARENTHESIS", "DirectDeclarator");
        addProduction("DirectDeclarator", "LEFT_PARENTHESIS", "IdentifierList", "RIGHT_PARENTHESIS", "DirectDeclarator");
        addProduction("DirectDeclarator", "LEFT_PARENTHESIS", "RIGHT_PARENTHESIS", "DirectDeclarator");

        addProduction("Declarator", "DirectDeclarator");
        addProduction("Declarator", "Pointer", "DirectDeclarator");

        addProduction("LabeledStatement", "IDENTIFIER", "TERNARY_CONDITIONAL_RIGHT", "Statement");
        addProduction("LabeledStatement", "CASE", "ConditionalExpression", "TERNARY_CONDITIONAL_RIGHT", "Statement"); //Uproszczone z ConstantExpression na ConditionalExpression
        addProduction("LabeledStatement", "DEFAULT", "TERNARY_CONDITIONAL_RIGHT", "Statement");

        addProduction("ExpressionStatement", "SEMICOLON");
        addProduction("ExpressionStatement", "Expression", "SEMICOLON");

        addProduction("SelectionStatement", "IF", "LEFT_PARENTHESIS", "Expression", "RIGHT_PARENTHESIS", "Statement");
        addProduction("SelectionStatement", "IF", "LEFT_PARENTHESIS", "Expression", "RIGHT_PARENTHESIS", "Statement", "ELSE", "Statement");
        addProduction("SelectionStatement", "SWITCH", "LEFT_PARENTHESIS", "Expression", "RIGHT_PARENTHESIS", "Statement");

        addProduction("IterationStatement", "WHILE", "LEFT_PARENTHESIS", "Expression", "RIGHT_PARENTHESIS", "Statement");
        addProduction("IterationStatement", "DO", "Statement", "WHILE", "LEFT_PARENTHESIS", "Expression", "RIGHT_PARENTHESIS", "SEMICOLON");
        addProduction("IterationStatement", "FOR", "LEFT_PARENTHESIS", "ExpressionStatement", "ExpressionStatement", "Statement");
        addProduction("IterationStatement", "WHILE", "LEFT_PARENTHESIS", "ExpressionStatement", "ExpressionStatement", "Expression", "RIGHT_PARENTHESIS", "Statement");

        addProduction("JumpStatement", "GOTO", "IDENTIFIER", "SEMICOLON");
        addProduction("JumpStatement", "CONTINUE", "SEMICOLON");
        addProduction("JumpStatement", "BREAK", "SEMICOLON");
        addProduction("JumpStatement", "RETURN", "SEMICOLON");
        addProduction("JumpStatement", "RETURN", "Expression", "SEMICOLON");

        addProduction("Statement", "LabeledStatement");
        addProduction("Statement", "CompoundStatement");
        addProduction("Statement", "ExpressionStatement");
        addProduction("Statement", "SelectionStatement");
        addProduction("Statement", "IterationStatement");
        addProduction("Statement", "JumpStatement");

        addProduction("StatementList", "Statement");
        addProduction("StatementList", "Statement", "StatementList");

        addProduction("CompoundStatement", "LEFT_BRACE", "RIGHT_BRACE");
        addProduction("CompoundStatement", "LEFT_BRACE", "DeclarationList",  "RIGHT_BRACE");
        addProduction("CompoundStatement", "LEFT_BRACE", "StatementList",  "RIGHT_BRACE");
        addProduction("CompoundStatement", "LEFT_BRACE", "DeclarationList", "StatementList", "RIGHT_BRACE");

        addProduction("FunctionDefinition", "DeclarationSpecifiers", "Declarator", "CompoundStatement");
        addProduction("FunctionDefinition", "DeclarationSpecifiers", "Declarator", "DeclarationList", "CompoundStatement");
        addProduction("FunctionDefinition", "Declarator", "CompoundStatement");
        addProduction("FunctionDefinition", "Declarator", "DeclarationList" , "CompoundStatement");

        addProduction("ShiftExpression", "AdditiveExpression");
        addProduction("ShiftExpression", "AdditiveExpression", "BITWISE_LEFT", "ShiftExpression");
        addProduction("ShiftExpression", "AdditiveExpression", "BITWISE_RIGHT", "ShiftExpression");

        addProduction("RelationalExpression", "ShiftExpression");
        addProduction("RelationalExpression", "ShiftExpression", "LOWER", "RelationalExpression");
        addProduction("RelationalExpression", "ShiftExpression", "GREATER", "RelationalExpression");
        addProduction("RelationalExpression", "ShiftExpression", "LOWER_OR_EQUAL", "RelationalExpression");
        addProduction("RelationalExpression", "ShiftExpression", "GREATER_OR_EQUAL", "RelationalExpression");

        addProduction("EqualityExpression", "RelationalExpression");
        addProduction("EqualityExpression", "RelationalExpression", "EQUAL_TO", "EqualityExpression");
        addProduction("EqualityExpression", "RelationalExpression", "NOT_EQUAL_TO", "EqualityExpression");

        addProduction("AndExpression", "EqualityExpression");
        addProduction("AndExpression", "EqualityExpression", "AMPERSAND", "AndExpression");

        addProduction("ExclusiveOrExpression", "AndExpression");
        addProduction("ExclusiveOrExpression", "AndExpression", "BITWISE_XOR", "ExclusiveOrExpression");

        addProduction("InclusiveOrExpression", "ExclusiveOrExpression");
        addProduction("InclusiveOrExpression", "ExclusiveOrExpression", "BITWISE_OR","InclusiveOrExpression");

        addProduction("LogicalAndExpression", "InclusiveOrExpression");
        addProduction("LogicalAndExpression", "InclusiveOrExpression", "LOGIC_AND", "LogicalAndExpression");

        addProduction("LogicalOrExpression", "LogicalAndExpression");
        addProduction("LogicalOrExpression", "LogicalAndExpression", "LOGIC_OR", "LogicalOrExpression");

        addProduction("ConditionalExpression", "LogicalOrExpression");
        addProduction("ConditionalExpression", "LogicalOrExpression", "TERNARY_CONDITIONAL_LEFT", "Expression", "TERNARY_CONDITIONAL_RIGHT", "ConditionalExpression");

        addProduction("AssignmentOperator", "DIRECT_ASSIGNMENT");
        addProduction("AssignmentOperator", "PRODUCT_ASSIGNMENT");
        addProduction("AssignmentOperator", "QUOTIENT_ASSIGNMENT");
        addProduction("AssignmentOperator", "REMAINDER_ASSIGNMENT");
        addProduction("AssignmentOperator", "SUM_ASSIGNMENT");
        addProduction("AssignmentOperator", "DIFFERENCE_ASSIGNMENT");
        addProduction("AssignmentOperator", "BITWISE_LEFT_ASSIGNMENT");
        addProduction("AssignmentOperator", "BITWISE_RIGHT_ASSIGNMENT");
        addProduction("AssignmentOperator", "BITWISE_AND_ASSIGNMENT");
        addProduction("AssignmentOperator", "BITWISE_XOR_ASSIGNMENT");
        addProduction("AssignmentOperator", "BITWISE_OR_ASSIGNMENT");

        addProduction("AssignmentExpression", "ConditionalExpression");
        addProduction("AssignmentExpression", "UnaryExpression", "AssignmentOperator", "AssignmentExpression");

        addProduction("InitializerList", "Initializer");
        addProduction("InitializerList", "Initializer", "COMMA", "InitializerList");

        addProduction("Initializer", "AssignmentExpression");
        addProduction("Initializer", "LEFT_BRACE", "InitializerList", "RIGHT_BRACE");
        addProduction("Initializer", "LEFT_BRACE", "InitializerList", "COMMA", "RIGHT_BRACE");

        addProduction("InitDeclarator", "Declarator");
        addProduction("InitDeclarator", "Declarator", "DIRECT_ASSIGNMENT", "Initializer");

        addProduction("InitDeclaratorList", "InitDeclarator");
        addProduction("InitDeclaratorList", "InitDeclarator", "COMMA", "InitDeclaratorList");

        addProduction("Declaration", "DeclarationSpecifiers", "SEMICOLON");
        addProduction("Declaration", "DeclarationSpecifiers", "InitDeclaratorList", "SEMICOLON");

        addProduction("ExternalDeclaration", "FunctionDefinition");
        addProduction("ExternalDeclaration", "Declaration");

        addProduction("TranslationUnit", "ExternalDeclaration", "EOF");
        addProduction("TranslationUnit", "ExternalDeclaration", "TranslationUnit");

        start = symbols.get("TranslationUnit");
    }

    public void addProduction(String left, String... right ){
        symbols.compute(left, (key, old) -> tryAdding(key, old, right));
    }

    private Symbol tryAdding(String key, Symbol old, String... right) throws UndefinedSymbol {
        if (old == null){
            old = new Nonterminal(key, new ArrayList<>());
        }
        if (old.getClass() != Nonterminal.class){
            throw new RuntimeException();
        }

        Nonterminal nonterminal = (Nonterminal) old;
        if (right.length != 0){
            nonterminal.productions.add(
                    Arrays.stream(right)
                            .map(s -> nameToSymbol(s).orElseThrow(() -> new UndefinedSymbol(key, right, s)))
                            .collect(Collectors.toList()));
        }
        return nonterminal;
    }

    private Optional<Symbol> nameToSymbol(String name){
        return Optional.ofNullable(symbols.get(name));
    }
}
