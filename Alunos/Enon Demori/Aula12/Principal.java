package enon;

import java.util.Scanner;
public class Principal {
public static Scanner scan = new Scanner(System.in);

public static void consultarClima() {
    scan.nextLine();
    System.out.println("Digite o nome da cidade que deseja consultar:");
    String cidade = scan.nextLine();

    try {
        WeatherData clima = ClimaService.obterDadosClimaticos(cidade);

        System.out.printf("║  CLIMA EM %-30s \n", clima.getLocalCompleto().toUpperCase());
        System.out.println("Temperatura atual: " + clima.getTemperaturaAtual() + "°C");
        System.out.println("Máxima do dia: " + clima.getTemperaturaMaxima() + "°C");
        System.out.println("Mínima do dia: " + clima.getTemperaturaMinima() + "°C");
        System.out.println("Umidade: " + clima.getUmidade() + "%");
        System.out.println("Condição: " + clima.getCondicao());
        System.out.println("Precipitação: " + clima.getPrecipitacao() + "mm");
        System.out.println("Velocidade do vento: " + clima.getVentoVelocidade() + " km/h");
        System.out.println("Direção do vento: " + clima.getVentoDirecao());
    } catch (Exception e) {
        System.out.println("Erro ao consultar o clima: " + e.getMessage());
    }
}

    public static void main(String[] args) {
        
        
        System.out.println("Seja bem vindo ao sistema de consulta de dados meteorológicos!");
        int opcao = 0;
        do{
            System.out.println("-----------------------------------");
            System.out.println("1 - Consultar clima de uma cidade");
            System.out.println("2 - Sair");
            System.out.println("-----------------------------------");
       
            switch (opcao = scan.nextInt()) {
            
            case 1:
            consultarClima();
            break;

            case 2:
            System.out.println("Saindo do sistema. Até logo!");
            break;

            default:
            System.out.println("Opção inválida. Por favor, tente novamente.");
        }
        
        }
        while (opcao!=2);
    }
}

