import java.util.Scanner;

class Vendedor {
    private String nome;
    private int idade;
    private String loja;
    private String cidade;
    private String bairro;
    private String rua;
    private double salarioBase;
    private double[] salarioRecebido = {1500.0, 1600.0, 1700.0};

    public Vendedor(String nome, int idade, String loja, String cidade, String bairro, String rua, double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + ", Idade: " + idade + ", Loja: " + loja);
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
    private Vendedor[] vendedores;
    private Cliente[] clientes;
    private int totalVendedores = 0;
    private int totalClientes = 0;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua, int capacidadeVendedores, int capacidadeClientes) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.vendedores = new Vendedor[capacidadeVendedores];
        this.clientes = new Cliente[capacidadeClientes];
    }

    public int contarClientes() {
        return totalClientes;
    }

    public int contarVendedores() {
        return totalVendedores;
    }

    public void apresentarse() {
        System.out.println("Nome Fantasia: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + rua + ", " + bairro + ", " + cidade);
    }
}

public class CalculadoraPlantas {
    private static final int MESES = 12;
    private static final int DIAS = 31;
    private static int[][] vendas = new int[MESES][DIAS];

    public static double calcularPrecoTotal(int quantidade, double precoUnitario) {
        return quantidade * precoUnitario;
    }

    public static double calcularDesconto(int quantidade, double precoUnitario) {
        double total = quantidade * precoUnitario;
        return (quantidade > 10) ? total * 0.05 : 0.0;
    }

    public static double calcularTroco(double valorPago, double totalCompra) {
        return valorPago - totalCompra;
    }

    public static void registrarVenda(int mes, int dia, int quantidade) {
        if (mes >= 1 && mes <= 12 && dia >= 1 && dia <= 31) {
            vendas[mes - 1][dia - 1] += quantidade;
        } else {
            System.out.println("Data inválida!");
        }
    }

    public static int consultarVendas(int mes, int dia) {
        if (mes >= 1 && mes <= 12 && dia >= 1 && dia <= 31) {
            return vendas[mes - 1][dia - 1];
        }
        System.out.println("Data inválida!");
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        int totalPlantasVendidas = 0;
        double valorTotalVendas = 0.0;
        double totalDescontosAplicados = 0.0;

        do {
            System.out.println("--- Calculadora da Dona Gabrielinha ---");
            System.out.println("[1] - Registrar Venda");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Exibir Registro de Vendas");
            System.out.println("[4] - Consultar Vendas por Dia e Mês");
            System.out.println("[5] - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Informe a quantidade de plantas: ");
                    int quantidade = scanner.nextInt();
                    System.out.print("Informe o preço unitário da planta: ");
                    double precoUnitario = scanner.nextDouble();
                    System.out.print("Informe o mês da venda (1-12): ");
                    int mes = scanner.nextInt();
                    System.out.print("Informe o dia da venda (1-31): ");
                    int dia = scanner.nextInt();

                    double desconto = calcularDesconto(quantidade, precoUnitario);
                    double totalCompra = calcularPrecoTotal(quantidade, precoUnitario) - desconto;
                    System.out.println("O preço total da compra é: R$ " + totalCompra);
                    if (desconto > 0) {
                        System.out.println("Um desconto de R$ " + desconto + " foi aplicado. \n");
                    }

                    totalPlantasVendidas += quantidade;
                    valorTotalVendas += totalCompra;
                    totalDescontosAplicados += desconto;
                    registrarVenda(mes, dia, quantidade);
                    break;

                case 2:
                    System.out.print("Informe o valor pago pelo cliente: ");
                    double valorPago = scanner.nextDouble();
                    System.out.print("Informe o valor total da compra: ");
                    double totalCompraParaTroco = scanner.nextDouble();
                    double troco = calcularTroco(valorPago, totalCompraParaTroco);
                    System.out.println("O troco a ser dado ao cliente é de: R$ " + troco);
                    break;

                case 3:
                    System.out.println("--- Registro de Vendas ---");
                    System.out.println("Total de plantas vendidas: " + totalPlantasVendidas);
                    System.out.println("Valor total das vendas: R$ " + valorTotalVendas);
                    System.out.println("Total de descontos aplicados: R$ " + totalDescontosAplicados);
                    break;

                case 4:
                    System.out.print("Informe o mês da venda (1-12): ");
                    int mesConsulta = scanner.nextInt();
                    System.out.print("Informe o dia da venda (1-31): ");
                    int diaConsulta = scanner.nextInt();
                    int vendasDoDia = consultarVendas(mesConsulta, diaConsulta);
                    if (vendasDoDia != -1) {
                        System.out.println("Quantidade de vendas no dia " + diaConsulta + "/" + mesConsulta + ": " + vendasDoDia + "\n");
                    }
                    break;

                case 5:
                    System.out.println("Saindo da calculadora, até mais Dona Gabrielinha!");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 5);

        scanner.close();
    }
}
