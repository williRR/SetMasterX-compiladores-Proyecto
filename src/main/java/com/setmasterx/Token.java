package com.setmasterx;

public record Token(TokenType type, String lexeme, int line, int column) {
}

