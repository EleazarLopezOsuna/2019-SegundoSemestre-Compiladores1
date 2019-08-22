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
comentarioMultiLinea    = "/*"( [^*] | (\*+[^*/]) )*\*+\/
hexadecimal             = "#"[0-9a-f]{6}

//--------> Estados

%%

/*--------- 3ra Area: Reglas Lexicas ---------*/

//--------> Simbolos

<YYINITIAL> "("                     { return new Symbol(Simbolos.parentesisA, yycolumn, yyline, yytext()); }
<YYINITIAL> ")"                     { return new Symbol(Simbolos.parentesisC, yycolumn, yyline, yytext()); }
<YYINITIAL> ";"                     { return new Symbol(Simbolos.puntoComa, yycolumn, yyline, yytext()); }
<YYINITIAL> "."                     { return new Symbol(Simbolos.punto, yycolumn, yyline, yytext()); }
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

//--------> Expresiones Regulares

<YYINITIAL> {numero}                  { return new Symbol(Simbolos.numero, yycolumn, yyline, yytext()); }
<YYINITIAL> {cadena}                  { return new Symbol(Simbolos.cadena, yycolumn, yyline, yytext()); }
<YYINITIAL> {comentarioLinea}         { return new Symbol(Simbolos.comentarioLinea, yycolumn, yyline, yytext()); }
<YYINITIAL> {comentarioMultiLinea}    { return new Symbol(Simbolos.comentarioMultiLinea, yycolumn, yyline, yytext()); }
<YYINITIAL> {hexadecimal}             { return new Symbol(Simbolos.hexadecimal, yycolumn, yyline, yytext()); }

//--------> Caracteres adicionales
[\t\r\n\f\s\v\xxx\xhh]                { /* Espacios en blanco se ignoran */ }

//--------> Manejo de Error Lexico

.                                   { 
                                        System.out.println("Error Lexico Lexema: " + yytext() + " Fila: " + yyline + " Columna: " + yycolumn);
                                        Errores error = new Errores(yytext(),yyline,yycolumn
                                            ,"Error Lexico, No Especificado en el Lenguaje","Lexico");
                                        ErroresLexicos.insertar(error);
                                    }
