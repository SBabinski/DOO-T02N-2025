package org.example.servico;

import org.example.dto.SerieDto;

import java.util.List;
import java.util.Scanner;

public class ServicoLista {

    private ServicoUsuario usuario;

    public ServicoLista(ServicoUsuario usuario) {
        this.usuario = usuario;
    }

    public void adicionarSerie(String nomeSerie, ServicoApi api, Scanner scanner, String tipoLista) {
        SerieDto serie = api.buscarSerie(nomeSerie);
        if (serie != null) {
            List<SerieDto> lista = getListaPorTipo(tipoLista);

            boolean existe = false;
            for (SerieDto s : lista) {
                if (s.name.equalsIgnoreCase(serie.name)) {
                    existe = true;
                    break;
                }
            }

            if (existe) {
                System.out.println("Série já está na lista de " + tipoLista);
            } else {
                lista.add(serie);
                System.out.println("Série adicionada à lista de " + tipoLista);
            }
        }
    }

    public void removerSerie(String nomeSerie, String tipoLista) {
        List<SerieDto> lista = getListaPorTipo(tipoLista);

        boolean removido = false;
        for (int i = 0; i < lista.size(); i++) {
            SerieDto s = lista.get(i);
            if (s.name.equalsIgnoreCase(nomeSerie)) {
                lista.remove(i);
                removido = true;
                break;
            }
        }

        if (removido) {
            System.out.println("Série removida da lista de " + tipoLista);
        } else {
            System.out.println("Série não encontrada na lista de " + tipoLista);
        }
    }

    public void listarSerie(String tipoLista, Scanner scanner) {
        List<SerieDto> lista = getListaPorTipo(tipoLista);

        if (lista.isEmpty()) {
            System.out.println("A lista de " + tipoLista + " está vazia.");
            return;
        }

        System.out.println("Ordenar por:");
        System.out.println("1. Nome (A-Z)");
        System.out.println("2. Nota geral (maior para menor)");
        System.out.println("3. Estado (concluída, transmitindo, cancelada)");
        System.out.println("4. Data de estreia (mais recente)");
        System.out.print("Escolha uma opção: ");

        int opcao = 0;
        try {
            opcao = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Opção inválida, listando sem ordenação.");
        }

        ordenarListaSimples(lista, opcao);

        System.out.println("\nLista de " + tipoLista + ":");
        for (SerieDto s : lista) {
            System.out.println("------------------");
            System.out.println(s);
        }
    }

    private void ordenarListaSimples(List<SerieDto> lista, int opcao) {
        int tamanho = lista.size();
        for (int i = 0; i < tamanho - 1; i++) {
            for (int j = i + 1; j < tamanho; j++) {
                SerieDto a = lista.get(i);
                SerieDto b = lista.get(j);
                boolean trocar = false;

                if (opcao == 1) {
                    if (a.name != null && b.name != null) {
                        if (a.name.compareTo(b.name) > 0) {
                            trocar = true;
                        }
                    }
                }

                if (opcao == 2) {
                    double notaA = 0;
                    double notaB = 0;

                    if (a.rating != null && a.rating.average != null) {
                        notaA = a.rating.average;
                    }

                    if (b.rating != null && b.rating.average != null) {
                        notaB = b.rating.average;
                    }

                    if (notaA < notaB) {
                        trocar = true;
                    }
                }

                if (opcao == 3) {
                    int estadoA = converterStatus(a.status);
                    int estadoB = converterStatus(b.status);

                    if (estadoA > estadoB) {
                        trocar = true;
                    }
                }

                if (opcao == 4) {
                    String dataA = "";
                    String dataB = "";

                    if (a.premiered != null) {
                        dataA = a.premiered;
                    }

                    if (b.premiered != null) {
                        dataB = b.premiered;
                    }

                    if (dataA.compareTo(dataB) < 0) {
                        trocar = true;
                    }
                }

                if (trocar) {
                    lista.set(i, b);
                    lista.set(j, a);
                }
            }
        }
    }

    private int converterStatus(String status) {
        if (status == null) {
            return 4;
        }

        if (status.equals("Ended") || status.equals("ended") || status.equals("ENDED")) {
            return 0;
        }

        if (status.equals("Running") || status.equals("running") || status.equals("RUNNING")) {
            return 1;
        }

        if (status.equals("Canceled") || status.equals("canceled") || status.equals("CANCELED")) {
            return 2;
        }

        return 3;
    }

    private List<SerieDto> getListaPorTipo(String tipoLista) {
        if (tipoLista.equalsIgnoreCase("favoritos")) {
            return usuario.getFavoritos();
        }

        if (tipoLista.equalsIgnoreCase("assistidas")) {
            return usuario.getAssistidas();
        }

        if (tipoLista.equalsIgnoreCase("para assistir")) {
            return usuario.getParaAssistir();
        }

        throw new IllegalArgumentException("Lista inválida");
    }
}