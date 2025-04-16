import java.util.*;

class Endereco {
    String estado, cidade, bairro, numero, complemento;

    public Endereco(String estado, String cidade, String bairro, String numero, String complemento) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public void apresentarLogradouro() {
        System.out.println("Endereço: " + ruaCompleta());
    }

    public String ruaCompleta() {
        return bairro + ", N°: " + numero + ", " + complemento + " - " + cidade + ", " + estado;
    }
}

abstract class Pessoa {
    String nome;
    int idade;
    Endereco endereco;

    public Pessoa(String nome, int idade, Endereco endereco) {
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
    }

    public abstract void apresentarse();
}

class Vendedor extends Pessoa {
    String loja;
    double salarioBase;
    List<Double> salarioRecebido;

    public Vendedor(String nome, int idade, Endereco endereco, String loja, double salarioBase, Double... salarios) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new ArrayList<>(Arrays.asList(salarios));
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + ", Idade: " + idade + ", Loja: " + loja);
    }

    public double calcularMedia() {
        return salarioRecebido.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}

class Gerente extends Pessoa {
    String loja;
    double salarioBase;
    List<Double> salarioRecebido;

    public Gerente(String nome, int idade, Endereco endereco, String loja, double salarioBase, Double... salarios) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new ArrayList<>(Arrays.asList(salarios));
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + ", Idade: " + idade + ", Loja: " + loja);
    }

    public double calcularMedia() {
        return salarioRecebido.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
    }
}

class Cliente extends Pessoa {
    public Cliente(String nome, int idade, Endereco endereco) {
        super(nome, idade, endereco);
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + ", Idade: " + idade);
    }
}

class Item {
    int id;
    String nome, tipo;
    double valor;

    public Item(int id, String nome, String tipo, double valor) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    public void gerarDescricao() {
        System.out.println("Item: " + id + " - " + nome + " (" + tipo + ") - R$ " + valor);
    }
}

class Pedido {
    int id;
    Date dataCriacao, dataPagamento, dataVencimentoReserva;
    Cliente cliente;
    Vendedor vendedor;
    Loja loja;
    List<Item> itens;

    public Pedido(int id, Date dataCriacao, Date dataPagamento, Date dataVencimentoReserva,
                  Cliente cliente, Vendedor vendedor, Loja loja, List<Item> itens) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataPagamento = dataPagamento;
        this.dataVencimentoReserva = dataVencimentoReserva;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.itens = itens;
    }

    public double calcularValorTotal() {
        return itens.stream().mapToDouble(item -> item.valor).sum();
    }

    public void gerarDescricaoVenda() {
        System.out.println("Pedido criado em: " + dataCriacao + ", Valor Total: R$ " + calcularValorTotal());
    }
}

class ProcessaPedido {
    public Pedido processar(int id, Cliente cliente, Vendedor vendedor, Loja loja, List<Item> itens,
                            Date dataCriacao, Date dataPagamento, Date dataVencimentoReserva) {
        Pedido pedido = new Pedido(id, dataCriacao, dataPagamento, dataVencimentoReserva, cliente, vendedor, loja, itens);
        if (confirmarPagamento(pedido)) {
            System.out.println("Pedido confirmado!");
        } else {
            System.out.println("Reserva vencida. Pedido cancelado.");
        }
        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date hoje = new Date();
        return hoje.before(pedido.dataVencimentoReserva);
    }
}

class Loja {
    String nomeFantasia, razaoSocial, cnpj;
    Endereco endereco;
    List<Vendedor> vendedores = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, Endereco endereco) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    public void adicionarVendedor(Vendedor v) { vendedores.add(v); }
    public void adicionarCliente(Cliente c) { clientes.add(c); }
    public int contarClientes() { return clientes.size(); }
    public int contarVendedores() { return vendedores.size(); }

    public void apresentarse() {
        System.out.println("Loja: " + nomeFantasia + ", CNPJ: " + cnpj);
        endereco.apresentarLogradouro();
    }
}

public class SistemaMyPlant {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Endereco endLoja = new Endereco("SP", "São Paulo", "Centro", "123", "Sala 01");
        Loja loja = new Loja("My Plant", "My Plant Ltda", "00.000.000/0001-00", endLoja);

        Vendedor vendedor = new Vendedor("Lucas", 30, endLoja, "My Plant", 2500.0, 2500.0, 2600.0, 2700.0);
        Cliente cliente = new Cliente("Maria", 28, new Endereco("SP", "São Paulo", "Moema", "456", "Ap 3"));

        loja.adicionarVendedor(vendedor);
        loja.adicionarCliente(cliente);

        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Exibir dados da loja");
            System.out.println("2. Exibir vendedores e bônus");
            System.out.println("3. Exibir clientes");
            System.out.println("4. Criar pedido (dados fake)");
            System.out.println("5. Exibir dados de um gerente");
            System.out.println("6. Testar endereço personalizado");
            System.out.println("0. Sair");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    loja.apresentarse();
                    break;
                case 2:
                    for (Vendedor v : loja.vendedores) {
                        v.apresentarse();
                        System.out.println("Média salarial: " + v.calcularMedia());
                        System.out.println("Bônus: " + v.calcularBonus());
                    }
                    break;
                case 3:
                    for (Cliente c : loja.clientes) {
                        c.apresentarse();
                    }
                    break;
                case 4:
                    List<Item> itens = new ArrayList<>();
                    itens.add(new Item(1, "Samambaia", "Planta", 35.0));
                    itens.add(new Item(2, "Vaso Cerâmica", "Acessório", 50.0));

                    ProcessaPedido processador = new ProcessaPedido();
                    Date agora = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(agora);
                    cal.add(Calendar.DATE, 2);
                    Date vencimento = cal.getTime();

                    Pedido novoPedido = processador.processar(1, cliente, vendedor, loja, itens, agora, agora, vencimento);
                    novoPedido.gerarDescricaoVenda();
                    break;
                case 5:
                    Gerente gerente = new Gerente("Fernanda", 40, new Endereco("PR", "Foz do Iguaçu", "Bairro Alvorada", "789", "Cobertura"), "My Plant RJ", 4000.0, 4000.0, 4200.0, 4500.0);
                    gerente.apresentarse();
                    System.out.println("Média salarial: " + gerente.calcularMedia());
                    System.out.println("Bônus: " + gerente.calcularBonus());
                    break;
                case 6:
                    Endereco enderecoTeste = new Endereco("PR", "Cascavel", "tropical", "321", "Loja 2");
                    enderecoTeste.apresentarLogradouro();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (opcao != 0);
    }
}