import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Loja {
    private static String nomeFantasia = "My Plant";
    private static String razaoSocial = "My Plant";
    private static String cnpj = "1234567890";
    private static String cidade = "Teste", bairro = "Teste", rua = "Teste";
    private static List<Vendedor> vendedores = new ArrayList<Vendedor>();
    private static List<Cliente> clientes = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public static void cadastrarVendedor() {
        System.out.println("Nome: ");
        String nome = sc.nextLine();
        System.out.println("Idade: ");
        int idade = sc.nextInt();
        System.out.println("Cidade: ");
        String cidade = sc.nextLine();
        sc.next();
        System.out.println("Bairro: ");
        String bairro = sc.nextLine();
        sc.next();
        System.out.println("Rua: ");
        String rua = sc.nextLine();
        sc.next();
        System.out.println("Salário: ");
        double salarioBase = sc.nextDouble();

        Vendedor vendedor = new Vendedor(nome, idade, cidade, bairro, rua, salarioBase);
        vendedores.add(vendedor);
        System.out.println("Vendedor cadastrado com sucesso!");
    }

    public static void cadastrarCliente() {
        System.out.println("Nome: ");
        String nome = sc.next();
        System.out.println("Idade: ");
        int idade = sc.nextInt();
        System.out.println("Cidade: ");
        String cidade = sc.nextLine();
        sc.next();
        System.out.println("Bairro: ");
        String bairro = sc.nextLine();
        sc.next();
        System.out.println("Rua: ");
        String rua = sc.nextLine();


        Cliente cliente = new Cliente(nome, idade, cidade, bairro, rua);
        clientes.add(cliente);
        System.out.println("Cliente adicionado com sucesso!");
    }

    public static void apresentarVendedores() {
        for (Vendedor vendedor : vendedores) {
            System.out.println(vendedor);
        }
    }

    public static void apresentarClientes() {
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public static void calcularMediaPeloNome() {
        System.out.println("Calcular média salarial pelo nome: ");
        String nome = sc.next();

        for (Vendedor vendedor : vendedores) {
            if (vendedor.getNome().equals(nome)) {
                System.out.println(vendedor.calcularMedia());
            } else {
                System.out.println("Vendedor não encontrado!");
            }
        }
    }

    public static void calcularBonusPeloNome() {
        System.out.println("Calcular bônus salarial");
        System.out.println("Nome: ");
        String nome = sc.next();

        for (Vendedor vendedor : vendedores) {
            if (vendedor.getNome().equals(nome)) {
                System.out.println(vendedor.calcularBonus());
            }
        }
    }

    public static int contarVendedores() {
        return vendedores.size();
    }

    public static int contarClientes() {
        return clientes.size();
    }


    public static void apresentarse() {
        System.out.println("Loja{" +
                "cidade='" + cidade + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}');
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

}
