package org.aplicacao;

import org.aplicacao.servico.ApiServico;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static ApiServico apiServico = new ApiServico();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        File envFile = new File(".env");
        if (!envFile.exists()) {
            envFile.createNewFile();
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("        INSIRA SUA API_KEY NO AQUI:       ");
            System.out.println("╚════════════════════════════════════════╝");
            String chave_API = sc.nextLine();


            try (FileWriter writer = new FileWriter(envFile)) {
                writer.write("API_KEY=" + chave_API);
            }


            apiServico.setAPI_KEY(chave_API);

        }



        if (envFile.exists()){
            try (Scanner fileScanner = new Scanner(envFile)){
                while (fileScanner.hasNextLine()) {
                    String linha = fileScanner.nextLine();
                    if (linha.startsWith("API_KEY=")) {
                        String chave_API = linha.replace("API_KEY=", "").trim();
                        apiServico.setAPI_KEY(chave_API);
                        break;
                    }
                }
            }
        }


        testeApiKey();

        menu();

    }

    static void menu() {
        while (true) {
            System.out.println();
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("          MENU DE OPÇÕES PRINCIPAL        ");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ [1] VER CLIMA TEMPO DA SUA CIDADE      ║");
            System.out.println("║                                        ║");
            System.out.println("║ [0] FECHAR O PROGRAMA                  ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println();

            int opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    inserirCEP();
                    break;
                case 0:
                    System.exit(0);
            }
        }


    }

    static void inserirCEP() {
        try {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("         INSIRA O NOME DA CIDADE          ");
            System.out.println("╚════════════════════════════════════════╝");
            String nomeCEP = sc.nextLine();

            apiServico.getLocation(nomeCEP);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static void testeApiKey() {
        int status = apiServico.apiTestResponse();

        if (status == 200) {
            System.out.println();
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("       ✅ API RESPONDIDA COM SUCESSO       ");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("╔═══════════════════════════════════════════════════════╗");
            System.out.println("║        ❌ ERRO API NÃO RESPONDEU CORRETAMENTE          ║");
            System.out.println("╠═══════════════════════════════════════════════════════╣");
            System.out.println("║           \uD83D\uDD11  INSIRA SUA API_KEY NO AQUI:             ║");
            System.out.println("╚═══════════════════════════════════════════════════════╝");
            System.out.println();
            String chave_API = sc.nextLine();



            apiServico.setAPI_KEY(chave_API);
            testeApiKey();
            try {
                FileWriter writer = new FileWriter(".env");
                writer.write("API_KEY=" + chave_API);
                writer.close();
            } catch (IOException e) {
                System.out.println("Erro ao salvar a API_KEY no arquivo .env");
            }


        }
    }
}


