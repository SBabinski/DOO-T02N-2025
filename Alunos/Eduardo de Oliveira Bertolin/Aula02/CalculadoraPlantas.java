import java.util.Scanner;
public class CalculadoraPlantas {

    public static double calcularPrecoTotal(int quantidade, double precoUnitario){
    return (quantidade * precoUnitario);
    }

    public static double calcularTroco (double valorPago, double totalCompra){
    return (valorPago-totalCompra);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        
        do {
                System.out.println("--- Calculadora da Dona Gabrielinha ---");
                System.out.println("[1] - Calcular Preço Total");
                System.out.println("[2] - Calcular Troco");
                System.out.println("[3] - Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                
                switch(opcao){
                    case 1:
                        System.out.print("Informe a quantidade de plantas: ");
                        int quantidade = scanner.nextInt();
                        System.out.print("Informe o preço unitário da planta: ");
                        double precoUnitario = scanner.nextDouble();
                        
                        double total = calcularPrecoTotal (quantidade, precoUnitario);
                        System.out.println("O preço total da compra é: R$ " + total);
                        break;
                        
                    case 2:
                        System.out.print("Informe o valor pago pelo cliente: ");
                        double valorPago = scanner.nextDouble();
                        System.out.print("Informe o valor total da compra: ");
                        double totalCompra = scanner.nextDouble();
                        
                        double troco = calcularTroco (valorPago, totalCompra);
                        System.out.println("O troco a ser dado ao cliente é de: R$ " + troco);
                        break;
                        
                    case 3:
                        System.out.println("Saindo da calculadora, até mais Dona Gabrielinha!");
                        break;
                        
                    default:
                        System.out.println("Opção inválida! Tente novamente:");
                }
            } while (opcao != 3);
        scanner.close();
    }
}
