package enon;

import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GerenciarListas {

    private static List<SerieData> favoritas = new ArrayList<>();
    private static List<SerieData> assistidas = new ArrayList<>();
    private static List<SerieData> desejoAssistir = new ArrayList<>();
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

    public static void menuExibirListas() {
        while (true) {
            int opcao = tryCatchUniversalInt(
                    "\n-- EXIBIR LISTAS --\n" +
                            "1 - Favoritas\n" +
                            "2 - Assistidas\n" +
                            "3 - Desejo Assistir\n" +
                            "4 - Voltar",
                    1, 4);
            switch (opcao) {

                case 1:
                    while (true) {
                        int escolhaFavoritas = tryCatchUniversalInt(
                                "\n-- FAVORITAS --\n" +
                                        "1 - Exibir séries favoritas\n" +
                                        "2 - Remover série favorita\n" +
                                        "3 - Ordenar séries favoritas\n" +
                                        "4 - Voltar",
                                1, 4);

                        if (escolhaFavoritas == 1) {
                            if (favoritas.isEmpty()) {
                                System.out.println("Sua lista de séries favoritas está vazia.");
                            } else {
                                System.out.println("Exibindo séries favoritas...");
                                for (SerieData serie : favoritas) {
                                    System.out.println(
                                            serie.getNome() + " | Nota: " + serie.getNotaGeral() + " | Status: "
                                                    + serie.getEstado());
                                }
                            }
                        } else if (escolhaFavoritas == 2) {
                            removerSerieDaLista(favoritas, "Favoritas");
                        } else if (escolhaFavoritas == 3) {
                            menuOrdenarListas(favoritas, "Favoritas");
                        } else if (escolhaFavoritas == 4) {
                            System.out.println("Voltando ao menu principal...");
                            return;
                        }
                    }
                case 2:
                    while (true) {
                        int escolhaAssistidas = tryCatchUniversalInt(
                                "\n-- FAVORITAS --\n" +
                                        "1 - Exibir séries assistidas\n" +
                                        "2 - Remover série assistida\n" +
                                        "3 - Ordenar séries assistidas\n" +
                                        "4 - Voltar",
                                1, 4);

                        if (escolhaAssistidas == 1) {
                            if (assistidas.isEmpty()) {
                                System.out.println("Sua lista de séries assistidas está vazia.");
                            } else {
                                System.out.println("Exibindo séries assistidas...");
                                for (SerieData serie : assistidas) {
                                    System.out.println(
                                            serie.getNome() + " | Nota: " + serie.getNotaGeral() + " | Status: "
                                                    + serie.getEstado());
                                }
                            }
                        } else if (escolhaAssistidas == 2) {
                            removerSerieDaLista(assistidas, "Assistidas");
                        } else if (escolhaAssistidas == 3) {
                            menuOrdenarListas(assistidas, "Assistidas");
                        } else if (escolhaAssistidas == 4) {
                            System.out.println("Voltando ao menu principal...");
                            return;
                        }
                    }
                case 3:
                    while (true) {
                        int escolhaDesejo = tryCatchUniversalInt(
                                "\n-- FAVORITAS --\n" +
                                        "1 - Exibir séries que deseja assistir\n" +
                                        "2 - Remover série que deseja assistir\n" +
                                        "3 - Ordenar séries que deseja assistir\n" +
                                        "4 - Voltar",
                                1, 4);

                        if (escolhaDesejo == 1) {
                            if (desejoAssistir.isEmpty()) {
                                System.out.println("Sua lista de séries que deseja assistir está vazia.");
                            } else {
                                System.out.println("Exibindo séries que desejo assistir...");
                                for (SerieData serie : desejoAssistir) {
                                    System.out.println(
                                            serie.getNome() + " | Nota: " + serie.getNotaGeral() + " | Status: "
                                                    + serie.getEstado());
                                }
                            }
                        } else if (escolhaDesejo == 2) {
                            removerSerieDaLista(desejoAssistir, "Desejo Assistir");
                        } else if (escolhaDesejo == 3) {
                            menuOrdenarListas(desejoAssistir, "Desejo Assistir");
                        } else if (escolhaDesejo == 4) {
                            System.out.println("Voltando ao menu principal...");
                            return;
                        }
                    }
                case 4:
                    System.out.println("Voltando ao menu principal...");
                    return;
            }
        }
    }

    public static void menuOrdenarListas(List<SerieData> lista, String nomeLista) {
        if (lista.isEmpty()) {
            System.out.println("A lista de " + nomeLista + " está vazia. Não há nada para ordenar.");
            return;
        }
        while (true) {
            int opcao = tryCatchUniversalInt(
                    "\n-- ORDENAR LISTAS --\n" +
                            "1 - Por nome (A-Z) \n" +
                            "2 - Por nota geral\n" +
                            "3 - Por estado da série\n" +
                            "4 - Por data de estreia\n" +
                            "5 - Voltar",
                    1, 5);
            switch (opcao) {

                case 1:
                    System.out.println("Ordenando por nome (A-Z)...");
                    ordenarPorNome(lista);

                    break;
                case 2:
                    System.out.println("Ordenando por nota geral...");
                    ordenarPorNota(lista);
                    break;
                case 3:
                    System.out.println("Ordenando por estado da série...");
                    ordenarPorEstado(lista);
                    break;
                case 4:
                    System.out.println("Ordenando por data de estreia...");
                    ordenarPorDataEstreia(lista);
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal...");
                    return;
            }
        }
    }

    public static void menuOpcoesDaSerie(SerieData serie) {
        while (true) {
            int opcao = tryCatchUniversalInt(
                    "\n-- O QUE DESEJA FAZER COM \"" + serie.getNome() + "\"? --\n" +
                            "1 - Ver mais detalhes\n" +
                            "2 - Adicionar aos favoritos\n" +
                            "3 - Adicionar às assistidas\n" +
                            "4 - Adicionar à lista de desejo\n" +
                            "5 - Voltar",
                    1, 5);

            switch (opcao) {
                case 1:
                    exibirDetalhes(serie);
                    break;
                case 2:
                    if (!listaContemSerie(favoritas, serie)) {
                        favoritas.add(serie);
                        System.out.println("Adicionado aos favoritos.");
                    } else {
                        System.out.println("Essa série já está na lista de favoritos.");
                    }
                    break;
                case 3:
                    if (!listaContemSerie(assistidas, serie)) {
                        assistidas.add(serie);
                        System.out.println("Adicionado às assistidas.");
                    } else {
                        System.out.println("Essa série já está na lista de assistidas.");
                    }
                    break;
                case 4:
                    if (!listaContemSerie(desejoAssistir, serie)) {
                        desejoAssistir.add(serie);
                        System.out.println("Adicionado à lista de desejo.");
                    } else {
                        System.out.println("Essa série já está na lista de desejo.");
                    }
                    break;
                case 5:
                    return;
            }
        }
    }

    public static void exibirDetalhes(SerieData serie) {
        System.out.println("\n=== Detalhes da Série ===");
        System.out.println("Nome: " + serie.getNome());
        System.out.println("Idioma: " + serie.getIdioma());
        System.out.println("Gêneros: " + String.join(", ", serie.getGeneros()));
        System.out.println("Nota geral: " + serie.getNotaGeral());
        System.out.println("Status: " + serie.getEstado());
        System.out.println("Data de estreia: " + serie.getDataEstreia());
        System.out.println("Data de término: " + serie.getDataFim());
        System.out.println("Emissora: " + serie.getEmissora());
    }

    public static void removerSerieDaLista(List<SerieData> lista, String nomeLista) {
        if (lista.isEmpty()) {
            System.out.println("A lista de " + nomeLista + " está vazia.");
            return;
        }

        System.out.println("\n--- " + nomeLista.toUpperCase() + " ---");
        for (int i = 0; i < lista.size(); i++) {
            SerieData serie = lista.get(i);
            System.out.println((i + 1) + " - " + serie.getNome());
        }
        System.out.println((lista.size() + 1) + " - Cancelar");

        int escolha = tryCatchUniversalInt("Escolha o número da série para remover:", 1, lista.size() + 1);
        if (escolha == lista.size() + 1) {
            System.out.println("Remoção cancelada.");
            return;
        }

        SerieData removida = lista.remove(escolha - 1);
        System.out.println("Série \"" + removida.getNome() + "\" removida da lista de " + nomeLista + ".");
    }

    private static boolean listaContemSerie(List<SerieData> lista, SerieData serie) {
        for (SerieData s : lista) {
            if (s.getNome().equalsIgnoreCase(serie.getNome())) {
                return true;
            }
        }
        return false;
    }

    private static void ordenarPorNome(List<SerieData> lista) {
        lista.sort(Comparator.comparing(SerieData::getNome, String.CASE_INSENSITIVE_ORDER));
        System.out.println("Lista ordenada por nome (A-Z).");
    }

    private static void ordenarPorNota(List<SerieData> lista) {
        lista.sort(Comparator.comparingDouble(SerieData::getNotaGeral).reversed());
        System.out.println("Lista ordenada por nota geral.");
    }

    private static void ordenarPorEstado(List<SerieData> lista) {
        lista.sort(Comparator.comparingInt(serie -> {
            String estado = serie.getEstado().toLowerCase();
            if (estado.equals("running"))
                return 0;
            if (estado.equals("ended"))
                return 1;
            return 2; // canceled ou null se vier errado ass:enonDebug
        }));
        System.out.println("Lista ordenada por estado da série.");
    }

    private static void ordenarPorDataEstreia(List<SerieData> lista) {
        lista.sort(Comparator.comparing(SerieData::getDataEstreia));
        System.out.println("Lista ordenada por data de estreia.");
    }

    public static void salvarListasEmJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("favoritas.json"), favoritas);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("assistidas.json"), assistidas);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("desejo.json"), desejoAssistir);
            System.out.println("Listas salvas com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar listas: " + e.getMessage());
        }
    }

    public static void carregarListasDeJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File f1 = new File("favoritas.json");
            if (f1.exists()) {
                favoritas = mapper.readValue(f1, new TypeReference<List<SerieData>>() {
                });
            }
            File f2 = new File("assistidas.json");
            if (f2.exists()) {
                assistidas = mapper.readValue(f2, new TypeReference<List<SerieData>>() {
                });
            }
            File f3 = new File("desejo.json");
            if (f3.exists()) {
                desejoAssistir = mapper.readValue(f3, new TypeReference<List<SerieData>>() {
                });
            }
            System.out.println("Listas carregadas com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar listas: " + e.getMessage());
        }
    }
}