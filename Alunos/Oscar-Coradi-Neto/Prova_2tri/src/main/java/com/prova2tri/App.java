package com.prova2tri;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Persistencia persistencia = new Persistencia();
    private static final TvMazeApi api = new TvMazeApi();
    private static Usuario usuario;

    public static void main(String[] args) {
        carregarOuCriarUsuario();
        System.out.println("\nBem-vindo(a) ao seu Rastreador de Séries, " + usuario.getNome() + "!");

        loopPrincipal();

        System.out.println("Salvando seus dados... Até a próxima!");
        persistencia.salvarDados(usuario);
        scanner.close();
    }

    private static void loopPrincipal() {
        boolean executando = true;
        while (executando) {
            exibirMenuPrincipal();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    procurarSerie();
                    break;
                case 2:
                    gerenciarListas();
                    break;
                case 0:
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            if (opcao == 1 || opcao == 2) {
                persistencia.salvarDados(usuario);
            }
        }
    }


    private static void exibirMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Procurar por uma série");
        System.out.println("2. Ver/Gerenciar minhas listas");
        System.out.println("0. Sair do programa");
        System.out.print("Escolha uma opção: ");
    }
    
    private static void gerenciarListas() {
        boolean voltando = false;
        while (!voltando) {
            System.out.println("\n--- GERENCIAR LISTAS ---");
            System.out.println("1. Ver lista de Favoritas");
            System.out.println("2. Ver lista de 'Já Assistidas'");
            System.out.println("3. Ver lista de 'Desejo Assistir'");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma lista para gerenciar: ");
            int opcao = lerOpcao();
            
            List<Serie> listaSelecionada = null;
            String nomeLista = "";

            switch (opcao) {
                case 1: listaSelecionada = usuario.getFavoritas(); nomeLista = "Favoritas"; break;
                case 2: listaSelecionada = usuario.getAssistidas(); nomeLista = "Já Assistidas"; break;
                case 3: listaSelecionada = usuario.getParaAssistir(); nomeLista = "Desejo Assistir"; break;
                case 0: voltando = true; continue;
                default: System.out.println("Opção inválida."); continue;
            }

            if (listaSelecionada != null) {
                gerenciarListaEspecifica(nomeLista, listaSelecionada);
            }
        }
    }

    private static void gerenciarListaEspecifica(String nomeLista, List<Serie> lista) {
        boolean gerenciando = true;
        while(gerenciando) {
            System.out.println("\n--- Gerenciando a lista: " + nomeLista.toUpperCase() + " ---");
            if (lista.isEmpty()) {
                System.out.println("Esta lista está vazia.");
            } else {
                for (Serie serie : lista) {
                    System.out.println(serie);
                }
            }

            System.out.println("\nOpções para esta lista:");
            System.out.println("1. Ordenar lista");
            System.out.println("2. Remover uma série desta lista");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    menuOrdenarLista(lista);
                    break;
                case 2:
                    menuRemoverSerie(lista);
                    break;
                case 0:
                    gerenciando = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
    
    private static void menuRemoverSerie(List<Serie> lista) {
        if (lista.isEmpty()) {
            System.out.println("\nNão há séries para remover.");
            return;
        }
        System.out.println("\n--- REMOVER SÉRIE ---");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getNome());
        }
        System.out.println("0. Cancelar");
        System.out.print("Digite o número da série que deseja remover: ");

        int escolha = lerOpcao();
        if (escolha > 0 && escolha <= lista.size()) {
            Serie serieParaRemover = lista.get(escolha - 1);
            System.out.println("Removendo '" + serieParaRemover.getNome() + "'...");
            lista.remove(serieParaRemover);
            System.out.println("Série removida com sucesso!");
        } else {
            System.out.println("Remoção cancelada.");
        }
    }

    private static void menuOrdenarLista(List<Serie> lista) {
        if (lista.isEmpty()) {
            System.out.println("\nNão há séries para ordenar.");
            return;
        }
        System.out.println("\n--- Ordenar lista por: ---");
        System.out.println("1. Ordem Alfabética");
        System.out.println("2. Nota");
        System.out.println("3. Status");
        System.out.println("4. Data de Estreia");
        System.out.println("0. Cancelar");
        System.out.print("Escolha uma opção de ordenação: ");
        
        int opcao = lerOpcao();
        switch (opcao) {
            case 1: usuario.ordenarListaPorNome(lista); System.out.println("Lista ordenada por nome."); break;
            case 2: usuario.ordenarListaPorNota(lista); System.out.println("Lista ordenada por nota."); break;
            case 3: usuario.ordenarListaPorStatus(lista); System.out.println("Lista ordenada por status."); break;
            case 4: usuario.ordenarListaPorDataEstreia(lista); System.out.println("Lista ordenada por data de estreia."); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }


    private static void procurarSerie() {
        System.out.print("\nDigite o nome da série para buscar: ");
        String busca = scanner.nextLine();
        try {
            List<Serie> resultados = api.buscarSeries(busca);
            if (resultados.isEmpty()) {
                System.out.println("Nenhuma série encontrada com este nome.");
                return;
            }

            System.out.println("\n--- RESULTADOS DA BUSCA ---");
            for (int i = 0; i < resultados.size(); i++) {
                System.out.println((i + 1) + ". " + resultados.get(i).getNome());
            }
            System.out.println("0. Cancelar");

            System.out.print("\nEscolha o número da série para ver detalhes ou adicionar: ");
            int escolha = lerOpcao();
            if (escolha > 0 && escolha <= resultados.size()) {
                Serie serieEscolhida = resultados.get(escolha - 1);
                System.out.println(serieEscolhida);
                adicionarSerieEmLista(serieEscolhida);
            }

        } catch (Exception e) {
            System.err.println("ERRO: Ocorreu um problema ao buscar a série. Verifique sua conexão ou tente novamente.");
            e.printStackTrace();
        }
    }

    private static void adicionarSerieEmLista(Serie serie) {
        System.out.println("\nAdicionar '" + serie.getNome() + "' em qual lista?");
        System.out.println("1. Favoritas");
        System.out.println("2. Já Assistidas");
        System.out.println("3. Desejo Assistir");
        System.out.println("0. Cancelar");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcao();
        switch (opcao) {
            case 1: usuario.adicionarSerie(serie, usuario.getFavoritas()); break;
            case 2: usuario.adicionarSerie(serie, usuario.getAssistidas()); break;
            case 3: usuario.adicionarSerie(serie, usuario.getParaAssistir()); break;
            default: System.out.println("Operação cancelada.");
        }
    }

    private static void carregarOuCriarUsuario() {
        usuario = persistencia.carregarDados();
        if (usuario == null) {
            System.out.println("Bem-vindo(a)! Parece ser seu primeiro acesso.");
            System.out.print("Por favor, digite seu nome ou apelido: ");
            String nome = scanner.nextLine();
            usuario = new Usuario(nome);
        }
    }
    
    private static int lerOpcao() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); 
            return opcao;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); 
            return -1; 
        }
    }
}