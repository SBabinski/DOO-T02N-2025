package seriestv.pack;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

public class Principal {
    private static final Scanner sc = new Scanner(System.in); // nome em caixa alta por ser constante
    private static GerenciadorListas gerenciadorListas;

    public static void main(String[] args) {
        Usuario usuario = Usuario.iniciarUsuario();
        gerenciadorListas = new GerenciadorListas(usuario);
        menuGeral();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> Usuario.salvarUsuario(usuario)));
    }

    private static void menuGeral() {
        while (true) {
            printarMenu();

            int opcao = lerOpcaoNumerica("Selecione uma opção: ", 0, 3);

            switch (opcao) {
                case 1 -> pesquisa();
                case 2 -> {
                    System.out.println("=======================");
                    System.out.println("LISTAS");
                    System.out.println("1 - Favoritas");
                    System.out.println("2 - Já assisti");
                    System.out.println("3 - Quero assistir");
                    System.out.println("0 - Voltar");
                    System.out.println("=======================");

                    int op = lerOpcaoNumerica("Insira o número correspondente para selecionar uma opção:", 0, 3);
                    if (op != 0) {
                        gerenciadorListas.menuLista(op);
                    }
                }
                case 0 -> {
                    System.out.println("Saindo do programa... Até logo!");
                    return;
                }
            }
            System.out.println();
        }
    }

    private static void pesquisa() {
        try {
            System.out.println("Digite o título da série para pesquisa, ou zero para voltar:");
            String nome = sc.nextLine();
            if (nome.equals("0"))
                return;

            String nomeEncoded = URLEncoder.encode(nome, StandardCharsets.UTF_8);
            String urlString = "https://api.tvmaze.com/search/shows?q=" + nomeEncoded;
            System.out.println("Procurando resultados correspondentes...");

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

                Gson gson = new Gson();
                SerieResult[] resultados = gson.fromJson(responseBody, SerieResult[].class);

                if (resultados.length == 0) {
                    System.out.println("Nenhuma série encontrada.");
                    return;
                }

                List<Serie> listaResultados = new ArrayList<>();
                System.out.println("Séries encontradas:");
                System.out.println("========================================:");
                int index = 1;
                for (SerieResult resultado : resultados) {
                    Serie s = resultado.show;
                    listaResultados.add(s);
                    List<String> detalhes = s.infoResumida();
                    System.out.println(index++ + " - " + s.getName() +
                            (!detalhes.isEmpty() ? " (" + String.join(", ", detalhes) + ")" : ""));
                }
                System.out.println("========================================:");

                int escolha = lerOpcaoNumerica("Digite o número da série para ver detalhes ou 0 para voltar:",
                        0, listaResultados.size());

                if (escolha == 0)
                    return;

                Serie s = listaResultados.get(escolha - 1);
                System.out.println("=======================");
                s.mostrarDetalhes();

                System.out.println("=======================");
                System.out.println("OPÇÕES:");
                System.out.println("1 - Adicionar a uma lista");
                System.out.println("0 - Voltar");
                System.out.println("=======================");
                int op = lerOpcaoNumerica("Insira o número correspondente para selecionar uma opção:", 0, 1);

                if (op == 1) {
                    adicaoLista(s);
                }

            } else {
                System.out.println("Erro na requisição: Código " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao fazer requisição: " + e.getMessage());

        }

    }

    private static void adicaoLista(Serie s) {
        System.out.println("=======================");
        System.out.println("LISTAS");
        System.out.println("1 - Favoritas");
        System.out.println("2 - Já assisti");
        System.out.println("3 - Quero assistir");
        System.out.println("0 - Voltar");
        System.out.println("=======================");
        int op = lerOpcaoNumerica("Insira o número correspondente para selecionar uma opção:", 0, 3);

        switch (op) {
            case 1 -> gerenciadorListas.adicionarFavorita(s);
            case 2 -> gerenciadorListas.adicionarAssistida(s);
            case 3 -> gerenciadorListas.adicionarDesejo(s);
        }
    }

    private static void printarMenu() {
        System.out.println("=====================================");
        System.out.println("BMDI - MENU PRINCIPAL");
        System.out.println("=====================================");
        System.out.println("1 - Buscar série");
        System.out.println("2 - Ver minhas listas");
        System.out.println("0 - Sair");
        System.out.println("=====================================");
    }

    public static int lerOpcaoNumerica(String mensagem, int minimo, int maximo) {
        while (true) {
            System.out.println(mensagem);
            String entrada = sc.nextLine();

            try {
                int opcao = Integer.parseInt(entrada);
                if (opcao >= minimo && opcao <= maximo) {
                    return opcao;
                } else {
                    System.out.printf("Por favor, digite um número entre %d e %d.%n", minimo, maximo);
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número inteiro válido.");
            }
        }
    }
}