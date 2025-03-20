import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
                    System.out.println("Digite o dia (1-31): ");
                    int dia = calculadora.sc.nextInt();
                    System.out.println("Digite o mês (1-12): ");
                    int mes = calculadora.sc.nextInt();

                    double precoTotal = calculadora.calcularPrecoTotal(quantidade, preco, dia, mes);
                    System.out.println("O preço total é: R$" + String.format("%.2f", precoTotal));

                    if (quantidade > 10) {
                        System.out.println("Desconto de 5% aplicado por comprar mais de 10 plantas!");
                    }
                    break;
                case 2:
                    System.out.println("Digite o valor recebido: ");
                    double valorRecebido = calculadora.sc.nextDouble();
                    System.out.println("Digite o valor total: ");
                    double valorTotal = calculadora.sc.nextDouble();
                    System.out.println("O troco é: R$" + String.format("%.2f", calculadora.calcularTroco(valorRecebido, valorTotal)));
                    break;
                case 3:
                    calculadora.exibirHistoricoVendas();
                    break;
                case 4:
                    System.out.println("Digite o dia (1-31): ");
                    int diaConsulta = calculadora.sc.nextInt();
                    System.out.println("Digite o mês (1-12): ");
                    int mesConsulta = calculadora.sc.nextInt();
                    calculadora.consultarVendasPorData(diaConsulta, mesConsulta);
                    break;
                case 5:
                    System.out.println("Digite o mês (1-12): ");
                    int mesConsultaTotal = calculadora.sc.nextInt();
                    calculadora.consultarVendasPorMes(mesConsultaTotal);
                    break;
                case 6:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 6);

        calculadora.sc.close();
    }
}

class Venda {
    private int quantidadePlantas;
    private double precoUnitario;
    private double valorTotal;
    private double desconto;
    private int dia;
    private int mes;

    public Venda(int quantidadePlantas, double precoUnitario, double valorTotal, double desconto, int dia, int mes) {
        this.quantidadePlantas = quantidadePlantas;
        this.precoUnitario = precoUnitario;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
        this.dia = dia;
        this.mes = mes;
    }

    // Getters
    public int getQuantidadePlantas() {
        return quantidadePlantas;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getDesconto() {
        return desconto;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    @Override
    public String toString() {
        return "Data: " + dia + "/" + mes +
               " | Quantidade: " + quantidadePlantas +
               " | Preço unitário: R$" + String.format("%.2f", precoUnitario) +
               " | Desconto: R$" + String.format("%.2f", desconto) +
               " | Valor total: R$" + String.format("%.2f", valorTotal);
    }
}

class Calculadora {
    public Scanner sc = new Scanner(System.in);
    private List<Venda> historicoVendas;

    public Calculadora() {
        this.historicoVendas = new ArrayList<>();
    }

    public double calcularPrecoTotal(int quantidade, double preco, int dia, int mes) {
        double valorBruto = quantidade * preco;
        double desconto = 0.0;

        if (quantidade > 10) {
            desconto = valorBruto * 0.05;
        }

        double valorFinal = valorBruto - desconto;

        // Registrar a venda com data
        registrarVenda(quantidade, preco, valorFinal, desconto, dia, mes);

        return valorFinal;
    }

    private void registrarVenda(int quantidade, double precoUnitario, double valorTotal, double desconto, int dia, int mes) {
        Venda venda = new Venda(quantidade, precoUnitario, valorTotal, desconto, dia, mes);
        historicoVendas.add(venda);
    }

    public double calcularTroco(double valorRecebido, double valorTotal) {
        return valorRecebido - valorTotal;
    }

    public void exibirHistoricoVendas() {
        if (historicoVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada ainda.");
            return;
        }

        System.out.println("\n===== HISTÓRICO DE VENDAS =====");
        for (int i = 0; i < historicoVendas.size(); i++) {
            System.out.println("Venda #" + (i + 1) + ": " + historicoVendas.get(i));
        }
        System.out.println("==============================\n");
    }

    public void consultarVendasPorData(int dia, int mes) {
        int totalVendas = 0;
        double valorTotal = 0;

        System.out.println("\n===== VENDAS DO DIA " + dia + "/" + mes + " =====");

        boolean encontrouVendas = false;
        for (Venda venda : historicoVendas) {
            if (venda.getDia() == dia && venda.getMes() == mes) {
                System.out.println(venda);
                totalVendas++;
                valorTotal += venda.getValorTotal();
                encontrouVendas = true;
            }
        }

        if (!encontrouVendas) {
            System.out.println("Nenhuma venda encontrada para esta data.");
        } else {
            System.out.println("\nTotal de vendas: " + totalVendas);
            System.out.println("Valor total do dia: R$" + String.format("%.2f", valorTotal));
        }
        System.out.println("==============================\n");
    }

    public void consultarVendasPorMes(int mes) {
        int totalVendas = 0;
        double valorTotal = 0;

        System.out.println("\n===== VENDAS DO MÊS " + mes + " =====");

        boolean encontrouVendas = false;
        for (Venda venda : historicoVendas) {
            if (venda.getMes() == mes) {
                totalVendas++;
                valorTotal += venda.getValorTotal();
                encontrouVendas = true;
            }
        }

        if (!encontrouVendas) {
            System.out.println("Nenhuma venda encontrada para este mês.");
        } else {
            System.out.println("Total de vendas no mês: " + totalVendas);
            System.out.println("Valor total do mês: R$" + String.format("%.2f", valorTotal));

            // Análise por dia do mês
            System.out.println("\nDetalhamento por dia:");
            for (int dia = 1; dia <= 31; dia++) {
                int vendasDia = 0;
                double valorDia = 0;

                for (Venda venda : historicoVendas) {
                    if (venda.getMes() == mes && venda.getDia() == dia) {
                        vendasDia++;
                        valorDia += venda.getValorTotal();
                    }
                }

                if (vendasDia > 0) {
                    System.out.println("Dia " + dia + ": " + vendasDia + " vendas - R$" +
                                      String.format("%.2f", valorDia));
                }
            }
        }
        System.out.println("==============================\n");
    }

    public int menu() {
        System.out.println("\n===== CALCULADORA DONA GABRIELINHA =====");
        System.out.println("[1] - Calcular Preço Total");
        System.out.println("[2] - Calcular Troco");
        System.out.println("[3] - Exibir Histórico de Vendas");
        System.out.println("[4] - Consultar Vendas por Data");
        System.out.println("[5] - Consultar Vendas por Mês");
        System.out.println("[6] - Sair");
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