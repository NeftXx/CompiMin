/*****************************************************************
 * Lexer.java
 *
 * Copyright ©2020 Ronald Berdúo. All Rights Reserved.
 * This software is the proprietary information of Ronald Berdúo.
 *
 *****************************************************************/
package lexer;

import parser.Sym;

import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author Ronald Berdúo
 */

%%

%class Lexer
%cupsym Sym
%public
%unicode
%line
%column
%cup
%char

%{
    /**
     * Nombre del archivo que se esta analizando
     */
    private String filename;

    /**
     * Guarda el texto de las cadenas
     */
    private StringBuilder builder;

    /**
     * Creador de simbolos complejos
     */
    private ComplexSymbolFactory symbolFactory;

    /**
     * Constructor del analizador lexico
     *
     * @param in Entrada que se va analizar
     * @param symbolFactory creador de simbolos complejos
     */
    public Lexer(java.io.Reader in, ComplexSymbolFactory symbolFactory, String filename) {
	    this(in);
        this.builder = new StringBuilder();
	    this.symbolFactory = symbolFactory;
        this.filename = filename;
    }

    /**
     * Metodo que devuelve un nuevo java_cup.runtime.Symbol
     *
     * @param name nombre que recibira el simbolo
     * @param sym numero de token
     * @param value valor que recibira el simbolo
     * @param buflength tam del valor
     * @return java_cup.runtime.Symbol
     */
    private Symbol symbol(String name, int sym, Object value, int buflength) {
        Location left = new Location(
            this.filename,
            yyline + 1,
            yycolumn + yylength() - buflength,
            yychar + yylength() - buflength
        );
        Location right= new Location(
            this.filename,
            yyline + 1,
            yycolumn + yylength(),
            yychar + yylength()
        );
        return symbolFactory.newSymbol(name, sym, left, right, value);
    }

    /**
     * Metodo que devuelve un nuevo java_cup.runtime.Symbol
     *
     * @param name nombre que recibira el simbolo
     * @param sym numero de token
     * @return java_cup.runtime.Symbol
     */
    private Symbol symbol(String name, int sym) {
        Location left = new Location(
            this.filename,
            yyline + 1,
            yycolumn + 1,
            yychar
        );
        Location right= new Location(
            this.filename,
            yyline + 1,
            yycolumn + yylength(),
            yychar + yylength()
        );
        return symbolFactory.newSymbol(name, sym, left, right);
    }

    /**
     * Devuelve un nuevo java_cup.runtime.Symbol
     *
     * @param name nombre que recibira el simbolo
     * @param sym numero de token
     * @param val valor que recibira el simbolo
     * @return java_cup.runtime.Symbol
     */
    private Symbol symbol(String name, int sym, Object val) {
        Location left = new Location(
            this.filename,
            yyline + 1,
            yycolumn + 1,
            yychar
        );
        Location right= new Location(
            this.filename,
            yyline + 1,
            yycolumn + yylength(),
            yychar + yylength()
        );
        return symbolFactory.newSymbol(name, sym, left, right, val);
    }

    private void report_error(String message, Symbol info) {
        if (info instanceof ComplexSymbol) {
            ComplexSymbol cs = (ComplexSymbol) info;
            System.err.println(
                "Error léxico que abarca desde " + cs.getLeft() + " a " + cs.getRight() + ", " + message
            );
            return;
        }
        if (info.left != -1)
            System.err.println("Error léxico en " + info.left + ", " + message);    		
        else
            System.err.println("Error léxico, " + message);

    }
%}

%eofval{
    return symbolFactory.newSymbol(
        "EOF", Sym.EOF,
        new Location(
            this.filename,
            yyline + 1,
            yycolumn + 1,
            yychar
        ),
        new Location(
            this.filename,
            yyline + 1,
            yycolumn + 1,
            yychar + 1
        )
    );
%eofval}

/* Definición léxica */
Digito          = [0-9]

