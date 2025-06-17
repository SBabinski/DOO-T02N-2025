import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();
        int opcao;

        do {
            opcao = calculadora.menu();

            switch (opcao) {
                case 1:
                    System.out.println("Digite a quantidade da planta: ");
                    int quantidade = calculadora.sc.nextInt();
                    System.out.println("Digite o preço da planta: ");
                    double preco = calculadora.sc.nextDouble();
                    System.out.println("O preço total é: " + calculadora.calcularPrecoTotal(quantidade, preco));
                    break;
                case 2:
                    System.out.println("Digite o valor recebido: ");
                    double valorRecebido = calculadora.sc.nextDouble();
                    System.out.println("Digite o valor total: ");
                    double valorTotal = calculadora.sc.nextDouble();
                    System.out.println("O troco é: " + calculadora.calcularTroco(valorRecebido, valorTotal));
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 3);

        calculadora.sc.close();
    }
}

class Calculadora {
    public Scanner sc = new Scanner(System.in);

    public double calcularPrecoTotal(int quantidade, double preco) {
        return quantidade * preco;
    }

    public double calcularTroco(double valorRecebido, double valorTotal) {
        return valorRecebido - valorTotal;
    }

    public int menu() {
        System.out.println("[1] - Calcular Preço Total");
        System.out.println("[2] - Calcular Troco");
        System.out.println("[3] - Sair");
        System.out.print("Escolha uma opção: ");
        return sc.nextInt();
    }
}



/* Enunciado do Problema: Calculadora para loja de plantas da Dona Gabrielinha

Dona Gabrielinha é uma senhora apaixonada por plantas e recentemente abriu uma encantadora loja de venda de plantas exóticas. Ela descobriu que quando um cliente entra em seu estabelecimento e toma uma xícara de café, a conversão da venda sobe para 80%, assim descobrindo um negócio muito rentável. Para facilitar o gerenciamento de suas vendas e o cálculo dos preços, ela decidiu procurar a sua ajuda para desenvolver uma calculadora especializada. Por ser idosa e antiquada, um de seus requisitos é que a solução seja em Java☕.

A calculadora deve ser capaz de realizar as seguintes operações:
1. Cálculo de Preço Total:
    Dona Gabrielinha deseja calcular o preço total da venda de um item, considerando a quantidade de uma planta vendida vezes seu preço unitário.
    Entrada: Receber dois valores, primeiro a quantidade da referida planta, segundo a valor o preço da mesma.
    Saída: Retornar o resultado do cálculo.


2. Cálculo de Troco:
    A calculadora deve calcular o troco a ser dado ao cliente, considerando o valor pago.
    Entrada: Receber dois valores, primeiro o valor recebido pelo cliente, segundo o valor total da compra.
    Saída: Retornar o resultado do cálculo.

3. Criar Menu Console:
    [1] - Calcular Preço Total
    [2] - Calcular Troco
    [3] - Sair

INFORMAÇÕES IMPORTANTES
* CRIAR PASTA PARA A AULA 2 E FAZER O PROJETO LÁ.
* A ENTREGA CONSISTE NO LINK DO PULL REQUEST DO SEU GIT.
* APÓS ADICIONADO O LINK, MARCAR ATIVIDADE COMO ENTREGUE!
* ENTREGAS EM ATRASO SERÁ DESCONTADO METADE DA NOTA!!
* APÓS UMA SEMANA DA DATA FINAL DA ATIVIDADE, NÃO SERÁ MAIS POSSÍVEL REALIZAR ENTREGA. */