import java.util.Scanner;

public class SistemaMyPlant {

    public static void main(String[] args) {

        Loja loja = new Loja(
                "My Plant",
                "My Plant LTDA",
                "12.345.678/0001-90",
                "Cascavel",
                "Centro",
                "Rua das Flores");

        Vendedor vendedor1 = new Vendedor(
                "Carlos",
                30,
                loja,
                "Cascavel",
                "Centro",
                "Rua das Flores",
                2000,
                new double[] { 2200, 2300, 2100 });

        Vendedor vendedor2 = new Vendedor(
                "Mariana",
                28,
                loja,
                "Cascavel",
                "Centro",
                "Rua das Rosas",
                2500,
                new double[] { 2600, 2500, 2700 });

        Cliente cliente1 = new Cliente("Lucas", 35, "Cascavel", "Centro", "Rua A");
        Cliente cliente2 = new Cliente("Juliana", 27, "Cascavel", "Centro", "Rua B");

        loja.adicionarVendedor(vendedor1);
        loja.adicionarVendedor(vendedor2);
        loja.adicionarCliente(cliente1);
        loja.adicionarCliente(cliente2);

        System.out.println("=== Loja ===");
        loja.apresentarse();
        System.out.println("Total de Clientes: " + loja.contarClientes());
        System.out.println("Total de Vendedores: " + loja.contarVendedores());

        System.out.println("\n=== Vendedores ===");
        vendedor1.apresentarse();
        System.out.println("Média Salarial: " + vendedor1.calcularMedia());
        System.out.println("Bônus: " + vendedor1.calcularBonus());

        System.out.println();
        vendedor2.apresentarse();
        System.out.println("Média Salarial: " + vendedor2.calcularMedia());
        System.out.println("Bônus: " + vendedor2.calcularBonus());

        System.out.println("\n=== Clientes ===");
        cliente1.apresentarse();
        cliente2.apresentarse();
    }
}

class Vendedor {
    String nome;
    int idade;
    Loja loja;
    String cidade;
    String bairro;
    String rua;
    double salarioBase;
    double[] salarioRecebido;

    public Vendedor(String nome, int idade, Loja loja, String cidade, String bairro, String rua, double salarioBase,
            double[] salarioRecebido) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + " | Idade: " + idade + " | Loja: " + loja.nomeFantasia);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double salario : salarioRecebido) {
            soma += salario;
        }
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}

class Cliente {
    String nome;
    int idade;
    String cidade;
    String bairro;
    String rua;

    public Cliente(String nome, int idade, String cidade, String bairro, String rua) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + " | Idade: " + idade);
    }
}

class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    String cidade;
    String bairro;
    String rua;
    Vendedor[] vendedores = new Vendedor[10];
    Cliente[] clientes = new Cliente[10];
    int contadorVendedores = 0;
    int contadorClientes = 0;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public void adicionarVendedor(Vendedor v) {
        if (contadorVendedores < vendedores.length) {
            vendedores[contadorVendedores++] = v;
        }
    }

    public void adicionarCliente(Cliente c) {
        if (contadorClientes < clientes.length) {
            clientes[contadorClientes++] = c;
        }
    }

    public int contarClientes() {
        return contadorClientes;
    }

    public int contarVendedores() {
        return contadorVendedores;
    }

    public void apresentarse() {
        System.out.println("Nome Fantasia: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + rua + ", " + bairro + ", " + cidade);
    }
}
