import java.util.*;

public class CalculadoraLojaPlantasMelhorado {

    static class Venda {
        int quantidade;
        double valorTotal;
        double descontoAplicado;
        int dia;
        int mes;

        Venda(int quantidade, double valorTotal, double descontoAplicado, int dia, int mes) {
            this.quantidade = quantidade;
            this.valorTotal = valorTotal;
            this.descontoAplicado = descontoAplicado;
            this.dia = dia;
            this.mes = mes;
        }

        @Override
        public String toString() {
            return "Data: " + dia + "/" + mes + " - Quantidade: " + quantidade + ", Valor Total: R$ " + String.format("%.2f", valorTotal) +
                    ", Desconto Aplicado: R$ " + String.format("%.2f", descontoAplicado);
        }
    }

    private static final List<Venda> registroVendas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal(scanner);
                    break;
                case 2:
                    calcularTroco(scanner);
                    break;
                case 3:
                    exibirRegistroVendas();
                    break;
                case 4:
                    registrarVendaComData(scanner);
                    break;
                case 5:
                    consultarVendasPorData(scanner);
                    break;
                case 6:
                    System.out.println("Encerrando a calculadora. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 6);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n--- Calculadora Dona Gabrielinha ---");
        System.out.println("[1] - Calcular Preço Total");
        System.out.println("[2] - Calcular Troco");
        System.out.println("[3] - Exibir Registro de Vendas");
        System.out.println("[4] - Registrar Venda com Data");
        System.out.println("[5] - Consultar Vendas por Data");
        System.out.println("[6] - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();
        System.out.print("Digite o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();

        double valorTotal = quantidade * precoUnitario;
        double desconto = 0;

        if (quantidade > 10) {
            desconto = valorTotal * 0.05;
            valorTotal -= desconto;
            System.out.printf("Desconto de 5%% aplicado: R$ %.2f\n", desconto);
        }

        System.out.printf("Preço Total a Pagar: R$ %.2f\n", valorTotal);
    }

    private static void calcularTroco(Scanner scanner) {
        System.out.print("Digite o valor recebido do cliente: ");
        double valorRecebido = scanner.nextDouble();
        System.out.print("Digite o valor total da compra: ");
        double valorCompra = scanner.nextDouble();

        double troco = valorRecebido - valorCompra;
        if (troco >= 0) {
            System.out.printf("Troco a ser dado: R$ %.2f\n", troco);
        } else {
            System.out.println("Valor recebido é insuficiente para cobrir a compra.");
        }
    }

    private static void exibirRegistroVendas() {
        if (registroVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada até o momento.");
        } else {
            System.out.println("\n--- Registro de Vendas ---");
            for (Venda venda : registroVendas) {
                System.out.println(venda);
            }
        }
    }

    private static void registrarVendaComData(Scanner scanner) {
        System.out.print("Digite o dia da venda: ");
        int dia = scanner.nextInt();
        System.out.print("Digite o mês da venda: ");
        int mes = scanner.nextInt();
        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();
        System.out.print("Digite o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();

        double valorTotal = quantidade * precoUnitario;
        double desconto = 0;

        if (quantidade > 10) {
            desconto = valorTotal * 0.05;
            valorTotal -= desconto;
            System.out.printf("Desconto de 5%% aplicado: R$ %.2f\n", desconto);
        }

        System.out.printf("Preço Total a Pagar: R$ %.2f\n", valorTotal);

        registroVendas.add(new Venda(quantidade, valorTotal, desconto, dia, mes));
    }

    private static void consultarVendasPorData(Scanner scanner) {
        System.out.print("Digite o dia da consulta: ");
        int dia = scanner.nextInt();
        System.out.print("Digite o mês da consulta: ");
        int mes = scanner.nextInt();

        double totalVendas = 0;
        int totalQuantidade = 0;
        boolean encontrouVenda = false;

        for (Venda venda : registroVendas) {
            if (venda.dia == dia && venda.mes == mes) {
                totalVendas += venda.valorTotal;
                totalQuantidade += venda.quantidade;
                encontrouVenda = true;
            }
        }

        if (encontrouVenda) {
            System.out.printf("Total de vendas no dia %d/%d: Quantidade %d - Valor Total: R$ %.2f\n",
                    dia, mes, totalQuantidade, totalVendas);
        } else {
            System.out.printf("Nenhuma venda registrada no dia %d/%d.\n", dia, mes);
        }
    }
}

