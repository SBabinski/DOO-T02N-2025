package Prova_2tri;

import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Map<String, Usuario> usuarios = Persistencia.carregarUsuarios();

        System.out.print("Digite seu nome ou apelido: ");
        String nome = sc.nextLine();

        Usuario usuario = usuarios.get(nome);
        if (usuario == null) {
            usuario = new Usuario(nome);
            usuarios.put(nome, usuario);
            System.out.println("Novo usuário criado!");
        }
        System.out.println("Bem-vindo, " + usuario.getNome() + "!");

        while (true) {
            try {
                System.out.println("\nMenu:");
                System.out.println("1 - Procurar séries");
                System.out.println("2 - Adicionar/remover favoritos");
                System.out.println("3 - Adicionar/remover assistidas");
                System.out.println("4 - Adicionar/remover quero assistir");
                System.out.println("5 - Exibir listas");
                System.out.println("6 - Salvar e sair");
                System.out.print("Escolha uma opção: ");
                int op = Integer.parseInt(sc.nextLine());

                switch (op) {
                    case 1:
                        procurarSeries();
                        break;
                    case 2:
                        gerenciarLista(usuario, "favoritos");
                        break;
                    case 3:
                        gerenciarLista(usuario, "assistidas");
                        break;
                    case 4:
                        gerenciarLista(usuario, "queroAssistir");
                        break;
                    case 5:
                        exibirListas(usuario);
                        break;
                    case 6:
                    Persistencia.salvarUsuarios(usuarios);
                    System.out.println("Dados salvos. Até mais!");
                    return;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void procurarSeries() {
        try {
            System.out.print("Digite o nome da série: ");
            String nome = sc.nextLine();
            List<Serie> resultados = SerieAPI.buscarSeriePorNome(nome);
            if (resultados.isEmpty()) {
                System.out.println("Nenhuma série encontrada.");
                return;
            }
            for (int i = 0; i < resultados.size(); i++) {
                System.out.println("\n[" + (i + 1) + "]\n" + resultados.get(i));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar séries! Verifique sua conexão ou tente novamente mais tarde.");
        }
    }

    private static void gerenciarLista(Usuario usuario, String tipo) {
        try {
            System.out.print("Digite o nome da série para buscar: ");
            String nome = sc.nextLine();
            List<Serie> resultados = SerieAPI.buscarSeriePorNome(nome);
            if (resultados.isEmpty()) {
                System.out.println("Nenhuma série encontrada.");
                return;
            }
            for (int i = 0; i < resultados.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + resultados.get(i).getNome());
            }
            System.out.print("Escolha o número da série para adicionar/remover: ");
            int idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx < 0 || idx >= resultados.size()) {
                System.out.println("Número inválido.");
                return;
            }
            Serie serie = resultados.get(idx);

            switch (tipo) {
                case "favoritos":
                    if (usuario.getFavoritos().contains(serie)) {
                        usuario.removeFavorito(serie);
                        System.out.println("Removido dos favoritos.");
                    } else {
                        usuario.addFavorito(serie);
                        System.out.println("Adicionado aos favoritos.");
                    }
                    break;
                case "assistidas":
                    if (usuario.getAssistidas().contains(serie)) {
                        usuario.removeAssistida(serie);
                        System.out.println("Removido da lista de assistidas.");
                    } else {
                        usuario.addAssistida(serie);
                        System.out.println("Adicionado à lista de assistidas.");
                    }
                    break;
                case "queroAssistir":
                    if (usuario.getQueroAssistir().contains(serie)) {
                        usuario.removeQueroAssistir(serie);
                        System.out.println("Removido da lista de quero assistir.");
                    } else {
                        usuario.addQueroAssistir(serie);
                        System.out.println("Adicionado à lista de quero assistir.");
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Erro ao gerenciar lista: " + e.getMessage());
        }
    }

    private static void exibirListas(Usuario usuario) {
        System.out.println("\nQual lista deseja exibir?");
        System.out.println("1 - Favoritos");
        System.out.println("2 - Assistidas");
        System.out.println("3 - Quero assistir");
        System.out.print("Escolha: ");
        int op = Integer.parseInt(sc.nextLine());
        Set<Serie> lista = null;
        String nomeLista = "";
        switch (op) {
            case 1:
                lista = usuario.getFavoritos();
                nomeLista = "Favoritos";
                break;
            case 2:
                lista = usuario.getAssistidas();
                nomeLista = "Assistidas";
                break;
            case 3:
                lista = usuario.getQueroAssistir();
                nomeLista = "Quero assistir";
                break;
            default:
                System.out.println("Opção inválida!");
                return;
        }
        List<Serie> listaOrdenada = new ArrayList<>(lista);
        if (listaOrdenada.isEmpty()) {
            System.out.println(nomeLista + " vazio.");
            return;
        }
        System.out.println("Como deseja ordenar?");
        System.out.println("1 - Ordem alfabética");
        System.out.println("2 - Nota geral");
        System.out.println("3 - Estado da série");
        System.out.println("4 - Data de estreia");
        System.out.print("Escolha: ");
        int ord = Integer.parseInt(sc.nextLine());
        switch (ord) {
            case 1:
                listaOrdenada.sort(Comparator.comparing(Serie::getNome));
                break;
            case 2:
                listaOrdenada.sort(Comparator.comparing(Serie::getNotaGeral).reversed());
                break;
            case 3:
                listaOrdenada.sort(Comparator.comparing(Serie::getEstado));
                break;
            case 4:
                listaOrdenada.sort(Comparator.comparing(Serie::getDataEstreia, Comparator.nullsFirst(String::compareTo)));
                break;
            default:
                System.out.println("Opção inválida. Exibindo sem ordenação.");
        }
        for (Serie s : listaOrdenada) {
            System.out.println("\n" + s);
        }
    }
}