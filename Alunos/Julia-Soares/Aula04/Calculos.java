import java.util.*;

public class Calculos {

    private int quantidadeProduto;
    private double precoUnitario;
    private double precoTotal;
    private double valorRecebido;
    private double troco;
    private double descontosAplicados;
    private double desconto;
    private double precoFinalDesconto;

    private List<String> vendas = new ArrayList<>();
    private List<VendaDiaria> vendasPorData = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    public void calcularPrecoTotal() {
        System.out.println("Informe o valor do preço unitário:");
        precoUnitario = scanner.nextDouble();

        System.out.println("Informe a quantidade do produto:");
        quantidadeProduto = scanner.nextInt();

        if (quantidadeProduto > 10) {
            precoTotal = precoUnitario * quantidadeProduto;
            descontosAplicados = quantidadeProduto / 10;
            desconto = 0.05 * precoTotal * descontosAplicados;
            precoFinalDesconto = precoTotal - desconto;
            System.out.printf("O preço total com desconto de 5%% é: R$ %.2f%n", precoFinalDesconto);
        } else {
            precoTotal = precoUnitario * quantidadeProduto;
            precoFinalDesconto = precoTotal;
            desconto = 0;
            System.out.printf("O preço total é: R$ %.2f%n", precoTotal);
        }

        String registro = "Quantidade: " + quantidadeProduto +
                ", Valor Bruto: R$" + String.format("%.2f", precoTotal) +
                ", Desconto: R$" + String.format("%.2f", desconto) +
                ", Valor Final: R$" + String.format("%.2f", precoFinalDesconto);
        vendas.add(registro);

        registrarVendaPorData(quantidadeProduto);
    }

    public void calcularTroco() {
        System.out.println("Informe o valor pago pelo cliente: ");
        valorRecebido = scanner.nextDouble();

        if (valorRecebido < precoFinalDesconto) {
            System.out.println("Erro: Valor insuficiente para a compra!");
        } else {
            troco = valorRecebido - precoFinalDesconto;
            System.out.printf("Troco do cliente: R$ %.2f%n", troco);
        }
    }

    public void mostrarVendas() {
        System.out.println("\n--- Registro de Vendas ---\n");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
        } else {
            for (String venda : vendas) {
                System.out.println(venda);
            }
        }
    }

    public void registrarVendaPorData(int quantidade) {
        System.out.println("Informe o dia da venda:");
        int dia = scanner.nextInt();
        System.out.println("Informe o mês da venda:");
        int mes = scanner.nextInt();

        boolean encontrou = false;
        for (VendaDiaria venda : vendasPorData) {
            if (venda.getDia() == dia && venda.getMes() == mes) {
                venda.adicionarQuantidade(quantidade);
                encontrou = true;
                break;
            }
        }

        if (!encontrou) {
            vendasPorData.add(new VendaDiaria(dia, mes, quantidade));
        }

        System.out.println("Venda registrada com sucesso para o dia " + dia + "/" + mes);
    }

    public void buscarVendaPorData() {
        System.out.println("Informe o dia que deseja buscar:");
        int dia = scanner.nextInt();
        System.out.println("Informe o mês:");
        int mes = scanner.nextInt();

        boolean encontrou = false;
        for (VendaDiaria venda : vendasPorData) {
            if (venda.getDia() == dia && venda.getMes() == mes) {
                System.out.println("Quantidade total de vendas em " + dia + "/" + mes + ": " + venda.getQuantidade());
                encontrou = true;
                break;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma venda registrada em " + dia + "/" + mes);
        }
    }
}
