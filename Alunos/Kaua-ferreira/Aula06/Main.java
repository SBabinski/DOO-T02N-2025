import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Vendedor {
    private String nome;
    private int idade;
    private String loja;
    private String cidade;
    private String bairro;
    private String rua;
    private double salarioBase;
    private List<Double> salarioRecebido;

    public Vendedor(String nome, int idade, String loja, String cidade, String bairro, String rua, double salarioBase, Double... salarios) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new ArrayList<>(Arrays.asList(salarios));
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + ", Idade: " + idade + ", Loja: " + loja);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double salario : salarioRecebido) {
            soma += salario;
        }
        return soma / salarioRecebido.size();
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}

class Cliente {
    private String nome;
    private int idade;
    private String cidade;
    private String bairro;
    private String rua;

    public Cliente(String nome, int idade, String cidade, String bairro, String rua) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + ", Idade: " + idade);
    }
}

class Loja {
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String rua;
    private List<Vendedor> vendedores;
    private List<Cliente> clientes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.vendedores = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public void adicionarVendedor(Vendedor vendedor) {
        vendedores.add(vendedor);
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public int contarClientes() {
        return clientes.size();
    }

    public int contarVendedores() {
        return vendedores.size();
    }

    public void apresentarse() {
        System.out.println("Nome Fantasia: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + cidade + ", " + bairro + ", " + rua);
    }
}

public class Main {
    public static void main(String[] args) {
        Loja loja = new Loja("My Plant", "My Plant Ltda", "12.345.678/0001-99", "São Paulo", "Centro", "Rua das Flores");

        Vendedor vendedor1 = new Vendedor("Carlos", 30, "My Plant", "São Paulo", "Centro", "Rua das Flores", 2500.0, 2500.0, 2600.0, 2700.0);
        Vendedor vendedor2 = new Vendedor("Ana", 28, "My Plant", "São Paulo", "Centro", "Rua das Rosas", 2200.0, 2200.0, 2250.0, 2300.0);

        Cliente cliente1 = new Cliente("Mariana", 35, "São Paulo", "Bela Vista", "Rua Azul");
        Cliente cliente2 = new Cliente("Roberto", 40, "São Paulo", "Moema", "Rua Verde");

        loja.adicionarVendedor(vendedor1);
        loja.adicionarVendedor(vendedor2);

        loja.adicionarCliente(cliente1);
        loja.adicionarCliente(cliente2);

        loja.apresentarse();
        System.out.println("Número de vendedores: " + loja.contarVendedores());
        System.out.println("Número de clientes: " + loja.contarClientes());

        System.out.println("\nInformações dos vendedores:");
        vendedor1.apresentarse();
        System.out.println("Média Salarial: " + vendedor1.calcularMedia());
        System.out.println("Bônus: " + vendedor1.calcularBonus());

        vendedor2.apresentarse();
        System.out.println("Média Salarial: " + vendedor2.calcularMedia());
        System.out.println("Bônus: " + vendedor2.calcularBonus());

        System.out.println("\nInformações dos clientes:");
        cliente1.apresentarse();
        cliente2.apresentarse();
    }
}
