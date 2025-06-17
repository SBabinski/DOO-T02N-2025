import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Calculadora {
    public Scanner sc = new Scanner(System.in);
    private List<Venda> historicoVendas;
    private List<Pedido> pedidos;
    private int proximoIdPedido;

    public Calculadora() {
        this.historicoVendas = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.proximoIdPedido = 1;
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

    public void criarPedidoFake() {
        // Cria um endereço para o cliente
        Endereco enderecoCliente = new Endereco("SP", "São Paulo", "Jardins", "Av. Paulista", 1500, "Apto 501");

        // Cria o cliente
        Cliente cliente = new Cliente("Maria Souza", 30, enderecoCliente);

        // Cria um endereço para o vendedor
        Endereco enderecoVendedor = new Endereco("SP", "São Paulo", "Centro", "Rua das Flores", 100, "");

        // Cria o vendedor
        Double[] salarios = {1500.0, 1600.0, 1700.0};
        Vendedor vendedor = new Vendedor("João Silva", 25, "My Plant Central", enderecoVendedor, 1500.0, salarios);

        // Cria um endereço para a loja
        Endereco enderecoLoja = new Endereco("SP", "São Paulo", "Centro", "Rua dos Jardins", 200, "");

        // Cria a loja
        Loja loja = new Loja("My Plant", "My Plant Plantas LTDA", "12.345.678/0001-90", enderecoLoja);

        // Cria alguns itens
        List<Item> itens = new ArrayList<>();
        itens.add(new Item(1, "Rosa", "Flor", 15.99));
        itens.add(new Item(2, "Cacto", "Planta", 25.50));
        itens.add(new Item(3, "Vaso de Cerâmica", "Acessório", 45.00));

        // Processa o pedido
        ProcessaPedido processador = new ProcessaPedido();
        Pedido pedido = processador.processar(proximoIdPedido++, cliente, vendedor, loja, itens);

        // Adiciona o pedido à lista
        pedidos.add(pedido);

        System.out.println("Pedido criado com sucesso!");
        pedido.gerarDescricaoVenda();
    }

    public void exibirHistoricoPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado ainda.");
            return;
        }

        System.out.println("\n===== HISTÓRICO DE PEDIDOS =====");
        for (Pedido pedido : pedidos) {
            pedido.gerarDescricaoVenda();
            System.out.println("---------------------------");
        }
        System.out.println("==============================\n");
    }

    public int menu() {
        System.out.println("\n===== CALCULADORA DONA GABRIELINHA =====");
        System.out.println("[1] - Calcular Preço Total");
        System.out.println("[2] - Calcular Troco");
        System.out.println("[3] - Exibir Histórico de Vendas");
        System.out.println("[4] - Criar Novo Pedido");
        System.out.println("[5] - Exibir Histórico de Pedidos");
        System.out.println("[6] - Sair");
        System.out.print("Escolha uma opção: ");
        return sc.nextInt();
    }
}