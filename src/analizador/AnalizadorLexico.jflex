package analizador;
import java_cup.runtime.Symbol;

%%

%class scanner
%unicode
%cup
%line
%column
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
LETRA = [A-Za-zÑñ]
CADENA = [\"]([^\"\n])*[\"]
NUMERO_DECIMAL = {NUMERO_ENTERO}{PUNTO}{NUMERO_ENTERO}
IDENTIFICADOR = {LETRA}({LETRA}|{NUMERO_ENTERO})+
INTERVALO_CARACTERES_ESPECIALES = ([!-\/\[-\^`{-~]){TILDE}([!-\/\[-\^`{-~])
INTERVALO_LETRAS = {LETRA}{TILDE}{LETRA}
INTERVALO_NUMEROS = {NUMERO_ENTERO}{TILDE}{NUMERO_ENTERO}
CONJUNTO_NUMEROS = {NUMERO_ENTERO}{ESPACIO}*({COMA}{ESPACIO}*{NUMERO_ENTERO}{ESPACIO}*)+
CONJUNTO_LETRAS = {LETRA}{ESPACIO}*({COMA}{ESPACIO}*{LETRA}{ESPACIO}*)+
COMENTARIO_LINEAL = {DIAGONAL}{DIAGONAL}({LETRA}+|{ESPACIO})+{SALTO_LINEA}*
COMENTARIO_MULTILINEAL ={MENOR_QUE}{ADMIRACION}{SALTO_LINEA}*({LETRA}+|{ESPACIO}|{SALTO_LINEA})+{ADMIRACION}{MAYOR_QUE}{SALTO_LINEA}*
COMENTARIO = ({COMENTARIO_LINEAL}|{COMENTARIO_MULTILINEAL})
ESPACIO = [\ \r\t\f]
%%  

/*Simbolos*/
{MENOR_QUE} {System.out.println("Reconocio : "+yytext()+" MENOR QUE"); return new Symbol(sym.MENOR_QUE, yyline, yycolumn, yytext());}
{MAYOR_QUE} {System.out.println("Reconocio : "+yytext()+" MAYOR QUE");return new Symbol(sym.MAYOR_QUE, yyline, yycolumn, yytext());}
{ADMIRACION} {System.out.println("Reconocio : "+yytext()+" ADMIRACION");return new Symbol(sym.ADMIRACION, yyline, yycolumn, yytext());}
{DIAGONAL} {System.out.println("Reconocio : "+yytext()+" DIAGONAL");return new Symbol(sym.DIAGONAL, yyline, yycolumn, yytext());}
{LLAVE_IZQUIERDA} {System.out.println("Reconocio : "+yytext()+" LLAVE IZQUIERDA"); return new Symbol(sym.LLAVE_IZQUIERDA, yyline, yycolumn, yytext());}
{LLAVE_DERECHA} {System.out.println("Reconocio : "+yytext()+" LLAVE DERECHA");return new Symbol(sym.LLAVE_DERECHA, yyline, yycolumn, yytext());}
{PORCENTAJE} {System.out.println("Reconocio : "+yytext()+" PORCENTAJE");return new Symbol(sym.PORCENTAJE, yyline, yycolumn, yytext());}
{PUNTO_COMA} {System.out.println("Reconocio : "+yytext()+" PUNTO Y COMA");return new Symbol(sym.PUNTO_COMA, yyline, yycolumn, yytext());}
{DOS_PUNTOS} {System.out.println("Reconocio : "+yytext()+" DOS PUNTOS");return new Symbol(sym.DOS_PUNTOS, yyline, yycolumn, yytext());}
{PUNTO} {System.out.println("Reconocio : "+yytext()+" PUNTO");return new Symbol(sym.PUNTO, yyline, yycolumn, yytext());}
{MENOS} {System.out.println("Reconocio : "+yytext()+" MENOS");return new Symbol(sym.MENOS, yyline, yycolumn, yytext());}
{COMA} {System.out.println("Reconocio : "+yytext()+" COMA");return new Symbol(sym.COMA, yyline, yycolumn, yytext());}
{INTERROGACION} {System.out.println("Reconocio : "+yytext()+" INTERROGACION");return new Symbol(sym.INTERROGACION, yyline, yycolumn, yytext());}
{SUMA} {System.out.println("Reconocio : "+yytext()+" SUMA");return new Symbol(sym.SUMA, yyline, yycolumn, yytext());}
{ASTERISCO} {System.out.println("Reconocio : "+yytext()+" ASTERISCO");return new Symbol(sym.ASTERISCO, yyline, yycolumn, yytext());}
{TILDE} {System.out.println("Reconocio : "+yytext()+" TILDE");return new Symbol(sym.TILDE, yyline, yycolumn, yytext());}
{BARRA_VERTICAL} {System.out.println("Reconocio : "+yytext()+" BARRA VERTICAL");return new Symbol(sym.BARRA_VERTICAL, yyline, yycolumn, yytext());}
{RESERVADA_CONJUNTO} {System.out.println("Reconocio : "+yytext()+" PR CONJUNTO");return new Symbol(sym.RESERVADA_CONJUNTO, yyline, yycolumn, yytext());}

/*caracteres especiales*/
{COMILLA_SIMPLE} {System.out.println("Reconocio : "+yytext()+" COMILLA SIMPLE");return new Symbol(sym.COMILLA_SIMPLE, yyline, yycolumn, yytext());}
{COMILLA_DOBLE} {System.out.println("Reconocio : "+yytext()+" COMILLA DOBLE");return new Symbol(sym.COMILLA_DOBLE, yyline, yycolumn, yytext());}

/*expresiones regulares*/
{NUMERO_ENTERO} {System.out.println("Reconocio : "+yytext()+" NUMERO ENTERO");return new Symbol(sym.NUMERO_ENTERO, yyline, yycolumn, yytext());}
{NUMERO_DECIMAL} {System.out.println("Reconocio : "+yytext()+" NUMERO DECIMAL");return new Symbol(sym.NUMERO_DECIMAL, yyline, yycolumn, yytext());}
{LETRA} {System.out.println("Reconocio : "+yytext()+" LETRA");return new Symbol(sym.LETRA, yyline, yycolumn, yytext());}
{CADENA} {System.out.println("Reconocio : "+yytext()+" CADENA");return new Symbol(sym.CADENA, yyline, yycolumn, yytext());}
{IDENTIFICADOR} {System.out.println("Reconocio : "+yytext()+" IDENTIFICADOR"); return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext());}
{INTERVALO_LETRAS} {System.out.println("Reconocio : "+yytext()+" INTERVALO DE LETRAS");return new Symbol(sym.INTERVALO_LETRAS, yyline, yycolumn, yytext());}
{INTERVALO_NUMEROS} {System.out.println("Reconocio : "+yytext()+" INTERVALO DE NUMEROS");return new Symbol(sym.INTERVALO_NUMEROS, yyline, yycolumn, yytext());}
{INTERVALO_CARACTERES_ESPECIALES} {System.out.println("Reconocio : "+yytext()+" INTERVALO DE CARACTERES ESPECIALES");return new Symbol(sym.INTERVALO_CARACTERES_ESPECIALES, yyline, yycolumn, yytext());}
{CONJUNTO_LETRAS} {System.out.println("Reconocio : "+yytext()+" CONJUNTO DE LETRAS");return new Symbol(sym.CONJUNTO_LETRAS, yyline, yycolumn, yytext());}
{CONJUNTO_NUMEROS} {System.out.println("Reconocio : "+yytext()+" CONJUNTO DE NUMEROS");return new Symbol(sym.CONJUNTO_NUMEROS, yyline, yycolumn, yytext());}

//se ignora
{SALTO_LINEA} {/*Los saltos de linea serán ignorados*/}
{COMENTARIO} {/*Los comentarios serán ignorados*/}
{ESPACIO} { /*Los espacios serán ignorados*/ }

 . {
    String error = "Error Léxico: "+yytext()+" en la linea "+(yyline+1)+" y columna "+(yycolumn+1);
    System.out.println(error);
}







