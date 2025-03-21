import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

public class Loja {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RegistroVenda registroVendas = new RegistroVenda();

        int opcao = 0;

        while (opcao != 5) {
            System.out.println("\nSeja Bem-vindo à Loja da Dona Gabrielinha, especializada em plantas!");
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("1 - Calcular preço total");
            System.out.println("2 - Calcular troco");
            System.out.println("3 - Exibir registro de vendas");
            System.out.println("4 - Buscar vendas por dia ou mês");
            System.out.println("5 - Sair do sistema");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("\nDigite a quantidade de plantas que deseja levar: ");
                    int qtd = scanner.nextInt();

                    System.out.print("Digite o valor unitário das plantas R$: ");
                    double unitario = scanner.nextDouble();

                    double precoTotal = Calculadora.precoTotal(qtd, unitario);
                    double precoComDesconto = Calculadora.aplicarDesconto(qtd, precoTotal);

                    System.out.print("Digite a data da venda (dd mm aaaa): ");
                    int dia = scanner.nextInt();
                    int mes = scanner.nextInt();
                    int ano = scanner.nextInt();
                    LocalDate dataVenda = LocalDate.of(ano, mes, dia);

                    Venda venda = new Venda(qtd, unitario, precoTotal, precoComDesconto, dataVenda);
                    registroVendas.adicionarVenda(venda);

                    System.out.printf("O valor final da compra é R$: %.2f reais \n\n", precoComDesconto);
                    break;

                case 2:
                    System.out.print("\nQual o valor que o cliente entregou? R$: ");
                    double valorPago = scanner.nextDouble();

                    System.out.print("Qual foi o valor final da compra do cliente? R$: ");
                    double valorTotalCompra = scanner.nextDouble();

                    double trocoFinal = Calculadora.troco(valorPago, valorTotalCompra);
                    System.out.printf("O troco que será entregue para o cliente é de R$: %.2f reais\n\n", trocoFinal);
                    break;

                case 3:
                    System.out.println("\nExibindo registro de vendas...");
                    List<Venda> todasVendas = registroVendas.getVendas();
                    if (todasVendas.isEmpty()) {
                        System.out.println("Nenhuma venda realizada até o momento.");
                    } else {
                        for (Venda v : todasVendas) {
                            v.exibirVenda();
                        }
                    }
                    break;

                case 4:
                    System.out.println("\nEscolha uma opção:");
                    System.out.println("1 - Buscar vendas por dia");
                    System.out.println("2 - Buscar vendas por mês");
                    System.out.print("Opção: ");
                    int opcaoBusca = scanner.nextInt();

                    switch (opcaoBusca) {
                        case 1:
                            System.out.print("Digite o dia, mês e ano: ");
                            int diaBusca = scanner.nextInt();
                            int mesBusca = scanner.nextInt();
                            int anoBusca = scanner.nextInt();
                            registroVendas.buscarVendasPorDia(diaBusca, mesBusca, anoBusca);
                            break;

                        case 2:
                            System.out.print("Digite o mês e ano: ");
                            int mesBusca2 = scanner.nextInt();
                            int anoBusca2 = scanner.nextInt();
                            registroVendas.buscarVendasPorMes(mesBusca2, anoBusca2);
                            break;

                        default:
                            System.out.println("Opção inválida!");
                    }
                    break;

                case 5:
                    System.out.println("\nObrigado por utilizar nossa calculadora! :) \n");
                    break;

                default:
                    System.out.println("\nOpção selecionada não é válida!\n");
                    break;
            }
        }

        scanner.close();
    }
}
