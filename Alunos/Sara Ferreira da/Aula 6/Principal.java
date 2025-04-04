
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Funcionario {
    private String nome;
    private int idade;
    private String estabelecimento;
    private String cidade;
    private String bairro;
    private String endereco;
    private double salarioBase;
    private List<Double> historicoSalarios;

    public Funcionario(String nome, int idade, String estabelecimento, String cidade, String bairro, String endereco, double salarioBase, Double... salarios) {
        this.nome = nome;
        this.idade = idade;
        this.estabelecimento = estabelecimento;
        this.cidade = cidade;
        this.bairro = bairro;
        this.endereco = endereco;
        this.salarioBase = salarioBase;
        this.historicoSalarios = new ArrayList<>(Arrays.asList(salarios));
    }

    public void exibirDados() {
        System.out.println("Nome: " + nome + ", Idade: " + idade + ", Loja: " + estabelecimento);
    }

    public double calcularMediaSalarial() {
        return historicoSalarios.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public double calcularGratificacao() {
        return salarioBase * 0.2;
    }
}

class Comprador {
    private String nome;
    private int idade;
    private String cidade;
    private String bairro;
    private String endereco;

    public Comprador(String nome, int idade, String cidade, String bairro, String endereco) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.endereco = endereco;
    }

    public void exibirInformacoes() {
        System.out.println("Nome: " + nome + ", Idade: " + idade);
    }
}

class Empresa {
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String endereco;
    private List<Funcionario> equipe;
    private List<Comprador> clientes;

    public Empresa(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String endereco) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.endereco = endereco;
        this.equipe = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        equipe.add(funcionario);
    }

    public void adicionarCliente(Comprador comprador) {
        clientes.add(comprador);
    }

    public int obterTotalClientes() {
        return clientes.size();
    }

    public int obterTotalFuncionarios() {
        return equipe.size();
    }

    public void exibirDadosEmpresa() {
        System.out.println("Nome Fantasia: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Localização: " + cidade + ", " + bairro + ", " + endereco);
    }
}

public class Principal {
    public static void main(String[] args) {
        Empresa negocio = new Empresa("My Plant", "My Plant Ltda", "12.345.678/0001-99", "São Paulo", "Centro", "Avenida das Palmeiras");

        Funcionario f1 = new Funcionario("Carlos", 40, "My Plant", "São Paulo", "Centro", "Avenida das Palmeiras", 2500.0, 2500.0, 2600.0, 2700.0);
        Funcionario f2 = new Funcionario("Ana", 78, "My Plant", "São Paulo", "Centro", "Rua das Orquídeas", 2200.0, 2200.0, 2250.0, 2300.0);

        Comprador c1 = new Comprador("Mariana", 35, "São Paulo", "Bela Vista", "Rua Azul");
        Comprador c2 = new Comprador("Roberto", 70, "São Paulo", "Moema", "Rua Verde");

        negocio.adicionarFuncionario(f1);
        negocio.adicionarFuncionario(f2);
        negocio.adicionarCliente(c1);
        negocio.adicionarCliente(c2);

        negocio.exibirDadosEmpresa();
        System.out.println("Número de funcionários: " + negocio.obterTotalFuncionarios());
        System.out.println("Número de clientes: " + negocio.obterTotalClientes());

        System.out.println("\nInformações da equipe:");
        f1.exibirDados();
        System.out.println("Média Salarial: " + f1.calcularMediaSalarial());
        System.out.println("Gratificação: " + f1.calcularGratificacao());

        f2.exibirDados();
        System.out.println("Média Salarial: " + f2.calcularMediaSalarial());
        System.out.println("Gratificação: " + f2.calcularGratificacao());

        System.out.println("\nInformações dos clientes:");
        c1.exibirInformacoes();
        c2.exibirInformacoes();
    }
}