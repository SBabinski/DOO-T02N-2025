package com.seuprojeto;

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
        }
    }

// Cole este restante dentro da classe App.java

    private static void exibirMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Procurar por uma série");
        System.out.println("2. Ver/Gerenciar minhas listas");
        System.out.println("0. Sair do programa");
        System.out.print("Escolha uma opção: ");
    }
    
    private static void gerenciarListas() {
        boolean voltando = false;
        while(!voltando) {
            System.out.println("\n--- GERENCIAR LISTAS ---");
            System.out.println("1. Ver lista de Favoritas");
            System.out.println("2. Ver lista de 'Já Assistidas'");
            System.out.println("3. Ver lista de 'Desejo Assistir'");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
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
                exibirEOrdenarLista(nomeLista, listaSelecionada);
            }
        }
    }

    private static void exibirEOrdenarLista(String nomeLista, List<Serie> lista) {
        if (lista.isEmpty()) {
            usuario.exibirLista(nomeLista, lista);
            return;
        }

        boolean ordenando = true;
        while(ordenando) {
            System.out.println("\n--- Opções para a lista: " + nomeLista.toUpperCase() + " ---");
            System.out.println("1. Exibir lista");
            System.out.println("2. Ordenar por Ordem Alfabética");
            System.out.println("3. Ordenar por Nota");
            System.out.println("4. Ordenar por Status");
            System.out.println("5. Ordenar por Data de Estreia");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            switch (opcao) {
                case 1: /* Apenas exibe como está */ break;
                case 2: usuario.ordenarListaPorNome(lista); System.out.println("Lista ordenada por nome."); break;
                case 3: usuario.ordenarListaPorNota(lista); System.out.println("Lista ordenada por nota."); break;
                case 4: usuario.ordenarListaPorStatus(lista); System.out.println("Lista ordenada por status."); break;
                case 5: usuario.ordenarListaPorDataEstreia(lista); System.out.println("Lista ordenada por data de estreia."); break;
                case 0: ordenando = false; continue;
                default: System.out.println("Opção inválida."); continue;
            }
            usuario.exibirLista(nomeLista, lista);
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
            scanner.nextLine(); // Consome o \n que sobrou
            return opcao;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer do scanner
            return -1; // Retorna um valor inválido para o switch não fazer nada
        }
    }
}