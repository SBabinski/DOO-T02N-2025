package com.faculdade.tv_series;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GerenciadorSeries {
    private Usuario usuario;
    private final TVMazeAPI tvMazeAPI;
    private final Scanner scanner;

    public GerenciadorSeries() {
        this.tvMazeAPI = new TVMazeAPI();
        this.scanner = new Scanner(System.in);
        carregarDados(); 
    }

    
    private void carregarDados() {
        usuario = PersistenciaDados.carregarUsuario(); 
        if (usuario == null) {
            usuario = new Usuario(); 
            configurarNovoUsuario();
        } else {
            System.out.println("Dados do usuário '" + usuario.getNome() + "' carregados com sucesso!");
        }
    }

    
    private void configurarNovoUsuario() {
        System.out.print("Bem-vindo! Por favor, digite seu nome ou apelido: ");
        String nome = scanner.nextLine();
        usuario.setNome(nome);
        System.out.println("Olá, " + usuario.getNome() + "!");
    }

    
    private void salvarDados() {
        PersistenciaDados.salvarUsuario(usuario); 
    }

    
    public void iniciar() {
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    buscarESelecionarSerie();
                    break;
                case 2:
                    gerenciarListas();
                    break;
                case 3:
                    exibirListas();
                    break;
                case 0:
                    salvarDados();
                    System.out.println("Saindo do sistema. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
        scanner.close(); 
    }

   
    private void exibirMenuPrincipal() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Buscar e Adicionar Série");
        System.out.println("2. Gerenciar Minhas Listas");
        System.out.println("3. Exibir Minhas Listas");
        System.out.println("0. Sair");
        System.out.println("----------------------");
    }

   
    private void buscarESelecionarSerie() {
        System.out.print("Digite o nome da série para buscar: ");
        String nomeBusca = scanner.nextLine();
        List<Serie> resultados = tvMazeAPI.buscarSeries(nomeBusca);

        if (resultados.isEmpty()) {
            System.out.println("Nenhuma série encontrada com este nome.");
            return;
        }

        System.out.println("\n--- Resultados da Busca ---");
     
        for (int i = 0; i < resultados.size(); i++) {
            Serie serieEncontrada = resultados.get(i);
            System.out.println("\n--- Série " + (i + 1) + " ---"); 
            exibirDetalhesSerie(serieEncontrada); 
        }
        System.out.println("--------------------------"); 

        System.out.println("0. Voltar ao menu principal");
        int escolha = lerInteiro("Selecione o número da série para adicionar (0 para voltar): "); 

        if (escolha > 0 && escolha <= resultados.size()) {
            Serie serieSelecionada = resultados.get(escolha - 1);
            
            menuAdicionarSerie(serieSelecionada); 
        } else if (escolha != 0) {
            System.out.println("Opção inválida.");
        }
    }

    
    private void menuAdicionarSerie(Serie serie) {
        System.out.println("\nO que deseja fazer com '" + serie.getNome() + "'?");
        System.out.println("1. Adicionar à lista de Favoritos");
        System.out.println("2. Adicionar à lista de Séries Assistidas");
        System.out.println("3. Adicionar à lista de Séries que Desejo Assistir");
        System.out.println("0. Voltar");

        int opcao = lerInteiro("Escolha uma opção: ");
        switch (opcao) {
            case 1:
                usuario.adicionar(usuario.getFavoritos(), serie);
                break;
            case 2:
                usuario.adicionar(usuario.getAssistidas(), serie);
                break;
            case 3:
                usuario.adicionar(usuario.getDesejadas(), serie);
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

   
    private void gerenciarListas() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Listas ---");
            System.out.println("1. Gerenciar Favoritas");
            System.out.println("2. Gerenciar Séries Assistidas");
            System.out.println("3. Gerenciar Séries que Desejo Assistir");
            System.out.println("0. Voltar ao menu principal");

            opcao = lerInteiro("Escolha uma lista para gerenciar: ");

            switch (opcao) {
                case 1:
                    gerenciarListaEspecifica(usuario.getFavoritos(), "Favoritas");
                    break;
                case 2:
                    gerenciarListaEspecifica(usuario.getAssistidas(), "Séries Assistidas");
                    break;
                case 3:
                    gerenciarListaEspecifica(usuario.getDesejadas(), "Séries que Desejo Assistir");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    
    private void gerenciarListaEspecifica(List<Serie> lista, String nomeLista) {
        if (lista.isEmpty()) {
            System.out.println("A lista de " + nomeLista + " está vazia.");
            return;
        }

        System.out.println("\n--- Gerenciar " + nomeLista + " ---");
        exibirSeriesNaLista(lista); 

        System.out.println("\n1. Ver detalhes de uma série");
        System.out.println("2. Remover uma série");
        System.out.println("0. Voltar");

        int opcao = lerInteiro("Escolha uma opção: ");
        switch (opcao) {
            case 1:
                int idxVer = lerInteiro("Digite o número da série para ver detalhes: ");
                if (idxVer > 0 && idxVer <= lista.size()) {
                    Serie serieParaDetalhes = lista.get(idxVer - 1);
                    exibirDetalhesSerie(serieParaDetalhes); 
                } else {
                    System.out.println("Número de série inválido.");
                }
                break;
            case 2:
                int idxRemover = lerInteiro("Digite o número da série para remover: ");
                if (idxRemover > 0 && idxRemover <= lista.size()) {
                    Serie serieRemover = lista.get(idxRemover - 1);
                    usuario.remover(lista, serieRemover);
                    System.out.println("Série '" + serieRemover.getNome() + "' removida de " + nomeLista + ".");
                } else {
                    System.out.println("Número de série inválido.");
                }
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    
    private void exibirListas() {
        int opcao;
        do {
            System.out.println("\n--- Exibir Listas ---");
            System.out.println("1. Exibir Favoritos");
            System.out.println("2. Exibir Séries Assistidas");
            System.out.println("3. Exibir Séries que Desejo Assistir");
            System.out.println("0. Voltar ao menu principal");

            opcao = lerInteiro("Escolha uma lista para exibir: ");

            switch (opcao) {
                case 1:
                    exibirEOrdenarLista(usuario.getFavoritos(), "Favoritas");
                    break;
                case 2:
                    exibirEOrdenarLista(usuario.getAssistidas(), "Séries Assistidas");
                    break;
                case 3:
                    exibirEOrdenarLista(usuario.getDesejadas(), "Séries que Desejo Assistir");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

   
    private void exibirEOrdenarLista(List<Serie> lista, String nomeLista) {
        if (lista.isEmpty()) {
            System.out.println("A lista de " + nomeLista + " está vazia.");
            return;
        }

        
        List<Serie> listaParaExibir = new ArrayList<>(lista);

        System.out.println("\n--- Ordenar " + nomeLista + " ---");
        System.out.println("1. Ordem alfabética do nome");
        System.out.println("2. Nota geral");
        System.out.println("3. Estado da série (Concluída, Em exibição, Cancelada)");
        System.out.println("4. Data de estreia");
        System.out.println("0. Não ordenar (exibir a ordem atual)");

        int opcaoOrdem = lerInteiro("Escolha uma opção de ordenação: ");

        switch (opcaoOrdem) {
            case 1:
                listaParaExibir.sort(Comparator.comparing(Serie::getNome));
                System.out.println("Lista de " + nomeLista + " ordenada por nome:");
                break;
            case 2:
                
                listaParaExibir.sort(Comparator.comparingDouble(Serie::getNota).reversed());
                System.out.println("Lista de " + nomeLista + " ordenada por nota geral (maior para menor):");
                break;
            case 3:
                
                listaParaExibir.sort(Comparator.comparing(Serie::getStatus, (s1, s2) -> {
                    int order1 = getStatusOrder(s1);
                    int order2 = getStatusOrder(s2);
                    return Integer.compare(order1, order2);
                }));
                System.out.println("Lista de " + nomeLista + " ordenada por estado:");
                break;
            case 4:
                
                listaParaExibir.sort(Comparator.comparing(Serie::getDataEstreiaStr, Comparator.nullsLast((s1, s2) -> {
                    try {
                        LocalDate d1 = LocalDate.parse(s1);
                        LocalDate d2 = LocalDate.parse(s2);
                        return d1.compareTo(d2);
                    } catch (DateTimeParseException e) {
                        return 0; 
                    }
                })));
                System.out.println("Lista de " + nomeLista + " ordenada por data de estreia (mais antiga para mais recente):");
                break;
            case 0:
                System.out.println("Lista de " + nomeLista + " (sem ordenação):");
                break;
            default:
                System.out.println("Opção de ordenação inválida. Exibindo sem ordenar.");
               
        }
        exibirSeriesNaLista(listaParaExibir); 
    }

    
    private int getStatusOrder(String status) {
        return switch (status) {
            case "Em exibição" -> 1;
            case "Concluída" -> 2;
            case "Cancelada" -> 3;
            case "A definir", "Desconhecido" -> 4;
            default -> 5; 
        };
    }

    
    private void exibirSeriesNaLista(List<Serie> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma série na lista.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            Serie s = lista.get(i);
            System.out.printf("%d. %s (Nota: %.1f, Status: %s)\n",
                    (i + 1), s.getNome(), s.getNota(), s.getStatus());
        }
        System.out.println("--------------------------");
    }

    
private void exibirDetalhesSerie(Serie serie) {
    System.out.println("Nome: " + serie.getNome());
    System.out.println("Idioma: " + (serie.getIdioma() != null && !serie.getIdioma().isEmpty() ? serie.getIdioma() : "N/A"));
    System.out.println("Gêneros: " + (serie.getGeneros() != null && !serie.getGeneros().isEmpty() ? String.join(delimiter: ", ", serie.getGeneros()) : "N/A")); // Erro aqui: "delimiter: "
    System.out.println("Nota Geral: " + (serie.getNota() > 0 ? String.format("%.1f", serie.getNota()) : "N/A"));
    System.out.println("Estado: " + serie.getStatus());
    System.out.println("Data de Estreia: " + serie.getDataEstreia());
    System.out.println("Data de Término: " + serie.getDataTermino());
    System.out.println("Emissora: " + serie.getEmissora());
}

    
    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }
}