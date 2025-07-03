import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Serie;
import models.User;
import services.TVMazeAPIRequest;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;

public class Controller extends Serie {

    public static void buscarSerie() {
        try {
            System.out.println("Nome da serie:");
            List<Serie> series = TVMazeAPIRequest.serieRequest(App.scanner.nextLine());

            if (series != null) {
                mostrarLista("Resultado da Busca", series, false);

                System.out.println("digite o número da serie para ver mais detalhes:");
                int escolha = -1;
                try {
                    escolha = App.scanner.nextInt();
                    App.scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.err.println("Aviso: você não digitou um número");
                    App.scanner.nextLine();
                }

                if (escolha < 1 || escolha > series.size()) {
                    System.out.println("Cancelado");
                    return;
                }

                exibirDetalhesESalvar(series.get(escolha - 1));
            } else {
                System.out.println("Nenhuma serie encontrada");
            }
        } catch (InputMismatchException e) {
            System.err.println("Aviso: você não digitou um número");
            App.scanner.nextLine();
        }
    }

    public static void exibirDetalhesESalvar(Serie serie) {
        try {
            serie.detailInfo();

            System.out.println("\nDeseja adicionar essa série a alguma lista?");
            System.out.println("1. Favoritas");
            System.out.println("2. Assistidas");
            System.out.println("3. Para Assistir");
            System.out.println("0. Não adicionar");
            System.out.print("Escolha: ");
            int op = -1;
            try {
                op = App.scanner.nextInt();
                App.scanner.nextLine();
            } catch (InputMismatchException e) {
                System.err.println("Aviso: você não digitou um número");
                App.scanner.nextLine();
            }

            switch (op) {
                case 1 -> App.user.seriesFavoritas.add(serie);
                case 2 -> App.user.seriesAssistidas.add(serie);
                case 3 -> App.user.seriesParaAssistir.add(serie);
                case 0 -> System.out.println("Série não foi adicionada a nenhuma lista.");
                default -> System.out.println("Opção inválida.");
            }
        } catch (InputMismatchException e) {
            System.err.println("Aviso: você não digitou um número");
            App.scanner.nextLine();
        }
    }

    public static void exibirDetalhesERemover(Serie serie, List<Serie> lista) {
        int op = -1;
        serie.detailInfo();

        System.out.println("\nOpções");
        System.out.println("1. Remover da lista");
        System.out.println("0. Voltar");
        try {
            op = App.scanner.nextInt();
            App.scanner.nextLine();
        } catch (InputMismatchException e) {
            System.err.println("Aviso: Você não digitou um número");
        }
        if (op == 1) {
            lista.remove(serie);
        } else {
            System.out.println("\n");
        }

    }

    public static void mostrarLista(String titulo, List<Serie> list, boolean flag) {
        System.out.println("---" + titulo + "---");
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                System.out.print(i + 1 + ". ");
                list.get(i).showInfo();
                System.out.println("-----------------------------------");
            }

            System.out.println("0. Voltar");
            System.out.println("Selecione uma serie:");

            int op = -1;
            try {
                op = App.scanner.nextInt();
                App.scanner.nextLine();
            } catch (InputMismatchException e) {
                System.err.println("Aviso: você não digitou um número");
                App.scanner.nextLine();
            }
            if (op == 0) { 
                System.out.println("\n");
            } else {
                if (flag) {
                    exibirDetalhesERemover(list.get(op - 1), list);
                } else {
                    exibirDetalhesESalvar(list.get(op - 1));
                }
            }
        } else {
            System.out.println("Lista vazia.");
        }
    }

    public static void ordenarLista(List<Serie> lista) {
        int opcao = -1;

        System.out.println("Ordenar por:");
        System.out.println("1. Nome");
        System.out.println("2. Nota");
        System.out.println("3. Estado");
        System.out.println("4. Data de Estreia");
        System.out.print("Opção: ");
        try {
            opcao = App.scanner.nextInt();
            App.scanner.nextLine();
        } catch (InputMismatchException e) {
            System.err.println("Aviso: você não digitou um número");
            App.scanner.nextLine();
        }

        switch (opcao) {
            case 1 -> lista.sort((a, b) -> a.show.name.compareToIgnoreCase(b.show.name));
            case 2 -> lista.sort((a, b) -> Double.compare(b.show.rating.average, a.show.rating.average));
            case 3 -> lista.sort((a, b) -> estadoOrdem(a.show.status) - estadoOrdem(b.show.status));
            case 4 -> lista.sort(Comparator.comparing(s -> s.show.premiered));
            default -> System.out.println("Opção inválida.");
        }

        System.out.println("\nLista ordenada:");
        mostrarLista("Resultado da Ordenada", lista, false);
    }

    public static List<Serie> selecionarLista() {
        int lista = -1;

        System.out.println("Selecione a lista:");
        System.out.println("1. Favoritas");
        System.out.println("2. Assistidas");
        System.out.println("3. Para Assistir");
        try {
            lista = App.scanner.nextInt();
            App.scanner.nextLine();
        } catch (InputMismatchException e) {
            System.err.println("Aviso: você não digitou um número");
        }

        switch (lista) {
            case 1 -> {
                return App.user.seriesFavoritas;
            }
            case 2 -> {
                return App.user.seriesAssistidas;
            }
            case 3 -> {
                return App.user.seriesParaAssistir;
            }
            default -> {
                return List.of();
            }
        }

    }

    static int estadoOrdem(String estado) {
        return switch (estado.toLowerCase()) {
            case "ended" -> 0;
            case "running" -> 1;
            case "to be determined" -> 2;
            case "in development" -> 3;
            default -> 4;
        };
    }

    public static void salvarUsuario(String nomeArquivo) {
        try (Writer writer = new FileWriter(nomeArquivo)) {
            new Gson().toJson(App.user, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    public static User carregarUsuario(String nomeArquivo) {
        try (Reader reader = new FileReader(nomeArquivo)) {
            Type tipo = new TypeToken<User>() {}.getType();
            return new Gson().fromJson(reader, tipo);
        } catch (FileNotFoundException e) {
            return new User();
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuário: " + e.getMessage());
            return new User();
        }
    }


}
