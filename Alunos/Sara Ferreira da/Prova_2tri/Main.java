package com.faculdade.tvseries;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Usuario usuario = PersistenciaDados.carregarUsuario();

        
        if (usuario == null) {
            System.out.print("Digite seu nome ou apelido: ");
            usuario = new Usuario(scanner.nextLine());
        }

        
        while (true) {
            System.out.println("\nBem-vindo, " + usuario.getNome());
            System.out.println("1. Buscar séries");
            System.out.println("2. Ver lista (favoritos, assistidas, desejadas)");
            System.out.println("3. Remover série de uma lista");
            System.out.println("4. Ordenar listas");
            System.out.println("5. Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            
            switch (opcao) {
                case "1":
                    buscarESalvarSerie(usuario, scanner);
                    break;
                case "2":
                    exibirListas(usuario);
                    break;
                case "3":
                    removerSerie(usuario, scanner);
                    break;
                case "4":
                    ordenarListas(usuario, scanner);
                    break;
                case "5":
                    
                    PersistenciaDados.salvarUsuario(usuario);
                    System.out.println("Dados salvos. Até logo!");
                    return; 
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    
    private static void buscarESalvarSerie(Usuario usuario, Scanner scanner) {
        System.out.print("Digite o nome da série: ");
        String nome = scanner.nextLine();
        List<Serie> seriesEncontradas = TVMazeAPI.buscarSeries(nome); // Busca séries na API

        if (seriesEncontradas.isEmpty()) {
            System.out.println("Nenhuma série encontrada.");
            return;
        }

        
        System.out.println("\n--- Séries Encontradas ---");
        for (int i = 0; i < seriesEncontradas.size(); i++) {
            Serie s = seriesEncontradas.get(i);
            System.out.printf("\n%d - %s (%.1f) - %s\n", i + 1, s.getNome(), s.getNota(), s.getStatus());
            System.out.println("Idioma: " + s.getIdioma());
            System.out.println("Gêneros: " + String.join(", ", s.getGeneros()));
            System.out.println("Estreia: " + s.getDataEstreia());
            System.out.println("Término: " + s.getDataTermino());
            System.out.println("Emissora: " + s.getEmissora());
            System.out.println("--------------------------");
        }

        System.out.print("\nAdicionar qual série? (número ou 0 para cancelar): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= seriesEncontradas.size()) {
                System.out.println("Operação cancelada ou índice inválido.");
                return;
            }

            Serie selecionada = seriesEncontradas.get(idx);
            System.out.println("Adicionar em: 1 - Favoritos | 2 - Assistidas | 3 - Desejadas");
            String escolha = scanner.nextLine();

            
            switch (escolha) {
                case "1":
                    usuario.adicionar(usuario.getFavoritos(), selecionada);
                    System.out.println("Série adicionada aos Favoritos.");
                    break;
                case "2":
                    usuario.adicionar(usuario.getAssistidas(), selecionada);
                    System.out.println("Série adicionada às Assistidas.");
                    break;
                case "3":
                    usuario.adicionar(usuario.getDesejadas(), selecionada);
                    System.out.println("Série adicionada às Desejadas.");
                    break;
                default:
                    System.out.println("Opção inválida para adicionar série.");
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
        }
    }

    
    private static void exibirListas(Usuario usuario) {
        mostrarLista("Favoritos", usuario.getFavoritos());
        mostrarLista("Assistidas", usuario.getAssistidas());
        mostrarLista("Desejadas", usuario.getDesejadas());
    }

    
    private static void removerSerie(Usuario usuario, Scanner scanner) {
        System.out.println("Remover de: 1 - Favoritos | 2 - Assistidas | 3 - Desejadas");
        String tipo = scanner.nextLine();

        List<Serie> lista = null;
        switch (tipo) {
            case "1":
                lista = usuario.getFavoritos();
                break;
            case "2":
                lista = usuario.getAssistidas();
                break;
            case "3":
                lista = usuario.getDesejadas();
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        if (lista == null || lista.isEmpty()) {
            System.out.println("A lista está vazia ou foi selecionada uma opção inválida.");
            return;
        }

        System.out.println("\n--- Séries na Lista para Remover ---");
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, lista.get(i).getNome());
        }
        System.out.print("Escolha o número da série para remover: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx >= 0 && idx < lista.size()) {
                Serie serieRemovida = lista.get(idx);
                usuario.remover(lista, serieRemovida);
                System.out.println("Série '" + serieRemovida.getNome() + "' removida da lista.");
            } else {
                System.out.println("Número de série inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
        }
    }

    
    private static void ordenarListas(Usuario usuario, Scanner scanner) {
        System.out.println("Ordenar qual lista? 1 - Favoritos | 2 - Assistidas | 3 - Desejadas");
        List<Serie> lista = null;
        switch (scanner.nextLine()) {
            case "1":
                lista = usuario.getFavoritos();
                break;
            case "2":
                lista = usuario.getAssistidas();
                break;
            case "3":
                lista = usuario.getDesejadas();
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        if (lista == null || lista.isEmpty()) {
            System.out.println("A lista está vazia ou foi selecionada uma opção inválida para ordenação.");
            return;
        }

        System.out.println("Ordenar por: 1 - Nome | 2 - Nota | 3 - Status | 4 - Estreia");
        switch (scanner.nextLine()) {
            case "1":
                lista.sort(Comparator.comparing(Serie::getNome));
                System.out.println("Lista ordenada por Nome.");
                break;
            case "2":
                lista.sort(Comparator.comparingDouble(Serie::getNota).reversed());
                System.out.println("Lista ordenada por Nota (maior para menor).");
                break;
            case "3":
                lista.sort(Comparator.comparing(Serie::getStatus));
                System.out.println("Lista ordenada por Status.");
                break;
            case "4":
                
                lista.sort(Comparator.comparing(Serie::getDataEstreiaStr, Comparator.nullsLast(String::compareTo)));
                System.out.println("Lista ordenada por Data de Estreia.");
                break;
            default:
                System.out.println("Opção inválida para ordenação.");
        }

        mostrarLista("Lista ordenada", lista);
    }

    
    private static void mostrarLista(String titulo, List<Serie> lista) {
        System.out.println("\n📺 " + titulo.toUpperCase());
        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
        } else {
            for (Serie s : lista) {
                System.out.printf("• %s (%.1f) - %s\n", s.getNome(), s.getNota(), s.getStatus());
            }
        }
    }
}