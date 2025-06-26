import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static Perfil perfil;

    public static void main(String[] args) {
        perfil = Json.carregarPerfil();

        if (perfil == null) {
            System.out.print("Digite seu nome para iniciar o perfil: ");
            perfil = new Perfil();
            perfil.setNome(scanner.nextLine());
            System.out.println("Perfil criado com sucesso!");
        } else {
            System.out.println("Bem-vindo de volta, " + perfil.getNome() + "!");
        }

        while (true) {
            exibirMenu();
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> buscarSerie();
                case "2" -> buscarMultiplasSeries();
                case "3" -> visualizarLista();
                case "4" -> adicionarSerie();
                case "5" -> removerSerie();
                case "6" -> ordenarLista();
                case "0" -> {
                    Json.salvarPerfil(perfil);
                    System.out.println("Perfil salvo!!");
                    return;
                }
                default -> System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println();
        System.out.println("1. Buscar série");
        System.out.println("2. Buscar multiplas séries");
        System.out.println("3. Ver uma lista");
        System.out.println("4. Adicionar série à lista");
        System.out.println("5. Remover série da lista");
        System.out.println("6. Ordenar uma lista");
        System.out.println("0. Sair e salvar");
        System.out.println();
        System.out.print("Escolha: ");
    }

   private static void buscarSerie() {
    System.out.print("Digite o nome da série que deseja buscar: ");
    String nome = scanner.nextLine();
    String query = URLEncoder.encode(nome, StandardCharsets.UTF_8);

    try {
        String json = ApiConnector.buscarUnicaSerie(query);
        if (json != null) {
            Serie resultado = SerieEstrutura.criarSerie(json);
            if (resultado != null) {
                System.out.println("\nSérie encontrada:\n" + resultado);
            } else {
                System.out.println("Erro ao criar série a partir do JSON.");
            }
        } else {
            System.out.println("Nenhuma série encontrada com esse nome.");
        }
    } catch (IOException | InterruptedException e) {
        System.out.println("Erro ao buscar série: " + e.getMessage());
    }
}

    private static void buscarMultiplasSeries() {
        System.out.println("Digite o nome da série que deseja buscar: ");
        String nome = scanner.nextLine();
        String query = URLEncoder.encode(nome, StandardCharsets.UTF_8);

        try {
            String json = ApiConnector.buscarMultiplasSeries(query);
            if(json != null) {
               List<Serie> resultados = SerieEstrutura.criarListaDeSeries(json);
               if (resultados != null) {

                 for (int i = 0; i < resultados.size(); i++) {
                    Serie serie = resultados.get(i);
                    System.out.println((i + 1) + ". " + serie + "\n");
                 }

                 } else {
                    System.out.println("Erro ao criar séries a partir do JSON");
                 }
              } else { 
                System.out.println("Nenhuma série encontrada com esse nome.");
              }
            } catch (IOException | InterruptedException e) {
                System.err.println("Erro ao Buscar série: " + e.getMessage());
            }       
        }

    private static void visualizarLista() {
        List<Serie> lista = escolherLista("visualizar");
        if (lista.isEmpty()) {
            System.out.println("Essa lista está vazia.");
        } else {
            lista.forEach(s -> System.out.println("\n" + s));
        }
    }

  private static void adicionarSerie() {
    System.out.print("Digite o nome da série que deseja adicionar: ");
    String nome = scanner.nextLine();
    String query = URLEncoder.encode(nome, StandardCharsets.UTF_8);

    try {
        String json = ApiConnector.buscarUnicaSerie(query);
        if (json != null) {
            Serie nova = SerieEstrutura.criarSerie(json);
            if (nova != null) {
                List<Serie> lista = escolherLista("adicionar");

                if (lista.contains(nova)) {
                    System.out.println("Essa série já está na lista!");
                } else {
                    lista.add(nova);
                    System.out.println("Série adicionada com sucesso."); 
                }

            } else {
                System.out.println("Erro ao criar objeto série a partir do JSON.");
            }
        } else {
            System.out.println("Série não encontrada.");
        }
    } catch (IOException | InterruptedException e) {
        System.out.println("Erro ao buscar série: " + e.getMessage());
    }
}

    private static void removerSerie() {
        List<Serie> lista = escolherLista("remover de");
        if (lista.isEmpty()) {
            System.out.println("Essa lista está vazia.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getTitulo());
        }
        System.out.print("Informe o número da série para remover: ");

        try {
            int escolha = Integer.parseInt(scanner.nextLine()) - 1;
            if (escolha >= 0 && escolha < lista.size()) {
                lista.remove(escolha);
                System.out.println("Série removida.");
            } else {
                System.out.println("Número inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private static void ordenarLista() {
        List<Serie> lista = escolherLista("ordenar");
        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }

        System.out.println("Critérios:");
        System.out.println("1. Título");
        System.out.println("2. Nota");
        System.out.println("3. Situação");
        System.out.println("4. Estreia");
        System.out.print("Escolha: ");
        String criterio = scanner.nextLine();

        switch (criterio) {
            case "1" -> lista.sort(Comparator.comparing(Serie::getTitulo));
            case "2" -> lista.sort(Comparator.comparing(Serie::getNota).reversed());
            case "3" -> lista.sort(Comparator.comparing(Serie::getSituacao));
            case "4" -> lista.sort(Comparator.comparing(Serie::getEstreia));
            default -> System.out.println("Critério inválido.");
        }
    }

    private static List<Serie> escolherLista(String acao) {
        System.out.println("\n Qual lista você deseja " + acao + "?");
        System.out.println("1. Favoritos");
        System.out.println("2. Já assistidas");
        System.out.println("3. Desejo ver");
        System.out.print("Opção: ");

        return switch (scanner.nextLine()) {
            case "1" -> perfil.getFavoritos();
            case "2" -> perfil.getJaVistas();
            case "3" -> perfil.getDesejoVer();
            default -> List.of();
        };
    }
} 