
import java.util.Scanner;

public class Calculos {
    
    private int quantidadeItens;
    private double valorUnitario;
    private double valorTotal;
    private double pagamentoRecebido;
    private double valorTroco;

    Scanner entrada = new Scanner(System.in);

    public void calcularValorTotal() {
        System.out.println("Informe o valor do preço unitário:");
        valorUnitario = entrada.nextDouble();

        System.out.println("Informe a quantidade do produto:");
        quantidadeItens = entrada.nextInt();

        valorTotal = valorUnitario * quantidadeItens;

        System.out.printf("O preço total é: R$ %.2f%n", valorTotal);
    }

    public void calcularTroco() {
        System.out.println("Informe o valor pago pelo cliente: ");
        pagamentoRecebido = entrada.nextDouble();

        if (pagamentoRecebido < valorTotal) {
            System.out.println("Erro: Valor insuficiente para a compra!");
        } else {
            valorTroco = pagamentoRecebido - valorTotal;
            System.out.printf("Troco do cliente: R$ %.2f%n", valorTroco);
        }
    }

    public static void main(String[] args) {
        executarCalculadora();
    }
    
    public static void executarCalculadora() {
        Calculos operacoes = new Calculos();
        Scanner entrada = new Scanner(System.in);

        boolean rodando = true;

        while (rodando) {
            System.out.println("--Iniciando Calculadora--");
            System.out.println("1 - Calcular valor total da compra");
            System.out.println("2 - Mostrar troco a ser devolvido");
            System.out.println("3 - Sair");

            int escolha = entrada.nextInt();
            entrada.nextLine();

            switch (escolha) {
                case 1:
                    operacoes.calcularValorTotal();
                    break;
                case 2:
                    operacoes.calcularTroco();
                    break;
                case 3:
                    rodando = false;
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente!");
                    break;
            }
        }
        entrada.close();
    }
}

