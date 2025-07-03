import tv.sistema.models.Perfil;
import tv.sistema.models.Serie;
import tv.sistema.services.ApiConnector;
import tv.sistema.services.JsonStorage;
import tv.sistema.services.SerieFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static Perfil perfil;

    public static void main(String[] args) {
        perfil = JsonStorage.carregarPerfil();

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
                case "2" -> visualizarLista();
                case "3" -> adicionarSerie();
                case "4" -> removerSerie();
                case "5" -> ordenarLista();
                case "6" -> {
                    JsonStorage.salvarPerfil(perfil);
                    System.out.println("Perfil salvo. Até a próxima!");
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Buscar série");
        System.out.println("2. Ver uma lista");
        System.out.println("3. Adicionar série à lista");
        System.out.println("4. Remover série da lista");
        System.out.println("5. Ordenar uma lista");
        System.out.println("6. Sair e salvar");
        System.out.print("Escolha: ");
    }

    private static void buscarSerie() {
        while (true) {
            System.out.print("Digite o nome da série que deseja buscar: ");
            String nome = scanner.nextLine();
            String query = URLEncoder.encode(nome, StandardCharsets.UTF_8);
            String respostaApi = ApiConnector.buscarUnicaSerie(query);

            if (respostaApi != null) {
                Serie resultado = SerieFactory.criarSerie(respostaApi);
                if (resultado != null) {
                    System.out.println("\nSérie encontrada:\n" + resultado);
                    break;
                } else {
                    System.out.println("Erro ao processar a série. Tente novamente.");
                }
            } else {
                System.out.println("Nenhuma série encontrada com esse nome. Tente novamente.");
            }
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
        Serie nova = SerieFactory.criarSerie(ApiConnector.buscarUnicaSerie(query));

        if (nova != null) {
            List<Serie> lista = escolherLista("adicionar");
            lista.add(nova);
            System.out.println("Série adicionada com sucesso.");
        } else {
            System.out.println("Série não encontrada.");
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
        System.out.println("\nPara qual lista você deseja " + acao + "?");
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
