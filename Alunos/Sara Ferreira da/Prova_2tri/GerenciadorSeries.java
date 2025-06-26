package com.faculdade.tvseries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter; 
import java.time.format.DateTimeParseException; 
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GerenciadorSeries {
    private Usuario usuario;
    private final TVMazeAPI tvMazeAPI;
    private static final String DATA_FILE = "dados_usuario.json"; 
    private final Scanner scanner; 

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public GerenciadorSeries() {
        this.tvMazeAPI = new TVMazeAPI();
        this.scanner = new Scanner(System.in);
        carregarDados(); 
    }

    
    private void carregarDados() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                
                usuario = gson.fromJson(reader, Usuario.class);
                System.out.println("Dados do usuário '" + usuario.getNome() + "' carregados com sucesso!");
            } catch (IOException | JsonSyntaxException e) {
                
                System.err.println("Erro ao carregar dados ou arquivo corrompido: " + e.getMessage());
                usuario = new Usuario();
                configurarNovoUsuario();
            }
        } else {
            
            usuario = new Usuario();
            configurarNovoUsuario();
        }
    }

    
    private void configurarNovoUsuario() {
        System.out.print("Bem-vindo! Por favor, digite seu nome ou apelido: ");
        String nome = scanner.nextLine();
        usuario.setNome(nome); 
        System.out.println("Olá, " + usuario.getNome() + "!");
    }

    
    private void salvarDados() {
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            
            gson.toJson(usuario, writer);
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    
    public void iniciar() {
        int opcao;
        do {
            exibirMenuPrincipal(); 
            opcao = lerInteiro("Escolha uma opção: "); 

            switch (opcao) {
                case 1:
                    buscarESelecionarSerie(); 
                case 2:
                    gerenciarListas(); 
                    break;
                case 3:
                    exibirListas(); 
                case 0:
                    salvarDados(); 
                    System.out.println("Saindo do sistema. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0); 
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
        List<Serie> resultados = TVMazeAPI.buscarSeries(nomeBusca); 
        
        if (resultados.isEmpty()) {
            System.out.println("Nenhuma série encontrada com este nome.");
            return;
        }

        System.out.println("\n--- Resultados da Busca ---");
        for (int i = 0; i < resultados.size(); i++) {
            System.out.println((i + 1) + ". " + resultados.get(i).getNome()); 
        }
        System.out.println("0. Voltar ao menu principal");

        int escolha = lerInteiro("Selecione o número da série para ver detalhes ou adicionar (0 para voltar): ");

        if (escolha > 0 && escolha <= resultados.size()) {
            Serie serieSelecionada = resultados.get(escolha - 1);
           
            System.out.println("\n--- Detalhes da Série ---");
            System.out.println("Nome: " + serieSelecionada.getNome());
            System.out.println("Idioma: " + serieSelecionada.getIdioma());
            System.out.println("Gêneros: " + (serieSelecionada.getGeneros() != null ? String.join(", ", serieSelecionada.getGeneros()) : "N/A"));
            System.out.println("Nota Geral: " + (serieSelecionada.getNota() > 0 ? serieSelecionada.getNota() : "N/A"));
            System.out.println("Estado: " + serieSelecionada.getStatus());
            System.out.println("Data de Estreia: " + serieSelecionada.getDataEstreia());
            System.out.println("Data de Término: " + serieSelecionada.getDataTermino());
            System.out.println("Emissora: " + serieSelecionada.getEmissora());
            System.out.println("-------------------------");

            menuAdicionarSerie(serieSelecionada); 
        } else if (escolha != 0) {
            System.out.println("Opção inválida.");
        }
    }

    
    private void menuAdicionarSerie(Serie serie) {
        System.out.println("\nO que deseja fazer com '" + serie.getNome() + "'?");
        System.out.println("1. Adicionar à lista de Favoritas");
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
                    gerenciarListaEspecifica(usuario.getDesejadas(), "Séries que Desejo Assistir"); // Corrigido: Usando getDesejadas()
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
                    System.out.println("\n--- Detalhes da Série ---");
                    System.out.println("Nome: " + serieParaDetalhes.getNome());
                    System.out.println("Idioma: " + serieParaDetalhes.getIdioma());
                    System.out.println("Gêneros: " + (serieParaDetalhes.getGeneros() != null ? String.join(", ", serieParaDetalhes.getGeneros()) : "N/A"));
                    System.out.println("Nota Geral: " + (serieParaDetalhes.getNota() > 0 ? serieParaDetalhes.getNota() : "N/A"));
                    System.out.println("Estado: " + serieParaDetalhes.getStatus());
                    System.out.println("Data de Estreia: " + serieParaDetalhes.getDataEstreia());
                    System.out.println("Data de Término: " + serieParaDetalhes.getDataTermino());
                    System.out.println("Emissora: " + serieParaDetalhes.getEmissora());
                    System.out.println("-------------------------");
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
            System.out.println("1. Exibir Favoritas");
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
                    exibirEOrdenarLista(usuario.getDesejadas(), "Séries que Desejo Assistir"); // Corrigido: Usando getDesejadas()
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

        List<Serie> listaParaExibir = new java.util.ArrayList<>(lista); 

        System.out.println("\n--- Ordenar " + nomeLista + " ---");
        System.out.println("1. Ordem alfabética do nome");
        System.out.println("2. Nota geral");
        System.out.println("3. Estado da série (Concluída, Em exibição, Cancelada)");
        System.out.println("4. Data de estreia");
        System.out.println("0. Não ordenar (exibir na ordem atual)");

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
                listaParaExibir.sort(Comparator.comparing(Serie::getStatus));
                System.out.println("Lista de " + nomeLista + " ordenada por estado:");
                break;
            case 4:
                
                listaParaExibir.sort(Comparator.comparing(Serie::getDataEstreiaStr, Comparator.nullsLast(String::compareTo)));
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

  
    private void exibirSeriesNaLista(List<Serie> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma série na lista.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getNome() + " (Nota: " + lista.get(i).getNota() + ", Status: " + lista.get(i).getStatus() + ")");
        }
        System.out.println("--------------------------");
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