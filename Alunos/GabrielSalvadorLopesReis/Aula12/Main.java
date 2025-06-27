package Aula12;

import java.util.Scanner;

import static Aula12.VisualCrossingRequest.climaApi;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String token;
        do {
            System.out.println("Digite a cidade | [0] para sair");
            token = scanner.nextLine();

            if (!token.equals("0")) {
                System.out.println(climaApi(token));
            } else {
                break;
            }
        } while (true);
    }
}
