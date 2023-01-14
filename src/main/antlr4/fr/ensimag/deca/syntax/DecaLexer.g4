lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}

// Deca lexer rules.


// Deca lexer rules.
MINUS: '-' ;
PLUS: '+' ;
OBRACE :'{' ;
CBRACE : '}' ;
COMMA :',' ;
EQUALS : '=' ;
PRINT : 'print' ;
PRINTLN : 'println' ;
PRINTX  : 'printx' ;
PRINTLNX : 'printlnx' ;
OPARENT : '(' ;
CPARENT : ')' ;
SEMI : ';';
IF : 'if' ;
ELSE : 'else';
WHILE : 'while';
RETURN : 'return' ;
AND  : '&&' ;
EQEQ : '==' ;
OR  : '||' ;
NEQ : '!=' ;
LEQ : '<=' ;
GEQ : '>=' ;
GT  : '>' ;
LT  : '<' ;
INSTANCEOF : 'instanceof' ;
EXCLAM :'!';
DOT : '.' ;

TRUE: 'true';
FALSE:'false';
THIS: 'this';
NULL:'null';
CLASS:'class' ;
EXTENDS:'extends' ;
PROTECTED:'protected' ;
TIMES : '*' ;
SLASH :'/' ;
PERCENT :'%' ;
READINT :'readInt' ;
READFLOAT :'readFloat' ;
NEW : 'new' ;
ASM :'asm' ;

ESPACE : (' ') { skip();};

TAB : '\t' {skip();};


fragment DIGIT : '0' .. '9';

fragment LETTER : ('a' .. 'z' | 'A' .. 'Z') ;
IDENT :(LETTER | '$' | '_')(LETTER | DIGIT | '$' | '_')*;



fragment NUM : DIGIT+;
fragment POSITIVE_DIGIT : '1' .. '9' ;
INT : '0' | POSITIVE_DIGIT DIGIT*;
SIGN : ('+' | '-') ;
EXP : ('E' | 'e') SIGN NUM ;
fragment DEC : NUM '.' NUM;
fragment FLOATDEC : (DEC | DEC EXP) ('F' | 'f' | ) ;


fragment DIGITHEX : ('0' .. '9' | 'A' .. 'F' | 'a' .. 'f') ;
NUMHEX : DIGITHEX+ ;
FLOATHEX : ('0x' |'0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f' | ) ;
FLOAT : FLOATDEC | FLOATHEX ;

EOL : ('\n') { skip(); };
STRING_CAR : ~('"'|'\\'| '\n' ) ;
STRING : '"'(STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING : '"' (STRING_CAR | EOL | '\\"' | '\\\\')* '"' ;

COMMENTAIRE :(  ('/*' .*? '*/') | ('//' .*? '\n' ) ) { skip(); } ;




fragment FILENAME : (LETTER | DIGIT | '.' | '-' | '_')+;
INCLUDE : '#include' (' ')* '"' FILENAME '"' ;   // FAux

