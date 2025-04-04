import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Loja {
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String rua;
    private ArrayList<Vendedor> vendedores;
    private ArrayList<Cliente> clientes;
    static Scanner sc = new Scanner(System.in);

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

    public void apresentarse() {
        System.out.println("Nome Fantasia: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + rua + ", " + bairro + ", " + cidade);
    }

    public void contarClientes() {
        System.out.println("Quantidade de clientes: " + clientes.size());
    }

    public void contarVendedores() {
        System.out.println("Quantidade de vendedores: " + vendedores.size());
    }

    public void cadastrarVendedor() {
        System.out.println("Insira o nome do Vendedor: ");
        String nome = sc.next();
        System.out.println("Insira a idade do Vendedor: ");
        int idade = sc.nextInt();
        System.out.println("Insira a cidade do Vendedor: ");
        String cidade = sc.next();
        System.out.println("Insira o bairro do Vendedor: ");
        String bairro = sc.next();
        System.out.println("Insira a rua do Vendedor: ");
        String rua = sc.next();
        System.out.println("Insira o salário base do Vendedor: ");
        double salarioBase = sc.nextDouble();
        double[] salariosRecebidos = {3000, 3200, 3100};
        Vendedor vendedor = new Vendedor(nome, idade, nomeFantasia, cidade, bairro, rua, salarioBase, salariosRecebidos);
        vendedores.add(vendedor);

        System.out.println("Média Salarial: " + vendedor.calcularMedia());
        System.out.println("Bônus: " + vendedor.calcularBonus());
    }

    public void cadastrarCliente() {
        System.out.println("Insira o nome do Cliente: ");
        String nome = sc.next();
        System.out.println("Insira a idade do Cliente: ");
        int idade = sc.nextInt();
        System.out.println("Insira a cidade do Cliente: ");
        String cidade = sc.next();
        System.out.println("Insira o bairro do Cliente: ");
        String bairro = sc.next();
        System.out.println("Insira a rua do Cliente: ");
        String rua = sc.next();
        Cliente cliente = new Cliente(nome, idade, cidade, bairro, rua);
        clientes.add(cliente);

        cliente.apresentarse();
    }
}
