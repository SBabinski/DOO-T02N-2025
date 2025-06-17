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
                    double precoTotal = calculadora.calcularPrecoTotal(quantidade, preco);
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
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 4);

        calculadora.sc.close();
    }
}

class Venda {
    private int quantidadePlantas;
    private double precoUnitario;
    private double valorTotal;
    private double desconto;

    public Venda(int quantidadePlantas, double precoUnitario, double valorTotal, double desconto) {
        this.quantidadePlantas = quantidadePlantas;
        this.precoUnitario = precoUnitario;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
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

    @Override
    public String toString() {
        return "Quantidade: " + quantidadePlantas +
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

    public double calcularPrecoTotal(int quantidade, double preco) {
        double valorBruto = quantidade * preco;
        double desconto = 0.0;

        if (quantidade > 10) {
            desconto = valorBruto * 0.05;
        }

        double valorFinal = valorBruto - desconto;

        registrarVenda(quantidade, preco, valorFinal, desconto);

        return valorFinal;
    }

    private void registrarVenda(int quantidade, double precoUnitario, double valorTotal, double desconto) {
        Venda venda = new Venda(quantidade, precoUnitario, valorTotal, desconto);
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

    public int menu() {
        System.out.println("\n===== CALCULADORA DONA GABRIELINHA =====");
        System.out.println("[1] - Calcular Preço Total");
        System.out.println("[2] - Calcular Troco");
        System.out.println("[3] - Exibir Histórico de Vendas");
        System.out.println("[4] - Sair");
        System.out.print("Escolha uma opção: ");
        return sc.nextInt();
    }
}