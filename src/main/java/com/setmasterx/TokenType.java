package com.setmasterx;

public enum TokenType {
    SET_START,
    SET_END,
    INICIO,
    FIN,
    SI,
    ENTONCES,
    PARA_CADA,
    EN,
    VENN,

    UNION,
    INTERSECCION,
    DIFERENCIA,
    DIFERENCIA_SIMETRICA,

    CONTENCION,
    PERTENENCIA,
    ASIGNACION,
    IGUALDAD,

    LLAVE_ABRE,
    LLAVE_CIERRA,
    PAREN_ABRE,
    PAREN_CIERRA,
    COMA,

    IDENTIFICADOR,
    ENTERO,
    CARACTER,

    ERROR
}
