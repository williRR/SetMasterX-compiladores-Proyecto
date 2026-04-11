package com.setmasterx;

import java_cup.runtime.Symbol;

%%

%public
%class Lexer
%unicode
%line
%column
%cup

%{
    private Symbol symbol(TokenType type) {
        return new Symbol(type.ordinal(), yyline + 1, yycolumn + 1, yytext());
    }

    private Symbol symbol(TokenType type, Object value) {
        return new Symbol(type.ordinal(), yyline + 1, yycolumn + 1, value);
    }
%}

%eofval{
    return null;
%eofval}

Letra         = [A-Za-zÁÉÍÓÚáéíóúÑñ]
Digito        = [0-9]
Identificador = {Letra}({Letra}|{Digito}|_)*
Numero        = {Digito}+
Espacios      = [ \t\r\n\f]+

%%

{Espacios}                { /* ignorar */ }

// Comentarios
"//".*                    { /* ignorar */ }
[/][*][^*]*[*]+([^/*][^*]*[*]+)*[/]   { /* ignorar */ }

// Tokens de estructura
"SET_START"               { return symbol(TokenType.SET_START); }
"SET_END"                 { return symbol(TokenType.SET_END); }
"INICIO"                  { return symbol(TokenType.INICIO); }
"FIN"                     { return symbol(TokenType.FIN); }
"SI"                      { return symbol(TokenType.SI); }
"ENTONCES"                { return symbol(TokenType.ENTONCES); }
"PARA_CADA"               { return symbol(TokenType.PARA_CADA); }
"EN"                      { return symbol(TokenType.EN); }
"VENN"                    { return symbol(TokenType.VENN); }

// Operadores de conjuntos
"U"                       { return symbol(TokenType.UNION); }
"\u2229"                  { return symbol(TokenType.INTERSECCION); }
"-"                       { return symbol(TokenType.DIFERENCIA); }
"\u2206"                  { return symbol(TokenType.DIFERENCIA_SIMETRICA); }

// Operadores relacionales y símbolos
"\u2286"                  { return symbol(TokenType.CONTENCION); }
"\u2208"                  { return symbol(TokenType.PERTENENCIA); }
"=="                      { return symbol(TokenType.IGUALDAD); }
"="                       { return symbol(TokenType.ASIGNACION); }
"{"                       { return symbol(TokenType.LLAVE_ABRE); }
"}"                       { return symbol(TokenType.LLAVE_CIERRA); }
"("                       { return symbol(TokenType.PAREN_ABRE); }
")"                       { return symbol(TokenType.PAREN_CIERRA); }
","                       { return symbol(TokenType.COMA); }

// Literales
{Numero}                  { return symbol(TokenType.ENTERO, Integer.parseInt(yytext())); }
{Identificador}           { return symbol(TokenType.IDENTIFICADOR); }

// Regla crítica de error (siempre al final)
[^]                       { return symbol(TokenType.ERROR, new LexicalError(yytext(), yyline + 1, yycolumn + 1)); }
