package com.cinelume.view;

import com.cinelume.controller.UsuarioController;
import com.cinelume.model.Serie;
import com.cinelume.util.ApiClient;
import com.google.gson.JsonObject;
import java.util.*;

public class MenuView {
    private final UsuarioController usuarioController;
    private final ApiClient apiClient;
    private final Scanner scanner;
    private boolean usuarioLogado;

    public MenuView(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        this.apiClient = new ApiClient();
        this.scanner = new Scanner(System.in);
        this.usuarioLogado = false;
    }

    public void exibirMenu() {
        System.out.println("=== BEM-VINDO AO CINELUME ===");
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        
        if (nome.isEmpty()) {
            System.out.println("Aviso: Usando usuário temporário. Dados não serão salvos.\n");
        } else {
            usuarioLogado = true;
            System.out.println("Olá, " + nome + "!\n");
        }

        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 4);
    }

    // --- Métodos auxiliares ---
    private void exibirMenuPrincipal() {
        System.out.println("\n= MENU =");
        System.out.println("1. Buscar série");
        System.out.println("2. Ver favoritos");
        System.out.println("3. Gerenciar listas");
        System.out.println("4. Sair");
        System.out.print("Escolha: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> buscarSerie();
            case 2 -> exibirFavoritos();
            case 3 -> gerenciarListas();
            case 4 -> System.out.println("Saindo...");
            default -> System.out.println("Opção inválida! Tente novamente.");
        }
    }

    // --- Busca e exibição de séries ---
    private void buscarSerie() {
        System.out.print("\nNome da série: ");
        String query = scanner.nextLine().trim();
        
        if (query.isEmpty()) {
            System.out.println("Nome inválido!\n");
            return;
        }

        try {
            List<JsonObject> resultados = apiClient.buscarSeries(query);
            if (resultados.isEmpty()) {
                System.out.println("\nNenhum resultado exato para \"" + query + "\".");
                exibirSugestoesAlternativas(query);
            } else {
                exibirResultados(resultados);
            }
        } catch (Exception e) {
            System.err.println("Erro na busca: " + e.getMessage());
        }
    }

    private void exibirResultados(List<JsonObject> resultados) {
        System.out.println("\n=== RESULTADOS ===");
        for (int i = 0; i < resultados.size(); i++) {
            JsonObject serieJson = resultados.get(i);
            System.out.printf("%d. %s (%s)\n",
                i + 1,
                serieJson.get("name").getAsString(),
                serieJson.has("premiered") ? 
                    serieJson.get("premiered").getAsString().substring(0, 4) : "N/A"
            );
        }

        System.out.print("\nSelecione uma série (número) ou 0 para cancelar: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha > 0 && escolha <= resultados.size()) {
            Serie serieSelecionada = Serie.fromJson(resultados.get(escolha - 1));
            exibirDetalhesSerie(serieSelecionada);
        }
    }

    private void exibirSugestoesAlternativas(String query) {
        try {
            List<JsonObject> sugestoes = apiClient.buscarSeries(query.substring(0, Math.min(3, query.length())));
            if (sugestoes.isEmpty()) {
                System.out.println("Nenhuma sugestão disponível.");
                return;
            }

            System.out.println("\nSugestões:");
            for (int i = 0; i < Math.min(sugestoes.size(), 5); i++) {
                System.out.printf("%d. %s\n", i + 1, sugestoes.get(i).get("name").getAsString());
            }

            System.out.print("\nDeseja selecionar alguma? (Número ou 'N' para cancelar): ");
            String resposta = scanner.nextLine();
            
            if (!resposta.equalsIgnoreCase("N")) {
                int idx = Integer.parseInt(resposta) - 1;
                if (idx >= 0 && idx < sugestoes.size()) {
                    exibirDetalhesSerie(Serie.fromJson(sugestoes.get(idx)));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar sugestões: " + e.getMessage());
        }
    }

    private void exibirDetalhesSerie(Serie serie) {
        System.out.println("\n=== DETALHES ===");
        System.out.println(serie);
        
        System.out.println("\n= OPCOES =");
        System.out.println("1. Adicionar aos favoritos");
        System.out.println("2. Adicionar a outra lista");
        System.out.println("3. Voltar");
        System.out.print("Escolha: ");
        
        int escolha = lerOpcao();
        switch (escolha) {
            case 1 -> adicionarSerieALista("favoritos", serie);
            case 2 -> adicionarSerieAListaCustomizada(serie);
            case 3 -> System.out.println("Retornando...");
            default -> System.out.println("Opção inválida!");
        }
    }

    // --- Gerenciamento de listas ---
    private void adicionarSerieALista(String lista, Serie serie) {
        usuarioController.adicionarSerie(lista, serie);
        System.out.println("\nSérie adicionada à lista \"" + lista + "\"!");
    }

    private void adicionarSerieAListaCustomizada(Serie serie) {
        System.out.print("\nNome da lista: ");
        String nomeLista = scanner.nextLine();
        adicionarSerieALista(nomeLista, serie);
    }

    private void exibirFavoritos() {
        List<Serie> favoritos = usuarioController.getLista("favoritos");
        System.out.println("\n=== FAVORITOS ===");
        if (favoritos.isEmpty()) {
            System.out.println("Nenhuma série favoritada.");
        } else {
            favoritos.forEach(serie -> {
                System.out.println("\n" + serie);
                System.out.println("-------------------");
            });
        }
    }

    private void gerenciarListas() {
        System.out.println("\n= GERENCIAR LISTAS =");
        Map<String, List<Serie>> listas = usuarioController.getTodasListas();
        
        if (listas.isEmpty()) {
            System.out.println("Nenhuma lista criada.");
        } else {
            System.out.println("Suas listas:");
            listas.forEach((nome, series) -> 
                System.out.printf("- %s (%d séries)\n", nome, series.size())
            );
        }

        System.out.println("\n1. Criar nova lista");
        System.out.println("2. Renomear lista");
        System.out.println("3. Deletar lista");
        System.out.println("4. Ver séries de uma lista");
        System.out.println("5. Voltar");
        System.out.print("Escolha: ");
        
        int escolha = lerOpcao();
        switch (escolha) {
            case 1 -> criarLista();
            case 2 -> renomearLista();
            case 3 -> deletarLista();
            case 4 -> verSeriesDeLista();
            case 5 -> System.out.println("Retornando...");
            default -> System.out.println("Opção inválida!");
        }
    }

    private void criarLista() {
        System.out.print("\nNome da nova lista: ");
        String nome = scanner.nextLine();
        
        if (usuarioController.criarLista(nome)) {
            System.out.println("Lista \"" + nome + "\" criada!");
        } else {
            System.out.println("Erro: Lista já existe.");
        }
    }

    private void renomearLista() {
        System.out.print("\nNome da lista atual: ");
        String nomeAntigo = scanner.nextLine();
        System.out.print("Novo nome: ");
        String nomeNovo = scanner.nextLine();
        
        if (usuarioController.renomearLista(nomeAntigo, nomeNovo)) {
            System.out.println("Lista renomeada para \"" + nomeNovo + "\"!");
        } else {
            System.out.println("Erro: Lista não encontrada.");
        }
    }

    private void deletarLista() {
        System.out.print("\nNome da lista a deletar: ");
        String nome = scanner.nextLine();
        
        if (usuarioController.deletarLista(nome)) {
            System.out.println("Lista \"" + nome + "\" deletada!");
        } else {
            System.out.println("Erro: Lista não encontrada ou é 'favoritos'.");
        }
    }

    private void verSeriesDeLista() {
        System.out.print("\nNome da lista: ");
        String nome = scanner.nextLine();
        List<Serie> series = usuarioController.getLista(nome);
        
        if (series.isEmpty()) {
            System.out.println("Lista vazia ou não encontrada.");
        } else {
            System.out.println("\n=== SÉRIES EM " + nome.toUpperCase() + " ===");
            series.forEach(serie -> System.out.println("- " + serie.getNome()));
        }
    }
}