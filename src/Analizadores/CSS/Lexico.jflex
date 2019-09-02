/*--------- 1ra Area: Codigo de Usuario ---------*/

//--------> Paquetes e importaciones
package Analizadores.CSS;
import java_cup.runtime.*;
import Estructuras.Cola;
import Modelos.Errores;

/*--------- 2da Area: Opciones y Declaraciones ---------*/

%%
%{
    //--------> Codigo de Usuario en sintaxis Java
    public Cola ErroresLexicos = new Cola();

%}

//--------> Directivas
%public
%class Analisis_Lexico
%cupsym Simbolos
%cup
%char
%column
%full
%ignorecase
%line
%unicode
%8bit

//--------> Expresiones regulares
numero                  = [0-9]+
cadena                  = "'"[^"'"]*"'"
comentarioLinea         = "//".*
identificador           = [a-zA-Z]([a-zA-Z]|[0-9|"_"])*
subidentificador        = "."[a-zA-Z]([a-zA-Z]|[0-9|"_"])*
comentarioMultiLinea    = "/*"( [^*] | (\*+[^*/]) )*\*+\/
hexadecimal             = "#"[0-9a-f]{6}
booleano                = ("true"|"false")

//--------> Estados

%%

/*--------- 3ra Area: Reglas Lexicas ---------*/

//--------> Simbolos

<YYINITIAL> "("                     { return new Symbol(Simbolos.parentesisA, yycolumn, yyline, yytext()); }
<YYINITIAL> ")"                     { return new Symbol(Simbolos.parentesisC, yycolumn, yyline, yytext()); }
<YYINITIAL> ";"                     { return new Symbol(Simbolos.puntoComa, yycolumn, yyline, yytext()); }
<YYINITIAL> ":"                     { return new Symbol(Simbolos.dosPuntos, yycolumn, yyline, yytext()); }
<YYINITIAL> "{"                     { return new Symbol(Simbolos.llaveA, yycolumn, yyline, yytext()); }
<YYINITIAL> "}"                     { return new Symbol(Simbolos.llaveC, yycolumn, yyline, yytext()); }
<YYINITIAL> ","                     { return new Symbol(Simbolos.coma, yycolumn, yyline, yytext()); }

//--------> Palabras Reservaas

<YYINITIAL> "background"            { return new Symbol(Simbolos.background, yycolumn, yyline, yytext()); }
<YYINITIAL> "border"                { return new Symbol(Simbolos.border, yycolumn, yyline, yytext()); }
<YYINITIAL> "border-color"          { return new Symbol(Simbolos.borderColor, yycolumn, yyline, yytext()); }
<YYINITIAL> "border-width"          { return new Symbol(Simbolos.borderWidth, yycolumn, yyline, yytext()); }
<YYINITIAL> "align"                 { return new Symbol(Simbolos.align, yycolumn, yyline, yytext()); }
<YYINITIAL> "font"                  { return new Symbol(Simbolos.font, yycolumn, yyline, yytext()); }
<YYINITIAL> "font-size"             { return new Symbol(Simbolos.fontSize, yycolumn, yyline, yytext()); }
<YYINITIAL> "font-color"            { return new Symbol(Simbolos.fontColor, yycolumn, yyline, yytext()); }
<YYINITIAL> "hight"                 { return new Symbol(Simbolos.hight, yycolumn, yyline, yytext()); }
<YYINITIAL> "width"                 { return new Symbol(Simbolos.width, yycolumn, yyline, yytext()); }
<YYINITIAL> "rgb"                   { return new Symbol(Simbolos.rgb, yycolumn, yyline, yytext()); }
<YYINITIAL> "red"                   { return new Symbol(Simbolos.red, yycolumn, yyline, yytext()); }
<YYINITIAL> "pink"                  { return new Symbol(Simbolos.pink, yycolumn, yyline, yytext()); }
<YYINITIAL> "orange"                { return new Symbol(Simbolos.orange, yycolumn, yyline, yytext()); }
<YYINITIAL> "yellow"                { return new Symbol(Simbolos.yellow, yycolumn, yyline, yytext()); }
<YYINITIAL> "purple"                { return new Symbol(Simbolos.purple, yycolumn, yyline, yytext()); }
<YYINITIAL> "magenta"               { return new Symbol(Simbolos.magenta, yycolumn, yyline, yytext()); }
<YYINITIAL> "green"                 { return new Symbol(Simbolos.green, yycolumn, yyline, yytext()); }
<YYINITIAL> "blue"                  { return new Symbol(Simbolos.blue, yycolumn, yyline, yytext()); }
<YYINITIAL> "brown"                 { return new Symbol(Simbolos.brown, yycolumn, yyline, yytext()); }
<YYINITIAL> "white"                 { return new Symbol(Simbolos.white, yycolumn, yyline, yytext()); }
<YYINITIAL> "gray"                  { return new Symbol(Simbolos.gray, yycolumn, yyline, yytext()); }
<YYINITIAL> "black"                 { return new Symbol(Simbolos.black, yycolumn, yyline, yytext()); }
<YYINITIAL> "left"                  { return new Symbol(Simbolos.ALIleft, yycolumn, yyline, yytext()); }
<YYINITIAL> "right"                 { return new Symbol(Simbolos.ALIright, yycolumn, yyline, yytext()); }
<YYINITIAL> "center"                { return new Symbol(Simbolos.ALIcenter, yycolumn, yyline, yytext()); }

//--------> Expresiones Regulares

<YYINITIAL> {numero}                  { return new Symbol(Simbolos.numero, yycolumn, yyline, yytext()); }
<YYINITIAL> {cadena}                  { return new Symbol(Simbolos.cadena, yycolumn, yyline, yytext()); }
<YYINITIAL> {identificador}           { return new Symbol(Simbolos.cadena, yycolumn, yyline, yytext()); }
<YYINITIAL> {subidentificador}        { return new Symbol(Simbolos.cadena, yycolumn, yyline, yytext()); }
<YYINITIAL> {comentarioLinea}         {  }
<YYINITIAL> {comentarioMultiLinea}    {  }
<YYINITIAL> {hexadecimal}             { return new Symbol(Simbolos.hexadecimal, yycolumn, yyline, yytext()); }
<YYINITIAL> {booleano}                { return new Symbol(Simbolos.booleano, yycolumn, yyline, yytext()); }

//--------> Caracteres adicionales
[\t\r\n\f\s\v\xxx]                { /* Espacios en blanco se ignoran */ }

//--------> Manejo de Error Lexico

.                                   { 
                                        System.out.println("Error Lexico Lexema: " + yytext() + " Fila: " + yyline + " Columna: " + yycolumn);
                                        Errores error = new Errores(yytext(),yyline,yycolumn
                                            ,"Error Lexico, No Especificado en el Lenguaje","Lexico");
                                        ErroresLexicos.insertar(error);
                                    }
