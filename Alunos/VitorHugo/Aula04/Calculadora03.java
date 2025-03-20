import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculadora03 {
    private static Map<String, Integer> vendasDiarias = new HashMap<>();
    private static Map<String, Double> valoresDiarios = new HashMap<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        
        do {
            System.out.println("Menu:");
            System.out.println("1. Registrar venda");
            System.out.println("2. Consultar vendas por dia e mês");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    registrarVenda(scanner);
                    break;
                case 2:
                    consultarVendas(scanner);
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 3);
        
        scanner.close();
    }
    
    private static void registrarVenda(Scanner scanner) {
        System.out.print("Informe o dia da venda: ");
        int dia = scanner.nextInt();
        System.out.print("Informe o mês da venda: ");
        int mes = scanner.nextInt();
        System.out.print("Informe a quantidade de plantas vendidas: ");
        int quantidade = scanner.nextInt();
        System.out.print("Informe o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();
        
        String chave = dia + "/" + mes;
        vendasDiarias.put(chave, vendasDiarias.getOrDefault(chave, 0) + quantidade);
        valoresDiarios.put(chave, valoresDiarios.getOrDefault(chave, 0.0) + (quantidade * precoUnitario));
        
        System.out.println("Venda registrada com sucesso!");
    }
    
    private static void consultarVendas(Scanner scanner) {
        System.out.print("Informe o dia da consulta: ");
        int dia = scanner.nextInt();
        System.out.print("Informe o mês da consulta: ");
        int mes = scanner.nextInt();
        
        String chave = dia + "/" + mes;
        int totalVendas = vendasDiarias.getOrDefault(chave, 0);
        double valorTotal = valoresDiarios.getOrDefault(chave, 0.0);
        
        System.out.println("Total de vendas no dia " + chave + ": " + totalVendas + " plantas");
        System.out.println("Valor total arrecadado: R$ " + valorTotal);
    }
}
