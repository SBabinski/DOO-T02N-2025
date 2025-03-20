package calcVetor;
import java.util.Scanner;

public class AddVetor {

    public static void main(String[] args) {
        calculadoraMelhor();
    }

    public static void calculadoraMelhor() {
        Scanner scanner = new Scanner(System.in);
        
        
        int[] quantidades = new int[100]; 
        double[] totais = new double[100]; 
        double[] descontos = new double[100]; 
        int[] dias = new int[100]; 
        int[] meses = new int[100]; 
        int contador = 0; 

        while (true) {
            try {
                System.out.println("\nCalculadora da Loja de Plantas:");
                System.out.println("1- Calcular Venda");
                System.out.println("2- Calcular Troco");
                System.out.println("3- Ver Registro de Vendas");
                System.out.println("4- Sair");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();

                if (opcao == 1) {
                    System.out.print("Mês (1-12): ");
                    int mes = scanner.nextInt(); 
                    System.out.print("Dia (1-31): ");
                    int dia = scanner.nextInt(); 
                    System.out.print("Quantidade de plantas: ");
                    int quantidade = scanner.nextInt();
                    System.out.print("Preço unitário: ");
                    double preco = scanner.nextDouble();

                    double total = quantidade * preco;
                    double desconto = 0;

                    if (quantidade > 10) {
                        desconto = total * 0.05;
                        total -= desconto;
                        System.out.printf("Desconto de 5%% aplicado: R$ %.2f\n", desconto);
                    }

                    System.out.printf("Preço total: R$ %.2f\n", total);

                   
                    quantidades[contador] = quantidade;
                    totais[contador] = total;
                    descontos[contador] = desconto;
                    dias[contador] = dia;  
                    meses[contador] = mes; 
                    contador++;

                } else if (opcao == 2) {
                    System.out.print("Valor recebido: ");
                    double recebido = scanner.nextDouble();
                    System.out.print("Valor total da compra: ");
                    double total = scanner.nextDouble();

                    if (recebido < total) {
                        System.out.println("Valor informado é insuficiente");
                    } else {
                        System.out.printf("Troco: R$ %.2f\n", recebido - total);
                    }
                } else if (opcao == 3) {
                    System.out.println("\nRegistro de Vendas:");
                    if (contador == 0) {
                        System.out.println("Sem registros.");
                    } else {
                        for (int i = 0; i < contador; i++) {
                            System.out.printf("Venda %d - Mês: %d, Dia: %d - Quantidade: %d  Total: R$ %.2f  Desconto: R$ %.2f\n",
                                    i + 1, meses[i], dias[i], quantidades[i], totais[i], descontos[i]);
                        }
                    }
                } else if (opcao == 4) {
                    System.out.println("Saindo.");
                    break;
                } else {
                    System.out.println("Opção inválida, tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida, tente novamente.");
                scanner.nextLine(); 
            }
        }
        scanner.close();
    }
}