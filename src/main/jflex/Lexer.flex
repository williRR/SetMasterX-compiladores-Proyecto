package com.setmasterx;

import java.util.ArrayList;
import java.util.List;

%%

%public
%class Lexer
%unicode
%line
%column
%type Token
%function nextToken

%{
    private final List<LexicalError> errors = new ArrayList<>();

    public List<LexicalError> getErrors() {
        return errors;
    }

    private Token token(TokenType type) {
        return new Token(type, yytext(), yyline + 1, yycolumn + 1);
    }
%}

%eofval{
    return null;
%eofval}

WHITESPACE = [ \t\r\n\f]+
ID         = [a-zA-Z_][a-zA-Z0-9_]*
INT        = [0-9]+
CHAR       = \'([^\\\'\n\r]|\\.)\'

%%

{WHITESPACE}            { /* Ignorar espacios */ }

"SET_START"             { return token(TokenType.SET_START); }
"SET_END"               { return token(TokenType.SET_END); }
"SI"                    { return token(TokenType.SI); }
"ENTONCES"              { return token(TokenType.ENTONCES); }
"PARA_CADA"             { return token(TokenType.PARA_CADA); }
"EN"                    { return token(TokenType.EN); }
"VENN"                  { return token(TokenType.VENN); }

"U"                     { return token(TokenType.UNION); }
"∩"                     { return token(TokenType.INTERSECCION); }
"-"                     { return token(TokenType.DIFERENCIA); }
"∆"                     { return token(TokenType.DIFERENCIA_SIMETRICA); }

"⊆"                     { return token(TokenType.CONTENCION); }
"∈"                     { return token(TokenType.PERTENENCIA); }
"=="                    { return token(TokenType.IGUALDAD); }

{INT}                   { return token(TokenType.ENTERO); }
{CHAR}                  { return token(TokenType.CARACTER); }
{ID}                    { return token(TokenType.IDENTIFICADOR); }

.                       { errors.add(new LexicalError(yytext(), yyline + 1, yycolumn + 1)); }

