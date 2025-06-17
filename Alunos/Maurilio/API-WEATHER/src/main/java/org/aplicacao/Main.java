package org.aplicacao;

import org.aplicacao.dto.WeatherDto;
import org.aplicacao.servico.ApiServico;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static ApiServico apiServico = new ApiServico();
    static WeatherDto weatherDto = new WeatherDto();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        menu();

    }

    static void menu(){
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

            switch (opc){
                case 1:
                    inserirCEP();
                    break;
                case 0:
                    System.exit(0);
            }
        }


    }

    static void inserirCEP(){
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

}