/**
 *  Finalización de línea:
 *
 *  Carácter salto de línea (LF ASCII)
 *  Carácter retorno de carro (CR ASCII)
 *  Carácter retorno de carro (CR ASCII) seguido de carácter salto de línea (LF ASCII)
 */
Fin_linea        = \r|\n|\r\n

/**
 *  Espacios en blanco:
 *
 *  Espacio (SP ASCII)
 *  Tabulación horizontal (HT ASCII)
 *  Caracteres de finalización de línea
 */
Espacios        = {Fin_linea} | [ \t\f]

/* Identificadores */
Identificador   = [_]*[a-zA-Z][a-zA-Z0-9_]*

/* literales */
Integer_Literal = {Digito}+
Decimal_Literal = {Digito}+"."{Digito}+
Boolean_Literal = true|false
Null_Literal    = null

/* Estados */

/**
 *  Comentarios:
 *
 *  Los comentarios de una línea que serán delimitados al inicio con los símbolos #
 *  y al final con un carácter de finalización de línea.
 */
%state COMENTARIO_DE_FIN_DE_LINEA

/**
 *  Comentarios múltiples:
 *  
 *  Los comentarios múltiples serán delimitados al inicio con los símbolos #*
 *  y al final con un los símbolos *#
 */
%state COMENTARIO_TRADICIONAL

/**
 * string: "<Caracteres ASCII>"
 */
%state CADENA

%%

<YYINITIAL> {
    /*
        palabras claves del lenguaje
        se llama al metodo al metodo symbol(nombre, token)
    */
    "continue"          { return symbol("continue", Sym.CONTINUE); }
    "function"          { return symbol("function", Sym.FUNCTION); }
    "default"           { return symbol("default", Sym.DEFAULT); }
    "println"           { return symbol("println", Sym.PRINTLN); }
    "return"            { return symbol("return", Sym.RETURN); }
    "switch"            { return symbol("switch", Sym.SWITCH); }
    "print"             { return symbol("print", Sym.PRINT); }
    "break"             { return symbol("break", Sym.BREAK); }
    "while"             { return symbol("while", Sym.WHILE); }
    "case"              { return symbol("case", Sym.CASE); }
    "else"              { return symbol("else", Sym.ELSE); }
    "for"               { return symbol("for", Sym.FOR); }
    "do"                { return symbol("do", Sym.DO); }
    "if"                { return symbol("if", Sym.IF); }


    /* literales */
    {Integer_Literal}   {
                            Symbol sym;
                            try {
                                sym = symbol("Literal integer", Sym.LIT_ENTERO, Integer.parseInt(yytext()));
                            } catch (NumberFormatException ex) {
                                sym = symbol("Literal integer", Sym.LIT_ENTERO, 0);
                                report_error("el número " + yytext () + " esta fuera del rango de un número entero.", sym);
                            }
                            return sym;
                        }

    {Decimal_Literal}   {
                            Symbol sym;
                            try {
                                sym = symbol("Literal double", Sym.LIT_DECIMAL, Double.parseDouble(yytext()));
                            } catch (NumberFormatException ex) {
                                sym = symbol("Literal double", Sym.LIT_DECIMAL, 0);
                                report_error("el número " + yytext () + " esta fuera del rango de un número decimal.", sym);
                            }
                            return sym;
                        }

    {Boolean_Literal}   { return symbol(yytext(), Sym.LIT_BOOLEANO, Boolean.parseBoolean(yytext())); }

    {Null_Literal}      { return symbol("null", Sym.NULL); }

    \"                  { builder.setLength(0); yybegin(CADENA); }

    /* nombres */
    {Identificador}     { return symbol("Id(" + yytext() + ")", Sym.ID, yytext()); }

    /* separadores */
    "=="                { return symbol("==", Sym.IGUAL_QUE);       }
    "!="                { return symbol("!=", Sym.DIFERENTE_QUE);   }
    ">="                { return symbol(">=", Sym.MAYOR_IGUAL_QUE); }
    "<="                { return symbol("<=", Sym.MENOR_IGUAL_QUE); }
    "||"                { return symbol("||", Sym.OR);              }
    "&&"                { return symbol("&",  Sym.AND);             }
    "!"                 { return symbol("!",  Sym.NOT);             }
    "^"                 { return symbol("^",  Sym.POTENCIA);        }
    "%"                 { return symbol("%",  Sym.MODULO);          }
    "+"                 { return symbol("+",  Sym.MAS);             }
    "-"                 { return symbol("-",  Sym.MENOS);           }
    "*"                 { return symbol("*",  Sym.MULT);            }
    "/"                 { return symbol("/",  Sym.DIV);             }
    "="                 { return symbol("=",  Sym.IGUAL);           }
    ">"                 { return symbol(">",  Sym.MAYOR_QUE);       }
    "<"                 { return symbol("<",  Sym.MENOR_QUE);       }
    "?"                 { return symbol("?",  Sym.INTERROGANTE);    }
    ":"                 { return symbol(":",  Sym.DOS_PUNTOS);      }
    "("                 { return symbol("(",  Sym.PAR_IZQ);         }
    ")"                 { return symbol(")",  Sym.PAR_DER);         }
    "["                 { return symbol("[",  Sym.COR_IZQ);         }
    "]"                 { return symbol("]",  Sym.COR_DER);         }
    ";"                 { return symbol(";",  Sym.PUNTO_COMA);      }
    "."                 { return symbol(".",  Sym.PUNTO);           }
    ","                 { return symbol(",",  Sym.COMA);            }
    "{"                 { return symbol("{",  Sym.LLAVE_IZQ);       }
    "}"                 { return symbol("}",  Sym.LLAVE_DER);       }

    /* espacios en blanco */
    {Espacios}          { /* IGNORAR ESPACIOS */ }

    /* comentarios */
    "/*"                { yybegin(COMENTARIO_TRADICIONAL); }
    "//"                { yybegin(COMENTARIO_DE_FIN_DE_LINEA); }
}

