package enon;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import static enon.GerenciarListas.menuExibirListas;

public class Principal {

    public static Scanner scan = new Scanner(System.in);

    public static int tryCatchUniversalInt(String mensagem, int minimo, int maximo) {
        while (true) {
            System.out.println(mensagem);
            String entrada = scan.nextLine();

            try {
                int opcao = Integer.parseInt(entrada);
                if (opcao >= minimo && opcao <= maximo) {
                    return opcao;
                } else {
                    System.out.println(
                            "Opção inválida. Por favor, escolha um número entre " + minimo + " e " + maximo + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número válido.");
            }
        }
    }

    public static void consultarSerie() {
        System.out.println("Digite o nome da série que deseja pesquisar: ");
        String nomeSerie = scan.nextLine();

        List<SerieData> resultados = ConexaoApiTvMaze.buscarSeries(nomeSerie);

        if (resultados.isEmpty()) {
            System.out.println("Nenhuma série encontrada com o nome: " + nomeSerie);
            return;
        }

        System.out.println("\nSéries encontradas:");
        for (int i = 0; i < resultados.size(); i++) {
            SerieData serie = resultados.get(i);
            System.out.println((i + 1) + " - " + serie.getNome() +
                    " | Idioma: " + serie.getIdioma() +
                    " | Nota: " + serie.getNotaGeral() +
                    " | Status: " + serie.getEstado());
        }

        System.out.println((resultados.size() + 1) + " - Voltar");
        int escolha = tryCatchUniversalInt("Escolha uma série para ver opções:", 1, resultados.size() + 1);

        if (escolha == resultados.size() + 1) {
            return;
        }

        SerieData selecionada = resultados.get(escolha - 1);
        GerenciarListas.menuOpcoesDaSerie(selecionada);
    }

    public static void main(String[] args) {
        GerenciarListas.carregarListasDeJson();

        String nomeSalvo = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("usuario.txt"))) {
            nomeSalvo = reader.readLine();
        } catch (IOException e) {

        }

        String nomeUsuario;

        if (nomeSalvo != null && !nomeSalvo.trim().isEmpty()) {
            nomeUsuario = nomeSalvo.trim();
            System.out.println("Seja bem-vindo de volta, " + nomeUsuario + "!");
        } else {
            System.out.println("Digite seu nome ou apelido:");
            nomeUsuario = scan.nextLine().trim();
            while (nomeUsuario.isEmpty()) {
                System.out.println("Nome inválido. Por favor, digite seu nome ou apelido:");
                nomeUsuario = scan.nextLine().trim();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuario.txt"))) {
                writer.write(nomeUsuario);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o nome do usuário.");
            }
            System.out.println("Olá, " + nomeUsuario + "! Bem-vindo ao sistema.");
        }
        int opcao = 0;
        while (true) {
            System.out.println("-----------------------------------");
            System.out.println("1 - Consultar Série");
            System.out.println("2 - Gerenciar Listas");
            System.out.println("3 - Sair");
            System.out.println("-----------------------------------");

            opcao = tryCatchUniversalInt("Escolha uma opção:", 1, 3);

            switch (opcao) {
                case 1:
                    consultarSerie();
                    break;
                case 2:
                    menuExibirListas();
                    break;
                case 3:
                    GerenciarListas.salvarListasEmJson();
                    System.out.println("Saindo do sistema. Até logo!");
                    System.exit(0);
            }
        }
    }
}
