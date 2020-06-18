# JavaCCompiler
C compiler in Java

_Jakub Ficoń, Igor Noga, Bartosz Ociepka_

## Wstęp

## Specyfikacja jezyka
Specyfikacja została zaczerpnięta ze standardu ISO/IEC 9899:201x[[1]](#1), nasza aplikacja obsługuje wybrane elementy z tamtej specyfikacji.
#### Elementy leksykalne
Tokenizer w naszej aplikacji rozpoznaje nastepujace elementy jezyka:
##### 1. Identyfikatory
##### 2. Stałe (constants)
Wyróżniamy: stałe liczbowe, całkowite i znakowe.
##### 3. Słowa kluczowe:
```
auto        AUTO
break       BREAK
case        CASE
char        CHAR
const       CONST
continue    CONTINUE
default     DEFAULT
do          DO
double      DOUBLE
else        ELSE
enum        ENUM
extern      EXTERN
float       FLOAT
for         FOR
goto        GOTO
if          IF
int         INT
long        LONG
register    REGISTER
return      RETURN
short       SHORT
signed      SIGNED
sizeof      SIZEOF
static      STATIC
struct      STRUCT
switch      SWITCH
typedef     TYPEDEF
union       UNION
unsigned    UNSIGNED
void        VOID
volatile    VOLATILE
while       WHILE
```
##### 4. Separatory:
```
[ ]     LEFT_BRACKET RIGHT_BRACKET
{ }     LEFT_BRACE RIGHT_BRACE
( )     LEFT_PARENTHESIS RIGHT_PARENTHESIS
<       LOWER
>       GREATER
<=      LOWER_OR_EQUAL
>=      GREATER_OR_EQUAL
.       DOT
->      ARROW
++      PLUS_PLUS
--      MINUS_MINUS
&       AMPERSAND
*       STAR
+       PLUS
-       MINUS
!       LOGIC_NOT
/       DIVIDE
\%      MODULO
<<      BITWISE_LEFT
>>      BITWISE_RIGHT
==      EQUAL_TO
!=      NOT_EQUAL_TO
\^      BITWISE_XOR
|       BITWISE_OR
&&      LOGIC_AND
||      LOGIC_OR
?       TERNARY_CONDITIONAL_LEFT
:       TERNARY_CONDITIONAL_RIGHT
;       SEMICOLON
~       TILDA
...     ELLIPSIS
=       DIRECT_ASSIGNMENT
*=      PRODUCT_ASSIGNMENT
/=      QUOTIENT_ASSIGNMENT
%=      REMAINDER_ASSIGNMENT
+=      SUM_ASSIGNMENT
-=      DIFFERENCE_ASSIGNMENT
<<      BITWISE_LEFT_ASSIGNMENT
>>      BITWISE_RIGHT_ASSIGNMENT
&=      BITWISE_AND_ASSIGNMENT
^=      BITWISE_XOR_ASSIGNMENT
|=      BITWISE_OR_ASSIGNMENT
,       COMMA
#       HASHTAG
##      DOUBLE_HASHTAG
```
##### 5. Koniec pliku (`EOF`) `\0`

#### Produkcje gramatyczne
Opierając się na gramatyce Yacc[[2]](#2), przygotowaliśmy następujące definicje produkcji gramatycznych:
```
UnaryOperator:
        AMPERSAND
        STAR
        PLUS
        MINUS
        TILDA
        LOGIC_NOT

PrimaryExpression:
        IDENTIFIER
        STRING_LITERAL
        INT_LITERAL
        LEFT_PARENTHESIS, Expression, RIGHT_PARENTHESIS

ArgumentExpressionList:
        AssignmentExpression
        AssignmentExpression, COMMA , ArgumentExpressionList

PostfixExpression: 
        PrimaryExpression
        LEFT_BRACKET, Expression, RIGHT_BRACKET, PostfixExpression
        LEFT_PARENTHESIS, RIGHT_PARENTHESIS, PostfixExpression
        LEFT_PARENTHESIS, ArgumentExpressionList, RIGHT_PARENTHESIS, PostfixExpression
        DOT, IDENTIFIER
        ARROW, IDENTIFIER
        PLUS_PLUS
        MINUS_MINUS

UnaryExpression:
        PostfixExpression
        PLUS_PLUS, UnaryExpression
        MINUS_MINUS, UnaryExpression
        UnaryOperator, CastExpression
        SIZEOF, UnaryExpression
        SIZEOF, LEFT_PARENTHESIS, TypeName, RIGHT_PARENTHESIS

SpecifierQualifierList:
        TypeSpecifier
        TypeSpecifier, SpecifierQualifierList
        TypeQualifier
        TypeQualifier, SpecifierQualifierList

DirectAbstractDeclarator:
        LEFT_PARENTHESIS, AbstractDeclarator, RIGHT_PARENTHESIS
        LEFT_BRACKET, RIGHT_BRACKET
        LEFT_BRACKET, ConditionalExpression, RIGHT_BRACKET
        LEFT_BRACKET, RIGHT_BRACKET, DirectAbstractDeclarator
        LEFT_BRACKET, ConditionalExpression, RIGHT_BRACKET, DirectAbstractDeclarator
        LEFT_PARENTHESIS, RIGHT_PARENTHESIS
        LEFT_PARENTHESIS, ParameterTypeList, RIGHT_PARENTHESIS
        LEFT_PARENTHESIS, RIGHT_PARENTHESIS, DirectAbstractDeclarator
        LEFT_PARENTHESIS, ParameterTypeList, RIGHT_PARENTHESIS, DirectAbstractDeclarator

TypeQualifierList:
        TypeQualifier
        TypeQualifierList, TypeQualifier, TypeQualifierList

Pointer:
        STAR
        STAR, TypeQualifierList
        STAR, Pointer
        STAR, TypeQualifierList, Pointer

AbstractDeclarator:
        Pointer
        DirectAbstractDeclarator
        Pointer, DirectAbstractDeclarator

TypeName:
        SpecifierQualifierList
        SpecifierQualifierList, AbstractDeclarator

CastExpression:	
        UnaryExpression
	LEFT_PARENTHESIS, TypeName, RIGHT_PARENTHESIS, CastExpression

MultiplicativeExpression:	
	CastExpression
	CastExpression, STAR, MultiplicativeExpression
	CastExpression, DIVIDE, MultiplicativeExpression
	CastExpression, MODULO, MultiplicativeExpression

AdditiveExpression:	
	MultiplicativeExpression
	MultiplicativeExpression, PLUS, AdditiveExpression
	MultiplicativeExpression, MINUS, AdditiveExpression

Expression:	
	AssignmentExpression
	AssignmentExpression, COMMA, Expression

Assignment:	
	IDENTIFIER, DIRECT_ASSIGNMENT, Expression

StructDeclarator:	
	Declarator
	TERNARY_CONDITIONAL_RIGHT, ConditionalExpression
	Declarator, TERNARY_CONDITIONAL_RIGHT, ConditionalExpression

StructDeclaratorList:	
	StructDeclarator
	StructDeclarator, COMMA, StructDeclaratorList

StructDeclaration:	
	SpecifierQualifierList, StructDeclaratorList, SEMICOLON

StructDeclarationList:	
	StructDeclaration
	StructDeclaration, StructDeclarationList

StructOrUnion:	
	STRUCT
	UNION

StructOrUnionSpecifier:	
	StructOrUnion, IDENTIFIER, LEFT_BRACE, StructDeclarationList, RIGHT_BRACE
	StructOrUnion, LEFT_BRACE, StructDeclarationList, RIGHT_BRACE
	StructOrUnion, IDENTIFIER

Enumerator:	
	IDENTIFIER
	IDENTIFIER, DIRECT_ASSIGNMENT, ConditionalExpression

EnumeratorList:	
	Enumerator
	Enumerator, COMMA, EnumeratorList

EnumSpecifier:	
	ENUM, LEFT_BRACE, EnumeratorList, RIGHT_BRACE
	ENUM, IDENTIFIER, LEFT_BRACE, EnumeratorList, RIGHT_BRACE
	ENUM, IDENTIFIER

TypeSpecifier:	
	INT
	VOID
	CHAR
	SHORT
	INT
	LONG
	FLOAT
	DOUBLE
	SIGNED
	UNSIGNED
	StructOrUnionSpecifier
	EnumSpecifier
	
TypeQualifier:	
	CONST
	VOLATILE

StorageClassSpecifier:	
	TYPEDEF
	EXTERN
	STATIC
	AUTO
	REGISTER

DeclarationSpecifiers:	
	TypeSpecifier
	TypeSpecifier, DeclarationSpecifiers
	TypeQualifier
	TypeQualifier, DeclarationSpecifiers
	StorageClassSpecifier
	StorageClassSpecifier, DeclarationSpecifiers

DeclarationList:	
	Declaration
	Declaration, DeclarationList

ParameterDeclaration:	
	DeclarationSpecifiers, Declarator
	DeclarationSpecifiers, AbstractDeclarator
	DeclarationSpecifiers

ParameterList:	
	ParameterDeclaration
	ParameterDeclaration, COMMA, ParameterList

ParameterTypeList:	
	ParameterList
	ParameterList, COMMA, ELLIPSIS

IdentifierList:	
	IDENTIFIER
	IDENTIFIER, COMMA, IdentifierList

DirectDeclarator:	
	IDENTIFIER
	LEFT_PARENTHESIS, Declarator, RIGHT_PARENTHESIS
	LEFT_BRACKET, ConditionalExpression, RIGHT_BRACKET
	LEFT_BRACKET, RIGHT_BRACKET
	LEFT_PARENTHESIS, ParameterTypeList, RIGHT_PARENTHESIS
	LEFT_PARENTHESIS, IdentifierList, RIGHT_PARENTHESIS
	LEFT_PARENTHESIS, RIGHT_PARENTHESIS

DirectDeclaratorList:	
	DirectDeclarator
	DirectDeclarator, DirectDeclaratorList

Declarator:	
	DirectDeclaratorList
	Pointer, DirectDeclaratorList

LabeledStatement:	
	IDENTIFIER, TERNARY_CONDITIONAL_RIGHT, Statement
	CASE, ConditionalExpression, TERNARY_CONDITIONAL_RIGHT, Statement //Uproszczone z ConstantExpression na ConditionalExpression
	DEFAULT, TERNARY_CONDITIONAL_RIGHT, Statement

ExpressionStatement:	
	SEMICOLON
	Expression, SEMICOLON

SelectionStatement:	
	IF, LEFT_PARENTHESIS, Expression, RIGHT_PARENTHESIS, Statement
	IF, LEFT_PARENTHESIS, Expression, RIGHT_PARENTHESIS, Statement, ELSE, Statement
	SWITCH, LEFT_PARENTHESIS, Expression, RIGHT_PARENTHESIS, Statement

IterationStatement:	
	WHILE, LEFT_PARENTHESIS, Expression, RIGHT_PARENTHESIS, Statement
	DO, Statement, WHILE, LEFT_PARENTHESIS, Expression, RIGHT_PARENTHESIS, SEMICOLON
	FOR, LEFT_PARENTHESIS, ExpressionStatement, ExpressionStatement, Statement
	WHILE, LEFT_PARENTHESIS, ExpressionStatement, ExpressionStatement, Expression, RIGHT_PARENTHESIS, Statement

JumpStatement:	
	GOTO, IDENTIFIER, SEMICOLON
	CONTINUE, SEMICOLON
	BREAK, SEMICOLON
	RETURN, SEMICOLON
	RETURN, Expression, SEMICOLON

Statement:	
	LabeledStatement
	CompoundStatement
	ExpressionStatement
	SelectionStatement
	IterationStatement
	JumpStatement

StatementList:	
	Statement
	Statement, StatementList

CompoundStatement:	
	LEFT_BRACE, RIGHT_BRACE
	LEFT_BRACE, DeclarationList,  RIGHT_BRACE
	LEFT_BRACE, StatementList,  RIGHT_BRACE
	LEFT_BRACE, DeclarationList, StatementList, RIGHT_BRACE

FunctionDefinition:	
	DeclarationSpecifiers, Declarator, DeclarationList, CompoundStatement
	DeclarationSpecifiers, Declarator, CompoundStatement
	Declarator, DeclarationList , CompoundStatement
	Declarator, CompoundStatement

ShiftExpression:	
	AdditiveExpression
	AdditiveExpression, BITWISE_LEFT, ShiftExpression
	AdditiveExpression, BITWISE_RIGHT, ShiftExpression

RelationalExpression:	
	ShiftExpression
	ShiftExpression, LOWER, RelationalExpression
	ShiftExpression, GREATER, RelationalExpression
	ShiftExpression, LOWER_OR_EQUAL, RelationalExpression
	ShiftExpression, GREATER_OR_EQUAL, RelationalExpression

EqualityExpression:	
	RelationalExpression
	RelationalExpression, EQUAL_TO, EqualityExpression
	RelationalExpression, NOT_EQUAL_TO, EqualityExpression

AndExpression:	
	EqualityExpression
	EqualityExpression, AMPERSAND, AndExpression

ExclusiveOrExpression:	
	AndExpression
	AndExpression, BITWISE_XOR, ExclusiveOrExpression

InclusiveOrExpression:	
	ExclusiveOrExpression
	ExclusiveOrExpression, BITWISE_OR,InclusiveOrExpression

LogicalAndExpression:	
	InclusiveOrExpression
	InclusiveOrExpression, LOGIC_AND, LogicalAndExpression

LogicalOrExpression:	
	LogicalAndExpression
	LogicalAndExpression, LOGIC_OR, LogicalOrExpression

ConditionalExpression:	
	LogicalOrExpression
	LogicalOrExpression, TERNARY_CONDITIONAL_LEFT, Expression, TERNARY_CONDITIONAL_RIGHT, ConditionalExpression

AssignmentOperator:	
	DIRECT_ASSIGNMENT
	PRODUCT_ASSIGNMENT
	QUOTIENT_ASSIGNMENT
	REMAINDER_ASSIGNMENT
	SUM_ASSIGNMENT
	DIFFERENCE_ASSIGNMENT
	BITWISE_LEFT_ASSIGNMENT
	BITWISE_RIGHT_ASSIGNMENT
	BITWISE_AND_ASSIGNMENT
	BITWISE_XOR_ASSIGNMENT
	BITWISE_OR_ASSIGNMENT

AssignmentExpression:	
	ConditionalExpression
	UnaryExpression, AssignmentOperator, AssignmentExpression

InitializerList:	
	Initializer
	Initializer, COMMA, InitializerList

Initializer:	
	AssignmentExpression
	LEFT_BRACE, InitializerList, RIGHT_BRACE
	LEFT_BRACE, InitializerList, COMMA, RIGHT_BRACE

InitDeclarator:	
	Declarator
	Declarator, DIRECT_ASSIGNMENT, Initializer

InitDeclaratorList:	
	InitDeclarator
	InitDeclarator, COMMA, InitDeclaratorList

Declaration:	
	DeclarationSpecifiers, SEMICOLON
	DeclarationSpecifiers, InitDeclaratorList, SEMICOLON

ExternalDeclaration:	
	FunctionDefinition
	Declaration

TranslationUnit:	
	ExternalDeclaration, EOF
	ExternalDeclaration, TranslationUnit

```
## Sposób działania
#### Skaner
W procesie analizy leksykalnej, kod źródłowy przechodzi przed dwa etapy: Tokenizer i Lekser.

Tokenizer dokonuje transormacji kodu źródłowego do postaci tablicy stringów, gdzie każdy string odpowiada jednemu tokenowi (jakiemu konkretnie - tym zajmie się lekser).

W procesie tokenizacji dodatkowo zignorowane zostają komentarze i białe znaki, które w procesie kompilacji nie mają znaczenia.

Lekser dokonuje zamiany stringów otrzymanych z tokenizera na konkretne tokeny - jeżeli znajdzie odpowienik w słowniku zdefiniowanych słów kluczowych i separatorów to przypisuje mu odpowiedni typ, a jeżeli nie odnajdzie takiego tokenu w słowniku, to dokonuje odpowiednich sprawdzeń i przypisuje tokenowi jeden z typów - stała znakowa, liczbowa, całkowita lub identyfikator.

Tak przygotowaną listę tokenów można następnie przekazać dalej do analizy składniowej.
#### Parser
#### Generator
Generator kodu wyjściowego tworzymy implementując wzorzec projektowy Visitor. Węzły drzewa składniowego posiadają metodę accept która na poszczególnych poddrzewach uruchamia metodę Visitora visit.

Działanie metody visit jest zależne od tego w jakim węźle Vistor się obecnie znajduje. Dla większości typów węzłów są generowane odpowiednie fragmenty kodu wyjściowego, oraz w wywoływane są metody visit na na poddrzewach (dzieciach) obecnie analizowanego węzła. Kolejność odwiedzanych dzieci jest zależna od oczekiwanego efektu końcowego.

Drzewo składniowe jest przez visitora przechodzone w głąb (DFS), do momentu aż algorytm dotrze do węzła oznaczonego jako EOF. Wtedy generator przerywa pracę.

Zastosowanie tego wzorca umożliwia oddzielenie logiki generatora od drzewa składniowego i w efekcie stworzenie wielu visitorów w zależności od potrzeb i oczekiwanych wyników kompilacji.

## Praca nad projektem
####Podział pracy

## Dalszy rozwój aplikacji

##Wnioski

## Źródła
- <a id="1">[1]</a>  http://www.open-std.org/jtc1/sc22/wg14/www/docs/n1548.pdf
- <a id="2">[2]</a> https://www.lysator.liu.se/c/ANSI-C-grammar-y.html