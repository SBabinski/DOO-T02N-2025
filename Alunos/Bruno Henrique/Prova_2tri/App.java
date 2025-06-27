import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    private Scanner scanner;
    private ListaDeSeries seriesFavoritas;
    private ListaDeSeries seriesAssistidas;
    private ListaDeSeries desejoAssistir;
    private List<Serie> ultimaBusca;
    private String nomeUsuario;
    private static final String SAUDACAO = "bemVindo.json";
    private static final String ARQUIVO_FAVORITAS = "favoritas.json";
    private static final String ARQUIVO_ASSISTIDAS = "assistidas.json";
    private static final String ARQUIVO_DESEJO = "desejo.json";

    public App() {
        this.scanner = new Scanner(System.in);
        this.seriesFavoritas = new ListaDeSeries();
        this.seriesAssistidas = new ListaDeSeries();
        this.desejoAssistir = new ListaDeSeries();
        this.seriesFavoritas.carregarJson(ARQUIVO_FAVORITAS);
        this.seriesAssistidas.carregarJson(ARQUIVO_ASSISTIDAS);
        this.desejoAssistir.carregarJson(ARQUIVO_DESEJO);
        this.ultimaBusca = new ArrayList<>();

    }

    public void run() {
        carregarNomeUsuario();
        System.out.println("Bem-vindo de volta, " + this.nomeUsuario);

        if (this.nomeUsuario.equals("Usuário")) {
            System.out.println("Digite seu nome de usuário:");
            this.nomeUsuario = scanner.nextLine();
            System.out.println("Bem-vindo(a), " + this.nomeUsuario);
        }

        while (true) {
            exibirMenu();

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        buscarSerie();
                        break;
                    case 2:
                        adicionarSerieLista();
                        break;
                    case 3:
                        removerSerieDeUmaLista();
                        break;
                    case 4:
                        exibirListas();
                        break;
                    case 5:
                        ordenarListas();
                        break;
                    case 0:
                        salvarESair();
                        return;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, digite um número válido!");
                scanner.nextLine();
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n--------- Catálogo de Séries TV ---------");
        System.out.println("1. Buscar série");
        System.out.println("2. Adicionar série à lista");
        System.out.println("3. Remover Série de uma Lista");
        System.out.println("4. Exibir listas");
        System.out.println("5. Ordenar Listas");
        System.out.println("0. Salvar e sair");
        System.out.print("Digite sua escolha: ");
    }

    private void buscarSerie() {
        System.out.print("Digite o nome da série para buscar: ");
        String nomeSerie = scanner.nextLine();

        try {
            String url = UrlBuilder.criarUrl(nomeSerie);
            String jsonResponse = HttpService.fazerRequisicao(url);
            this.ultimaBusca = JsonParserService.parseJsonParaListaSeries(jsonResponse);

            if (this.ultimaBusca.isEmpty()) {
                System.out.println("Nenhuma série encontrada com o nome '" + nomeSerie + "'.");
            } else {
                System.out.println("------------------------------------");
                System.out.println("Séries encontradas:");
                for (int i = 0; i < this.ultimaBusca.size(); i++) {
                    Serie s = this.ultimaBusca.get(i);
                    System.out.printf("%d. %s (Nota: %.1f)\n", (i + 1), s.getNome(), s.getNota());
                }
                System.out.println("------------------------------------");
                System.out.println("\nPara adicionar uma série, use a opção 2 do menu.");
            }
        } catch (Exception e) {
            System.err.println("ERRO ao buscar série: " + e.getMessage());
        }
    }

    private void adicionarSerieLista() {
        if (this.ultimaBusca.isEmpty()) {
            System.out.println("Você precisa buscar por uma série primeiro (opção 1).");
            return;
        }

        System.out.print("Digite o número da série que deseja adicionar: ");

        try {
            int numeroSerie = scanner.nextInt();
            scanner.nextLine();

            if (numeroSerie < 1 || numeroSerie > this.ultimaBusca.size()) {
                System.out.println("Número inválido.");
                return;
            }

            Serie serieEscolhida = this.ultimaBusca.get(numeroSerie - 1);

            System.out.println("Adicionar '" + serieEscolhida.getNome() + "' em qual lista?");
            System.out.println("1. Favoritas");
            System.out.println("2. Assistidas");
            System.out.println("3. Desejo Assistir");
            System.out.print("Escolha uma opção: ");

            int numeroLista = scanner.nextInt();
            scanner.nextLine();

            switch (numeroLista) {
                case 1:
                    seriesFavoritas.adicionarSerie(serieEscolhida);
                    System.out.println("'" + serieEscolhida.getNome() + "' adicionada aos Favoritos.");
                    break;
                case 2:
                    seriesAssistidas.adicionarSerie(serieEscolhida);
                    System.out.println("'" + serieEscolhida.getNome() + "' adicionada às Assistidas.");
                    break;
                case 3:
                    desejoAssistir.adicionarSerie(serieEscolhida);
                    System.out.println("'" + serieEscolhida.getNome() + "' adicionada à Lista de Desejos.");
                    break;
                default:
                    System.out.println("Opção de lista inválida.");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, digite um número válido.");
            scanner.nextLine();
        }
    }

    private void removerSerieDeUmaLista() {
        System.out.println("De qual lista você deseja remover uma série?");
        System.out.println("1. Favoritas");
        System.out.println("2. Assistidas");
        System.out.println("3. Desejo Assistir");
        System.out.print("Escolha uma opção: ");

        try {
            int numeroLista = scanner.nextInt();
            scanner.nextLine();

            ListaDeSeries listaEscolhida = null;

            switch (numeroLista) {
                case 1:
                    listaEscolhida = seriesFavoritas;
                    System.out.println("\n--- Séries Favoritas Atuais ---");
                    break;
                case 2:
                    listaEscolhida = seriesAssistidas;
                    System.out.println("\n--- Séries Assistidas Atuais ---");
                    break;
                case 3:
                    listaEscolhida = desejoAssistir;
                    System.out.println("\n--- Lista de Desejos Atual ---");
                    break;
                default:
                    System.out.println("Opção de lista inválida.");
                    return;
            }

            if (listaEscolhida.getSeries().isEmpty()) {
                System.out.println("A lista está vazia. Não há nada para remover.");
                return;
            }

            for (int i = 0; i < listaEscolhida.getSeries().size(); i++) {
                System.out.println((i + 1) + ". " + listaEscolhida.getSeries().get(i).getNome());
            }

            System.out.print("Digite o número da série que deseja remover: ");
            int numeroSerieParaRemover = scanner.nextInt();
            scanner.nextLine();

            if (numeroSerieParaRemover < 1 || numeroSerieParaRemover > listaEscolhida.getSeries().size()) {
                System.out.println("Número inválido.");
                return;
            }

            Serie serieRemovida = listaEscolhida.getSeries().get(numeroSerieParaRemover - 1);
            listaEscolhida.removerSerie(serieRemovida);

            System.out.println("'" + serieRemovida.getNome() + "' foi removida com sucesso!");
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, digite um número válido.");
            scanner.nextLine();
        }
    }

    private void exibirListas() {
        System.out.println("\n--- Listas ---");
        System.out.println("\n== FAVORITAS ==");
        if (seriesFavoritas.getSeries().isEmpty()) {
            System.out.println("(Vazio)");
        } else {
            for (Serie s : seriesFavoritas.getSeries()) {
                System.out.println("- " + s.getNome());
                exibirDetalhesSerie(s);
            }
        }

        System.out.println("\n== JÁ ASSISTIDAS ==");
        if (seriesAssistidas.getSeries().isEmpty()) {
            System.out.println("(Vazio)");
        } else {
            for (Serie s : seriesAssistidas.getSeries()) {
                System.out.println("- " + s.getNome());
                exibirDetalhesSerie(s);
            }
        }

        System.out.println("\n== DESEJO ASSISTIR ==");
        if (desejoAssistir.getSeries().isEmpty()) {
            System.out.println("(Vazio)");
        } else {
            for (Serie s : desejoAssistir.getSeries()) {
                System.out.println("- " + s.getNome());
                exibirDetalhesSerie(s);
            }
        }
    }

    private void ordenarListas() {
        System.out.println("Qual lista você deseja ordenar?");
        System.out.println("1. Favoritas");
        System.out.println("2. Assistidas");
        System.out.println("3. Desejo Assistir");
        System.out.print("Escolha uma opção: ");
        int numeroLista = scanner.nextInt();
        scanner.nextLine();

        ListaDeSeries listaEscolhida = null;

        switch (numeroLista) {
            case 1:
                listaEscolhida = seriesFavoritas;
                break;
            case 2:
                listaEscolhida = seriesAssistidas;
                break;
            case 3:
                listaEscolhida = desejoAssistir;
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        if (listaEscolhida.getSeries().isEmpty()) {
            System.out.println("A lista está vazia, não há nada para ordenar.");
            return;
        }

        System.out.println("Ordenar por qual critério?");
        System.out.println("1. Ordem Alfabética");
        System.out.println("2. Nota");
        System.out.println("3. Estado da Série");
        System.out.println("4. Data de Estreia");

        System.out.print("Escolha uma opção: ");
        int numeroCriterio = scanner.nextInt();
        scanner.nextLine();

        List<Serie> listaOrdenada;

        if (numeroCriterio == 1) {
            listaOrdenada = listaEscolhida.listaOrdenadaPorNome();
            System.out.println("\n--- Lista Ordenada por Nome ---");
        } else if (numeroCriterio == 2) {
            listaOrdenada = listaEscolhida.listaOrdenadaPorNota();
            System.out.println("\n--- Lista Ordenada por Nota ---");
        } else if (numeroCriterio == 3) {
            listaOrdenada = listaEscolhida.listaOrdenadaPorEstado();
            System.out.println("\n--- Lista Ordenada por Estado ---");
        } else if (numeroCriterio == 4) {
            listaOrdenada = listaEscolhida.listaOrdenadaPorDataDeEstreia();
            System.out.println("\n--- Lista Ordenada por Data de Estreia ---");
        } else {
            System.out.println("Critério inválido.");
            return;
        }

        for (Serie s : listaOrdenada) {
            System.out.printf("- %s (Nota: %.1f)\n", s.getNome(), s.getNota());
        }
    }

    private void salvarNomeUsuario() {
        try (FileWriter writer = new FileWriter(SAUDACAO)) {
            writer.write("{\"nomeUsuario\":\"" + this.nomeUsuario + "\"}");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o nome do usuário: " + e.getMessage());
        }
    }

    private void carregarNomeUsuario() {
        try (FileReader reader = new FileReader(SAUDACAO)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            this.nomeUsuario = jsonObject.get("nomeUsuario").getAsString();
        } catch (IOException e) {
            this.nomeUsuario = "Usuário";
            System.out.println("Nenhum nome de usuário encontrado. Usando padrão: 'Usuário'.");
        }
    }

    private void exibirDetalhesSerie(Serie serie) {
        System.out.println("Nome: " + serie.getNome());
        System.out.println("Idioma: " + serie.getIdioma());
        System.out.println("Gêneros: " + serie.getGenero());
        System.out.println("Nota: " + serie.getNota());
        System.out.println("Estado: " + serie.getEstado());
        System.out.println("Data de Lançamento: " + serie.getDataDeLancamento());
        System.out.println("Data de Término: " + serie.getDataDeTermino());
        System.out.println("Emissora: " + serie.getEmissora());
    }

    private void salvarESair() {
        System.out.println("Salvando...");
        salvarNomeUsuario();
        seriesFavoritas.salvarJson(ARQUIVO_FAVORITAS);
        seriesAssistidas.salvarJson(ARQUIVO_ASSISTIDAS);
        desejoAssistir.salvarJson(ARQUIVO_DESEJO);
        System.out.println("Dados salvos. Saindo do aplicativo...");
        scanner.close();
    }
}