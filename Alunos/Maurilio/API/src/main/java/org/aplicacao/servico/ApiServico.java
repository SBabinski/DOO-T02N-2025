package org.aplicacao.servico;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aplicacao.Main;
import org.aplicacao.dto.OrdemList;
import org.aplicacao.dto.ProcuraFilmeDto;
import org.aplicacao.dto.ShowWrapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ApiServico {
    Scanner sc = new Scanner(System.in);
    Main main = new Main();

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    List<ProcuraFilmeDto> listaFilme = new ArrayList<>();
    List<ProcuraFilmeDto> listaFilmeEspecifico = new ArrayList<>();

    public List<ProcuraFilmeDto> getNomeFilme(String name) throws IOException, InterruptedException {

        String filmeEncoded = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());

        try {

            String url = "https://api.tvmaze.com/search/shows?q=" + filmeEncoded;
            HttpResponse<String> response = response(url);


            if (response.statusCode() == 200) {
                List<ShowWrapper> showWrappers = objectMapper.readValue(response.body(), new TypeReference<List<ShowWrapper>>() {
                });

                int i = 0;
                for (ShowWrapper wrapper : showWrappers) {
                    i++;
                    ProcuraFilmeDto filme = wrapper.getShow();
                    listaFilme.add(filme);
                    imprimeFilme(filme, i);
                }


            } else {
                System.out.println("Erro na resposta da API: " + response.statusCode());
            }
            if (listaFilme.isEmpty()) {
                System.out.println("\n------Nenhuma série ou filme encontrado!------\n");
                return listaFilme;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        while (true) {
            System.out.println();
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║         DESEJA ORDENAR A LISTA?        ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  [1] SIM                               ║");
            System.out.println("║  [2] NÃO                               ║");
            System.out.println("╚════════════════════════════════════════╝");
            int opc = sc.nextInt();
            sc.nextLine();

            if (opc == 1) {
                Main.menuOrderList(listaFilme);
                break;
            } else if (opc == 2) {
                break;
            } else {
                System.out.println();
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║                 ❌ ERRO                 ║");
                System.out.println("╠════════════════════════════════════════╣");
                System.out.println("║         INSIRA UM NÚMERO VÁLIDO.       ║");
                System.out.println("╚════════════════════════════════════════╝");
                System.out.println();
            }
        }
        return listaFilme;
    }


    public List<ProcuraFilmeDto> AdicionarNaLista(int opc, List<ProcuraFilmeDto> list) {

        if (listaFilme.isEmpty()) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║                 ❌ ERRO                 ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║               LISTA VAZIA.             ║");
            System.out.println("╚════════════════════════════════════════╝");
            return list;
        }

        if (opc == -1) {
            System.out.println("╔═════════════════════════════════════════════════════════╗");
            System.out.println("║                      VOLTANDO AO MENU!                  ║");
            System.out.println("╚═════════════════════════════════════════════════════════╝");
            return list;
        }

        if (opc > 0 && opc <= listaFilme.size()) {
            ProcuraFilmeDto selecionado = listaFilme.get(opc - 1);
            if (!list.contains(selecionado)) {
                list.add(selecionado);
                System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("        ✔ ADICIONADO: " + selecionado.getName() + "  ID:" + selecionado.getId() + " COM SUCESSO.         ");
                System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
            } else {
                System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                           ⚠ JÁ ESTÁ NA LISTA.                        ║");
                System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
            }
        } else {
            System.out.println("╔═════════════════════════════════════════════════════════╗");
            System.out.println("║                      ❌ OPÇÃO INVALIDA!                  ║");
            System.out.println("╚═════════════════════════════════════════════════════════╝");
        }
        return list;
    }

    public List<ProcuraFilmeDto> removerNaLista(int opc, List<ProcuraFilmeDto> list) {



        if (list.isEmpty()) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║                 ❌ ERRO                 ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║               LISTA VAZIA.             ║");
            System.out.println("╚════════════════════════════════════════╝");
            return list;
        }

        if (opc == -1) {
            System.out.println("╔═════════════════════════════════════════════════════════╗");
            System.out.println("║                      VOLTANDO AO MENU!                  ║");
            System.out.println("╚═════════════════════════════════════════════════════════╝");
            return list;
        }

        if (opc > 0 && opc <= list.size()) {
            ProcuraFilmeDto selecionado = list.get(opc - 1);
            if (list.contains(selecionado)) {
                list.remove(selecionado);
                System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("        ✔ REMOVIDO: " + selecionado.getName() + "  ID:" + selecionado.getId() + " COM SUCESSO.         ");
                System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
            } else {
                System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
                System.out.println("          ⚠ NÃO EXISTE " + selecionado.getName() + " NA LISTA.         ");
                System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
            }
        } else {
            System.out.println("╔═════════════════════════════════════════════════════════╗");
            System.out.println("║                      ❌ OPÇÃO INVALIDA!                  ║");
            System.out.println("╚═════════════════════════════════════════════════════════╝");
        }
        return list;
    }

    public void ExibirLista(List<ProcuraFilmeDto> list, String name) {
        if (list.isEmpty()){
            System.out.println();
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║                 ❌ ERRO ❌               ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║               LISTA VAZIA.             ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println();
            return;
        }else {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("       ✔ EXIBINDO " + name);
            System.out.println("╚═══════════════════════════════════════╝");
            int i = 1;
            for (ProcuraFilmeDto l : list) {
                imprimeFilme(l, i++);
            }
        }
    }

    public List<ProcuraFilmeDto> getNomeFilmeEspecifico(String name) throws IOException, InterruptedException {
        String filmeEncoded = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());

        try {
            String url = "https://api.tvmaze.com/singlesearch/shows?q=" + filmeEncoded;
            HttpResponse<String> response = response(url);


            if (response.statusCode() == 200) {
                ProcuraFilmeDto filme = objectMapper.readValue(response.body(), ProcuraFilmeDto.class);
                listaFilmeEspecifico.add(filme);
                imprimeFilme(filme, 1);

            } else {
                System.out.println("Erro na resposta da API: " + response.statusCode());
            }

            if (listaFilmeEspecifico.isEmpty()) {
                System.out.println("\n------Nenhuma série ou filme encontrado!------\n");
                return listaFilmeEspecifico;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaFilmeEspecifico;
    }


//    REFATORAÇÃO DO MEU CÓDIGO

    private HttpResponse<String> response(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }


    private void imprimeFilme(ProcuraFilmeDto filme, int i) {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("               🎬 DETALHES DO FILME: " + filme.getName());
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════╝");


        if (filme.getImage() != null && filme.getImage().getMedium() != null && !filme.getImage().getMedium().isEmpty()) {
        String numeroimagem = String.valueOf(filme.getImage().getMedium());
        int tamanhoimagem = " Imagem: ".length() + numeroimagem.length();
        System.out.print("╔");
        for (int j = 0; j < tamanhoimagem; j++) System.out.print("═");
        System.out.println("╗");
            System.out.println(" Imagem: " + filme.getImage().getMedium());
        }


        String numero = String.valueOf(i);
        int largura = numero.length();


        System.out.print("╔");
        for (int j = 0; j < largura; j++) System.out.print("═");
        System.out.println("╗");
        System.out.printf("║" + i + "║");


        System.out.printf("[Id: " + filme.getId() + "] / ");
        System.out.printf("[Nome: " + filme.getName() + "] / ");
        System.out.printf("[Linguagem: " + filme.getLanguage() + "] / ");

        System.out.print("[Gênero: ");
        if (filme.getGenres() != null && !filme.getGenres().isEmpty()) {
            System.out.printf(String.join(", ", filme.getGenres()) + "] / ");
        } else {
            System.out.print("N/A!] / ");
        }

        System.out.print("[Nota geral: ");
        if (filme.getRating().getAverage() != null) {
            System.out.print(filme.getRating().getAverage() + "] / ");
        } else {
            System.out.print("N/A!] / ");
        }

        System.out.printf("[Status: " + filme.getStatus() + "] / ");
        System.out.print("[Data de estreia: " + filme.getPremiered() + "] / ");
        System.out.print("[Data de término: " + filme.getEnded() + "] / ");

        System.out.print("[Site oficial: ");
        if (filme.getOfficialSite() != null) {
            System.out.print(filme.getOfficialSite() + "] / ");
        } else {
            System.out.print("N/A!] / ");
        }


        System.out.print("[Nome da emissora que transmitiu: ");
        if (filme.getNetwork() != null) {
            System.out.printf(filme.getNetwork().getNome() + "] / ");
        } else {
            System.out.print("N/A!] / ");
        }
        System.out.print("[País: ");
        if (filme.getNetwork() != null && filme.getNetwork().getCountry() != null) {
            System.out.println(filme.getNetwork().getCountry().getNome() + "] / ");
        } else {
            System.out.println("N/A!] / ");
        }

        System.out.printf("╚");
        for (int j = 0; j < largura; j++) System.out.print("═");
        System.out.println("╝");


        System.out.print(" Resumo: ");
        if (filme.getSummary() != null) {
            System.out.println(filme.getSummary());
        } else {
            System.out.println("N/A!");
        }
        String numerosumario = String.valueOf(filme.getSummary());
        int tamanhosumario = " Resumo: ".length() + numerosumario.length();

        System.out.print("╚");
        for (int j = 0; j < tamanhosumario; j++) System.out.print("═");
        System.out.print("╝");
    }

    public int ordenarLista(int opc, List<ProcuraFilmeDto> list) {
        if (listaFilme.isEmpty()) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("                LISTA VAZIA..             ");
            System.out.println("╚════════════════════════════════════════╝");
            return 0;

        }
        switch (opc) {
            case 1:
                clearConsole();
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("         LISTA EM ORDEM ALFABÉTICA        ");
                System.out.println("╚════════════════════════════════════════╝");
                Collections.sort(listaFilme, OrdemList.compareAlfabeto());
                break;
            case 2:
                clearConsole();
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("       LISTA EM ORDEM DE NOTA GERAL       ");
                System.out.println("╚════════════════════════════════════════╝");
                Collections.sort(listaFilme, OrdemList.compareNotaGeral());
                break;
            case 3:
                clearConsole();
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("              LISTA EM STATUS             ");
                System.out.println("╚════════════════════════════════════════╝");
                Collections.sort(listaFilme, OrdemList.compareEstadoSerie());
                break;
            case 4:
                clearConsole();
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("              LISTA EM DATAS              ");
                System.out.println("║                                        ║");
                System.out.println("║ [1] COMPARAR DATA DE ESTREIA DA SÉRIE  ║");
                System.out.println("║ [2] COMPARAR DATA DE TÉRMINO DA SÉRIE  ║");
                System.out.println("╚════════════════════════════════════════╝");
                opc = sc.nextInt();
                sc.nextLine();
                if (opc == 1){
                    System.out.println("╔════════════════════════════════════════╗");
                    System.out.println("              LISTA EM DATAS              ");
                    System.out.println("║                                        ║");
                    System.out.println("║ [1] COMPARAR DATA ESTREIA DECRESCENTE  ║");
                    System.out.println("║ [2] COMPARAR DATA ESTREIA CRESCENTE    ║");
                    System.out.println("╚════════════════════════════════════════╝");
                    opc = sc.nextInt();
                    sc.nextLine();
                    Collections.sort(listaFilme, OrdemList.compareDateEstreiaSerie(opc));
                    break;
                }
                if (opc == 2){
                    System.out.println("╔════════════════════════════════════════╗");
                    System.out.println("              LISTA EM DATAS              ");
                    System.out.println("║                                        ║");
                    System.out.println("║ [1] COMPARAR DATA TÉRMINO DECRESCENTE  ║");
                    System.out.println("║ [2] COMPARAR DATA TÉRMINO CRESCENTE    ║");
                    System.out.println("╚════════════════════════════════════════╝");
                    opc = sc.nextInt();
                    sc.nextLine();
                    Collections.sort(listaFilme, OrdemList.compareDateTerminoSerie(opc));
                    break;
                }
        }

        int i = 0;
        for (ProcuraFilmeDto filmeDto : listaFilme) {
            i++;
            imprimeFilme(filmeDto, i);
        }
        return opc;
    }


    public final static void clearConsole() {
        for (int i = 0; i <= 50; i++) {
            System.out.println();
        }
    }


}



