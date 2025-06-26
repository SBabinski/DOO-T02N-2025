import model.Serie;
import model.Usuario;
import service.TvMazeService;
import service.JsonPersistenciaService;
import util.OrdenadorSeries;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        JsonPersistenciaService jsonService = new JsonPersistenciaService();
        Usuario usuario = jsonService.carregar();

        if (usuario == null) {
            System.out.print("Digite seu nome ou apelido: ");
            usuario = new Usuario(sc.nextLine());
        }

        TvMazeService api = new TvMazeService();
        int opc;

        do {
            System.out.println("""
                \n--- MENU PRINCIPAL ---
                1. Buscar séries
                2. Ver listas
                3. Ordenar listas
                4. Remover séries das listas
                5. Salvar e sair
                ----------------------""");

            try {
                System.out.print("Escolha uma opção: ");
                opc = Integer.parseInt(sc.nextLine());

                switch (opc) {
                    case 1 -> buscarSerie(api, usuario, sc);
                    case 2 -> verListas(usuario);
                    case 3 -> ordenarListas(usuario, sc);
                    case 4 -> removerSerie(usuario, sc);
                    case 5 -> {
                        jsonService.salvar(usuario);
                        System.out.println("Dados salvos. Até mais, " + usuario.getNome() + "!");
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
                opc = 0; 
            }

        } while (opc != 5);
    }

    private static void buscarSerie(TvMazeService api, Usuario usuario, Scanner sc) {
        System.out.print("Nome da série: ");
        List<Serie> resultados = api.buscarSeries(sc.nextLine());
        if (resultados.isEmpty()) {
            System.out.println("Nenhuma série encontrada.");
            return;
        }

        for (int i = 0; i < resultados.size(); i++) {
            System.out.println((i + 1) + " - " + resultados.get(i).getNome());
        }

        System.out.print("Escolha uma série (0 para cancelar): ");
        int escolha = Integer.parseInt(sc.nextLine());
        if (escolha > 0 && escolha <= resultados.size()) {
            Serie s = resultados.get(escolha - 1);
            System.out.println(s);
            System.out.println("1-Favoritar 2-Mover para Assistidas 3-Mover para Desejo 0-Cancelar");
            int acao = Integer.parseInt(sc.nextLine());
            switch (acao) {
                case 1 -> usuario.adicionarFavorita(s);
                case 2 -> usuario.adicionarAssistida(s);
                case 3 -> usuario.adicionarDesejo(s);
            }
        }
    }

    private static void verListas(Usuario usuario) {
        System.out.println("\n--- Suas Listas ---");
        System.out.println("\nFavoritas:");
        usuario.getFavoritas().forEach(System.out::println);
        System.out.println("\nAssistidas:");
        usuario.getAssistidas().forEach(System.out::println);
        System.out.println("\nDeseja assistir:");
        usuario.getDesejo().forEach(System.out::println);
    }

    private static void ordenarListas(Usuario usuario, Scanner sc) {
        System.out.println("Ordenar por: 1-Nome 2-Nota 3-Status 4-Estreia");
        int tipo = Integer.parseInt(sc.nextLine());
        System.out.println("Qual lista? 1-Favoritas 2-Assistidas 3-Desejo");
        int qual = Integer.parseInt(sc.nextLine());

        List<Serie> lista = switch (qual) {
            case 1 -> usuario.getFavoritas();
            case 2 -> usuario.getAssistidas();
            case 3 -> usuario.getDesejo();
            default -> null;
        };

        if (lista != null) {
            switch (tipo) {
                case 1 -> OrdenadorSeries.ordenarPorNome(lista);
                case 2 -> OrdenadorSeries.ordenarPorNota(lista);
                case 3 -> OrdenadorSeries.ordenarPorStatus(lista);
                case 4 -> OrdenadorSeries.ordenarPorEstreia(lista);
                default -> System.out.println("Critério inválido!");
            }
            System.out.println("Lista ordenada!");
        }
    }

    private static void removerSerie(Usuario usuario, Scanner sc) {
        System.out.println("De qual lista deseja remover? 1-Favoritas 2-Assistidas 3-Desejo");
        int tipo = Integer.parseInt(sc.nextLine());
        System.out.print("Digite o ID da série a remover: ");
        int id = Integer.parseInt(sc.nextLine());

        switch (tipo) {
            case 1 -> usuario.removerFavorita(id);
            case 2 -> usuario.removerAssistida(id);
            case 3 -> usuario.removerDesejo(id);
            default -> System.out.println("Lista inválida.");
        }

        System.out.println("Remoção concluída (se o ID existir na lista).");
    }
}
