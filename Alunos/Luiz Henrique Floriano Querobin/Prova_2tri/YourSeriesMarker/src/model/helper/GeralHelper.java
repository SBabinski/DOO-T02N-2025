package model.helper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GeralHelper {

    public static Integer inputInteger() {
        var scanner = new Scanner(System.in);
        Integer number = null;

        while (true) {
            try {
                number = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Erro ao consultar API: você deve digitar um número inteiro.");
            }
        }

        return number;
    }
}
