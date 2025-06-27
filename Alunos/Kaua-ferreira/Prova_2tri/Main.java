package com.monitoradeseries;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner; 

import com.monitoradeseries.model.Serie; 
import com.monitoradeseries.model.Usuario;
import com.monitoradeseries.service.TVMazeService;
import com.monitoradeseries.util.JsonDataManager;

public class Main {
    private static Usuario usuario;
    private static TVMazeService tvMazeService = new TVMazeService();
    private static JsonDataManager dataManager = new JsonDataManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
      
        usuario = dataManager.carregarDados();
        if (usuario == null) {
            System.out.print("Bem-vindo! Qual o seu nome ou apelido? ");
            String nome = scanner.nextLine();
            usuario = new Usuario(nome);
            dataManager.salvarDados(usuario); 
        }
        System.out.println("Olá, " + usuario.getNome() + "!");

        int opcao;
        do {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        buscarSerie();
                        break;
                    case 2:
                        gerenciarLista(usuario.getSeriesFavoritas(), "Favoritos");
                        break;
                    case 3:
                        gerenciarLista(usuario.getSeriesJaAssistidas(), "Séries Já Assistidas");
                        break;
                    case 4:
                        gerenciarLista(usuario.getSeriesDesejoAssistir(), "Séries Que Deseja Assistir");
                        break;
                    case 5:
                        exibirListas();
                        break;
                    case 0:
                        System.out.println("Salvando dados e saindo...");
                        dataManager.salvarDados(usuario);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                opcao = -1; 
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                opcao = -1;
            }
            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine(); 
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Buscar série");
        System.out.println("2. Gerenciar lista de Favoritos");
        System.out.println("3. Gerenciar lista de Séries Já Assistidas");
        System.out.println("4. Gerenciar lista de Séries Que Deseja Assistir");
        System.out.println("5. Exibir todas as listas");
        System.out.println("0. Sair");
        System.out.println("----------------------");
    }

    private static void buscarSerie() {
        System.out.print("Digite o nome da série para buscar: ");
        String nomeBusca = scanner.nextLine();
        List<Serie> seriesEncontradas = tvMazeService.buscarSeriesPorNome(nomeBusca);

        if (seriesEncontradas.isEmpty()) {
            System.out.println("Nenhuma série encontrada com este nome.");
            return;
        }

        System.out.println("\nSéries Encontradas:");
        for (int i = 0; i < seriesEncontradas.size(); i++) {
            System.out.println((i + 1) + ". " + seriesEncontradas.get(i).getName());
        }

        System.out.print("Digite o número da série para ver detalhes e adicionar às suas listas (0 para voltar): ");
        try {
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha > 0 && escolha <= seriesEncontradas.size()) {
                Serie serieSelecionada = seriesEncontradas.get(escolha - 1);
                exibirDetalhesSerie(serieSelecionada);
                menuAdicionarSerie(serieSelecionada);
            } else if (escolha != 0) {
                System.out.println("Número inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
        }
    }

    private static void exibirDetalhesSerie(Serie serie) {
        System.out.println("\n--- Detalhes da Série ---");
        System.out.println(serie.toString()); 
        System.out.println("--------------------------");
    }

    private static void menuAdicionarSerie(Serie serie) {
        System.out.println("\nAdicionar '" + serie.getName() + "' a qual lista?");
        System.out.println("1. Favoritos");
        System.out.println("2. Já Assistidas");
        System.out.println("3. Desejo Assistir");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            boolean adicionado = false;
            switch (opcao) {
                case 1:
                    adicionado = usuario.adicionarSerieFavorita(serie);
                    break;
                case 2:
                    adicionado = usuario.adicionarSerieJaAssistida(serie);
                    break;
                case 3:
                    adicionado = usuario.adicionarSerieDesejoAssistir(serie);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }
            if (adicionado) {
                System.out.println("Série adicionada com sucesso!");
            } else {
                System.out.println("Série já está nesta lista.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
        }
    }

    private static void gerenciarLista(List<Serie> lista, String nomeLista) {
        if (lista.isEmpty()) {
            System.out.println("A lista de " + nomeLista + " está vazia.");
            return;
        }

        int opcao;
        do {
            System.out.println("\n--- Gerenciar " + nomeLista + " ---");
            exibirSeriesNaLista(lista);
            System.out.println("1. Remover série");
            System.out.println("2. Exibir detalhes de uma série");
            System.out.println("3. Ordenar lista");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        removerSerieDaLista(lista, nomeLista);
                        break;
                    case 2:
                        exibirDetalhesDaLista(lista);
                        break;
                    case 3:
                        ordenarLista(lista, nomeLista);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private static void exibirSeriesNaLista(List<Serie> lista) {
        if (lista.isEmpty()) {
            System.out.println("(Lista vazia)");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getName());
        }
    }

    private static void removerSerieDaLista(List<Serie> lista, String nomeLista) {
        if (lista.isEmpty()) {
            System.out.println("A lista de " + nomeLista + " está vazia.");
            return;
        }
        System.out.print("Digite o número da série que deseja remover: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice >= 0 && indice < lista.size()) {
                Serie serieRemovida = lista.remove(indice);
                System.out.println("Série '" + serieRemovida.getName() + "' removida da lista de " + nomeLista + ".");
            } else {
                System.out.println("Número inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
        }
    }

    private static void exibirDetalhesDaLista(List<Serie> lista) {
        if (lista.isEmpty()) {
            System.out.println("A lista está vazia.");
            return;
        }
        System.out.print("Digite o número da série para ver detalhes: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice >= 0 && indice < lista.size()) {
                exibirDetalhesSerie(lista.get(indice));
            } else {
                System.out.println("Número inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
        }
    }

    private static void ordenarLista(List<Serie> lista, String nomeLista) {
        if (lista.isEmpty()) {
            System.out.println("A lista de " + nomeLista + " está vazia para ordenar.");
            return;
        }

        System.out.println("\nOrdenar por:");
        System.out.println("1. Ordem alfabética do nome");
        System.out.println("2. Nota geral");
        System.out.println("3. Estado da série");
        System.out.println("4. Data de estreia");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1:
                    lista.sort(Comparator.comparing(Serie::getName));
                    System.out.println("Lista ordenada por nome.");
                    break;
                case 2:
                    
                    lista.sort(Comparator.comparing(s -> s.getRating() != null && s.getRating().getAverage() != null ? s.getRating().getAverage() : -1.0, Comparator.reverseOrder()));
                    System.out.println("Lista ordenada por nota.");
                    break;
                case 3:
                    
                    lista.sort(Comparator.comparing(Serie::getStatus, Comparator.nullsLast(String::compareTo)));
                    System.out.println("Lista ordenada por estado.");
                    break;
                case 4:
                    
                    lista.sort(Comparator.comparing(Serie::getPremiered, Comparator.nullsLast(String::compareTo)));
                    System.out.println("Lista ordenada por data de estreia.");
                    break;
                default:
                    System.out.println("Opção de ordenação inválida.");
            }
            exibirSeriesNaLista(lista); 
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
        }
    }

    private static void exibirListas() {
        System.out.println("\n--- Minhas Listas ---");
        System.out.println("\n-- Favoritos --");
        exibirSeriesNaLista(usuario.getSeriesFavoritas());
        System.out.println("\n-- Já Assistidas --");
        exibirSeriesNaLista(usuario.getSeriesJaAssistidas());
        System.out.println("\n-- Desejo Assistir --");
        exibirSeriesNaLista(usuario.getSeriesDesejoAssistir());
        System.out.println("--------------------");
    }
}  
   

