%{
   long NumeroLinea = 1;
%}

letra       [a-zA-Z_¥¤]
digito      [0-9]
signo       \+|\-
CR          \n
char        .
espacio     \x20|\t|\v
anything    .|\n

%%

(P|p)(R|r)(O|o)(C|c)(E|e)(D|d)(I|i)(M|m)(I|i)(E|e)(N|n)(T|t)(O|o)       return PROCEDIMIENTO;

(I|i)(N|n)(I|i)(C|c)(I|i)(O|o)                                          return INICIO;

(F|f)(I|i)(N|n)                                                         return FIN;

(V|v)(A|a)(R|r)                                                         return VAR;

(E|e)(N|n)(T|t)(E|e)(R|r)(O|o)                                          { yylval.entero = 1; return TIPO_SIMPLE; }
(B|b)(O|o)(O|o)(L|l)(E|e)(A|a)(N|n)(O|o)                                { yylval.entero = 2; return TIPO_SIMPLE; }

(A|a)(R|r)(R|r)(A|a)(Y|y)                                               return TIPO_ARRAY;

(E|e)(N|n)(T|t)(R|r)(A|a)(D|d)(A|a)                                     return ENTRADA;

(S|s)(A|a)(L|l)(I|i)(D|d)(A|a)                                          return SALIDA;

(V|v)(E|e)(R|r)(D|d)(A|a)(D|d)(E|e)(R|r)(O|o)                           { yylval.entero = 1; return CONSTANTE_BOOL; }
(F|f)(A|a)(L|l)(S|s)(O|o) 						{ yylval.entero = 0; return CONSTANTE_BOOL; }

(S|s)(I|i)                                                              return SI;

(E|e)(N|n)(T|t)(O|o)(N|n)(C|c)(E|e)(S|s)                                return ENTONCES;

(S|s)(I|i)(_)(N|n)(O|o)                                                 return SI_NO;

(M|m)(I|i)(E|e)(N|n)(T|t)(R|r)(A|a)(S|s)                                return MIENTRAS;

(H|h)(A|a)(C|c)(E|e)(R|r)                                               return HACER;

(R|r)(E|e)(P|p)(E|e)(T|t)(I|i)(R|r)                                     return REPETIR;

(H|h)(A|a)(S|s)(T|t)(A|a)                                               return HASTA;

:=                                                                      return ASIGNACION;

(N|n)(O|o)                                                              return NO;

(O|o)                                                                   return O;

\+                                                                      { yylval.entero = +1; return SIGNO; }
\-                                                                      { yylval.entero = -1; return SIGNO; }

\*|\/|ú|(Y|y)                                                           { yylval.entero = yytext[0];
									  return OP_MULTIPLICATIVO;
									}

\<          								{ yylval.entero = 1; return OP_RELACION; }
\<=                                                                     { yylval.entero = 2; return OP_RELACION; }
=                                                                       { yylval.entero = 3; return OP_RELACION; }
\<\>                                                                    { yylval.entero = 4; return OP_RELACION; }
\>                                                                      { yylval.entero = 5; return OP_RELACION; }
\>=                                                                     { yylval.entero = 6; return OP_RELACION; }

\(                                                                      return ABRIR_PARENTESIS;

\)                                                                      return CERRAR_PARENTESIS;

\[                                                                      return ABRIR_CORCHETE;

\]                                                                      return CERRAR_CORCHETE;

\.                                                                      return PUNTO;

\.\.                                                                    return RANGO;

:                                                                       return DOS_PUNTOS;

,                                                                       return COMA;

;                                                                       return PUNTO_Y_COMA;

{letra}({letra}|{digito})*                                              { yylval.cadena = malloc(strlen(yytext)+1);
									  strcpy(yylval.cadena,yytext);
									  return ID; }

'({char}|'')*'                                                          { yylval.cadena = malloc(strlen(yytext)+1);
									  strcpy(yylval.cadena,yytext);
									  return CADENA; }

{digito}+                                                               { yylval.entero = atoi(yytext); return NATURAL;}

{CR}                                                                    NumeroLinea++;

\(\*[^\*\)]*\*\)|\{[^\}]*\}                                             ;

{espacio}                                                               ;

.                                                                       fprintf (stdout,"L¡nea %ld: Error l‚xico '%s'\n",NumeroLinea,yytext);

%%

