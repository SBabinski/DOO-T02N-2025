import java.util.ArrayList;
import java.util.Scanner;

public class CalculadoraV2 {
    private static Scanner scan = new Scanner(System.in);

    private static ArrayList<String> datasVendas = new ArrayList<>();
    private static ArrayList<Double> valoresVendas = new ArrayList<>();

    public static void main(String[] args) {
        exibirMenu();
        scan.close();
    }

    public static void exibirMenu() {
        int opcao;
        double total = 0;

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Registrar Venda");
            System.out.println("[4] - Consultar Vendas do Dia");
            System.out.println("[5] - Sair");
            System.out.print("Escolha a opção: ");
            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    total = calcularPrecoTotal();
                    break;
                case 2:
                    calcularTroco(total);
                    break;
                case 3:
                    registrarVenda();
                    break;
                case 4:
                    consultarVendas();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public static double calcularPrecoTotal() {
        System.out.print("Qual o valor unitário das plantas? ");
        double valor = scan.nextDouble();  

        System.out.print("Qual a quantidade comprada? ");
        int qtd = scan.nextInt();

        double desconto = (qtd >= 10) ? 0.1 : 0.0;  
        double total = valor * qtd * (1 - desconto);

        System.out.printf("O valor total foi de R$%.2f (Desconto aplicado: %.0f%%)\n", total, desconto * 100);

        return total;
    }

    public static void calcularTroco(double total) {
        if (total == 0) {
            System.out.println("Nenhuma compra foi feita ainda.");
            return;
        }

        System.out.print("Qual o valor que o cliente deu? ");
        double cliente = scan.nextDouble();

        double troco = cliente - total;
        if (troco > 0) {
            System.out.printf("O troco foi de R$%.2f\n", troco);
        } else if (troco == 0) {
            System.out.println("O valor entregue foi exato ao valor total da compra.");
        } else {
            System.out.println("Caloteiro! Ainda falta R$" + Math.abs(troco));
        }
    }

    public static void registrarVenda() {
        System.out.print("Informe o mês da venda (1-12): ");
        int mes = scan.nextInt();
        System.out.print("Informe o dia da venda (1-31): ");
        int dia = scan.nextInt();

        String data = String.format("%02d-%02d", mes, dia); 
        
        System.out.print("Qual o valor unitário das plantas? ");
        double valor = scan.nextDouble();  

        System.out.print("Qual a quantidade comprada? ");
        int qtd = scan.nextInt();

        double desconto = (qtd >= 10) ? 0.1 : 0.0;  
        double total = valor * qtd * (1 - desconto);

        datasVendas.add(data);
        valoresVendas.add(total);

        System.out.printf("Venda registrada para %s. Valor total: R$%.2f (Desconto: %.0f%%)\n", data, total, desconto * 100);
    }

    public static void consultarVendas() {
        System.out.print("Informe o mês da consulta (1-12): ");
        int mes = scan.nextInt();
        System.out.print("Informe o dia da consulta (1-31): ");
        int dia = scan.nextInt();

        String data = String.format("%02d-%02d", mes, dia); 

        double totalDia = 0;
        for (int i = 0; i < datasVendas.size(); i++) {
            if (datasVendas.get(i).equals(data)) {
                totalDia += valoresVendas.get(i);
            }
        }

        if (totalDia > 0) {
            System.out.printf("Vendas de %s: R$%.2f\n", data, totalDia);
        } else {
            System.out.println("Nenhuma venda registrada para a data informada.");
        }
    }
}
import java.util.ArrayList;
import java.util.Scanner;

public class CalculadoraV2 {
    private static Scanner scan = new Scanner(System.in);

    private static ArrayList<String> datasVendas = new ArrayList<>();
    private static ArrayList<Double> valoresVendas = new ArrayList<>();

    public static void main(String[] args) {
        exibirMenu();
        scan.close();
    }

    public static void exibirMenu() {
        int opcao;
        double total = 0;

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Registrar Venda");
            System.out.println("[4] - Consultar Vendas do Dia");
            System.out.println("[5] - Sair");
            System.out.print("Escolha a opção: ");
            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    total = calcularPrecoTotal();
                    break;
                case 2:
                    calcularTroco(total);
                    break;
                case 3:
                    registrarVenda();
                    break;
                case 4:
                    consultarVendas();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public static double calcularPrecoTotal() {
        System.out.print("Qual o valor unitário das plantas? ");
        double valor = scan.nextDouble();  

        System.out.print("Qual a quantidade comprada? ");
        int qtd = scan.nextInt();

        double desconto = (qtd >= 10) ? 0.1 : 0.0;  
        double total = valor * qtd * (1 - desconto);

        System.out.printf("O valor total foi de R$%.2f (Desconto aplicado: %.0f%%)\n", total, desconto * 100);

        return total;
    }

    public static void calcularTroco(double total) {
        if (total == 0) {
            System.out.println("Nenhuma compra foi feita ainda.");
            return;
        }

        System.out.print("Qual o valor que o cliente deu? ");
        double cliente = scan.nextDouble();

        double troco = cliente - total;
        if (troco > 0) {
            System.out.printf("O troco foi de R$%.2f\n", troco);
        } else if (troco == 0) {
            System.out.println("O valor entregue foi exato ao valor total da compra.");
        } else {
            System.out.println("Caloteiro! Ainda falta R$" + Math.abs(troco));
        }
    }

    public static void registrarVenda() {
        System.out.print("Informe o mês da venda (1-12): ");
        int mes = scan.nextInt();
        System.out.print("Informe o dia da venda (1-31): ");
        int dia = scan.nextInt();

        String data = String.format("%02d-%02d", mes, dia); 
        
        System.out.print("Qual o valor unitário das plantas? ");
        double valor = scan.nextDouble();  

        System.out.print("Qual a quantidade comprada? ");
        int qtd = scan.nextInt();

        double desconto = (qtd >= 10) ? 0.1 : 0.0;  
        double total = valor * qtd * (1 - desconto);

        datasVendas.add(data);
        valoresVendas.add(total);

        System.out.printf("Venda registrada para %s. Valor total: R$%.2f (Desconto: %.0f%%)\n", data, total, desconto * 100);
    }

    public static void consultarVendas() {
        System.out.print("Informe o mês da consulta (1-12): ");
        int mes = scan.nextInt();
        System.out.print("Informe o dia da consulta (1-31): ");
        int dia = scan.nextInt();

        String data = String.format("%02d-%02d", mes, dia); 

        double totalDia = 0;
        for (int i = 0; i < datasVendas.size(); i++) {
            if (datasVendas.get(i).equals(data)) {
                totalDia += valoresVendas.get(i);
            }
        }

        if (totalDia > 0) {
            System.out.printf("Vendas de %s: R$%.2f\n", data, totalDia);
        } else {
            System.out.println("Nenhuma venda registrada para a data informada.");
        }
    }
}
