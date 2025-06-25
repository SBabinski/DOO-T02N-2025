package sistemaSerie;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Usuario usuario = SalvarUsu.carregar();
        if (usuario == null) {
            System.out.print("Digite seu nome ou apelido: ");
            usuario = new Usuario(sc.nextLine());
        }
        System.out.println("Bem-vindo(a) de volta " + usuario.getNome() + "!");
        while (true) {
        	
            System.out.println("==================================");
            System.out.println("1. Buscar série\n2. Adicionar a favoritos\n3. Adicionar a assistidas\n4. Adicionar a desejo assistir\n5. Remover série\n6. Ver listas\n7. Ordenar listas\n8. Sair");           
            System.out.println("==================================");
            System.out.println("Escolha uma opção entre 1 e 8:");
            String op = sc.nextLine();

            switch (op) {
                case "1" -> buscarSerie();
                case "2" -> adicionar(usuario.getFavoritos(), "FAVORITOS");
                case "3" -> adicionar(usuario.getAssistidas(), "ASSISTIDAS");
                case "4" -> adicionar(usuario.getDesejoAssistir(), "DESEJO ASSISTIR");
                case "5" -> remover(usuario);
                case "6" -> mostrarListas(usuario);
                case "7" -> ordenar(usuario);
                case "8" -> {
                    SalvarUsu.salvar(usuario);
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida");
            }
        }
    }
    
    

    private static void buscarSerie() {
        System.out.print("Digite o nome da série: ");
        String nomeBusca = sc.nextLine();
        List<Serie> series = ApiTVMaze.buscarSeries(nomeBusca);

        if (series.isEmpty()) {
            System.out.println("Nenhuma série encontrada.");
            return;
        }

        for (int i = 0; i < series.size(); i++) {
            System.out.println((i + 1) + " - " + series.get(i).getNome());
        }
        
        System.out.print("=========================================\n");
        System.out.print("Escolha o número da série que deseja ver:\n");
       
        try {
            int opcao = Integer.parseInt(sc.nextLine()) - 1;
            if (opcao >= 0 && opcao < series.size()) {
                System.out.println(series.get(opcao));
            } else {
                System.out.println("Opção inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private static void adicionar(List<Serie> lista, String tipo) {
        System.out.print("Digite o nome da série: ");
        List<Serie> series = ApiTVMaze.buscarSeries(sc.nextLine());

        if (series.isEmpty()) {
            System.out.println("Nenhuma série encontrada.\n");
            return;
        }

        for (int i = 0; i < series.size(); i++) {
            System.out.println((i + 1) + " - " + series.get(i).getNome());
        }

        System.out.print("Escolha o número da série para adicionar em " + tipo + ": ");
        try {
            int opcao = Integer.parseInt(sc.nextLine()) - 1;
            if (opcao >= 0 && opcao < series.size()) {
                Serie selecionada = series.get(opcao);
                if (lista.contains(selecionada)) {
                    System.out.println("Essa série já está na lista " + tipo + ".");
                    return;
                }
                lista.add(selecionada);
                System.out.println("Adicionado a " + tipo);
            } else {
                System.out.println("Opção inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private static void remover(Usuario usuario) {
        System.out.println("De qual lista deseja remover?");
        System.out.println("1. Favoritos");
        System.out.println("2. Assistidas");
        System.out.println("3. Desejo Assistir");
        System.out.println("4. Voltar");
        String opcao = sc.nextLine();

        List<Serie> lista;
        String nomeLista;
        switch (opcao) {
            case "1" -> {
                lista = usuario.getFavoritos();
                nomeLista = "Favoritos";
            }
            case "2" -> {
                lista = usuario.getAssistidas();
                nomeLista = "Assistidas";
            }
            case "3" -> {
                lista = usuario.getDesejoAssistir();
                nomeLista = "Desejo Assistir";
            }
            case "4" -> {
                return;
            }
            default -> {
                System.out.println("Opção inválida.");
                return;
            }
        }

        if (lista.isEmpty()) {
            System.out.println("A lista " + nomeLista + " está vazia.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i).getNome());
        }

        System.out.print("Digite o número da série que deseja remover: ");
        try {
            int idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx >= 0 && idx < lista.size()) {
                Serie removida = lista.remove(idx);
                System.out.println("Série \"" + removida.getNome() + "\" removida da lista " + nomeLista + ".");
            } else {
                System.out.println("Número inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private static void mostrarListas(Usuario u) {
        System.out.println("\nFAVORITOS:");
        u.getFavoritos().forEach(System.out::println);
        System.out.println("\nASSISTIDAS:");
        u.getAssistidas().forEach(System.out::println);
        System.out.println("\nDESEJO ASSISTIR:");
        u.getDesejoAssistir().forEach(System.out::println);
    }

    private static void ordenar(Usuario u) {
        System.out.println("Qual lista deseja ordenar?");
        System.out.println("1. Favoritos");
        System.out.println("2. Assistidas");
        System.out.println("3. Desejo Assistir");
        System.out.println("4. Voltar");
        String listaEscolha = sc.nextLine();

        List<Serie> lista;
        String nomeLista;

        switch (listaEscolha) {
            case "1" -> {
                lista = u.getFavoritos();
                nomeLista = "Favoritos";
            }
            case "2" -> {
                lista = u.getAssistidas();
                nomeLista = "Assistidas";
            }
            case "3" -> {
                lista = u.getDesejoAssistir();
                nomeLista = "Desejo Assistir";
            }
            case "4" -> {
                return;
            }
            default -> {
                System.out.println("Opção inválida.");
                return;
            }
        }

        if (lista.isEmpty()) {
            System.out.println("A lista " + nomeLista + " está vazia e não pode ser ordenada.");
            return;
        }

        System.out.println("Escolha o critério de ordenação:");
        System.out.println("1. Nome");
        System.out.println("2. Nota");
        System.out.println("3. Status");
        System.out.println("4. Estreia");
        
        String criterio = sc.nextLine();

        Comparator<Serie> comp = switch (criterio) {
            case "1" -> Comparator.comparing(Serie::getNome);
            case "2" -> Comparator.comparingDouble(Serie::getNota).reversed();
            case "3" -> Comparator.comparing(Serie::getStatus);
            case "4" -> Comparator.comparing(Serie::getDataEstreia);           
            default -> null;
        };

        if (comp == null) {
            System.out.println("Critério inválido.");
            return;
        }

        lista.sort(comp);
        System.out.println("Lista " + nomeLista + " ordenada com sucesso.");
    }
}