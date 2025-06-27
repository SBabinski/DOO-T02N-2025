import models.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static Scanner scanner = new Scanner(System.in);
    public static User user;

    public static void main(String[] args) {
        user = Controller.carregarUsuario("usuario.json");

        if (user.name == null || user.name.isBlank()) {
            System.out.print("Digite seu nome: ");
            user.name = App.scanner.nextLine();
        }

        int opcao = -1;
        do {
            System.out.println("Usuário: " + user.name);
            System.out.println("--- MENU DE SÉRIES ---");
            System.out.println("1. Buscar série");
            System.out.println("2. Favoritas");
            System.out.println("3. Assistidas");
            System.out.println("4. Para Assistir");
            System.out.println("5. Ordenar Listas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção:\n");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.err.println("Aviso: você não digitou um número");
                scanner.nextLine();
            }
            switch (opcao) {
                case 1 -> Controller.buscarSerie();
                case 2 -> Controller.mostrarLista("Favoritas", user.seriesFavoritas, true);
                case 3 -> Controller.mostrarLista("Assistidas", user.seriesAssistidas, true);
                case 4 -> Controller.mostrarLista("Para Assistir", user.seriesParaAssistir, true);
                case 5 -> Controller.ordenarLista(Controller.selecionarLista());
                case 0 -> {
                    Controller.salvarUsuario("usuario.json");
                }
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }
}
