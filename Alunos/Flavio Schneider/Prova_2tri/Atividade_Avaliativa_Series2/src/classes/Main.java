package classes;

import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

        Usuario usuario = Persistencia.carregarUsuario();
        if (usuario == null) {
            System.out.print("Digite seu nome ou apelido: ");
            String nome = scanner.nextLine();
            usuario = new Usuario(nome);
        }

        boolean sair = false;
        while (!sair) {
            System.out.println("\nOlá " + usuario.getNome() + "! Escolha uma opção:");
            System.out.println("1 - Buscar série");
            System.out.println("2 - Ver listas");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Digite o nome da série para buscar: ");
                    String nomeSerie = scanner.nextLine();
                    Serie serie = Api.buscarSerie(nomeSerie); // Ajuste para retornar Serie
                    if (serie != null) {
                        System.out.println("\nSérie encontrada: " + serie.getNome());
                        
                        
                        System.out.println("Deseja adicionar a alguma lista? (f)avoritas, (a)ssistidas, (d)esejadas, (n)ão");
                        String escolha = scanner.nextLine();
                        switch (escolha.toLowerCase()) {
                            case "f":
                                usuario.adicionarFavorita(serie);
                                System.out.println("Adicionada às favoritas.");
                                break;
                            case "a":
                                usuario.adicionarAssistida(serie);
                                System.out.println("Adicionada às assistidas.");
                                break;
                            case "d":
                                usuario.adicionarDesejada(serie);
                                System.out.println("Adicionada às desejadas.");
                                break;
                            default:
                                System.out.println("Nenhuma lista selecionada.");
                                break;
                        }
                        Persistencia.salvarUsuario(usuario);
                    } else {
                        System.out.println("Série não encontrada.");
                    }
                    break;

                case "2":
                    System.out.println("Qual lista deseja ver? (f)avoritas, (a)ssistidas, (d)esejadas");
                    String listaEscolha = scanner.nextLine();
                    List<Serie> lista = null;
                    switch (listaEscolha.toLowerCase()) {
                        case "f":
                            lista = usuario.getFavoritas();
                            break;
                        case "a":
                            lista = usuario.getAssistidas();
                            break;
                        case "d":
                            lista = usuario.getDesejadas();
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            continue;
                    }

                    System.out.println("Como deseja ordenar? (nome, nota, estado, estreia)");
                    String ordenacao = scanner.nextLine();

                    lista = ordenarLista(lista, ordenacao);

                    System.out.println("\nLista de séries:");
                    for (Serie s : lista) {
                        System.out.println("- " + s.getNome() + " (Nota: " + s.getNota() + ", Estado: " + s.getEstado() + ")");
                    }
                    break;

                case "3":
                    sair = true;
                    System.out.println("Até mais!");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        scanner.close();
    }

    // Método para ordenar a lista (copiado do que vimos antes)
    public static List<Serie> ordenarLista(List<Serie> lista, String criterio) {
        if (lista == null) return null;

        switch (criterio.toLowerCase()) {
            case "nome":
                lista.sort((s1, s2) -> s1.getNome().compareToIgnoreCase(s2.getNome()));
                break;
            case "nota":
                lista.sort((s1, s2) -> Double.compare(s2.getNota(), s1.getNota())); // descendente
                break;
            case "estado":
                lista.sort((s1, s2) -> s1.getEstado().compareToIgnoreCase(s2.getEstado()));
                break;
            case "estreia":
                lista.sort((s1, s2) -> s1.getDataEstreia().compareTo(s2.getDataEstreia()));
                break;
            default:
                System.out.println("Critério de ordenação inválido. Mantendo ordem atual.");
                break;
        }
        return lista;
	}

}
