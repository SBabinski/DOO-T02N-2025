import java.util.ArrayList;
import java.util.Scanner;

public class Calculadora_Aula04 {

    public static ArrayList<Float> quantidadePlantasVendidas = new ArrayList<>();
    public static ArrayList<Float> valorTotalVendas = new ArrayList<>(); 
    public static ArrayList<Float> descontosAplicados = new ArrayList<>();
    public static Scanner scan = new Scanner(System.in);
    public static int vendasHoje = 0;
    public static int[][] diaMes = new int[12][30];
    public static void main(String[] args) {
    
        boolean rodar = true;
        while (rodar) {
            System.out.println("""
                        Selecione entre as opções:
                        [1] - Calcular Preço Total e Troco
                        [2] - Calcular Troco Manualmente
                        [3] - Relatorio
                        [4] - Buscar vendas por dia
                        [5] - Sair
                    """);
            System.out.print("Opção?: ");
            int opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    calculadora();
                    break;
                case 2:
                    System.out.print("Digite o valor da compra: ");
                    float valorCompra = scan.nextFloat();
                    System.out.print("Digite o valor recebido do cliente: ");
                    float valorTransferencia = scan.nextFloat();
                    calculadoraTroco(valorCompra, valorTransferencia);
                    break;
                case 3:
                    Relatorio();
                    break;
                case 4:
                    buscarVendasDia();
                    break;
                case 5:
                    rodar = false;
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    public static void calculadora() {

        System.out.println("Digite o dia de hoje: ");
        int dia = scan.nextInt();
        System.out.println("Digite o mês atual: ");
        int mes = scan.nextInt();

        System.out.print("Digite a quantidade de plantas: ");
        Float quantidadePlantas = scan.nextFloat();
        quantidadePlantasVendidas.add(quantidadePlantas);

        System.out.println("Digite o valor da planta: ");
        float valorPlanta = scan.nextFloat();

        if(quantidadePlantas > 10 ) {
            System.out.println("Você está comprando mais de 10 plantas, o valor da plantas");
            System.out.println("Ganhou 5% de desconto: ");
            valorPlanta = valorPlanta * 0.95f;
            descontosAplicados.add(valorPlanta);
        }else {
            descontosAplicados.add(0f);// coloquei para quando a compra não ter desconto, armazenar o valor 0 na lista...
        }
        float valorCompra = valorPlanta * quantidadePlantas;
        valorTotalVendas.add(valorCompra);

        System.out.println("Valor da compra: " + valorCompra + " REAIS");
        
        System.out.print("Valor recebido do cliente: ");
        float valorTransferencia = scan.nextFloat();

        calculadoraTroco(valorCompra, valorTransferencia);
        
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 30; j++) {
                if((i == mes)&&(j == dia)){
                    vendasHoje++;
                    diaMes[i][j] = vendasHoje; 
                }
            }
        }

    }

    public static void calculadoraTroco(float valorCompra, float valorTransferencia) {
        float valorTroco = valorTransferencia - valorCompra;

        if (valorTroco < 0) {
            System.out.println("O cliente ainda deve:" + valorTroco);
        } else {
            System.out.println("O troco do cliente é:" + valorTroco);
        }
    }

    public static void Relatorio(){
        System.out.println("Relatório de vendas:");
        for (int i = 0; i < quantidadePlantasVendidas.size(); i++) {
            System.out.print("Venda " + (i + 1) + ": " + quantidadePlantasVendidas.get(i) + " plantas vendidas ");
            System.out.print(" Valor: R$ " + valorTotalVendas.get(i));
    
            if (descontosAplicados.get(i) == 0) {
                System.out.println(" Compra sem desconto");
            } else {
                System.out.println(" Desconto aplicado: R$ " + descontosAplicados.get(i));
            }
        }
    }

    public static void buscarVendasDia(){
        System.out.println("Relatório de vendas por dia:");
        System.out.println();
        System.out.println("digite o dia que deseja verificar: ");
        int dia = scan.nextInt();
        System.out.println("digite o mês que deseja verificar: ");
        int mes = scan.nextInt();

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 30; j++) {
                if ((i == mes) && (j  == dia)) {
                    System.out.println("Vendas no dia " + dia + "/" + mes + ": " + diaMes[i][j]);
                }
                
            }
        }
    }

}