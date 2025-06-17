import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();

        System.out.println("\n===== DEMONSTRAÇÃO MY PLANT =====");

        Double[] salarios = {1500.0, 1600.0, 1700.0};
        Vendedor vendedor1 = new Vendedor("João Silva", 25, "My Plant Central", "São Paulo", "Centro", "Rua das Flores", 1500.0, salarios);
        vendedor1.apresentarse();
        vendedor1.calcularMedia();
        vendedor1.calcularBonus();

        Cliente cliente1 = new Cliente("Maria Souza", 30, "São Paulo", "Jardins", "Av. Paulista");
        cliente1.apresentarse();

        Loja loja = new Loja("My Plant", "My Plant Plantas LTDA", "12.345.678/0001-90",
                          "São Paulo", "Centro", "Rua dos Jardins");
        loja.adicionarVendedor(vendedor1);
        loja.adicionarCliente(cliente1);
        loja.apresentarLoja();
        loja.contarVendedores();
        loja.contarClientes();

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
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 4);

        calculadora.sc.close();
    }
}

class Vendedor {
    private String nome;
    private int idade;
    private String loja;
    private String cidade;
    private String bairro;
    private String rua;
    private Double salarioBase;
    private Double salarioRecebido[];

    public Vendedor(String nome, int idade, String loja, String cidade, String bairro, String rua, Double salarioBase,
            Double[] salarioRecebido) {
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
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja);
    }

    public void calcularMedia() {
        double soma = 0.0;
        for (int i = 0; i < salarioRecebido.length; i++) {
            soma += salarioRecebido[i];
        }
        double media = soma / salarioRecebido.length;
        System.out.println("Média de vendas: " + media);
    }

    public void calcularBonus() {
        double bonus = 0.0;
        bonus = salarioBase * 0.2;

        System.out.println("Bônus: " + bonus);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(Double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Double[] getSalarioRecebido() {
        return salarioRecebido;
    }

    public void setSalarioRecebido(Double[] salarioRecebido) {
        this.salarioRecebido = salarioRecebido;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Cidade: " + cidade);
        System.out.println("Bairro: " + bairro);
        System.out.println("Rua: " + rua);
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

    public void contarClientes() {
        System.out.println("Total de clientes: " + clientes.size());
    }

    public void contarVendedores() {
        System.out.println("Total de vendedores: " + vendedores.size());
    }

    public void apresentarLoja() {
        System.out.println("Nome Fantasia: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Cidade: " + cidade);
        System.out.println("Bairro: " + bairro);
        System.out.println("Rua: " + rua);
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(List<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }


}

class Venda {
    private int quantidadePlantas;
    private double precoUnitario;
    private double valorTotal;
    private double desconto;

    public Venda(int quantidadePlantas, double precoUnitario, double valorTotal, double desconto) {
        this.quantidadePlantas = quantidadePlantas;
        this.precoUnitario = precoUnitario;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
    }

    // Getters
    public int getQuantidadePlantas() {
        return quantidadePlantas;
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

    @Override
    public String toString() {
        return "Quantidade: " + quantidadePlantas +
               " | Preço unitário: R$" + String.format("%.2f", precoUnitario) +
               " | Desconto: R$" + String.format("%.2f", desconto) +
               " | Valor total: R$" + String.format("%.2f", valorTotal);
    }
}

class Calculadora {
    public Scanner sc = new Scanner(System.in);
    private List<Venda> historicoVendas;

    public Calculadora() {
        this.historicoVendas = new ArrayList<>();
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

    public int menu() {
        System.out.println("\n===== CALCULADORA DONA GABRIELINHA =====");
        System.out.println("[1] - Calcular Preço Total");
        System.out.println("[2] - Calcular Troco");
        System.out.println("[3] - Exibir Histórico de Vendas");
        System.out.println("[4] - Sair");
        System.out.print("Escolha uma opção: ");
        return sc.nextInt();
    }
}
