import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class att4 {

    
    public static double calcularPrecoTotal(int quantidade, double precoUnitario) {
        return quantidade * precoUnitario;
    }

    public static double calcularPrecoComDesconto(int quantidade, double precoUnitario) {
        double precoTotal = calcularPrecoTotal(quantidade, precoUnitario);
        if (quantidade > 10) {
            double desconto = precoTotal * 0.05; 
            precoTotal -= desconto;
            return precoTotal;
        }
        return precoTotal; 
    }

    public static void registrarVenda(int quantidade, double precoUnitario, ArrayList<venda> registroVendas) {
        double valorTotal = calcularPrecoComDesconto(quantidade, precoUnitario);
        double desconto = calcularPrecoTotal(quantidade, precoUnitario) - valorTotal;

        venda novaVenda = new venda(quantidade, precoUnitario, valorTotal, desconto);
        registroVendas.add(novaVenda);

        System.out.println("Venda registrada com sucesso!");
        System.out.printf("Valor total da venda: R$%.2f\n", valorTotal);
        if (desconto > 0) {
            System.out.printf("Desconto aplicado: R$%.2f\n", desconto);
        }
    }

    public static void exibirRegistroVendas(ArrayList<venda> registroVendas) {
        if (registroVendas.isEmpty()) {
            System.out.println("Não há vendas registradas.");
        } else {
            System.out.println("Registro de Vendas:");
            for (int i = 0; i < registroVendas.size(); i++) {
                venda v = registroVendas.get(i);
                System.out.println("Venda " + (i + 1) + ":");
                System.out.println(v);
                System.out.println("-------------------------------");
            }
        }
    }

    public static double calcularTroco(double valorPago, double valorTotal) {
        return valorPago - valorTotal;
    }

  
    static HashMap<Integer, HashMap<Integer, Integer>> vendasPorMesEDia = new HashMap<>();

    public static void registrarVendasPorDia(int quantidade, int mes, int dia) {
        vendasPorMesEDia.putIfAbsent(mes, new HashMap<>());
        HashMap<Integer, Integer> vendasDia = vendasPorMesEDia.get(mes);
        vendasDia.put(dia, vendasDia.getOrDefault(dia, 0) + quantidade);
    }

    public static int buscarVendasPorMes(int mes) {
        return vendasPorMesEDia.getOrDefault(mes, new HashMap<>()).values().stream().mapToInt(Integer::intValue).sum();
    }

    public static int buscarVendasPorDia(int mes, int dia) {
        return vendasPorMesEDia.getOrDefault(mes, new HashMap<>()).getOrDefault(dia, 0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<venda> registroVendas = new ArrayList<>();
        int opcao = 0;

        while (opcao != 6) {

            System.out.println("Escolha uma opção:");
            System.out.println("[1] - Registrar Venda");
            System.out.println("[2] - Exibir Registro de Vendas");
            System.out.println("[3] - Calcular Troco");
            System.out.println("[4] - Registrar Vendas por Dia");
            System.out.println("[5] - Buscar Vendas por Mês ou Dia");
            System.out.println("[6] - Sair");
            System.out.print("Digite sua opção: ");
            opcao = scanner.nextInt();

            if (opcao == 1) {
                System.out.print("Digite a quantidade de plantas: ");
                int quantidade = scanner.nextInt();
                System.out.print("Digite o preço unitário da planta: R$ ");
                double precoUnitario = scanner.nextDouble();

                registrarVenda(quantidade, precoUnitario, registroVendas);

            } else if (opcao == 2) {
                exibirRegistroVendas(registroVendas);

            } else if (opcao == 3) {
                System.out.print("Digite o valor pago pelo cliente: R$ ");
                double valorPago = scanner.nextDouble();
                System.out.print("Digite o valor total da compra: R$ ");
                double valorTotal = scanner.nextDouble();

                if (valorPago >= valorTotal) {
                    double troco = calcularTroco(valorPago, valorTotal);
                    System.out.printf("Troco a ser devolvido: R$ %.2f\n", troco);
                } else {
                    System.out.println("Erro: O valor pago é insuficiente!");
                }

            } else if (opcao == 4) {
                System.out.print("Digite o mês da venda (1-12): ");
                int mes = scanner.nextInt();
                System.out.print("Digite o dia da venda: ");
                int dia = scanner.nextInt();
                System.out.print("Digite a quantidade de vendas realizadas: ");
                int quantidade = scanner.nextInt();

                registrarVendasPorDia(quantidade, mes, dia);
                System.out.println("Vendas registradas com sucesso!");

            } else if (opcao == 5) {
                System.out.println("[1] Buscar Vendas por Mês");
                System.out.println("[2] Buscar Vendas por Dia");
                System.out.print("Escolha a opção: ");
                int escolha = scanner.nextInt();

                if (escolha == 1) {
                    System.out.print("Digite o mês para buscar as vendas: ");
                    int mesBusca = scanner.nextInt();
                    int totalVendasMes = buscarVendasPorMes(mesBusca);
                    System.out.println("Total de vendas no mês " + mesBusca + ": " + totalVendasMes);
                } else if (escolha == 2) {
                    System.out.print("Digite o mês para buscar as vendas: ");
                    int mesBusca = scanner.nextInt();
                    System.out.print("Digite o dia para buscar as vendas: ");
                    int diaBusca = scanner.nextInt();
                    int totalVendasDia = buscarVendasPorDia(mesBusca, diaBusca);
                    System.out.println("Total de vendas no dia " + diaBusca + " do mês " + mesBusca + ": " + totalVendasDia);
                }

            } else if (opcao == 6) {
                System.out.println("Saindo... Até logo!");

            } else {
                System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }
        }

        scanner.close();
    }
}

class venda {
    private int quantidade;
    private double precoUnitario;
    private double valorTotal;
    private double desconto;

    public venda(int quantidade, double precoUnitario, double valorTotal, double desconto) {
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
    }

    public int getQuantidade() {
        return quantidade;
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

    public String toString() {
        return "Quantidade: " + quantidade + ", Preço Unitário: R$" + precoUnitario + ", Total: R$" + valorTotal + ", Desconto: R$" + desconto;
    }
}
