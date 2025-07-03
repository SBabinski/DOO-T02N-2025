package com.cinelume.util;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }
    
    public static int lerInt(String mensagem, int min, int max) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor >= min && valor <= max) return valor;
                System.out.printf("Digite um número entre %d e %d\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido! Digite um número.");
            }
        }
    }
}