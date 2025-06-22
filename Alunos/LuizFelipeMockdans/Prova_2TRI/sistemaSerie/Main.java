package sistemaSerie;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String ARQUIVO_JSON = "usuario.json";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Gson gson = new Gson();
        Usuario usuario = carregarUsuario(gson);
        ApiTvmaze api = new ApiTvmaze();

        while (true) {
            System.out.println("\nBem-vindo(a), " + usuario.getNome());
            System.out.println("1. Buscar série");
            System.out.println("2. Ver listas");
            System.out.println("3. Salvar e sair");
            int opcao = sc.nextInt(); sc.nextLine();

            try {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("Nome da série: ");
                        String nome = sc.nextLine();
                        Serie serie = api.buscarSeriePorNome(nome);
                        System.out.println(serie);

                        System.out.println("Adicionar em: 1-Favoritas, 2-Assistidas, 3-Para Assistir, 0-Nenhum");
                        int escolha = sc.nextInt(); sc.nextLine();

                        switch (escolha) {
                            case 1 -> usuario.adicionarSerie(usuario.getFavoritas(), serie);
                            case 2 -> usuario.adicionarSerie(usuario.getAssistidas(), serie);
                            case 3 -> usuario.adicionarSerie(usuario.getParaAssistir(), serie);
                        }
                    }

                    case 2 -> {
                        System.out.println("1 - Favoritas\n2 - Assistidas\n3 - Para Assistir");
                        int tipo = sc.nextInt(); sc.nextLine();

                        List<Serie> lista = switch (tipo) {
                            case 1 -> usuario.getFavoritas();
                            case 2 -> usuario.getAssistidas();
                            case 3 -> usuario.getParaAssistir();
                            default -> null;
                        };

                        if (lista != null) {
                            System.out.println("Ordenar por: 1-Nome, 2-Nota, 3-Status, 4-Data Estreia, 0-Nenhum");
                            int ord = sc.nextInt(); sc.nextLine();
                            switch (ord) {
                                case 1 -> ListaSeries.ordenarPorNome(lista);
                                case 2 -> ListaSeries.ordenarPorNota(lista);
                                case 3 -> ListaSeries.ordenarPorStatus(lista);
                                case 4 -> ListaSeries.ordenarPorDataEstreia(lista);
                            }

                            ListaSeries.exibirLista(lista);

                            System.out.println("Deseja remover uma série dessa lista? (s/n)");
                            String resposta = sc.nextLine();

                            if (resposta.equalsIgnoreCase("s")) {
                                System.out.print("Digite o nome exato da série que deseja remover: ");
                                String nomeRemover = sc.nextLine();

                                Serie serieRemover = lista.stream()
                                        .filter(s -> s.getNome().equalsIgnoreCase(nomeRemover))
                                        .findFirst()
                                        .orElse(null);

                                if (serieRemover != null) {
                                    usuario.removerSerie(lista, serieRemover);
                                    System.out.println("Série removida com sucesso!");
                                } else {
                                    System.out.println("Série não encontrada na lista.");
                                }
                            }
                        }
                    }

                    case 3 -> {
                        salvarUsuario(gson, usuario);
                        System.out.println("Dados salvos. Até logo!");
                        return;
                    }

                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static Usuario carregarUsuario(Gson gson) {
        try (Reader r = new FileReader(ARQUIVO_JSON)) {
            Type tipo = new TypeToken<Usuario>() {}.getType();
            return gson.fromJson(r, tipo);
        } catch (IOException e) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Digite seu nome: ");
            return new Usuario(sc.nextLine());
        }
    }

    private static void salvarUsuario(Gson gson, Usuario usuario) {
        try (Writer w = new FileWriter(ARQUIVO_JSON)) {
            gson.toJson(usuario, w);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }
}
