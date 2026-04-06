package com.setmasterx;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java_cup.runtime.Symbol;

public class Main {

    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecciona archivo fuente de SetMaster X");
        chooser.setFileFilter(new FileNameExtensionFilter("SetMaster X (*.cs, *.txt)", "cs", "txt"));

        int result = chooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("No se seleccionó archivo.");
            return;
        }

        Map<String, Token> conjuntos = new LinkedHashMap<>();
        Map<String, List<Token>> elementosPorConjunto = new HashMap<>();

        int totalTokensProcesados = 0;
        boolean dentroDeLlaves = false;
        String conjuntoActual = null;
        String ultimoIdentificador = null;

        try (Reader reader = new InputStreamReader(
                new FileInputStream(chooser.getSelectedFile()), StandardCharsets.UTF_8)) {

            Lexer lexer = new Lexer(reader);

            System.out.println("=== TABLA DE TOKENS ===");

            Symbol s;
            while ((s = lexer.next_token()) != null) {
                TokenType type = (s.sym >= 0 && s.sym < TokenType.values().length)
                        ? TokenType.values()[s.sym]
                        : TokenType.ERROR;

                if (type == TokenType.ERROR && s.value instanceof LexicalError error) {
                    System.out.printf("[LINEA: %d, COLUMNA: %d] | SÍMBOLO NO VÁLIDO: \"%s\"%n",
                            error.line(), error.column(), error.symbol());
                    totalTokensProcesados++;
                    continue;
                }

                String lexema = (s.value == null) ? "" : s.value.toString();
                Token token = new Token(type, lexema, s.left, s.right);

                System.out.printf("[LINEA: %d, COLUMNA: %d] | TOKEN: %s | VALOR: \"%s\"%n",
                        token.line(), token.column(), token.type(), token.lexeme());

                totalTokensProcesados++;

                if (type == TokenType.LLAVE_ABRE) {
                    dentroDeLlaves = true;
                    conjuntoActual = ultimoIdentificador;
                    continue;
                }

                if (type == TokenType.LLAVE_CIERRA) {
                    dentroDeLlaves = false;
                    conjuntoActual = null;
                    continue;
                }

                if (type == TokenType.IDENTIFICADOR && !dentroDeLlaves) {
                    ultimoIdentificador = lexema;
                    conjuntos.putIfAbsent(lexema, token);
                    continue;
                }

                if (dentroDeLlaves &&
                        (type == TokenType.IDENTIFICADOR || type == TokenType.ENTERO || type == TokenType.CARACTER)) {
                    String clave = (conjuntoActual == null) ? "__SIN_CONJUNTO__" : conjuntoActual;
                    elementosPorConjunto.computeIfAbsent(clave, k -> new ArrayList<>()).add(token);
                }
            }

            System.out.println();
            System.out.println("=== RESUMEN FINAL ===");
            System.out.println("Conjuntos detectados: " + conjuntos.size());
            System.out.println("Tokens procesados: " + totalTokensProcesados);

        } catch (Exception e) {
            System.err.println("Error al procesar archivo: " + e.getMessage());
        }
    }
}
