# JavaCCompiler
C compiler in Java

_Jakub Ficoń, Igor Noga, Bartosz Ociepka_

## Wstęp

## Specyfikacja jezyka
Specyfikacja została zaczerpnięta ze standardu ISO/IEC 9899:201x, nasza aplikacja obsługuje wybrane elementy z tamtej specyfikacji.
#### Elementy leksykalne
Tokenizer w naszej aplikacji rozpoznaje nastepujace elementy jezyka:
##### 1. Identyfikatory
##### 2. Stałe (constants)
Wyróżniamy: stałe liczbowe, całkowite i znakowe.
##### 3. Słowa kluczowe:
```
auto
break
case
char
const
continue
default
do
double
else
enum
extern
float
for
goto
if
int
long
register
return
short
signed
sizeof
static
struct
switch
typedef
union
unsigned
void
volatile
while
\0
```
##### 4. Separatory:
```
[ ]
{ }
( )
<
>
<=
>=
.
->
++
--
&
*
+
-
!
/
\%
<<
>>
==
!=
\^
|
&&
||
?
:
;
~
...
=
*=
/=
%=
+=
-=
<<
>>
&=
^=
|=
,
#
##
```
##### 5. Koniec pliku (EOF) `\0`

## Sposób działania
#### Tokenizer
#### Parser
#### Generator

## Development process

## Further development

## Źródła
http://www.open-std.org/jtc1/sc22/wg14/www/docs/n1548.pdf