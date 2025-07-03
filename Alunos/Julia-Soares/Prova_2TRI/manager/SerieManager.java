package org.series.manager;

import java.util.*;
import org.series.Usuario;
import org.series.service.SerieService;
import org.series.Serie;
import java.time.LocalDate;

public class SerieManager {
    private Usuario usuario;
    private SerieService service = new SerieService();
    private Scanner scanner = new Scanner(System.in);

    public SerieManager(Usuario usuario) {
        this.usuario = usuario;
    }

    public void menu() {
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Buscar série");
            System.out.println("2. Ver listas");
            System.out.println("3. Sair");

            String op = scanner.nextLine();
            switch (op) {
                case "1":
                    buscarSerie();
                    break;
                case "2":
                    verListas();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void buscarSerie() {
        try {
            System.out.print("Nome da série: ");
            String nome = scanner.nextLine();
            Serie serie = service.buscarSeriePorNome(nome);
            if (serie == null) {
                System.out.println("Série não encontrada.");
                return;
            }
            System.out.println(serie);
            System.out.println("1. Adicionar a favoritos");
            System.out.println("2. Marcar como assistida");
            System.out.println("3. Adicionar à lista para assistir");
            System.out.println("4. Remover série de uma lista");
            System.out.println("5. Sair");
            String op = scanner.nextLine();

            switch (op) {
                case "1":
                    adicionarSerieNaLista(usuario.getFavoritas(), serie, "Favoritas");
                    break;
                case "2":
                    adicionarSerieNaLista(usuario.getAssistidas(), serie, "Assistidas");
                    break;
                case "3":
                    adicionarSerieNaLista(usuario.getDesejoAssistir(), serie, "Desejo Assistir");
                    break;
                case "4":
                    removerSerie();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }


    private boolean verificarSerieEmListas(Serie serie) {
        if (usuario.getFavoritas().stream().anyMatch(s -> s.getNome().equalsIgnoreCase(serie.getNome()))) {
            System.out.println("Esta série já está na lista de Favoritas.");
            return true;
        }
        if (usuario.getAssistidas().stream().anyMatch(s -> s.getNome().equalsIgnoreCase(serie.getNome()))) {
            System.out.println("Esta série já está na lista de Assistidas.");
            return true;
        }
        if (usuario.getDesejoAssistir().stream().anyMatch(s -> s.getNome().equalsIgnoreCase(serie.getNome()))) {
            System.out.println("Esta série já está na lista de Desejo Assistir.");
            return true;
        }
        return false;
    }

    private void adicionarSerieNaLista(List<Serie> lista, Serie serie, String nomeLista) {



        boolean jaExisteNaLista = lista.stream()
                .anyMatch(s -> s.getNome().equalsIgnoreCase(serie.getNome()));

        if (jaExisteNaLista) {
            System.out.println("Esta série já está na lista de " + nomeLista + "!");
        } else {
            lista.add(serie);
            System.out.println("Série adicionada à lista de " + nomeLista + " com sucesso!");
        }
    }

    private void ordenarLista(List<Serie> lista) {
        System.out.println("\nComo deseja ordenar?");
        System.out.println("1. Nome (A-Z)");
        System.out.println("2. Nota geral (maior para menor)");
        System.out.println("3. Estado da série");
        System.out.println("4. Data de estreia");
        System.out.print("Escolha: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                lista.sort(Comparator.comparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER));
                break;
            case "2":
                lista.sort(Comparator.comparingDouble(Serie::getNota).reversed());
                break;
            case "3":
                lista.sort(Comparator.comparing(Serie::getStatus));
                break;
            case "4":
                lista.sort(Comparator.comparing(serie -> {
                    String data = serie.getDataEstreia();
                    return data.equals("N/A") ? LocalDate.MIN : LocalDate.parse(data);
                }));
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        System.out.println("\n📋 Lista de séries:");
        lista.forEach(System.out::println);
    }

    private void verListas() {
        System.out.println("Qual lista deseja ver?");
        System.out.println("1. Favoritas");
        System.out.println("2. Assistidas");
        System.out.println("3. Desejo assistir");
        String escolha = scanner.nextLine();

        List<Serie> lista;
        String nomeLista;

        switch (escolha) {
            case "1" -> {
                lista = usuario.getFavoritas();
                nomeLista = "Favoritas";
            }
            case "2" -> {
                lista = usuario.getAssistidas();
                nomeLista = "Assistidas";
            }
            case "3" -> {
                lista = usuario.getDesejoAssistir();
                nomeLista = "Desejo Assistir";
            }
            default -> {
                System.out.println("Opção inválida.");
                return;
            }
        }

        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }

        ordenarLista(lista);

        while (true) {
            System.out.println("\nDeseja remover alguma série desta lista?");
            System.out.println("1. Sim");
            System.out.println("2. Voltar ao menu");
            String opcao = scanner.nextLine();

            if (opcao.equals("1")) {
                System.out.print("Digite o nome da série que deseja remover: ");
                String nome = scanner.nextLine();

                boolean removido = lista.removeIf(serie -> serie.getNome().equalsIgnoreCase(nome));

                if (removido) {
                    System.out.println("Série removida com sucesso!");
                } else {
                    System.out.println("Série não encontrada na lista.");
                }

                if (lista.isEmpty()) {
                    System.out.println("Lista está vazia agora.");
                    return;
                }

                System.out.println("\n📋 Lista atualizada:");
                lista.forEach(System.out::println);
            } else if (opcao.equals("2")) {
                return;
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }

    private void removerSerie() {
        System.out.print("Digite o nome da série que deseja remover: ");
        String nome = scanner.nextLine();

        System.out.println("De qual lista deseja remover?");
        System.out.println("1. Favoritas");
        System.out.println("2. Assistidas");
        System.out.println("3. Desejo assistir");

        String escolha = scanner.nextLine();
        List<Serie> lista;

        switch (escolha) {
            case "1" -> lista = usuario.getFavoritas();
            case "2" -> lista = usuario.getAssistidas();
            case "3" -> lista = usuario.getDesejoAssistir();
            default -> {
                System.out.println("Opção inválida.");
                return;
            }
        }

        boolean removido = lista.removeIf(serie -> serie.getNome().equalsIgnoreCase(nome));

        if (removido) {
            System.out.println("Série removida com sucesso!");
        } else {
            System.out.println("Série não encontrada na lista.");
        }
    }
}
