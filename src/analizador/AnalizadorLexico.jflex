package analizador;
import models.Excepcion;
import java_cup.runtime.Symbol;
import java.util.ArrayList;

%%

%class scanner
%unicode
%cup
%line
%column
%public
%ignorecase

%{
    public static ArrayList<Excepcion> erroresLexicos = new ArrayList<Excepcion>();
%}

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
NUMERAL = "#"
DOLAR = "$"
AMPERSON = "&"
IGUAL = "="
ARROBA = "@"
POTENCIA = "^"
GUION_BAJO = "_"
ACENTO_GRAVE = "`"
CORCHETE_IZQUIERDO = "["
CORCHETE_DERECHO = "]"

/*palabras reservadas*/
RESERVADA_CONJUNTO = "Conj" 

/*caracteres especiales*/

SALTO_LINEA = "\n"
COMILLA_SIMPLE = "\'"
COMILLA_DOBLE = "\""

/*Expresiones regulares*/
ESPACIO = [\ \r\t\f]
NUMERO = [0-9]
NUMERO_DECIMAL = [0-9]+("."[  |0-9]+)?
LETRA =[a-zA-ZÑñ]
IDENTIFICADOR = {LETRA}({LETRA}|{NUMERO}|"_")*
COMENTARIO_LINEAL = {DIAGONAL}{DIAGONAL}({LETRA}|{ESPACIO}|{NUMERO}|{NUMERO_DECIMAL}|{MENOR_QUE}|{MAYOR_QUE}|{ADMIRACION}|{DIAGONAL}|{LLAVE_IZQUIERDA}|{LLAVE_DERECHA}|{PORCENTAJE}|{PUNTO_COMA}|{DOS_PUNTOS}|
{PUNTO}|{MENOS}|{COMA}|{INTERROGACION}|{SUMA}|{ASTERISCO}|{TILDE}|{BARRA_VERTICAL}|{NUMERAL}|{DOLAR}|{AMPERSON}|
{IGUAL}|{ARROBA}|{POTENCIA}|{GUION_BAJO}|{COMILLA_SIMPLE}|{COMILLA_DOBLE}|{CORCHETE_IZQUIERDO}|{CORCHETE_DERECHO}|
{ACENTO_GRAVE})+{SALTO_LINEA}*
COMENTARIO_MULTILINEAL ={MENOR_QUE}{ADMIRACION}{SALTO_LINEA}*({LETRA}|{ESPACIO}|{NUMERO}|{NUMERO_DECIMAL}|{SALTO_LINEA}|{MENOR_QUE}|{MAYOR_QUE}|{ADMIRACION}|{DIAGONAL}|{LLAVE_IZQUIERDA}|{LLAVE_DERECHA}|{PORCENTAJE}|{PUNTO_COMA}|{DOS_PUNTOS}|
{PUNTO}|{MENOS}|{COMA}|{INTERROGACION}|{SUMA}|{ASTERISCO}|{TILDE}|{BARRA_VERTICAL}|{NUMERAL}|{DOLAR}|{AMPERSON}|
{IGUAL}|{ARROBA}|{POTENCIA}|{GUION_BAJO}|{COMILLA_SIMPLE}|{COMILLA_DOBLE}|{CORCHETE_IZQUIERDO}|{CORCHETE_DERECHO}|
{ACENTO_GRAVE})+{ADMIRACION}{MAYOR_QUE}{SALTO_LINEA}*
COMENTARIO = ({COMENTARIO_LINEAL}|{COMENTARIO_MULTILINEAL})

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
{NUMERAL} {System.out.println("Reconocio : "+yytext()+" NUMERAL");return new Symbol(sym.NUMERAL, yyline, yycolumn, yytext());}
{DOLAR} {System.out.println("Reconocio : "+yytext()+" DOLAR");return new Symbol(sym.DOLAR, yyline, yycolumn, yytext());}
{AMPERSON} {System.out.println("Reconocio : "+yytext()+" PR AMPERSON");return new Symbol(sym.AMPERSON, yyline, yycolumn, yytext());}
{IGUAL} {System.out.println("Reconocio : "+yytext()+" IGUAL");return new Symbol(sym.IGUAL, yyline, yycolumn, yytext());}
{ARROBA} {System.out.println("Reconocio : "+yytext()+" ARROBA");return new Symbol(sym.ARROBA, yyline, yycolumn, yytext());}
{POTENCIA} {System.out.println("Reconocio : "+yytext()+" POTENCIA");return new Symbol(sym.POTENCIA, yyline, yycolumn, yytext());}
{GUION_BAJO} {System.out.println("Reconocio : "+yytext()+" GUION BAJO");return new Symbol(sym.GUION_BAJO, yyline, yycolumn, yytext());}

/*caracteres especiales*/
{COMILLA_SIMPLE} {System.out.println("Reconocio : "+yytext()+" COMILLA SIMPLE");return new Symbol(sym.COMILLA_SIMPLE, yyline, yycolumn, yytext());}
{COMILLA_DOBLE} {System.out.println("Reconocio : "+yytext()+" COMILLA DOBLE");return new Symbol(sym.COMILLA_DOBLE, yyline, yycolumn, yytext());}

/*expresiones regulares*/
{NUMERO} {System.out.println("Reconocio : "+yytext()+" NUMERO");return new Symbol(sym.NUMERO, yyline, yycolumn, yytext());}
{NUMERO_DECIMAL} {System.out.println("Reconocio : "+yytext()+" NUMERO DECIMAL");return new Symbol(sym.NUMERO_DECIMAL, yyline, yycolumn, yytext());}
{LETRA} {System.out.println("Reconocio : "+yytext()+" LETRA");return new Symbol(sym.LETRA, yyline, yycolumn, yytext());}
{IDENTIFICADOR} {System.out.println("Reconocio : "+yytext()+" IDENTIFICADOR"); return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext());}

//se ignora
{SALTO_LINEA} {/*Los saltos de linea serán ignorados*/}
{COMENTARIO} {/*Los comentarios serán ignorados*/}
{ESPACIO} { /*Los espacios serán ignorados*/ }

 . {
    //String error = "Error Léxico: "+yytext()+" en la linea "+(yyline+1)+" y columna "+(yycolumn+1);
    Excepcion nuevo_error = new Excepcion("Léxico", yytext(), (yyline+1), (yycolumn+1));
    erroresLexicos.add(nuevo_error);
    //System.out.println(error);
}