package sistemaserie;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static Usuario usuario;

    public static void main(String[] args) {
        usuario = SalvarUsu.carregar();

        if (usuario == null) {
            System.out.print("Digite seu nome ou apelido: ");
            String nome = sc.nextLine();
            usuario = new Usuario(nome);
        }

        System.out.println("\nBem-vindo(a), " + usuario.getNome() + "!\n");

        menuPrincipal();

        SalvarUsu.salvar(usuario);
        System.out.println("Saindo... Dados salvos com sucesso.");
    }

    private static void menuPrincipal() {
        while (true) {
            System.out.println("=========== MENU ===========");
            System.out.println("1. Buscar série");
            System.out.println("2. Adicionar à lista");
            System.out.println("3. Remover série");
            System.out.println("4. Ver listas");
            System.out.println("5. Ordenar listas");
            System.out.println("0. Sair");
            System.out.println("============================");

            System.out.print("Escolha uma opção: ");
            String opcao = sc.nextLine();

            switch (opcao) {
                case "1" -> buscarSerie();
                case "2" -> menuAdicionar();
                case "3" -> removerSerie();
                case "4" -> mostrarListas();
                case "5" -> ordenarListas();
                case "0" -> { return; }
                default -> System.out.println("Opção inválida.\n");
            }
        }
    }

    private static void buscarSerie() {
        System.out.print("Digite o nome da série: ");
        String nomeBusca = sc.nextLine();
        List<Serie> series = ApiTVMaze.buscarSeries(nomeBusca);

        if (series.isEmpty()) {
            System.out.println("Nenhuma série encontrada.");
            return;
        }

        for (int i = 0; i < series.size(); i++) {
            System.out.println((i + 1) + " - " + series.get(i).getName());
        }

        System.out.print("Escolha o número da série para ver detalhes: ");
        try {
            int escolha = Integer.parseInt(sc.nextLine()) - 1;
            if (escolha >= 0 && escolha < series.size()) {
                System.out.println("\n" + series.get(escolha));
            } else {
                System.out.println("Opção inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

private static void menuAdicionar() {
    System.out.println("Para qual lista deseja adicionar?");
    System.out.println("1. Favoritos");
    System.out.println("2. Assistidas");
    System.out.println("3. Desejo Assistir");
    System.out.println("0. Voltar");
    System.out.print("Escolha: ");
    String opcao = sc.nextLine();

    List<Serie> lista;
    switch (opcao) {
        case "1" -> lista = usuario.getFavoritos();
        case "2" -> lista = usuario.getAssistidas();
        case "3" -> lista = usuario.getDesejoAssistir();
        case "0" -> { return; }
        default -> {
            System.out.println("Opção inválida.");
            return;
        }
    }

    adicionarNaLista(lista);
}


    private static void adicionarNaLista(List<Serie> lista) {
        System.out.print("Digite o nome da série: ");
        List<Serie> series = ApiTVMaze.buscarSeries(sc.nextLine());

        if (series.isEmpty()) {
            System.out.println("Nenhuma série encontrada.\n");
            return;
        }

        for (int i = 0; i < series.size(); i++) {
            System.out.println((i + 1) + " - " + series.get(i).getName());
        }

        System.out.print("Escolha o número da série para adicionar: ");
        try {
            int escolha = Integer.parseInt(sc.nextLine()) - 1;
            if (escolha >= 0 && escolha < series.size()) {
                Serie s = series.get(escolha);
                if (lista.contains(s)) {
                    System.out.println("Essa série já está na lista.");
                } else {
                    lista.add(s);
                    System.out.println("Série adicionada com sucesso.");
                }
            } else {
                System.out.println("Opção inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

private static void removerSerie() {
    System.out.println("\nDe qual lista deseja remover?");
    System.out.println("1. Favoritos");
    System.out.println("2. Assistidas");
    System.out.println("3. Desejo Assistir");
    System.out.println("0. Voltar");
    System.out.print("Escolha: ");
    String opcao = sc.nextLine();

    List<Serie> lista;
    switch (opcao) {
        case "1" -> lista = usuario.getFavoritos();
        case "2" -> lista = usuario.getAssistidas();
        case "3" -> lista = usuario.getDesejoAssistir();
        case "0" -> { return; }
        default -> {
            System.out.println("Opção inválida.");
            return;
        }
    }

    if (lista.isEmpty()) {
        System.out.println("Lista vazia.");
        return;
    }

    for (int i = 0; i < lista.size(); i++) {
        System.out.println((i + 1) + " - " + lista.get(i).getName());
    }

    System.out.print("Escolha o número da série a remover: ");
    try {
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        if (idx >= 0 && idx < lista.size()) {
            Serie removida = lista.remove(idx);
            System.out.println("Série \"" + removida.getName() + "\" removida com sucesso.");
        } else {
            System.out.println("Índice inválido.");
        }
    } catch (NumberFormatException e) {
        System.out.println("Entrada inválida.");
    }
}

    private static void mostrarListas() {
        System.out.println("\n=== FAVORITOS ===");
        usuario.getFavoritos().forEach(System.out::println);

        System.out.println("\n=== ASSISTIDAS ===");
        usuario.getAssistidas().forEach(System.out::println);

        System.out.println("\n=== DESEJO ASSISTIR ===");
        usuario.getDesejoAssistir().forEach(System.out::println);
    }

    private static void ordenarListas() {
    System.out.println("\nQual lista deseja ordenar?");
    System.out.println("1. Favoritos");
    System.out.println("2. Assistidas");
    System.out.println("3. Desejo Assistir");
    System.out.println("0. Voltar");
    System.out.print("Escolha: ");
    String escolha = sc.nextLine();

    List<Serie> lista;
    switch (escolha) {
        case "1" -> lista = usuario.getFavoritos();
        case "2" -> lista = usuario.getAssistidas();
        case "3" -> lista = usuario.getDesejoAssistir();
        case "0" -> { return; }
        default -> {
            System.out.println("Opção inválida.");
            return;
        }
    }

    if (lista.isEmpty()) {
        System.out.println("Lista vazia.");
        return;
    }

    System.out.println("Critério de ordenação:");
    System.out.println("1. Nome");
    System.out.println("2. Nota");
    System.out.println("3. Status");
    System.out.println("4. Data de Estreia");
    System.out.print("Escolha: ");

    String criterio = sc.nextLine();

    Comparator<Serie> comparator = switch (criterio) {
        case "1" -> Comparator.comparing(Serie::getName);
        case "2" -> Comparator.comparingDouble(s -> s.getRating() != null && s.getRating().getAverage() != null
                ? s.getRating().getAverage() : 0.0);
        case "3" -> Comparator.comparing(Serie::getStatus, String.CASE_INSENSITIVE_ORDER);
        case "4" -> Comparator.comparing(Serie::getPremiered, Comparator.nullsLast(String::compareTo));
        default -> null;
    };

    if (comparator == null) {
        System.out.println("Critério inválido.");
        return;
    }

    lista.sort(comparator);
    System.out.println("Lista ordenada com sucesso.");
}

}
