import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        SeriesManager manager = new SeriesManager();
        Usuario usuario = new Usuario();

        System.out.println("\nBEM VINDO " + usuario.getNome());

        int op = 0;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Buscar série");
            System.out.println("2. Ver listas");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            String input = scan.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Opção não pode ser vazia. Tente novamente.");
                continue;
            }

            try {
                op = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
                continue;
            }

            switch (op) {
                case 1 -> {
                    Show showEncontrado = TVMazeSearchGson.buscarAPI(scan);
                    if (showEncontrado != null) {
                        System.out.println("Deseja adicionar esta série à alguma lista?");
                        System.out.println("1. Favoritos");
                        System.out.println("2. Séries assistidas");
                        System.out.println("3. Desejo assistir");
                        System.out.println("4. Não adicionar");

                        int op2;
                        while (true) {
                            System.out.print("Escolha uma opção: ");
                            input = scan.nextLine().trim();
                            if (input.isEmpty()) {
                                System.out.println("Opção não pode ser vazia. Tente novamente.");
                                continue;
                            }
                            try {
                                op2 = Integer.parseInt(input);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Digite um número válido.");
                            }
                        }

                        String lista = switch (op2) {
                            case 1 -> "favoritos";
                            case 2 -> "assistidas";
                            case 3 -> "deseja";
                            default -> null;
                        };
                        if (lista != null) {
                            manager.adicionar(lista, showEncontrado);
                        }
                    }
                }

                case 2 -> {
                    manager.mostrarListas();
                    System.out.println("\nO que deseja fazer?");
                    System.out.println("1. Remover série");
                    System.out.println("2. Ordenar lista");
                    System.out.println("3. Voltar");

                    int escolha;
                    while (true) {
                        System.out.print("Escolha uma opção: ");
                        input = scan.nextLine().trim();
                        if (input.isEmpty()) {
                            System.out.println("Opção não pode ser vazia. Tente novamente.");
                            continue;
                        }
                        try {
                            escolha = Integer.parseInt(input);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Digite um número válido.");
                        }
                    }

                    switch (escolha) {
                        case 1 -> {
                            System.out.println("Escolha a lista:");
                            System.out.println("1. Favoritos");
                            System.out.println("2. Séries assistidas");
                            System.out.println("3. Desejo assistir");

                            int listaOp;
                            while (true) {
                                System.out.print("Escolha uma opção: ");
                                input = scan.nextLine().trim();
                                if (input.isEmpty()) {
                                    System.out.println("Opção não pode ser vazia. Tente novamente.");
                                    continue;
                                }
                                try {
                                    listaOp = Integer.parseInt(input);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Digite um número válido.");
                                }
                            }

                            String lista = switch (listaOp) {
                                case 1 -> "favoritos";
                                case 2 -> "assistidas";
                                case 3 -> "deseja";
                                default -> null;
                            };
                            if (lista != null) {
                                manager.mostrarListaEspecifica(lista);
                                System.out.print("Digite o número da série para remover: ");

                                int num;
                                while (true) {
                                    input = scan.nextLine().trim();
                                    if (input.isEmpty()) {
                                        System.out.println("Número não pode ser vazio. Tente novamente.");
                                        continue;
                                    }
                                    try {
                                        num = Integer.parseInt(input);
                                        break;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Digite um número válido.");
                                    }
                                }

                                manager.removerPorIndice(lista, num - 1);
                            }
                        }

                        case 2 -> {
                            System.out.println("Escolha a lista:");
                            System.out.println("1. Favoritos");
                            System.out.println("2. Séries assistidas");
                            System.out.println("3. Desejo assistir");

                            int listaOp;
                            while (true) {
                                System.out.print("Escolha uma opção: ");
                                input = scan.nextLine().trim();
                                if (input.isEmpty()) {
                                    System.out.println("Opção não pode ser vazia. Tente novamente.");
                                    continue;
                                }
                                try {
                                    listaOp = Integer.parseInt(input);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Digite um número válido.");
                                }
                            }

                            String lista = switch (listaOp) {
                                case 1 -> "favoritos";
                                case 2 -> "assistidas";
                                case 3 -> "deseja";
                                default -> null;
                            };
                            if (lista != null) {
                                System.out.println("Critério de ordenação:");
                                System.out.println("1. Nome (A-Z)");
                                System.out.println("2. Nota (maior por primeiro)");
                                System.out.println("3. Estado (já concluída, ainda transmitindo, cancelada)");
                                System.out.println("4. Data de estreia (mais antiga primeiro)");

                                int criterio;
                                while (true) {
                                    System.out.print("Escolha uma opção: ");
                                    input = scan.nextLine().trim();
                                    if (input.isEmpty()) {
                                        System.out.println("Opção não pode ser vazia. Tente novamente.");
                                        continue;
                                    }
                                    try {
                                        criterio = Integer.parseInt(input);
                                        break;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Digite um número válido.");
                                    }
                                }

                                manager.ordenarLista(lista, criterio);
                                manager.mostrarListaEspecifica(lista);
                            }
                        }

                        case 3 -> System.out.println("Voltando...");
                        default -> System.out.println("Opção inválida.");
                    }
                }

                case 3 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (op != 3);

        scan.close();
    }
}