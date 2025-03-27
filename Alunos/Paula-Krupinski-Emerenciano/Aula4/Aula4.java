import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Aula4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> historicoVendas = new ArrayList<>();
        int opcao;

        do {
            System.out.println("\nFLORICULTURA");
            System.out.println("1 - Calcular Preço Total");
            System.out.println("2 - Ver Histórico de Vendas");
            System.out.println("3 - Registrar Vendas do Dia");
            System.out.println("4 - Consultar Vendas por Dia e Mês");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:
                    System.out.print("Digite a quantidade de plantas: ");
                    int quantidade = scanner.nextInt();

                    System.out.print("Digite o preço de cada planta: ");
                    double precoUnitario = scanner.nextDouble();

                    double precoTotal = quantidade * precoUnitario;
                    double desconto = 0;

                    if (quantidade > 10) {
                        desconto = 0.05 * precoTotal;
                    }

                    double valorFinal = precoTotal - desconto;
                    System.out.println("Preço total: R$ " + precoTotal);
                    System.out.println("Desconto aplicado: R$ " + desconto);
                    System.out.println("Valor final com desconto: R$ " + valorFinal);

                    String venda = "Quantidade: " + quantidade + ", Valor da Venda: R$ " + precoTotal + ", Desconto: R$ " + desconto;
                    historicoVendas.add(venda);
                    break;

                case 2:
                    if (historicoVendas.isEmpty()) {
                        System.out.println("Nenhuma venda realizada.");
                    } else {
                        System.out.println("Histórico de Vendas:");
                        for (String vendaHistorico : historicoVendas) {
                            System.out.println(vendaHistorico);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o dia da venda: ");
                    int dia = scanner.nextInt();
                    System.out.print("Digite o mês da venda: ");
                    int mes = scanner.nextInt();
                    System.out.print("Digite a quantidade total de vendas do dia: ");
                    int vendasDoDia = scanner.nextInt();

                    String registro = "Dia: " + dia + ", Mês: " + mes + ", Total de Vendas: " + vendasDoDia;
                    historicoVendas.add(registro);
                    System.out.println("Venda registrada com sucesso!");
                    break;

                case 4:
                    System.out.print("Digite o dia: ");
                    int diaConsulta = scanner.nextInt();
                    System.out.print("Digite o mês: ");
                    int mesConsulta = scanner.nextInt();

                    boolean encontrado = false;
                    for (String registroVenda : historicoVendas) {
                        if (registroVenda.contains("Dia: " + diaConsulta) && registroVenda.contains("Mês: " + mesConsulta)) {
                            System.out.println(registroVenda);
                            encontrado = true;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Nenhuma venda registrada para essa data.");
                    }
                    break;

                case 5:
                    System.out.println("Saindo");
                    break;

                default:
                    System.out.println("Opção inválida");
            }

        } while (opcao != 5);

        scanner.close();
    }
}
