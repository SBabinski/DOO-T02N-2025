import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BuscarSerie buscarSerie = new BuscarSerie();
        Usuario usario = new Usuario();
        CarregarInformacao carregarInformacao = new CarregarInformacao();
        GerenciadorDados gerenciadorDados = new GerenciadorDados();

        DadosApp dados = gerenciadorDados.carregarDados();

        Favoritos favoritos = new Favoritos(dados.getFavoritos());
        Assistidas assistidas = new Assistidas(dados.getAssistidas());
        DesejaAssistir desejaAssistir = new DesejaAssistir(dados.getDesejaAssistir());

        List<Series> ultimaBusca = null;

        System.out.println("Bem-vindo ao sistema de busca de séries TV!");
        System.out.print("Para começar, poderia me dizer seu nome? ");

        String nomeUsuario = scanner.nextLine();
        usario.setNome(nomeUsuario);

        dados.getNomesUsuarios().add(nomeUsuario);

        System.out.println("Seja bem-vindo, " + usario.getNome() + "!");


        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("0 - Encerrar o sistema");
            System.out.println("1 - Buscar série no catálogo");
            System.out.println("2 - Listar séries favoritas");
            System.out.println("3 - Adicionar série aos favoritos");
            System.out.println("4 - Remover série dos favoritos");
            System.out.println("5 - Listar séries assistidas");
            System.out.println("6 - Adicionar série aos assistidas");
            System.out.println("7 - Remover série dos assistidas");
            System.out.println("8 - Listar séries que deseja assistir");
            System.out.println("9 - Adicionar série às que deseja assistir");
            System.out.println("10 - Remover série das que deseja assistir");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 0:
                        System.out.println("Encerrando o sistema...");
                        System.out.println("Salvando dados antes de sair...");
                        gerenciadorDados.salvarDados(dados);
                        scanner.close();
                        return;

                    case 1:
                        System.out.print("Digite o nome da série: ");
                        String nomeSerie = scanner.nextLine();
                        ultimaBusca = buscarSerie.buscarSeries(nomeSerie);
                        if (ultimaBusca == null || ultimaBusca.isEmpty()) {
                            System.out.println("Nenhuma série encontrada com esse nome.");
                        } else {
                            System.out.println("\n--- Séries encontradas ---");
                            ultimaBusca.forEach(serie -> {
                                System.out.println(
                                        "Id: " + serie.getId() + "\n" +
                                                "Nome: " + serie.getName() + "\n" +
                                                "Idioma: " + (serie.getLanguage() != null ? serie.getLanguage() : "N/A") + "\n" +
                                                "Gêneros: " + (serie.getGenres() != null && !serie.getGenres().isEmpty() ? String.join(", ", serie.getGenres()) : "N/A") + "\n" +
                                                "Nota: " + (serie.getRating() != null && serie.getRating().getAverage() != null ? serie.getRating().getAverage() : "N/A") + "\n" +
                                                "Status: " + (serie.getStatus() != null ? serie.getStatus() : "Desconhecido") + "\n" +
                                                "Estreia: " + (serie.getPremiered() != null ? serie.getPremiered() : "Desconhecida") + "\n" +
                                                "Término: " + (serie.getEnded() != null ? serie.getEnded() : "Não finalizada") + "\n" +
                                                "Emissora: " + (serie.getNetwork() != null ? serie.getNetwork().getName() : "Desconhecida") + "\n"
                                );
                                System.out.println("------------------------------\n");
                            });
                        }
                        break;


                    case 2:
                        System.out.println("\nEscolha o critério de ordenação para favoritos:");
                        System.out.println("1 - Nome (A-Z)");
                        System.out.println("2 - Nota geral (maior para menor)");
                        System.out.println("3 - Estado da série");
                        System.out.println("4 - Data de estreia");
                        System.out.print("Opção: ");
                        try {
                            int criterio = scanner.nextInt();
                            scanner.nextLine();
                            ((Favoritos) favoritos).listarOrdenado(criterio);
                        } catch (InputMismatchException e) {
                            System.out.println("Opção inválida. Por favor, insira um número.");
                            scanner.nextLine();
                        }
                        break;

                    case 3:
                        carregarInformacao.adicionarSerie(scanner, ultimaBusca, favoritos, "favoritos");
                        break;

                    case 4:
                        carregarInformacao.removerSerie(scanner, favoritos, "favoritos");
                        break;

                    case 5:
                        System.out.println("\nEscolha o critério de ordenação para assistidas:");
                        System.out.println("1 - Nome (A-Z)");
                        System.out.println("2 - Nota geral (maior para menor)");
                        System.out.println("3 - Estado da série");
                        System.out.println("4 - Data de estreia");
                        System.out.print("Opção: ");
                        try {
                            int criterio = scanner.nextInt();
                            scanner.nextLine();
                            ((Assistidas) assistidas).listarOrdenado(criterio);
                        } catch (InputMismatchException e) {
                            System.out.println("Opção inválida. Por favor, insira um número.");
                            scanner.nextLine();
                        }
                        break;

                    case 6:
                        carregarInformacao.adicionarSerie(scanner, ultimaBusca, assistidas, "assistidas");
                        break;

                    case 7:
                        carregarInformacao.removerSerie(scanner, assistidas, "assistidas");
                        break;

                    case 8:
                        System.out.println("\nEscolha o critério de ordenação para 'deseja assistir':");
                        System.out.println("1 - Nome (A-Z)");
                        System.out.println("2 - Nota geral (maior para menor)");
                        System.out.println("3 - Estado da série");
                        System.out.println("4 - Data de estreia");
                        System.out.print("Opção: ");
                        try {
                            int criterio = scanner.nextInt();
                            scanner.nextLine();
                            ((DesejaAssistir) desejaAssistir).listarOrdenado(criterio);
                        } catch (InputMismatchException e) {
                            System.out.println("Opção inválida. Por favor, insira um número.");
                            scanner.nextLine();
                        }
                        break;

                    case 9:
                        carregarInformacao.adicionarSerie(scanner, ultimaBusca, desejaAssistir, "deseja assistir");
                        break;

                    case 10:
                        carregarInformacao.removerSerie(scanner, desejaAssistir, "deseja assistir");
                        break;

                    default:
                        System.out.println("Opção inválida. Por favor, tente novamente.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite apenas números.");
                scanner.nextLine();
            }
        }
    }
}