package org.aplicacao;


import org.aplicacao.dto.ProcuraFilmeDto;
import org.aplicacao.dto.Usuario;
import org.aplicacao.servico.ApiServico;
import org.aplicacao.servico.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ApiServico apiServico = new ApiServico();
    static List<ProcuraFilmeDto> listaDeFavoritos = new ArrayList<>();
    static List<ProcuraFilmeDto> listaDeJaAssistidos = new ArrayList<>();
    static List<ProcuraFilmeDto> listaDeDesejosSeries = new ArrayList<>();
    static File salvarFavoritos;
    static File salvarAssistidos;
    static File salvarDesejos;

    public static void main(String[] args) throws IOException {

        File fileDiretorioSalvar = new File("ArquivosSalvarJson");
        if (!fileDiretorioSalvar.exists()){
            fileDiretorioSalvar.mkdir();
        }

        salvarFavoritos = new File(fileDiretorioSalvar,"favoritos.json");
        salvarAssistidos = new File(fileDiretorioSalvar,"assistidos.json");
        salvarDesejos = new File(fileDiretorioSalvar,"desejos.json");


        File fileArquivoUsuario = new File(fileDiretorioSalvar,"usuario.json");
        Usuario usuario;

        if (!fileArquivoUsuario.exists() || fileArquivoUsuario.length() == 0) {


                System.out.println();
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║            MUITO BEM VINDO!!           ║");
                System.out.println("╠════════════════════════════════════════╣");
                System.out.println("║      INSIRA SEU NOME DE USUÁRIO..      ║");
                System.out.println("╚════════════════════════════════════════╝");
                System.out.println();
                String nomeUsuario = sc.nextLine();

                usuario = new Usuario(nomeUsuario);
                JsonUtils.salvarUsuario(usuario, fileArquivoUsuario.getPath());
            } else {
            usuario = JsonUtils.carregarUsuario(fileArquivoUsuario.getPath());
            if (usuario != null) {
                System.out.println("╔══════════════════════════════════════════════════════╗");
                System.out.println("                 MUITO BEM VINDO " + usuario.getNome().toUpperCase());
                System.out.println("╚══════════════════════════════════════════════════════╝");
            } else {
                System.out.println();
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║                 ❌ ERRO                 ║");
                System.out.println("╠════════════════════════════════════════╣");
                System.out.println("║      ERRO AO CARREGAR O USUÁRIO..      ║");
                System.out.println("╚════════════════════════════════════════╝");
                System.out.println();
            }
            }


        try {
            listaDeFavoritos = new ArrayList<>(JsonUtils.carregarLista(salvarFavoritos.getPath()));
            listaDeJaAssistidos = new ArrayList<>(JsonUtils.carregarLista(salvarAssistidos.getPath()));
            listaDeDesejosSeries = new ArrayList<>(JsonUtils.carregarLista(salvarDesejos.getPath()));
        } catch (IOException e) {
            System.out.println();
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║                 ❌ ERRO                 ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║      ERRO AO CARREGAR AS LISTAS..      ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println();
            System.out.println(e.getMessage());
        }

        menuPrincipal();
    }


    public static void menuPrincipal() {

        boolean continuar = true;


        while (continuar) {

            System.out.println();
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("         🎬 MENU DE OPÇÕES PRINCIPAL       ");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ [1] PROCURAR SUAS SÉRIES               ║");
            System.out.println("║ [2] ADICIONAR SÉRIE A LISTA            ║");
            System.out.println("║ [3] REMOVER SÉRIE DA LISTA             ║");
            System.out.println("║ [4] EXIBIR LISTAS                      ║");
            System.out.println("║                                        ║");
            System.out.println("║ [0] FECHAR PROGRAMA                    ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println();


            int opc = lerOpcaoInt();
            sc.nextLine();


            switch (opc) {
                case 1:
                    menuDaPesquisaListaOuEspecifico();
                    break;
                case 2:
                    adicionarListSeries();
                    break;
                case 3:
                    removerListSeries();
                    break;
                case 4:
                    menuExibirList();
                    break;
                case 0:
                    System.out.println();
                    System.out.println("╔════════════════════════════════════════╗");
                    System.out.println("          ENCERRANDO O PROGRAMA...        ");
                    System.out.println("╚════════════════════════════════════════╝");
                    System.out.println();
                    System.exit(0);
                    break;
                default:
                    System.out.println();
                    System.out.println("╔════════════════════════════════════════╗");
                    System.out.println("║                 ❌ ERRO                 ║");
                    System.out.println("╠════════════════════════════════════════╣");
                    System.out.println("║    ALGO DEU ERRADO. TENTE NOVAMENTE.   ║");
                    System.out.println("╚════════════════════════════════════════╝");
                    System.out.println();
                    break;
            }
        }
    }


    public static void menuDaPesquisaListaOuEspecifico() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("            🎬 MENU DE PESQUISA            ");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ [1] BUSCAR LISTA DE SÉRIES             ║");
        System.out.println("║ [2] BUSCAR UM TÍTULO ESPECÍFICO        ║");
        System.out.println("║                                        ║");
        System.out.println("║ [0] VOLTAR AO MENU                     ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        int opc = lerOpcaoInt();
        sc.nextLine();

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("        🎬 INSIRA O NOME DA SÉRIE          ");
        System.out.println("╚════════════════════════════════════════╝");
        String nomeFilme = sc.nextLine();


        try {
            switch (opc) {
                case 1:
                    apiServico.getNomeFilme(nomeFilme);
                    break;
                case 2:
                    apiServico.getNomeFilmeEspecifico(nomeFilme);
                    break;
                case 0:
                    menuPrincipal();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void adicionarListSeries() {
        int numeroSerie;

        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("                 🎬 MENU DE LISTAS                ");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║ [1] ADICIONAR À LISTA DE FAVORITOS            ║");
        System.out.println("║ [2] ADICIONAR Á LISTA DE SÉRIES ASSISTIDAS    ║");
        System.out.println("║ [3] ADICIONAR À LISTA DE DESEJOS              ║");
        System.out.println("║                                               ║");
        System.out.println("║ [0] VOLTAR AO MENU                            ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.println();
        int opc = lerOpcaoInt();
        sc.nextLine();

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("        🎬 INSIRA O NOME DA SÉRIE          ");
        System.out.println("╚════════════════════════════════════════╝");
        String nomeFilme = sc.nextLine();

        try {

            apiServico.getNomeFilme(nomeFilme);

            switch (opc) {
                case 1:
                    System.out.println();
                    System.out.println("╔════════════════════════════════════════════════════════════════════╗");
                    System.out.println("       🎬 INSIRA UM NÚMERO DA SÉRIE PARA ADICIONAR AOS FAVORITOS!      ");
                    System.out.println("╠════════════════════════════════════════════════════════════════════╣");
                    System.out.println("║ INSIRA [-1] PARA RETORNAR AO MENU!                                 ║");
                    System.out.println("╚════════════════════════════════════════════════════════════════════╝");
                    System.out.println("\n");
                    numeroSerie = lerOpcaoInt();
                    sc.nextLine();


                    apiServico.AdicionarNaLista(numeroSerie, listaDeFavoritos);
                    JsonUtils.salvarLista(listaDeFavoritos, salvarFavoritos.getPath());
                    break;
                case 2:
                    System.out.println();
                    System.out.println("╔════════════════════════════════════════════════════════════════════╗");
                    System.out.println("     🎬 INSIRA UM NÚMERO DA SÉRIE PARA ADICIONAR AOS JÁ ASSISTIDOS!    ");
                    System.out.println("╠════════════════════════════════════════════════════════════════════╣");
                    System.out.println("║ INSIRA [-1] PARA RETORNAR AO MENU!                                 ║");
                    System.out.println("╚════════════════════════════════════════════════════════════════════╝");
                    System.out.println("\n");
                    numeroSerie = lerOpcaoInt();
                    sc.nextLine();


                    apiServico.AdicionarNaLista(numeroSerie, listaDeJaAssistidos);
                    JsonUtils.salvarLista(listaDeJaAssistidos, salvarAssistidos.getPath());
                    break;
                case 3:
                    System.out.println();
                    System.out.println("╔════════════════════════════════════════════════════════════════════╗");
                    System.out.println("            🎬 INSIRA UM NÚMERO DA SÉRIE QUE DESEJA ASSISTIR!          ");
                    System.out.println("╠════════════════════════════════════════════════════════════════════╣");
                    System.out.println("║ INSIRA [-1] PARA RETORNAR AO MENU!                                 ║");
                    System.out.println("╚════════════════════════════════════════════════════════════════════╝");
                    System.out.println("\n");
                    numeroSerie = lerOpcaoInt();
                    sc.nextLine();


                    apiServico.AdicionarNaLista(numeroSerie, listaDeDesejosSeries);
                    JsonUtils.salvarLista(listaDeDesejosSeries, salvarDesejos.getPath());
                    break;
                case 0:
                    menuPrincipal();
                    break;
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return;
    }

    public static void removerListSeries() {
        int numeroSerie;

        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("                 🎬 MENU DE LISTAS                ");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║ [1] REMOVER À LISTA DE FAVORITOS              ║");
        System.out.println("║ [2] REMOVER Á LISTA DE SÉRIES ASSISTIDAS      ║");
        System.out.println("║ [3] REMOVER À LISTA DE DESEJOS                ║");
        System.out.println("║                                               ║");
        System.out.println("║ [0] VOLTAR AO MENU                            ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.println();
        int opc = lerOpcaoInt();
        sc.nextLine();


        try {
            String name;


            switch (opc) {
                case 1:
                    name = "LISTA DE FAVORITOS";
                    apiServico.ExibirLista(listaDeFavoritos, name);

                    if (listaDeFavoritos.isEmpty()) {
                        return;
                    }
                    System.out.println();
                    System.out.println("╔════════════════════════════════════════════════════════════════════╗");
                    System.out.println("       🎬 INSIRA UM NÚMERO DA SÉRIE PARA REMOVER AOS FAVORITOS!        ");
                    System.out.println("╠════════════════════════════════════════════════════════════════════╣");
                    System.out.println("║ INSIRA [-1] PARA RETORNAR AO MENU!                                 ║");
                    System.out.println("╚════════════════════════════════════════════════════════════════════╝");
                    System.out.println("\n");
                    numeroSerie = lerOpcaoInt();
                    sc.nextLine();


                    apiServico.removerNaLista(numeroSerie, listaDeFavoritos);
                    JsonUtils.salvarLista(listaDeFavoritos, salvarFavoritos.getPath());
                    break;
                case 2:
                    name = "LISTA DE JÁ ASSISTIDOS";
                    apiServico.ExibirLista(listaDeJaAssistidos, name);

                    if (listaDeJaAssistidos.isEmpty()) {
                        return;
                    }

                    System.out.println();
                    System.out.println("╔════════════════════════════════════════════════════════════════════╗");
                    System.out.println("      🎬 INSIRA UM NÚMERO DA SÉRIE PARA REMOVER AOS JÁ ASSISTIDOS!     ");
                    System.out.println("╠════════════════════════════════════════════════════════════════════╣");
                    System.out.println("║ INSIRA [-1] PARA RETORNAR AO MENU!                                 ║");
                    System.out.println("╚════════════════════════════════════════════════════════════════════╝");
                    System.out.println("\n");
                    numeroSerie = lerOpcaoInt();
                    sc.nextLine();


                    apiServico.removerNaLista(numeroSerie, listaDeJaAssistidos);
                    JsonUtils.salvarLista(listaDeJaAssistidos, salvarAssistidos.getPath());
                    break;
                case 3:
                    name = "LISTA DE DESEJOS";
                    apiServico.ExibirLista(listaDeDesejosSeries, name);

                    if (listaDeDesejosSeries.isEmpty()) {
                        return;
                    }

                    System.out.println();
                    System.out.println("╔════════════════════════════════════════════════════════════════════╗");
                    System.out.println("      🎬 INSIRA UM NÚMERO DA SÉRIE QUE DESEJA REMOVER DO ASSISTIR!     ");
                    System.out.println("╠════════════════════════════════════════════════════════════════════╣");
                    System.out.println("║ INSIRA [-1] PARA RETORNAR AO MENU!                                 ║");
                    System.out.println("╚════════════════════════════════════════════════════════════════════╝");
                    System.out.println("\n");
                    numeroSerie = lerOpcaoInt();
                    sc.nextLine();


                    apiServico.removerNaLista(numeroSerie, listaDeDesejosSeries);
                    JsonUtils.salvarLista(listaDeDesejosSeries, salvarDesejos.getPath());
                    break;
                case 0:
                    return;
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return;
    }


    public static void menuExibirList() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("         🎬 QUAL LISTA DESEJA VER              ");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║ [1] LISTA DE FAVORITOS                     ║");
        System.out.println("║ [2] LISTA DE SÉRIES JÁ ASSISTIDAS          ║");
        System.out.println("║ [3] LISTA DE SÉRIES QUE DESEJA ASSISTIR    ║");
        System.out.println("║                                            ║");
        System.out.println("║ [0] VOLTAR AO MENU                         ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println();

        int opc = lerOpcaoInt();
        sc.nextLine();
        String name;

        switch (opc) {
            case 1:
                name = "LISTA DE FAVORITOS";
                apiServico.ExibirLista(listaDeFavoritos, name);
                break;
            case 2:
                name = "LISTA DE JÁ ASSISTIDOS";
                apiServico.ExibirLista(listaDeJaAssistidos, name);
                break;
            case 3:
                name = "LISTA DE DESEJOS";
                apiServico.ExibirLista(listaDeDesejosSeries, name);
                break;
            case 0:
                return;
        }

    }


    public static void menuOrderList(List<ProcuraFilmeDto> list) {

        while (true) {
            System.out.println();
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("                🎬 ORDENAÇÃO               ");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ [1] ORDENAR EM ORDEM ALFABÉTICA        ║");
            System.out.println("║ [2] ORDENAR EM NOTA GERAL              ║");
            System.out.println("║ [3] ORDENAR EM ESTADO DA SÉRIE         ║");
            System.out.println("║ [4] ORDENAR EM DATAS                   ║");
            System.out.println("║                                        ║");
            System.out.println("║ [0] VOLTAR AO MENU                     ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println();

            int opc = lerOpcaoInt();
            sc.nextLine();

            if (opc == 1 || opc == 2 || opc == 3 || opc == 4) {
                apiServico.ordenarLista(opc, list);
            } else if (opc == 0) {
                break;
            } else {
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║                 ❌ ERRO                 ║");
                System.out.println("╠════════════════════════════════════════╣");
                System.out.println("║    ALGO DEU ERRADO. TENTE NOVAMENTE.   ║");
                System.out.println("╚════════════════════════════════════════╝");
            }
        }
    }

    public static int lerOpcaoInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║                 ❌ ERRO                 ║");
                System.out.println("╠════════════════════════════════════════╣");
                System.out.println("║   ENTRADA INVÁLIDA! DIGITE UM NÚMERO.  ║");
                System.out.println("╚════════════════════════════════════════╝");
                System.out.println();
            }
        }
    }


}