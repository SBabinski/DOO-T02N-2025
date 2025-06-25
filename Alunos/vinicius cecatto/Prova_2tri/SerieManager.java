package prova2tri;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SerieManager {
    private User user;
    private Scanner scanner;

    public SerieManager(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
    }

    public void menu() {
        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1 - Buscar série");
            System.out.println("2 - Exibir listas");
            System.out.println("3 - Salvar e sair");
            System.out.print("Escolha: ");
            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1" -> buscarSerie();
                case "2" -> exibirListasMenu();
                case "3" -> {
                    System.out.println("Salvando dados e saindo...");
                    return;
                }
                default -> System.out.println("Ops, opção inválida. Tente novamente.");
            }
        }
    }

    private void buscarSerie() {
        while (true) {
            System.out.print("Digite o nome da série (ou 0 para voltar): ");
            String nomeBusca = scanner.nextLine();
            if (nomeBusca.equals("0")) {
                return;
            }

            try {
                List<Serie> resultados = ApiClient.buscarSeriesPorNome(nomeBusca);

                if (resultados.isEmpty()) {
                    System.out.println("Nenhuma série encontrada com esse nome. Deseja tentar novamente? (S/N)");
                    String resp = scanner.nextLine().trim().toUpperCase();
                    if (!resp.equals("S")) return;
                    else continue;
                }

                Serie principal = null;
                for (Serie s : resultados) {
                    if (s.getNome() != null && s.getNome().toLowerCase().contains(nomeBusca.toLowerCase())) {
                        principal = s;
                        break;
                    }
                }

                if (principal != null) {
                    System.out.println("\nSérie encontrada:");
                    System.out.println(principal.toString());

                    while (true) {
                        System.out.println("\nDeseja adicionar essa série a alguma lista?");
                        System.out.println("1 - Favoritos");
                        System.out.println("2 - Já assistidas");
                        System.out.println("3 - Deseja assistir");
                        System.out.println("0 - Voltar ao menu");
                        System.out.print("Escolha: ");
                        String opcao = scanner.nextLine();

                        switch (opcao) {
                            case "1" -> {
                                user.getFavoritos().add(principal);
                                System.out.println("Série adicionada aos Favoritos.");
                                return;
                            }
                            case "2" -> {
                                user.getAssistidas().add(principal);
                                System.out.println("Série adicionada às Já Assistidas.");
                                return;
                            }
                            case "3" -> {
                                user.getDesejoAssistir().add(principal);
                                System.out.println("Série adicionada à lista Deseja Assistir.");
                                return;
                            }
                            case "0" -> {
                                System.out.println("Operação cancelada. Voltando ao menu.");
                                return;
                            }
                            default -> System.out.println("Opção inválida. Tente novamente.");
                        }
                    }

                } else {
                    System.out.println("Nenhuma correspondência exata, mas aqui estão algumas sugestões próximas:");
                    for (int i = 0; i < Math.min(5, resultados.size()); i++) {
                        Serie s = resultados.get(i);
                        System.out.println((i + 1) + " - " + s.getNome());
                    }

                    while (true) {
                        System.out.println("Escolha o número da série para adicionar, ou 0 para voltar:");
                        String entrada = scanner.nextLine();

                        try {
                            int escolha = Integer.parseInt(entrada);
                            if (escolha == 0) return;
                            if (escolha > 0 && escolha <= resultados.size()) {
                                Serie selecionada = resultados.get(escolha - 1);
                                adicionarSerie(selecionada);
                                return;
                            } else {
                                System.out.println("Número inválido. Tente novamente.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Digite um número.");
                        }
                    }
                }

            } catch (IOException e) {
                System.out.println("Erro ao buscar série: " + e.getMessage());
                System.out.println("Deseja tentar novamente? (S/N)");
                String resp = scanner.nextLine().trim().toUpperCase();
                if (!resp.equals("S")) return;
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
                return;
            }
        }
    }

    private void adicionarSerie(Serie serie) {
        while (true) {
            System.out.println("Adicionar a qual lista?");
            System.out.println("1 - Favoritos");
            System.out.println("2 - Já assistidas");
            System.out.println("3 - Deseja assistir");
            System.out.println("0 - Cancelar");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> {
                    user.getFavoritos().add(serie);
                    System.out.println("Série adicionada aos Favoritos.");
                    return;
                }
                case "2" -> {
                    user.getAssistidas().add(serie);
                    System.out.println("Série adicionada às Já Assistidas.");
                    return;
                }
                case "3" -> {
                    user.getDesejoAssistir().add(serie);
                    System.out.println("Série adicionada à lista Deseja Assistir.");
                    return;
                }
                case "0" -> {
                    System.out.println("Operação cancelada.");
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void exibirListasMenu() {
        while (true) {
            System.out.println("\n--- Exibir Listas ---");
            System.out.println("1 - Favoritos");
            System.out.println("2 - Já assistidas");
            System.out.println("3 - Deseja assistir");
            System.out.println("4 - Remover série de uma lista");
            System.out.println("0 - Voltar ao menu");
            System.out.print("Escolha: ");
            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1" -> exibirLista(user.getFavoritos(), "Favoritos");
                case "2" -> exibirLista(user.getAssistidas(), "Já assistidas");
                case "3" -> exibirLista(user.getDesejoAssistir(), "Deseja assistir");
                case "4" -> removerSerieMenu();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void exibirLista(List<Serie> lista, String nomeLista) {
        if (lista.isEmpty()) {
            System.out.println("A lista " + nomeLista + " está vazia.");
            return;
        }

        ordenarLista(lista);

        System.out.println("\n--- Lista " + nomeLista + " ---");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i).toString());
        }
    }

    private void ordenarLista(List<Serie> lista) {
        while (true) {
            System.out.println("\nOrdenar por:");
            System.out.println("1 - Nome (Alfabético)");
            System.out.println("2 - Nota");
            System.out.println("3 - Estado");
            System.out.println("4 - Data de estreia");
            System.out.println("0 - Não ordenar");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> {
                    lista.sort(Comparator.comparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER));
                    return;
                }
                case "2" -> {
                    lista.sort(Comparator.comparing(Serie::getNota).reversed());
                    return;
                }
                case "3" -> {
                    lista.sort(Comparator.comparing(Serie::getStatus, String.CASE_INSENSITIVE_ORDER));
                    return;
                }
                case "4" -> {
                    lista.sort(Comparator.comparing(Serie::getDataEstreia, Comparator.nullsLast(String::compareTo)));
                    return;
                }
                case "0" -> {
                    return; // não ordena
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void removerSerieMenu() {
        while (true) {
            System.out.println("\nDe qual lista deseja remover uma série?");
            System.out.println("1 - Favoritos");
            System.out.println("2 - Já assistidas");
            System.out.println("3 - Deseja assistir");
            System.out.println("0 - Voltar ao menu");
            System.out.print("Escolha: ");
            String escolha = scanner.nextLine();

            List<Serie> lista = switch (escolha) {
                case "1" -> user.getFavoritos();
                case "2" -> user.getAssistidas();
                case "3" -> user.getDesejoAssistir();
                case "0" -> null;
                default -> {
                    System.out.println("Opção inválida. Tente novamente.");
                    yield null;
                }
            };

            if (lista == null) {
                if (escolha.equals("0")) return;
                continue;
            }

            if (lista.isEmpty()) {
                System.out.println("A lista selecionada está vazia.");
                return;
            }

            System.out.println("\n--- Lista ---");
            for (int i = 0; i < lista.size(); i++) {
                System.out.println((i + 1) + " - " + lista.get(i).getNome());
            }
            System.out.println("Digite o número da série para remover ou 0 para cancelar:");
            String entrada = scanner.nextLine();

            try {
                int idx = Integer.parseInt(entrada);
                if (idx == 0) {
                    System.out.println("Remoção cancelada.");
                    return;
                }
                if (idx < 1 || idx > lista.size()) {
                    System.out.println("Número inválido. Tente novamente.");
                } else {
                    Serie removida = lista.remove(idx - 1);
                    System.out.println("Série '" + removida.getNome() + "' removida da lista.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }
}
