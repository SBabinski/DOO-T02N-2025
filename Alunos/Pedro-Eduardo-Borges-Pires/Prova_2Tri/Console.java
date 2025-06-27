package spring_boot_api.tvmaze_api;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class Console {

    private final Scanner scanner = new Scanner(System.in);
    private final TvMazeClient tvMazeClient;
    private final ListasService listasService;

    public Console(TvMazeClient tvMazeClient, ListasService listasService) {
        this.tvMazeClient = tvMazeClient;
        this.listasService = listasService;
    }

    public void iniciar() {
        try {
            System.out.println("Digite seu nome:");
            String nome = scanner.nextLine();
            listasService.setNomeUsuario(nome);

            int opcao;
            do {
                try {
                    System.out.println("\nMenu:");
                    System.out.println("1. Buscar série");
                    System.out.println("2. Exibir listas");
                    System.out.println("3. Sair");
                    System.out.print("Escolha: ");
                    opcao = Integer.parseInt(scanner.nextLine());

                    switch (opcao) {
                        case 1 -> buscarSerie();
                        case 2 -> exibirListasMenu();
                        case 3 -> System.out.println("Saindo...");
                        default -> System.out.println("Opção inválida!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um número válido.");
                    opcao = -1;
                } catch (Exception e) {
                    System.out.println("Erro inesperado no menu: " + e.getMessage());
                    opcao = -1;
                }
            } while (opcao != 3);

        } catch (Exception e) {
            System.out.println("Erro inesperado ao iniciar o sistema: " + e.getMessage());
        }
    }

    private void buscarSerie() {
        try {
            System.out.print("Digite o nome da série para buscar: ");
            String nome = scanner.nextLine().trim();

            if (nome.isEmpty()) {
                System.out.println("Nome inválido.");
                return;
            }

            ShowsDTO serie = tvMazeClient.buscarPrimeiraSeriePorNome(nome);

            if (serie == null) {
                System.out.println("Nenhuma série encontrada.");
                return;
            }

            System.out.println("Série encontrada:");
            System.out.println(serie);

            System.out.println("\nAdicionar à lista:");
            System.out.println("1. Favoritas");
            System.out.println("2. Assistidas");
            System.out.println("3. Desejo assistir");
            System.out.println("4. Nenhuma");
            System.out.print("Escolha: ");
            int escolha = Integer.parseInt(scanner.nextLine());

            switch (escolha) {
                case 1 -> {
                    listasService.adicionarFavorita(serie);
                    System.out.println("Adicionado às favoritas!");
                }
                case 2 -> {
                    listasService.adicionarAssistida(serie);
                    System.out.println("Adicionado às assistidas!");
                }
                case 3 -> {
                    listasService.adicionarDesejo(serie);
                    System.out.println("Adicionado à lista de desejo!");
                }
                default -> System.out.println("Nenhuma ação realizada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Use apenas números para escolher a lista.");
        } catch (Exception e) {
            System.out.println("Erro ao buscar ou adicionar série: " + e.getMessage());
        }
    }

    private void exibirListasMenu() {
        try {
            System.out.println("\nQual lista deseja ver?");
            System.out.println("1. Favoritas");
            System.out.println("2. Assistidas");
            System.out.println("3. Desejo assistir");
            System.out.print("Escolha: ");
            int listaEscolhida = Integer.parseInt(scanner.nextLine());

            List<ShowsDTO> lista;

            switch (listaEscolhida) {
                case 1 -> lista = listasService.getListas().getFavoritas();
                case 2 -> lista = listasService.getListas().getAssistidas();
                case 3 -> lista = listasService.getListas().getDesejoAssistir();
                default -> {
                    System.out.println("Opção inválida.");
                    return;
                }
            }

            if (lista.isEmpty()) {
                System.out.println("Lista vazia.");
                return;
            }

            lista = ordenarEExibirLista(lista);

            System.out.println("\nDeseja remover alguma série dessa lista? (s/n)");
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("s")) {
                try {
                    System.out.println("Digite o número da série a ser removida:");
                    int indice = Integer.parseInt(scanner.nextLine()) - 1;

                    if (indice >= 0 && indice < lista.size()) {
                        ShowsDTO serie = lista.get(indice);

                        switch (listaEscolhida) {
                            case 1 -> listasService.removerFavorita(serie);
                            case 2 -> listasService.removerAssistida(serie);
                            case 3 -> listasService.removerDesejo(serie);
                        }

                        System.out.println("Série removida com sucesso.");
                    } else {
                        System.out.println("Índice inválido.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um número válido.");
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao exibir lista: " + e.getMessage());
        }
    }

    private List<ShowsDTO> ordenarEExibirLista(List<ShowsDTO> lista) {
        try {
            System.out.println("\nOrdenar por:");
            System.out.println("1. Nome (A-Z)");
            System.out.println("2. Nota geral");
            System.out.println("3. Estado da série");
            System.out.println("4. Data de estreia");
            System.out.print("Escolha: ");
            int ordem = Integer.parseInt(scanner.nextLine());

            Comparator<ShowsDTO> comparator = switch (ordem) {
                case 1 -> Comparator.comparing(ShowsDTO::getNome, String.CASE_INSENSITIVE_ORDER);
                case 2 -> Comparator.comparingDouble(ShowsDTO::getNotaGeral).reversed();
                case 3 -> Comparator.comparing(ShowsDTO::getEstado, String.CASE_INSENSITIVE_ORDER);
                case 4 -> Comparator.comparing(ShowsDTO::getDataEstreia, Comparator.nullsLast(String::compareTo));
                default -> null;
            };

            if (comparator != null) {
                lista = lista.stream().sorted(comparator).collect(Collectors.toList());
            }

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. A lista será exibida sem ordenação.");
        } catch (Exception e) {
            System.out.println("Erro ao ordenar lista: " + e.getMessage());
        }

        System.out.println("\nLista de Séries:");
        int i = 1;
        for (ShowsDTO serie : lista) {
            System.out.printf("%d. %s\n", i++, serie.toString());
        }

        return lista;
    }
}
