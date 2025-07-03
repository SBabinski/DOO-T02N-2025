package seriesTV;

import java.util.List;
import java.util.Scanner;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final ApiTvMazeService apiService = new ApiTvMazeService();
    private final PersistenciaJson persistencia = new PersistenciaJson();
    private Usuario usuario;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PersistenciaJson persistencia = new PersistenciaJson();

        Usuario usuario = persistencia.carregar();

        if (usuario == null || usuario.getNome() == null || usuario.getNome().isBlank()) {
            System.out.println("===== Sistema de Séries TV =====");
            System.out.print("Antes de começar, informe seu nome ou apelido: ");
            String nomeUsuario = scanner.nextLine();
            usuario = new Usuario(nomeUsuario);
        }

        Main menu = new Main();
        menu.usuario = usuario;
        menu.iniciar();
    }

    public void iniciar() {
        int opcao = 0;
        do {
            System.out.println("\n=== Bem-vindo, " + usuario.getNome() + " ===");
            System.out.println("1. Buscar série");
            System.out.println("2. Exibir listas");
            System.out.println("3. Remover série de uma lista");
            System.out.println("4. Salvar e sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Entrada inválida. Por favor, digite um número.");
                continue;
            }

            switch (opcao) {
                case 1 -> buscarSerie();
                case 2 -> mostrarListas();
                case 3 -> removerSerie();
                case 4 -> {
                    persistencia.salvar(usuario);
                    System.out.println("✅ Dados salvos. Saindo...");
                }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 4);
    }

    private void buscarSerie() {
        System.out.print("\nDigite o nome da série: ");
        String nomeBusca = scanner.nextLine();
        Serie serie = apiService.buscarSeriePorNome(nomeBusca);

        if (serie != null) {
            System.out.println(serie);
            System.out.println("\nDeseja adicionar a série em alguma lista?");
            System.out.println("1. Favoritas");
            System.out.println("2. Já assistidas");
            System.out.println("3. Quero assistir");
            System.out.println("4. Não adicionar");

            int escolha;
            try {
                escolha = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Entrada inválida. Série não será adicionada.");
                return;
            }

            ListaSeries listas = usuario.getListas();

            switch (escolha) {
                case 1 -> listas.adicionarFavorita(serie);
                case 2 -> listas.adicionarJaAssistida(serie);
                case 3 -> listas.adicionarDesejoAssistir(serie);
                case 4 -> System.out.println("Série não adicionada em nenhuma lista.");
                default -> System.out.println("Opção inválida.");
            }
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    private void mostrarListas() {
        ListaSeries listas = usuario.getListas();

        System.out.println("\nQual lista deseja visualizar?");
        System.out.println("1. Favoritas");
        System.out.println("2. Já assistidas");
        System.out.println("3. Quero assistir");

        int escolha;
        try {
            escolha = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Entrada inválida!");
            return;
        }

        List<Serie> lista = switch (escolha) {
            case 1 -> listas.getFavoritas();
            case 2 -> listas.getJaAssistidas();
            case 3 -> listas.getDesejoAssistir();
            default -> null;
        };

        if (lista == null) {
            System.out.println("Opção inválida.");
            return;
        }

        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }

        System.out.println("\nDeseja ordenar por:");
        System.out.println("1. Nome");
        System.out.println("2. Nota");
        System.out.println("3. Status");
        System.out.println("4. Data de estreia");
        System.out.println("5. Sem ordenação");

        int ordem;
        try {
            ordem = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Entrada inválida! Exibindo sem ordenação.");
            ordem = 5;
        }

        switch (ordem) {
            case 1 -> listas.ordenarPorNome(lista);
            case 2 -> listas.ordenarPorNota(lista);
            case 3 -> listas.ordenarPorStatus(lista);
            case 4 -> listas.ordenarPorDataEstreia(lista);
            case 5 -> {} // Sem ordenação
            default -> System.out.println("Opção de ordenação inválida. Exibindo sem ordenação.");
        }

        lista.forEach(System.out::println);
    }

    private void removerSerie() {
        ListaSeries listas = usuario.getListas();

        System.out.println("\nDe qual lista deseja remover uma série?");
        System.out.println("1. Favoritas");
        System.out.println("2. Já assistidas");
        System.out.println("3. Quero assistir");

        int escolha;
        try {
            escolha = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Entrada inválida!");
            return;
        }

        List<Serie> lista = switch (escolha) {
            case 1 -> listas.getFavoritas();
            case 2 -> listas.getJaAssistidas();
            case 3 -> listas.getDesejoAssistir();
            default -> null;
        };

        if (lista == null) {
            System.out.println("Opção inválida.");
            return;
        }

        if (lista.isEmpty()) {
            System.out.println("A lista está vazia.");
            return;
        }

        System.out.println("\nSéries na lista:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getNome());
        }

        System.out.print("\nDigite o número da série que deseja remover (ou 0 para cancelar): ");
        int indice;
        try {
            indice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Entrada inválida. Cancelando remoção.");
            return;
        }

        if (indice == 0) {
            System.out.println("Remoção cancelada.");
            return;
        }

        if (indice < 1 || indice > lista.size()) {
            System.out.println("Número inválido.");
            return;
        }

        Serie removida = lista.remove(indice - 1);
        System.out.println("✅ Série '" + removida.getNome() + "' removida com sucesso.");
    }
}
