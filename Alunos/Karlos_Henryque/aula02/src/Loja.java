import java.util.Scanner;

public class Loja {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcao = 0;

        while (opcao != 3) {
            System.out
                    .println("Sejá Bem Vindo a Loja da Dona Gabriela, aqui trabalhamos com plantas de varios modelos!");
            System.out.println("Poderia escolher uma das opções a baixo:");
            System.out.println("1 - Calcular preço total:");
            System.out.println("2 - Calcular troco:");
            System.out.println("3 - Sair do sistema:");
            opcao = scanner.nextInt();
            System.out.println("");

            switch (opcao) {
                case 1:
                    System.out.println("Digite a quantidade de plantas que deseja levar:");
                    int qtd = scanner.nextInt();
                    System.out.println("Digite o valor unitário das plantas R$:");
                    double unitario = scanner.nextDouble();

                    double precototal = Calculadora.precototal(qtd, unitario);
                    System.out.println("O preço final da compra ficou o valor de R$:" + precototal + " reais \n");
                    break;

                case 2:
                    System.out.println("Qual seriá o valor que o cliente te entregou:");
                    double valor = scanner.nextDouble();
                    System.out.println("Qual foi o valor final da compra do cliente");
                    double total = scanner.nextDouble();

                    double trocoFinal = Calculadora.troco(valor, total);
                    System.out.println("O troco que será entregue para o cliente e de R$: " + trocoFinal + " reais \n");
                    break;

                case 3:
                    System.out.println("Obrigado por utilizar nossa calculadora :) \n");
                    break;

                default:
                    System.out.println("Opção selecionada não é valida! \n");
                    break;
            }
        }
    }
}
