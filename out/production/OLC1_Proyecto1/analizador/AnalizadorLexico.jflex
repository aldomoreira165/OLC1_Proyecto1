package analizador;
import java_cup.runtime.Symbol;

%%
%class scanner
%unicode
%cup
%line
%public
%char
%ignorecase

/*simbolos*/

MENOR_QUE = "<"
MAYOR_QUE = ">"
ADMIRACION = "!"
DIAGONAL = "/"
LLAVE_IZQUIERDA = "{"
LLAVE_DERECHA = "}"
PORCENTAJE = "%"
PUNTO_COMA = ";"
DOS_PUNTOS = ":"
PUNTO = "."
MENOS = "-"
COMA = ","
INTERROGACION = "?"
SUMA = "+"
ASTERISCO = "*"
TILDE = "~"
BARRA_VERTICAL = "|"

/*palabras reservadas*/
RESERVADA_CONJUNTO = "Conj" 

/*caracteres especiales*/

SALTO_LINEA = "\n"
COMILLA_SIMPLE = "\'"
COMILLA_DOBLE = "\""

/*Expresiones regulares*/
NUMERO_ENTERO = [0-9]+
NUMERO_DECIMAL = [0-9]+.[0-9]+
PALABRA = [A-Za-zÑñ]+
ALFANUMERICO = ({PALABRA}+|{PALABRA}+{NUMERO_ENTERO}+)+
COMENTARIO_LINEAL = ({DIAGONAL}{DIAGONAL}{PALABRA})
COMENTARIO_MULTILINEAL = ({MENOR_QUE}{ADMIRACION}{SALTO_LINEA}{PALABRA}{SALTO_LINEA}{ADMIRACION}{MAYOR_QUE})
COMENTARIO = ({COMENTARIO_LINEAL}|{COMENTARIO_MULTILINEAL})
ESPACIO = [\ \r\t\f]
CARACTER_ESPECIAL = [!-}]
%%

/*falta validar comentarios*/

/*Simbolos*/
<YYINITIAL> {MENOR_QUE} {return new Symbol(sym.MENOR_QUE, yyline, yycolumn, yytext());}
<YYINITIAL> {MAYOR_QUE} {return new Symbol(sym.MAYOR_QUE, yyline, yycolumn, yytext());}
<YYINITIAL> {ADMIRACION} {return new Symbol(sym.ADMIRACION, yyline, yycolumn, yytext());}
<YYINITIAL> {DIAGONAL} {return new Symbol(sym.DIAGONAL, yyline, yycolumn, yytext());}
<YYINITIAL> {LLAVE_IZQUIERDA} {return new Symbol(sym.LLAVE_IZQUIERDA, yyline, yycolumn, yytext());}
<YYINITIAL> {LLAVE_DERECHA} {return new Symbol(sym.LLAVE_DERECHA, yyline, yycolumn, yytext());}
<YYINITIAL> {PORCENTAJE} {return new Symbol(sym.PORCENTAJE, yyline, yycolumn, yytext());}
<YYINITIAL> {PUNTO_COMA} {return new Symbol(sym.PUNTO_COMA, yyline, yycolumn, yytext());}
<YYINITIAL> {DOS_PUNTOS} {return new Symbol(sym.DOS_PUNTOS, yyline, yycolumn, yytext());}
<YYINITIAL> {PUNTO} {return new Symbol(sym.PUNTO, yyline, yycolumn, yytext());}
<YYINITIAL> {MENOS} {return new Symbol(sym.MENOS, yyline, yycolumn, yytext());}
<YYINITIAL> {COMA} {return new Symbol(sym.COMA, yyline, yycolumn, yytext());}
<YYINITIAL> {INTERROGACION} {return new Symbol(sym.INTERROGACION, yyline, yycolumn, yytext());}
<YYINITIAL> {SUMA} {return new Symbol(sym.SUMA, yyline, yycolumn, yytext());}
<YYINITIAL> {ASTERISCO} {return new Symbol(sym.ASTERISCO, yyline, yycolumn, yytext());}
<YYINITIAL> {TILDE} {return new Symbol(sym.TILDE, yyline, yycolumn, yytext());}
<YYINITIAL> {BARRA_VERTICAL} {return new Symbol(sym.BARRA_VERTICAL, yyline, yycolumn, yytext());}
<YYINITIAL> {RESERVADA_CONJUNTO} {return new Symbol(sym.RESERVADA_CONJUNTO, yyline, yycolumn, yytext());}

/*caracteres especiales*/
<YYINITIAL> {SALTO_LINEA} {return new Symbol(sym.SALTO_LINEA, yyline, yycolumn, yytext());}
<YYINITIAL> {COMILLA_SIMPLE} {return new Symbol(sym.COMILLA_SIMPLE, yyline, yycolumn, yytext());}
<YYINITIAL> {COMILLA_DOBLE} {return new Symbol(sym.COMILLA_DOBLE, yyline, yycolumn, yytext());}

/*expresiones regulares*/
<YYINITIAL> {NUMERO_ENTERO} {return new Symbol(sym.NUMERO_ENTERO, yyline, yycolumn, yytext());}
<YYINITIAL> {NUMERO_DECIMAL} {return new Symbol(sym.NUMERO_DECIMAL, yyline, yycolumn, yytext());}
<YYINITIAL> {PALABRA} {return new Symbol(sym.PALABRA, yyline, yycolumn, yytext());}
<YYINITIAL> {ALFANUMERICO} {return new Symbol(sym.ALFANUMERICO, yyline, yycolumn, yytext());}
<YYINITIAL> {COMENTARIO} {/*Los comentarios serán ignorados*/}
<YYINITIAL> {ESPACIO} { /*Los espacios serán ignorados*/ }
<YYINITIAL> {CARACTER_ESPECIAL} {return new Symbol(sym.CARACTER_ESPECIAL, yyline, yycolumn, yytext());}

<YYINITIAL> . {
    String error = "Error Lexico";
    System.out.println(error);
}







