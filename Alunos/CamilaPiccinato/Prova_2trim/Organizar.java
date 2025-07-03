import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Organizar {

    public static void exibirListaOrdenada(String nomeLista, List<Serie> lista, Scanner scanner) {
        if (lista.isEmpty()) {
            System.out.println("\nSua lista de " + nomeLista + " está vazia.");
            return;
        }

        int op = -1;

        while (op != 0) {
            System.out.println("\nComo deseja ordenar a lista de " + nomeLista + "?");
            System.out.println("[1] Nome (ordem alfabética)");
            System.out.println("[2] Nota (maior para menor)");
            System.out.println("[3] Status da série (ordem alfabética)");
            System.out.println("[4] Data de estreia (mais antiga primeiro)");
            System.out.println("[0] Voltar ao menu");

            try {
                op = Integer.parseInt(scanner.nextLine());

                List<Serie> listaOrdenada;

                switch (op) {
                    case 1:
                        listaOrdenada = lista.stream()
                            .sorted(Comparator.comparing(serie -> serie.getName().toLowerCase()))
                            .collect(Collectors.toList());
                        System.out.println("\nOrdenada por nome (A-Z):");
                        mostrar(listaOrdenada);
                        break;

                    case 2:
                        listaOrdenada = lista.stream()
                            .sorted(Comparator.comparingDouble(Serie::getRating).reversed())
                            .collect(Collectors.toList());
                        System.out.println("\nOrdenada por nota:");
                        mostrar(listaOrdenada);
                        break;

                    case 3:
                        listaOrdenada = lista.stream()
                            .sorted(Comparator.comparing(serie -> serie.getStatus().toLowerCase()))
                            .collect(Collectors.toList());
                        System.out.println("\nOrdenada por status:");
                        mostrar(listaOrdenada);
                        break;

                    case 4:
                        listaOrdenada = lista.stream()
                            .sorted(Comparator.comparing(serie -> {
                                String data = serie.getPremiered();
                                return data != null ? data : "9999-99-99";
                            }))
                            .collect(Collectors.toList());
                        System.out.println("\nOrdenada por data de estreia:");
                        mostrar(listaOrdenada);
                        break;

                    case 0:
                        System.out.println("Voltando ao menu...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    private static void mostrar(List<Serie> lista) {
        for (Serie serie : lista) {
            System.out.println("\n" + serie);
        }
    }
}
