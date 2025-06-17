public class App {
    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();


        System.out.println("\n===== DEMONSTRAÇÃO MY PLANT =====");

        Endereco enderecoVendedor = new Endereco("SP", "São Paulo", "Centro", "Rua das Flores", 100, "");
        Endereco enderecoCliente = new Endereco("SP", "São Paulo", "Jardins", "Av. Paulista", 1500, "Apto 501");
        Endereco enderecoLoja = new Endereco("SP", "São Paulo", "Centro", "Rua dos Jardins", 200, "");
        Endereco enderecoGerente = new Endereco("SP", "São Paulo", "Moema", "Alameda dos Anapurus", 800, "Sala 10");

        Double[] salariosVendedor = {1500.0, 1600.0, 1700.0};
        Vendedor vendedor1 = new Vendedor("João Silva", 25, "My Plant Central", enderecoVendedor, 1500.0, salariosVendedor);
        vendedor1.apresentarse();
        vendedor1.calcularMedia();
        vendedor1.calcularBonus();

        Double[] salariosGerente = {3000.0, 3250.0, 3500.0};
        Gerente gerente1 = new Gerente("Ana Santos", 35, "My Plant Central", enderecoGerente, 3000.0, salariosGerente);
        gerente1.apresentarse();
        gerente1.calcularMedia();
        gerente1.calcularBonus();

        Cliente cliente1 = new Cliente("Maria Souza", 30, enderecoCliente);
        cliente1.apresentarse();

        Loja loja = new Loja("My Plant", "My Plant Plantas LTDA", "12.345.678/0001-90", enderecoLoja);
        loja.adicionarVendedor(vendedor1);
        loja.adicionarCliente(cliente1);
        loja.adicionarGerente(gerente1);
        loja.apresentarLoja();
        loja.contarVendedores();
        loja.contarClientes();
        loja.contarGerentes();

        System.out.println("\n===== SISTEMA CALCULADORA =====");
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
                    calculadora.criarPedidoFake();
                    break;
                case 5:
                    calculadora.exibirHistoricoPedidos();
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