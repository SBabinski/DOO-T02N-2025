package Prova_2tri;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaController sistema = new SistemaController();
        Scanner scan = new Scanner(System.in);

        try {
            System.out.print("Digite seu nome: ");
            sistema.iniciar(scan.nextLine());

            while (true) {
                System.out.println("1. Buscar série\n2. Ver listas\n3. Remover serie de uma lista\n4. Ordenar uma lista\n5. Sair");
                switch (scan.nextInt()) {
                    case 1:
                        scan.nextLine();
                        System.out.print("Digite o nome da série: ");
                        Serie s = sistema.buscarSerie(scan.nextLine());
                        if (s == null) {
                            System.out.println("Série não encontrada.");
                            break;
                        }
                        System.out.println(s);
                        System.out.println("Adicionar a uma lista? (s/n)");
                        if (scan.nextLine().equalsIgnoreCase("s")) {
                            System.out.println("Escolha o tipo de lista:\n1. Favoritas\n2. Assistidas\n3. Deseja assistir");
                            int tipoLista = scan.nextInt();
                            scan.nextLine();
                            switch (tipoLista) {
                                case 1:
                                    sistema.adicionarFavoritos(s);
                                    System.out.println("Série adicionada às favoritas.");
                                    break;
                                case 2:
                                    sistema.getUsuario().getAssistidas().add(s);
                                    System.out.println("Série adicionada às assistidas.");
                                    break;
                                case 3:
                                    sistema.getUsuario().getDesejadas().add(s);
                                    System.out.println("Série adicionada à lista de deseja assistir.");
                                    break;
                                default:
                                    System.out.println("Opção inválida. Série não adicionada.");
                            }
                            sistema.salvar();
                        }
                        break;
                    case 2:
                        scan.nextLine();
                        System.out.println("Escolha o tipo de lista:\n1. Favoritas\n2. Assistidas\n3. Desejadas");
                        int tipoLista = scan.nextInt();
                        scan.nextLine();
                        if (tipoLista == 1) {
                            System.out.println("Séries favoritas:");
                            sistema.getUsuario().getFavoritas().forEach(System.out::println);
                        } else if (tipoLista == 2) {
                            System.out.println("Séries assistidas:");
                            sistema.getUsuario().getAssistidas().forEach(System.out::println);
                            System.out.println(" ");
                        } else if (tipoLista == 3) {
                            System.out.println("Séries desejadas:");
                            sistema.getUsuario().getDesejadas().forEach(System.out::println);
                            System.out.println(" ");
                        } else {
                            System.out.println("Opção inválida.");
                        }
                        break;
                    case 3:
                        scan.nextLine();
                        System.out.print("Digite o nome da série: ");
                        s = sistema.buscarSerie(scan.nextLine());
                        if (s == null) {
                            System.out.println("Série não encontrada.");
                            break;
                        }
                        System.out.println("Escolha da lista da qual remover:\n1. Favoritas\n2. Assistidas\n3. Desejadas");
                        int tipoRemocao = scan.nextInt();
                        scan.nextLine();
                        if (tipoRemocao == 1 && sistema.getUsuario().getFavoritas().contains(s)){
                            sistema.removerFavoritos(s);
                            System.out.println("Série removida das favoritas.");
                        } else if (tipoRemocao == 2 && sistema.getUsuario().getAssistidas().contains(s)) {
                            sistema.removerAssistidas(s);
                            System.out.println("Série removida das assistidas.");
                        } else if (tipoRemocao == 3 && sistema.getUsuario().getDesejadas().contains(s)) {
                            sistema.removerDesejadas(s);
                            System.out.println("Série removida da lista de deseja assistir.");
                        } else {
                            System.out.println("Série não encontrada na lista especificada.");
                        }
                        sistema.salvar();
                        break;
                    case 4:
                        scan.nextLine();
                        System.out.println("Escolha o tipo de lista para ordenar:\n1. Favoritas\n2. Assistidas\n3. Desejadas");
                        int tipoOrdenacao = scan.nextInt();
                        List<Serie> lista;
                        scan.nextLine();
                        if (tipoOrdenacao == 1) {
                            lista = sistema.getUsuario().getFavoritas();
                        } else if (tipoOrdenacao == 2) {
                            lista = sistema.getUsuario().getAssistidas();
                        } else if (tipoOrdenacao == 3) {
                            lista = sistema.getUsuario().getDesejadas();
                        } else {
                            System.out.println("Opção inválida.");
                            continue;
                        }
                        System.out.println("Escolha o critério de ordenação:\n1. Nome\n2. Nota\n3. Status\n4. Data de estreia");
                        tipoOrdenacao = scan.nextInt();
                        if (tipoOrdenacao < 1 || tipoOrdenacao > 4) {
                            System.out.println("Critério inválido.");
                            continue;
                        } else {
                            switch (tipoOrdenacao) {
                                case 1:
                                    lista = sistema.getListaOrdenada(lista, "nome");
                                    break;
                                case 2:
                                    lista = sistema.getListaOrdenada(lista, "nota");
                                    break;
                                case 3:
                                    lista = sistema.getListaOrdenada(lista, "status");
                                    break;
                                case 4:
                                    lista = sistema.getListaOrdenada(lista, "estreia");
                                    break;
                            }
                            sistema.salvar();
                            System.out.println("Lista ordenada com sucesso!");
                            break;
                        }
                        
                        case 5:
                        sistema.salvar();
                        return;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            try {
                sistema.salvar();
            } catch (Exception ignored) {}
        }

        scan.close();
    }
}