<COMENTARIO_TRADICIONAL> {
    "*/"                { yybegin(YYINITIAL); }
    <<EOF>>             { yybegin(YYINITIAL); }
    [^]                 { /* IGNORAR CUALQUIER COSA */ }
}

<COMENTARIO_DE_FIN_DE_LINEA> {
    {Fin_linea}         { yybegin(YYINITIAL); }
    .                   { /* IGNORAR */  }
}

<CADENA> {
    /* Fin de cadena */
    \"                  {
                            yybegin(YYINITIAL);
                            return symbol("String Literal", 
                                Sym.LIT_STRING, builder.toString(), builder.length()
                            );
                        }

    /* Secuencias de escape */
    "\\\""              { /* Insertar comillas doble */ builder.append('\"'); }
    "\\\\"              { /* Insertar barra */ builder.append('\\'); }
    "\\n"               { /* Insertar salto de linea */ builder.append('\n'); }
    "\\r"               { /* Insertar retorno de carro */ builder.append('\r'); }
    "\\t"               { /* Insertar tab vertical */ builder.append('\t'); }
    \\.                 { /* Cualquier otro, agregarlo al string como texto o reportar error */ builder.append(yytext()); }

    {Fin_linea}         {
                            Symbol sym = symbol("String Literal", 
                                Sym.LIT_STRING, builder.toString(), builder.length()
                            );
                            report_error("final de línea inesperado en una cadena.", sym);
                            yybegin(YYINITIAL);
                            return sym;
                        }

    [^\r\n\"\\]+        { builder.append(yytext()); }
}

/* Cualquier cosa que no coincida con lo de arriba es un error. */
[^] { report_error("el carácter \"" + yytext() + "\" no es válido dentro del lenguaje", symbol(yytext(), Sym.error)); }
