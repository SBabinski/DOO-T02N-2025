import model.SerieDTO;
import service.ApiService;
import service.SerieService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ApiService api = new ApiService();
        SerieService service = new SerieService();

        while (true) {
            System.out.println("\n====== MENU ======");
            System.out.println("1 - Buscar série");
            System.out.println("2 - Ver favoritos");
            System.out.println("3 - Ver assistidos");
            System.out.println("4 - Ver lista 'para assistir'");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            String entradaTexto = entrada.nextLine();
            int opcao;

            try {
                opcao = Integer.parseInt(entradaTexto);
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número.");
                continue;
            }

            if (opcao == 0) {
                System.out.println("Encerrando o programa...");
                break;
            }

            if (opcao == 1) {
                System.out.print("Digite o nome da série: ");
                String nomeSerie = entrada.nextLine();

                try {
                    SerieDTO serie = api.buscarSerie(nomeSerie);
                    System.out.println("\n--- Resultado ---");
                    System.out.println("Nome: " + serie.getNome());
                    System.out.println("Idioma: " + serie.getIdioma());
                    System.out.println("Gêneros: " + String.join(", ", serie.getGeneros()));
                    System.out.println("Nota: " + serie.getNota());
                    System.out.println("Status: " + serie.getStatus());
                    System.out.println("Estreia: " + serie.getDataEstreia());
                    System.out.println("Término: " + serie.getDataFim());
                    System.out.println("Emissora: " + serie.getEmissora());

                    System.out.println("\nDeseja adicionar:");
                    System.out.println("1 - Aos Favoritos");
                    System.out.println("2 - Aos Assistidos");
                    System.out.println("3 - À lista Para Assistir");
                    System.out.println("Outro - Nada");
                    System.out.print("Escolha: ");

                    String escolhaTexto = entrada.nextLine();
                    int escolha;

                    try {
                        escolha = Integer.parseInt(escolhaTexto);
                    } catch (NumberFormatException e) {
                        escolha = -1;
                    }

                    if (escolha == 1) service.adicionarFavorito(serie);
                    else if (escolha == 2) service.adicionarAssistido(serie);
                    else if (escolha == 3) service.adicionarParaAssistir(serie);

                } catch (Exception e) {
                    System.out.println("Erro ao buscar série: " + e.getMessage());
                }
            } else if (opcao == 2) {
                mostrarListaComAcoes("Favoritos", service.getFavoritos(), service::removerFavorito, entrada);
            } else if (opcao == 3) {
                mostrarListaComAcoes("Assistidos", service.getAssistidos(), service::removerAssistido, entrada);
            } else if (opcao == 4) {
                mostrarListaComAcoes("Para Assistir", service.getParaAssistir(), service::removerParaAssistir, entrada);
            } else {
                System.out.println("Opção inválida!");
            }
        }

        entrada.close();
    }

    private static void mostrarListaComRemocao(String nomeLista, java.util.List<SerieDTO> lista,
                                               java.util.function.IntConsumer removerFunc, Scanner entrada) {
        System.out.println("\n--- " + nomeLista + " ---");
        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println(i + " - " + lista.get(i).getNome());
        }

        System.out.print("Deseja remover algum? (s/n): ");
        String resposta = entrada.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            System.out.print("Digite o número da série: ");
            String indiceTexto = entrada.nextLine();

            try {
                int indice = Integer.parseInt(indiceTexto);
                if (indice >= 0 && indice < lista.size()) {
                    removerFunc.accept(indice);
                    System.out.println("Removido com sucesso.");
                } else {
                    System.out.println("Índice inválido.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    private static void mostrarListaComAcoes(String nomeLista, java.util.List<SerieDTO> lista,
                                             java.util.function.IntConsumer removerFunc, Scanner entrada) {
        System.out.println("\n--- " + nomeLista + " ---");
        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println(i + " - " + lista.get(i).getNome());
        }

        System.out.print("Escolha uma ação - (v) Ver detalhes, (r) Remover, (n) Nenhuma: ");
        String acao = entrada.nextLine();

        switch (acao.toLowerCase()) {
            case "v":
                System.out.print("Digite o número da série para ver detalhes: ");
                String indiceTextoV = entrada.nextLine();
                try {
                    int indice = Integer.parseInt(indiceTextoV);
                    if (indice >= 0 && indice < lista.size()) {
                        SerieDTO s = lista.get(indice);
                        System.out.println("\nDetalhes da série:");
                        System.out.println("Nome: " + s.getNome());
                        System.out.println("Idioma: " + s.getIdioma());
                        System.out.println("Gêneros: " + String.join(", ", s.getGeneros()));
                        System.out.println("Nota: " + s.getNota());
                        System.out.println("Status: " + s.getStatus());
                        System.out.println("Estreia: " + s.getDataEstreia());
                        System.out.println("Término: " + s.getDataFim());
                        System.out.println("Emissora: " + s.getEmissora());
                    } else {
                        System.out.println("Índice inválido.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um número.");
                }
                break;

            case "r":
                System.out.print("Digite o número da série para remover: ");
                String indiceTextoR = entrada.nextLine();
                try {
                    int indice = Integer.parseInt(indiceTextoR);
                    if (indice >= 0 && indice < lista.size()) {
                        removerFunc.accept(indice);
                        System.out.println("Removido com sucesso.");
                    } else {
                        System.out.println("Índice inválido.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um número.");
                }
                break;

            case "n":
                
                break;

            default:
                System.out.println("Ação inválida.");
        }
    }
}
