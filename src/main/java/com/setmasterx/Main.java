package com.setmasterx;

import javax.swing.JFileChooser;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecciona archivo fuente de SetMaster X");

        int result = chooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("No se seleccionó archivo.");
            return;
        }

        try (Reader reader = new InputStreamReader(
                new FileInputStream(chooser.getSelectedFile()), StandardCharsets.UTF_8)) {

            Lexer lexer = new Lexer(reader);

            System.out.println("=== TABLA DE TOKENS ===");
            System.out.printf("%-22s %-20s %-8s %-8s%n", "TOKEN", "LEXEMA", "LÍNEA", "COLUMNA");
            System.out.println("----------------------------------------------------------------");

            Token token;
            while ((token = lexer.nextToken()) != null) {
                System.out.printf("%-22s %-20s %-8d %-8d%n",
                        token.type(), token.lexeme(), token.line(), token.column());
            }

            if (!lexer.getErrors().isEmpty()) {
                System.out.println("\n=== ERRORES LÉXICOS ===");
                System.out.printf("%-12s %-8s %-8s%n", "SÍMBOLO", "LÍNEA", "COLUMNA");
                System.out.println("--------------------------------");
                for (LexicalError e : lexer.getErrors()) {
                    System.out.printf("%-12s %-8d %-8d%n", e.symbol(), e.line(), e.column());
                }
            }

        } catch (Exception e) {
            System.err.println("Error al procesar archivo: " + e.getMessage());
        }
    }
}

