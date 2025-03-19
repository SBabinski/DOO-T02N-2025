import java.util.Scanner;

public class calculadora {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int op = 0; 
        int[][] meses = new int[12][];
        float[][] totalVendido = new float[12][];
        float[][] totalDesconto = new float[12][];
        float total = 0;

        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int i = 0; i < 12; i++) {
            meses[i] = new int[diasPorMes[i]];
            totalVendido[i] = new float[diasPorMes[i]];
            totalDesconto[i] = new float[diasPorMes[i]];
        }


        while (op != 4) {     
            System.out.println("\n\n[Menu]\n[1] Calcular o preço total\n[2] Calcular troco\n[3] Relatório de vendas\n[4] Sair");
            op = scanner.nextInt();

            switch (op) {
                case 1:
                    total = calcularPrecoTotal(scanner, meses, totalDesconto, totalVendido);
                    break;
                case 2:
                    calcularTroco(scanner, total);
                    break;
                case 3:
                    visualizarRelatorio(scanner, meses, totalDesconto, totalVendido);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

    public static float calcularPrecoTotal(Scanner scanner, int[][] meses, float totalVendido[][], float totalDesconto[][]) {
        System.out.println("Insira o valor da planta: ");
        float valorP = scanner.nextFloat();

        System.out.println("Insira a quantidade de plantas: ");
        int quantP = scanner.nextInt();

        System.out.println("Insira o mês (1-12): ");
        int mes = scanner.nextInt() - 1;

        if (mes < 0 || mes >= 12){
            System.out.println("Mês inválido");
            return 0;
        }

        System.out.println("Insira o dia: ");
        int dia = scanner.nextInt() - 1;

        if (dia < 0 || dia >= meses[mes].length){
            System.out.println("Dia inválido");
            return 0;
        }

        meses[mes][dia] += quantP;

        float total = quantP * valorP; float desconto = 0;

        if (quantP >= 10){
            desconto = total * 0.05f;
            total -= desconto;
            System.out.println("\nCliente recebeu um desconto de 5%");
        } 
        
        System.out.printf("\nO total é de: %.2f", total);          
    
        totalVendido[mes][dia] += total;
        totalDesconto[mes][dia] += desconto;
      
        return total;
    }

    public static void calcularTroco(Scanner scanner, float total) {
        System.out.println("Insira o dinheiro dado pelo cliente: ");
        float dinheiroC = scanner.nextFloat();

        if (dinheiroC < total){
            System.out.printf("\nCliente está devendo: R$%.2f", dinheiroC - total);
        }
        
        else{
            System.out.printf("\nO troco do cliente deve ser de: %.2f\n", dinheiroC - total);
        }
    }

    public static void visualizarRelatorio(Scanner scanner, int meses[][], float totalVendido[][], float totalDesconto[][]) {

        System.out.println("\nRelatório de vendas");
        System.out.println("[1] Buscar venda por dia");
        System.out.println("[2] Buscar venda por mês");
        int op = scanner.nextInt();

        if (op == 1){
            System.out.println("Insira o mês (1-12): ");
            int mes = scanner.nextInt() - 1;

            if (mes < 0 || mes >= 12){
                System.out.println("Mês inválido");
                return;
            }

            System.out.println("Insira o dia: ");
            int dia = scanner.nextInt() - 1;

            if (dia < 0 || dia >= meses[mes].length){
                System.out.println("Dia inválido");
                return;
            }

            System.out.printf("\nQuantidade de plantas vendidas em %d/%d: %d vendas", dia + 1, mes + 1, meses[mes][dia]);
            System.out.printf("\nTotal vendido: R$%.2f", totalVendido[mes][dia]);
            System.out.printf("\nTotal de desconto concedidos: R$%.2f", totalDesconto[mes][dia]);
        }

        if (op == 2){
            System.out.println("Insira o mês (1-12): ");
            int mes = scanner.nextInt() - 1;

            if (mes < 0 || mes >= 12){
                System.out.println("Mês inválido");
                return;
            }

            int totalPlantasMes = 0;
            float totalReaisMes = 0, totalDescontoMes = 0;

            for (int i = 0; i < meses[mes].length; i++) {
                totalPlantasMes += meses[mes][i];
                totalReaisMes += totalVendido[mes][i];
                totalDescontoMes += totalDesconto[mes][i];
            }

            System.out.printf("\nQuantidade de plantas vendidas no mês %d: %d vendas", mes + 1, totalPlantasMes);
            System.out.printf("\nTotal vendido no mês: R$%.2f", totalReaisMes);
            System.out.printf("\nTotal de desconto concedidos no mês: R$%.2f", totalDescontoMes);
        }
    }
}
