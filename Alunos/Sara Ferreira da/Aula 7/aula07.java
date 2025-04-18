import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
     
        Gerente gerente = new Gerente("Carlos", 42, "My Plant", "Salvador", "Centro", "Rua Verde", 5000);
        gerente.apresentarSe();
        System.out.println("Média Salarial: " + gerente.calcularMedia());
        System.out.println("Bônus: " + gerente.calcularBonus());

      
        Item item1 = new Item(1, "Vaso Decorado", "Acessório", 80.0);
        item1.gerarDescricao();

  
        Cliente cliente = new Cliente("Ana", 30);
        Vendedor vendedor = new Vendedor("Bruno", 28);

       
        List<Item> itens = new ArrayList<>();
        itens.add(item1);

        Pedido pedido = new Pedido(1001, new Date(), null, new Date(System.currentTimeMillis() + 86400000), cliente, vendedor, "Loja My Plant", itens);
        pedido.gerarDescricaoVenda();


        ProcessaPedido processor = new ProcessaPedido();
        processor.processar(pedido);
    }
}


class Endereco {
    String estado, cidade, bairro, rua, numero, complemento;

    public Endereco(String estado, String cidade, String bairro, String rua, String numero, String complemento) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
    }

    public void apresentarLogradouro() {
        System.out.println("Endereço: " + rua + ", " + numero + " - " + bairro + ", " + cidade + " - " + estado + " (" + complemento + ")");
    }
}


class Gerente {
    String nome;
    int idade;
    String loja, cidade, bairro, rua;
    double salarioBase;
    double[] salarioRecebido = {5000.0, 5100.0, 5200.0};

    public Gerente(String nome, int idade, String loja, String cidade, String bairro, String rua, double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
    }

    public void apresentarSe() {
        System.out.println("Gerente: " + nome + " | Idade: " + idade + " | Loja: " + loja);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double salario : salarioRecebido) {
            soma += salario;
        }
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
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
        System.out.println("Item #" + id + ": " + nome + " - " + tipo + " - R$" + valor);
    }
}

class Cliente {
    String nome;
    int idade;

    public Cliente(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }
}

class Vendedor {
    String nome;
    int idade;

    public Vendedor(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }
}

class Pedido {
    int id;
    Date dataCriacao, dataPagamento, dataVencimentoReserva;
    Cliente cliente;
    Vendedor vendedor;
    String loja;
    List<Item> itens;

    public Pedido(int id, Date dataCriacao, Date dataPagamento, Date dataVencimentoReserva,
                  Cliente cliente, Vendedor vendedor, String loja, List<Item> itens) {
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
        double total = 0;
        for (Item item : itens) {
            total += item.valor;
        }
        return total;
    }

    public void gerarDescricaoVenda() {
        System.out.println("Pedido #" + id + " criado em: " + dataCriacao + " | Total: R$" + calcularValorTotal());
    }
}

class ProcessaPedido {
    public void processar(Pedido pedido) {
        if (confirmarPagamento(pedido)) {
            System.out.println("Pagamento confirmado!");
        } else {
            System.out.println("Reserva vencida. Pedido cancelado.");
        }
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date hoje = new Date();
        return hoje.before(pedido.dataVencimentoReserva);
    }
}
