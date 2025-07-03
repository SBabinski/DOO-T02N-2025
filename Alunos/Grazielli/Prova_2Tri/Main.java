package prova2tri;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");

        Usuario usuario;

        try {
            System.out.print("\nDigite seu nome: ");
            String nome = sc.nextLine().trim();

            usuario = JsonUtil.carregar(nome);
            if (usuario == null) {
                usuario = new Usuario(nome);
                System.out.println("Novo perfil criado.");
            } else {
                System.out.println("Bem-vindo de volta, " + usuario.getNome() + "!");
            }

            while (true) {
                System.out.println("\nMENU:");
                System.out.println("1. Adicionar série");
                System.out.println("2. Remover série");
                System.out.println("3. Ver listas");
                System.out.println("4. Salvar e sair");

                int opc = lerInteiro(sc, "Escolha uma opcao: ");

                if (opc == 1) {
                    System.out.print("Nome da série: ");
                    String nomeSerie = sc.nextLine();

                    Serie s = ApiTvMaze.buscarSerie(nomeSerie);

                    System.out.println("\nEncontrada: " + s.getNome());
                    System.out.println("Idioma: " + s.getIdioma());
                    System.out.println("Gêneros: " + s.getGeneros());
                    System.out.println("Nota Geral: " + s.getNota());
                    System.out.println("Estado: " + s.getStatus());
                    System.out.println("Estreia: " + s.getDataEstreia());
                    System.out.println("Término: " + s.getDataFim());
                    System.out.println("Emissora: " + s.getEmissora());

                    System.out.println("Adicionar a:");
                    System.out.println("1. Favoritas | 2. Assistidas | 3. Quero assistir");

                    int escolha = lerInteiro(sc, "Escolha uma opcao: ");

                    switch (escolha) {
                        case 1 -> {
                            if (!usuario.getFavoritas().contains(s)) {
                                usuario.getFavoritas().add(s);
                                System.out.println("Série adicionada às Favoritas.");
                            } else {
                                System.out.println("Série já está nas Favoritas.");
                            }
                        }
                        case 2 -> {
                            if (!usuario.getAssistidas().contains(s)) {
                                usuario.getAssistidas().add(s);
                                System.out.println("Série adicionada às Assistidas.");
                            } else {
                                System.out.println("Série já está nas Assistidas.");
                            }
                        }
                        case 3 -> {
                            if (!usuario.getQueroAssistir().contains(s)) {
                                usuario.getQueroAssistir().add(s);
                                System.out.println("Série adicionada à Quero Assistir.");
                            } else {
                                System.out.println("Série já está na lista Quero Assistir.");
                            }
                        }
                        default -> System.out.println("Opção inválida para lista.");
                    }

                } else if (opc == 2) {
                    System.out.println("\nDe qual lista deseja remover?");
                    System.out.println("1. Favoritas | 2. Assistidas | 3. Quero Assistir");

                    int lista = lerInteiro(sc, "Escolha uma opcao: ");

                    List<Serie> listaSelecionada = switch (lista) {
                        case 1 -> usuario.getFavoritas();
                        case 2 -> usuario.getAssistidas();
                        case 3 -> usuario.getQueroAssistir();
                        default -> null;
                    };

                    if (listaSelecionada == null) {
                        System.out.println("Lista inválida.");
                    } else if (listaSelecionada.isEmpty()) {
                        System.out.println("Lista está vazia.");
                    } else {
                        System.out.print("Digite o nome da série para remover: ");
                        String nomeSerie = sc.nextLine();

                        Serie serieARemover = null;
                        for (Serie serie : listaSelecionada) {
                            if (serie.getNome().equalsIgnoreCase(nomeSerie.trim())) {
                                serieARemover = serie;
                                break;
                            }
                        }

                        if (serieARemover != null) {
                            listaSelecionada.remove(serieARemover);
                            System.out.println("Série removida da lista.");
                        } else {
                            System.out.println("Série não encontrada na lista.");
                        }
                    }

                } else if (opc == 3) {
                    System.out.println("\n1. Favoritas | 2. Assistidas | 3. Quero Assistir");

                    int lista = lerInteiro(sc, "Escolha uma opcao: ");

                    List<Serie> listaSelecionada = switch (lista) {
                        case 1 -> usuario.getFavoritas();
                        case 2 -> usuario.getAssistidas();
                        case 3 -> usuario.getQueroAssistir();
                        default -> null;
                    };

                    if (listaSelecionada != null && !listaSelecionada.isEmpty()) {
                        System.out.println("Ordenar por:");
                        System.out.println("1. Nome | 2. Nota | 3. Status | 4. Estreia");

                        int ordem = lerInteiro(sc, "Escolha uma opcao: ");

                        switch (ordem) {
                            case 1 -> SerieManager.ordenarPorNome(listaSelecionada);
                            case 2 -> SerieManager.ordenarPorNota(listaSelecionada);
                            case 3 -> SerieManager.ordenarPorStatus(listaSelecionada);
                            case 4 -> SerieManager.ordenarPorData(listaSelecionada);
                            default -> System.out.println("Opção inválida para ordenação.");
                        }

                        SerieManager.exibirLista(listaSelecionada);
                    } else {
                        System.out.println("Lista vazia.");
                    }

                } else if (opc == 4) {
                    JsonUtil.salvar(usuario);
                    System.out.println("Dados salvos. Encerrando...");
                    break;

                } else {
                    System.out.println("Opção inválida.");
                }
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }

        sc.close();
    }

    private static int lerInteiro(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = sc.nextLine();
            try {
                return Integer.parseInt(linha.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número válido.");
            }
        }
    }
}
