import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuario = PersistenciaDados.carregarUsuario();

        if (usuario == null) {
            System.out.print("Digite seu nome ou apelido: ");
            usuario = new Usuario(scanner.nextLine());
        }

        while (true) {
            System.out.println("\nBem-vindo, " + usuario.getNome());
            System.out.println("1. Buscar séries");
            System.out.println("2. Ver lista (favoritos, assistidas, desejadas)");
            System.out.println("3. Remover série de uma lista");
            System.out.println("4. Ordenar listas");
            System.out.println("5. Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> buscarESalvarSerie(usuario, scanner);
                case "2" -> exibirListas(usuario);
                case "3" -> removerSerie(usuario, scanner);
                case "4" -> ordenarListas(usuario, scanner);
                case "5" -> {
                    PersistenciaDados.salvarUsuario(usuario);
                    System.out.println("Dados salvos. Até logo!");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void buscarESalvarSerie(Usuario usuario, Scanner scanner) {
        System.out.print("Digite o nome da série: ");
        String nome = scanner.nextLine();
        List<Serie> series = ConexaoTVMaze.buscarSeries(nome);

        if (series.isEmpty()) {
            System.out.println("Nenhuma série encontrada.");
            return;
        }

        for (int i = 0; i < series.size(); i++) {
            Serie s = series.get(i);
            System.out.printf("\n%d - %s (%.1f) - %s\n", i + 1, s.getNome(), s.getNota(), s.getStatus());
            System.out.println("Idioma: " + s.getIdioma());
            System.out.println("Gêneros: " + s.getGeneros());
            System.out.println("Estreia: " + s.getDataEstreia());
            System.out.println("Término: " + s.getDataTermino());
            System.out.println("Emissora: " + s.getEmissora());
        }

        System.out.print("\nAdicionar qual série? (número ou 0 para cancelar): ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        if (idx < 0 || idx >= series.size()) return;

        Serie selecionada = series.get(idx);
        System.out.println("Adicionar em: 1 - Favoritos | 2 - Assistidas | 3 - Desejadas");
        String escolha = scanner.nextLine();

        switch (escolha) {
            case "1" -> usuario.adicionar(usuario.getFavoritos(), selecionada);
            case "2" -> usuario.adicionar(usuario.getAssistidas(), selecionada);
            case "3" -> usuario.adicionar(usuario.getDesejadas(), selecionada);
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void exibirListas(Usuario usuario) {
        mostrarLista("Favoritos", usuario.getFavoritos());
        mostrarLista("Assistidas", usuario.getAssistidas());
        mostrarLista("Desejadas", usuario.getDesejadas());
    }

    private static void removerSerie(Usuario usuario, Scanner scanner) {
        System.out.println("Remover de: 1 - Favoritos | 2 - Assistidas | 3 - Desejadas");
        String tipo = scanner.nextLine();

        List<Serie> lista = switch (tipo) {
            case "1" -> usuario.getFavoritos();
            case "2" -> usuario.getAssistidas();
            case "3" -> usuario.getDesejadas();
            default -> {
                System.out.println("Inválido.");
                yield null;
            }
        };
        if (lista == null || lista.isEmpty()) return;

        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, lista.get(i).getNome());
        }
        System.out.print("Escolha a série para remover: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        if (idx >= 0 && idx < lista.size()) lista.remove(idx);
    }

    private static void ordenarListas(Usuario usuario, Scanner scanner) {
        System.out.println("Ordenar qual lista? 1 - Favoritos | 2 - Assistidas | 3 - Desejadas");
        List<Serie> lista = switch (scanner.nextLine()) {
            case "1" -> usuario.getFavoritos();
            case "2" -> usuario.getAssistidas();
            case "3" -> usuario.getDesejadas();
            default -> {
                System.out.println("Inválido.");
                yield null;
            }
        };
        if (lista == null || lista.isEmpty()) return;

        System.out.println("Ordenar por: 1 - Nome | 2 - Nota | 3 - Status | 4 - Estreia");
        switch (scanner.nextLine()) {
            case "1" -> lista.sort(Comparator.comparing(Serie::getNome));
            case "2" -> lista.sort(Comparator.comparingDouble(Serie::getNota).reversed());
            case "3" -> lista.sort(Comparator.comparing(Serie::getStatus));
            case "4" -> lista.sort(Comparator.comparing(Serie::getDataEstreia));
            default -> System.out.println("Opção inválida.");
        }

        mostrarLista("Lista ordenada", lista);
    }

    private static void mostrarLista(String titulo, List<Serie> lista) {
        System.out.println("\n📺 " + titulo.toUpperCase());
        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
        } else {
            for (Serie s : lista) {
                System.out.printf("• %s (%.1f) - %s\n", s.getNome(), s.getNota(), s.getStatus());
            }
        }
    }
}
