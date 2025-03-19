package CalculadoraPlantas.Aula3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

public class CalculadoraPlantas {
    private static final List<Venda> registroVendas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Calculadora Dona Gabrielinha ===");
            System.out.println("[1] - Calcular Preço Total e Registrar Venda");
            System.out.println("[2] - Exibir Registro Completo de Vendas");
            System.out.println("[3] - Calcular Troco");
            System.out.println("[4] - Consultar Vendas por Dia");
            System.out.println("[5] - Consultar Vendas por Mês");
            System.out.println("[6] - Limpar Histórico de Vendas");
            System.out.println("[7] - Sair");
            System.out.print("Escolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Opção inválida! Escolha um número: ");
                scanner.next();
            }
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal(scanner);
                    break;
                case 2:
                    exibirRegistroVendas();
                    break;
                case 3:
                    calcularTroco(scanner);
                    break;
                case 4:
                    consultarVendasPorDia(scanner);
                    break;
                case 5:
                    consultarVendasPorMes(scanner);
                    break;
                case 6:
                    limparRegistroVendas();
                    break;
                case 7:
                    System.out.println("Encerrando o programa... Até logo, Dona Gabrielinha!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 7);

        scanner.close();
    }

    public static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("Informe a quantidade de plantas: ");
        int quantidade = validarEntradaInteira(scanner);

        System.out.print("Informe o preço unitário da planta: ");
        BigDecimal precoUnitario = validarEntradaMonetaria(scanner);

        BigDecimal total = precoUnitario.multiply(BigDecimal.valueOf(quantidade));

        if (quantidade > 10) {
            BigDecimal desconto = total.multiply(BigDecimal.valueOf(0.05));
            total = total.subtract(desconto);
            System.out.printf("Desconto de 5%% aplicado! Valor com desconto: R$ %.2f\n", total);
        } else {
            System.out.printf("Preço total: R$ %.2f\n", total);
        }

        registrarVenda(quantidade, total);
    }

    public static void calcularTroco(Scanner scanner) {
        System.out.print("Informe o valor total da compra: ");
        BigDecimal totalCompra = validarEntradaMonetaria(scanner);

        System.out.print("Informe o valor pago pelo cliente: ");
        BigDecimal valorPago = validarEntradaMonetaria(scanner);

        if (valorPago.compareTo(totalCompra) < 0) {
            System.out.println("Valor insuficiente! O cliente precisa pagar pelo menos R$ " + totalCompra);
        } else {
            BigDecimal troco = valorPago.subtract(totalCompra);
            System.out.printf("Troco a ser dado ao cliente: R$ %.2f\n", troco);
        }
    }

    public static void registrarVenda(int quantidade, BigDecimal valor) {
        LocalDate dataVenda = LocalDate.now();
        Venda venda = new Venda(dataVenda, quantidade, valor);
        registroVendas.add(venda);
        System.out.println("Venda registrada com sucesso em " + dataVenda + "!");
    }

    public static void consultarVendasPorDia(Scanner scanner) {
        System.out.print("Informe a data (AAAA-MM-DD): ");
        try {
            LocalDate data = LocalDate.parse(scanner.next());

            BigDecimal totalVendas = BigDecimal.ZERO;
            int totalQuantidade = 0;

            for (Venda venda : registroVendas) {
                if (venda.getData().equals(data)) {
                    totalVendas = totalVendas.add(venda.getValor());
                    totalQuantidade += venda.getQuantidade();
                }
            }

            System.out.println("Total de vendas no dia " + data + ": " + totalQuantidade + " plantas, Valor: R$ " + totalVendas);
        } catch (Exception e) {
            System.out.println("Data inválida! Use o formato AAAA-MM-DD.");
        }
    }

    public static void consultarVendasPorMes(Scanner scanner) {
        System.out.print("Informe o mês e ano (AAAA-MM): ");
        try {
            String input = scanner.next();
            String[] partes = input.split("-");
            int ano = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);

            BigDecimal totalVendas = BigDecimal.ZERO;
            int totalQuantidade = 0;

            for (Venda venda : registroVendas) {
                if (venda.getData().getYear() == ano && venda.getData().getMonthValue() == mes) {
                    totalVendas = totalVendas.add(venda.getValor());
                    totalQuantidade += venda.getQuantidade();
                }
            }

            System.out.println("Total de vendas no mês " + input + ": " + totalQuantidade + " plantas, Valor: R$ " + totalVendas);
        } catch (Exception e) {
            System.out.println("Entrada inválida! Use o formato AAAA-MM.");
        }
    }

    public static void exibirRegistroVendas() {
        if (registroVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada até o momento.");
        } else {
            System.out.println("\n=== Registro de Vendas ===");
            for (Venda venda : registroVendas) {
                System.out.println(venda);
            }
        }
    }

    public static void limparRegistroVendas() {
        registroVendas.clear();
        System.out.println("Histórico de vendas apagado!");
    }

    public static int validarEntradaInteira(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida! Digite um número inteiro: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static BigDecimal validarEntradaMonetaria(Scanner scanner) {
        while (!scanner.hasNextBigDecimal()) {
            System.out.print("Entrada inválida! Digite um valor monetário válido: ");
            scanner.next();
        }
        return scanner.nextBigDecimal();
    }
}

class Venda {
    private final LocalDate data;
    private final int quantidade;
    private final BigDecimal valor;

    public Venda(LocalDate data, int quantidade, BigDecimal valor) {
        this.data = data;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Data: " + data + " | Quantidade: " + quantidade + " | Valor: R$ " + valor.setScale(2, RoundingMode.HALF_EVEN);
    }
}
