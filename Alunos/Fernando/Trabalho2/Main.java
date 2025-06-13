package Trabalho2;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scan = new Scanner(System.in);
    private static final SistemaController sistema = new SistemaController();

    public static void main(String[] args) {
        try {
            System.out.print("Digite seu nome: ");
            sistema.iniciar(scan.nextLine());

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Buscar notícia");
                System.out.println("2. Ver listas");
                System.out.println("3. Remover notícia de uma lista");
                System.out.println("4. Ordenar uma lista");
                System.out.println("5. Sair");

                int opcao = lerIntSeguro("Escolha uma opção: ");

                switch (opcao) {
                    case 1 -> menuBusca();
                    case 2 -> menuVerListas();
                    case 3 -> menuRemoverNoticia();
                    case 4 -> menuOrdenarLista();
                    case 5 -> {
                        sistema.salvar();
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        } finally {
            try {
                sistema.salvar();
            } catch (Exception ignored) {}
            scan.close();
        }
    }

    private static int lerIntSeguro(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int valor = scan.nextInt();
                scan.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
                scan.nextLine();
            }
        }
    }

    private static void menuBusca() {
        System.out.println("Tipo de busca:\n1. Por título\n2. Por palavra-chave\n3. Por data de publicação");
        int tipoBusca = lerIntSeguro("Escolha uma opção: ");

        Noticia noti = null;

        try {
            switch (tipoBusca) {
                case 1 -> {
                    System.out.print("Digite o título da notícia: ");
                    noti = sistema.buscarNoticiaPorTitulo(scan.nextLine());
                }
                case 2 -> {
                    System.out.print("Digite a palavra-chave da notícia: ");
                    noti = sistema.buscarNoticiaPorPalavraChave(scan.nextLine());
                }
                case 3 -> {
                    System.out.print("Digite a data (AAAA-MM-DD): ");
                    noti = sistema.buscarNoticiaPorData(scan.nextLine());
                }
                default -> {
                    System.out.println("Tipo de busca inválido.");
                    return;
                }
            }
        } catch (java.io.IOException e) {
            System.out.println("Erro ao buscar notícia: " + e.getMessage());
            return;
        }

        if (noti == null) {
            System.out.println("Notícia não encontrada.");
            return;
        }

        System.out.println("\n" + noti);
        System.out.println("Adicionar a uma lista? (s/n)");
        if (scan.nextLine().equalsIgnoreCase("s")) {
            adicionarNoticiaALista(noti);
        }
    }

    private static void adicionarNoticiaALista(Noticia noti) {
        System.out.println("Escolha a lista:\n1. Lidas\n2. Para ler depois\n3. Favoritas");
        int tipoLista = lerIntSeguro("Escolha uma opção: ");

        switch (tipoLista) {
            case 1 -> sistema.adicionarLidas(noti);
            case 2 -> sistema.adicionarParaLerDepois(noti);
            case 3 -> sistema.adicionarFavoritas(noti);
            default -> {
                System.out.println("Opção inválida. Notícia não adicionada.");
                return;
            }
        }

        try {
            sistema.salvar();
        } catch (java.io.IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
        System.out.println("Notícia adicionada com sucesso!");
    }

    private static void menuVerListas() {
        System.out.println("Escolha a lista:\n1. Lidas\n2. Para ler depois\n3. Favoritas");
        int tipo = lerIntSeguro("Escolha uma opção: ");

        List<Noticia> lista = switch (tipo) {
            case 1 -> sistema.getUsuario().getLidas();
            case 2 -> sistema.getUsuario().getParaLerDepois();
            case 3 -> sistema.getUsuario().getFavoritas();
            default -> null;
        };

        if (lista == null) {
            System.out.println("Opção inválida.");
            return;
        }

        if (lista.isEmpty()) {
            System.out.println("A lista está vazia.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private static void menuRemoverNoticia() throws IOException {
        System.out.println("\n=== Remover Notícia de Lista ===");

        System.out.println("De qual lista deseja remover?");
        System.out.println("1 - Lidas");
        System.out.println("2 - Para Ler Depois");
        System.out.println("3 - Favoritas");
        int tipo = lerIntSeguro("Opção: ");

        List<Noticia> lista = switch (tipo) {
            case 1 -> sistema.getUsuario().getLidas();
            case 2 -> sistema.getUsuario().getParaLerDepois();
            case 3 -> sistema.getUsuario().getFavoritas();
            default -> {
                System.out.println("Opção inválida.");
                yield null;
            }
        };

        System.out.print("Digite o título da notícia a ser removida: ");
        String titulo = scan.nextLine();

        Noticia noti = lista.stream()
            .filter(n -> n.getTitulo().trim().equalsIgnoreCase(titulo.trim()))
            .findFirst()
            .orElse(null);

        if (noti == null) {
            System.out.println("Notícia não encontrada nesta lista.");
            return;
        }

        switch (tipo) {
            case 1 -> sistema.removerLidas(noti);
            case 2 -> sistema.removerParaLerDepois(noti);
            case 3 -> sistema.removerFavoritas(noti);
        }

        sistema.salvar();
        System.out.println("Notícia removida com sucesso.");
}

    private static void menuOrdenarLista() {
        System.out.println("Ordenar qual lista?\n1. Lidas\n2. Para ler depois\n3. Favoritas");
        int tipo = lerIntSeguro("Escolha uma opção: ");

        List<Noticia> lista = switch (tipo) {
            case 1 -> sistema.getUsuario().getLidas();
            case 2 -> sistema.getUsuario().getParaLerDepois();
            case 3 -> sistema.getUsuario().getFavoritas();
            default -> null;
        };

        if (lista == null) {
            System.out.println("Opção inválida.");
            return;
        }

        System.out.println("Critério de ordenação:\n1. Título\n2. Data de Publicação\n3. Tipo");
        int criterio = lerIntSeguro("Escolha um critério: ");

        String campo = switch (criterio) {
            case 1 -> "titulo";
            case 2 -> "data";
            case 3 -> "tipo";
            default -> {
                System.out.println("Critério inválido.");
                yield null;
            }
        };

        if (campo == null) return;

        List<Noticia> ordenada = sistema.getListaOrdenada(lista, campo);
        ordenada.forEach(System.out::println);
        try {
            sistema.salvar();
        } catch (java.io.IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
        System.out.println("Lista ordenada com sucesso.");
    }
}