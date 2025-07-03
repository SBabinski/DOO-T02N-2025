package org.example;

import org.example.dto.SerieDto;
import org.example.servico.ServicoApi;
import org.example.servico.ServicoLista;
import org.example.servico.ServicoLista;
import org.example.servico.ServicoUsuario;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ServicoUsuario usuario = ServicoUsuario.carregarDados();

        if (usuario.getNomeUsuario() == null || usuario.getNomeUsuario().isEmpty()) {
            System.out.print("Digite um nome: ");
            String nome = scanner.nextLine();
            usuario.setNomeUsuario(nome);
            usuario.salvarDados();
        }

        ServicoApi api = new ServicoApi();
        ServicoLista servicoListas = new ServicoLista(usuario);

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\nUsuário: " + usuario.getNomeUsuario());
            System.out.println("Menu:");
            System.out.println("1. Buscar série");
            System.out.println("2. Adicionar série a uma lista");
            System.out.println("3. Remover série de uma lista");
            System.out.println("4. Listar séries de uma lista");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome da série: ");
                    String nomeSerie = scanner.nextLine();
                    SerieDto serie = api.buscarSerie(nomeSerie);
                    if (serie != null) {
                        System.out.println("\nInformações da Série:");
                        System.out.println(serie);
                    } else {
                        System.out.println("Série não encontrada.");
                    }
                    break;

                case 2:
                    System.out.print("Digite o nome da série para adicionar: ");
                    nomeSerie = scanner.nextLine();
                    String listaAdicionar = escolherLista(scanner, "adicionar");
                    if (listaAdicionar != null) {
                        servicoListas.adicionarSerie(nomeSerie, api, scanner, listaAdicionar);
                        usuario.salvarDados();
                    }
                    break;

                case 3:
                    System.out.print("Digite o nome da série para remover: ");
                    String nomeRemover = scanner.nextLine();
                    String listaRemover = escolherLista(scanner, "remover");
                    if (listaRemover != null) {
                        servicoListas.removerSerie(nomeRemover, listaRemover);
                        usuario.salvarDados();
                    }
                    break;

                case 4:
                    String listaListar = escolherLista(scanner, "listar");
                    if (listaListar != null) {
                        servicoListas.listarSerie(listaListar, scanner);
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    usuario.salvarDados();
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

        scanner.close();
    }

    private static String escolherLista(Scanner scanner, String acao) {
        System.out.println("Escolha a lista para " + acao + ":");
        System.out.println("1. Favoritos");
        System.out.println("2. Assistidas");
        System.out.println("3. Para assistir");
        System.out.print("Opção: ");
        String tipoLista = null;

        try {
            int opc = Integer.parseInt(scanner.nextLine());
            if (opc == 1) {
                tipoLista = "favoritos";
            } else if (opc == 2) {
                tipoLista = "assistidas";
            } else if (opc == 3) {
                tipoLista = "para assistir";
            } else {
                System.out.println("Opção inválida!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida!");
        }

        return tipoLista;
    }
}
