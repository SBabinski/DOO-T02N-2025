import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite seu nome ou apelido: ");
        Usuario usuario = new Usuario(scanner.nextLine());
        System.out.println("Bem-vindo(a), " + usuario.getNome() + "!");

        GerenciadorSeries gerenciador = new GerenciadorSeries();
        Map<String, List<Serie>> dados = Persistencia.carregar();

        dados.forEach((tipo, lista) -> lista.forEach(s -> gerenciador.adicionar(s, tipo)));

        while (true) {
            System.out.println("\n1. Buscar séries\n2. Ver listas\n3. Sair");
            String opcao = scanner.nextLine();

            if (opcao.equals("1")) {
                System.out.print("Digite o nome da série: ");
                String termo = scanner.nextLine();
                List<Serie> resultados = ServicoTVMaze.buscarSeries(termo);

                for (int i = 0; i < resultados.size(); i++) {
                    System.out.println((i + 1) + " - " + resultados.get(i));
                }

                System.out.print("Escolha o número da série para adicionar (ou 0 para cancelar): ");
                int escolha = Integer.parseInt(scanner.nextLine());
                if (escolha > 0 && escolha <= resultados.size()) {
                    Serie escolhida = resultados.get(escolha - 1);
                    System.out.println("Adicionar em: 1-Favoritas, 2-Assistidas, 3-Desejadas");
                    String tipo = switch (scanner.nextLine()) {
                        case "1" -> "favoritas";
                        case "2" -> "assistidas";
                        case "3" -> "desejadas";
                        default -> "";
                    };
                    if (!tipo.isEmpty()) {
                        gerenciador.adicionar(escolhida, tipo);
                        System.out.println("Adicionada com sucesso!");
                    }
                }

            } else if (opcao.equals("2")) {
                System.out.println("Qual lista deseja ver? 1-Favoritas, 2-Assistidas, 3-Desejadas");
                String tipo = switch (scanner.nextLine()) {
                    case "1" -> "favoritas";
                    case "2" -> "assistidas";
                    case "3" -> "desejadas";
                    default -> "";
                };
                if (!tipo.isEmpty()) {
                    System.out.println("Ordenar por: 1-Alfabética, 2-Nota, 3-Status, 4-Estreia");
                    Comparator<Serie> comp = switch (scanner.nextLine()) {
                        case "1" -> Util.ordemAlfabetica();
                        case "2" -> Util.porNota();
                        case "3" -> Util.porStatus();
                        case "4" -> Util.porEstreia();
                        default -> Util.ordemAlfabetica();
                    };
                    gerenciador.exibirLista(tipo, comp);
                }

            } else if (opcao.equals("3")) {
                Map<String, List<Serie>> salvar = new HashMap<>();
                salvar.put("favoritas", gerenciador.getLista("favoritas"));
                salvar.put("assistidas", gerenciador.getLista("assistidas"));
                salvar.put("desejadas", gerenciador.getLista("desejadas"));
                Persistencia.salvar(salvar);
                System.out.println("Até mais, " + usuario.getNome() + "!");
                break;
            }
        }
        scanner.close();
    }
}
