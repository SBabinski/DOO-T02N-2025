package seriestv.pack;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GerenciadorListas {
    private List<Serie> desejo;
    private List<Serie> assistidas;
    private List<Serie> favoritas;
    public static Scanner sc = new Scanner(System.in);

    public GerenciadorListas(Usuario usuario) {
        this.desejo = usuario.getDesejo();
        this.assistidas = usuario.getAssistidas();
        this.favoritas = usuario.getFavoritas();
    }

    public GerenciadorListas(List<Serie> desejo, List<Serie> assistidas, List<Serie> favoritas) {
        this.desejo = desejo != null ? desejo : new ArrayList<>();
        this.assistidas = assistidas != null ? assistidas : new ArrayList<>();
        this.favoritas = favoritas != null ? favoritas : new ArrayList<>();
    }

    public List<Serie> getDesejo() {
        return desejo;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public List<Serie> getFavoritas() {
        return favoritas;
    }

    public void adicionarDesejo(Serie serie) {
        if (desejo.contains(serie)) {
            System.out.println("Essa série já está na lista de desejo.");
            return;
        }
        desejo.add(serie);
        System.out.println("Série adicionada à lista de desejo.");
    }

    public void adicionarAssistida(Serie serie) {
        if (assistidas.contains(serie)) {
            System.out.println("Essa série já está na lista de assistidas.");
            return;
        }

        if (desejo.contains(serie)) {
            desejo.remove(serie);
        }

        assistidas.add(serie);
        System.out.println("Série adicionada à lista de assistidas.");
    }

    public void adicionarFavorita(Serie serie) {
        if (favoritas.contains(serie)) {
            System.out.println("Essa série já está na lista de favoritas.");
            return;
        }
        favoritas.add(serie);
        System.out.println("Série adicionada à lista de favoritas.");
    }

    public void listar(List<Serie> lista) {
        for (Serie s : lista) {
            List<String> detalhes = s.infoResumida();
            System.out.println(s.getName()
                    + (!detalhes.isEmpty() ? " (" + String.join(", ", detalhes) + ")" : ""));
            System.out.println("");
        }
    }

    public void listarComIndex(List<Serie> lista) {
        int index = 1;
        for (Serie s : lista) {
            List<String> detalhes = s.infoResumida();
            System.out.println(index + " - " + s.getName()
                    + (!detalhes.isEmpty() ? " (" + String.join(", ", detalhes) + ")" : ""));
            index++;
        }
        System.out.println("========================================:");
    }

    public void ordenarListaOriginal(List<Serie> lista, int criterio) {
        Comparator<Serie> comparador;

        switch (criterio) {
            case 1:
                comparador = Comparator.comparing(s -> s.getName().toLowerCase());
                break;
            case 2:
                comparador = Comparator.comparing(
                        s -> s.getRating() != null && s.getRating().getAverage() != null ? s.getRating().getAverage()
                                : 0.0,
                        Comparator.reverseOrder());
                break;
            case 3:
                comparador = Comparator.comparing(s -> {
                    switch (s.getStatus()) {
                        case "Ended":
                            return 0;
                        case "Running":
                            return 1;
                        case "Canceled":
                            return 2;
                        default:
                            return 3;
                    }
                });
                break;
            case 4:
                comparador = Comparator.comparing(s -> {
                    try {
                        return s.getPremiered() != null && !s.getPremiered().isEmpty()
                                ? LocalDate.parse(s.getPremiered())
                                : LocalDate.MIN;
                    } catch (Exception e) {
                        return LocalDate.MIN;
                    }
                });
                break;
            default:
                System.out.println("Critério inválido.");
                return;
        }

        lista.sort(comparador);
        System.out.println("Lista ordenada com sucesso.");
    }

    public void menuLista(int n) {
        List<Serie> lista = null;
        String titulo = null;

        if (n == 1) {
            lista = favoritas;
            titulo = "FAVORITAS";

        } else if (n == 2) {
            lista = assistidas;
            titulo = "JÁ ASSISTIDAS";
        } else if (n == 3) {
            lista = desejo;
            titulo = "QUE DESEJA ASSISTIR";
        }
        System.out.println("=======================");
        System.out.println("LISTA DE SÉRIES " + titulo);
        listar(lista);
        System.out.println("=======================");

        System.out.println("=======================");
        System.out.println("OPÇÕES");
        System.out.println("1 - Gerenciar elementos da lista");
        System.out.println("2 - Ordenar Lista");
        System.out.println("0 - Voltar");
        System.out.println("=======================");
        int op = Principal.lerOpcaoNumerica("Insira o número correspondente para selecionar uma opção:", 0, 2);
        if (op == 0) {
            return;
        } else if (op == 1) {
            listarComIndex(lista);
            int escolha = Principal.lerOpcaoNumerica(
                    "Digite o número da série para ver detalhes ou 0 para voltar:",
                    0,
                    lista.size());
            if (escolha == 0) {
                return;
            }
            Serie s = lista.get(escolha - 1);
            System.out.println("=======================");
            s.mostrarDetalhes();
            System.out.println("=======================");
            System.out.println("OPÇÕES");
            System.out.println("1 - Remover da lista");
            System.out.println("0 - Voltar");
            System.out.println("=======================");
            int op2 = Principal.lerOpcaoNumerica(
                    "Digite o número da série para ver detalhes ou 0 para voltar:",
                    0, 1);
            if (op2 == 0) {
                return;
            }
            lista.remove(escolha - 1);
            System.out.println("Série removida da lista '" + titulo.toLowerCase() + "' com sucesso.");
            return;
        } else if (op == 2) {
            System.out.println("=======================");
            System.out.println("OPÇÕES");
            System.out.println("1 - Ordem Alfabética");
            System.out.println("2 - Nota Geral");
            System.out.println("3 - Status");
            System.out.println("4 - Estreia");
            System.out.println("0 - Voltar");
            System.out.println("=======================");

            int op3 = Principal.lerOpcaoNumerica("Escolha:", 0, 4);

            if (op3 == 0) {
                return;
            }

            ordenarListaOriginal(lista, op3);
        }
        return;
    }
}
