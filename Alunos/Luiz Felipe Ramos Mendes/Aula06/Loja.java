import java.util.ArrayList;
import java.util.Scanner;

class Loja {

    private static String nomeFantasia = "PLantaria";
    private static String razaoSocial = "My Plant";
    private static String cpnj = "33172537/0001-98";
    private static String cidade = "SP";
    private static String bairro = "Itaim Bibi";
    private static String rua = "Faria Lima";
    private static ArrayList<Vendedor> vendedores = new ArrayList<>();
    private static ArrayList<Cliente> clientes = new ArrayList<>();

    public static void cadastroVendedor() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do vendedor:");
        String nome = scanner.nextLine();

        System.out.println("Digite a idade do vendedor:");
        int idade = scanner.nextInt();
        scanner.nextLine();  

        System.out.println("Digite o nome da loja:");
        String loja = scanner.nextLine();

        System.out.println("Digite a cidade do vendedor:");
        String cidade = scanner.nextLine();

        System.out.println("Digite o bairro do vendedor:");
        String bairro = scanner.nextLine();

        System.out.println("Digite a rua do vendedor:");
        String rua = scanner.nextLine();

        System.out.println("Digite o salário base do vendedor:");
        double salarioBase = scanner.nextDouble();


        double[] salarioRecebido = {1200, 1300, 1400};

        Vendedor vendedor = new Vendedor(nome, idade, loja, cidade, bairro, rua, salarioBase, salarioRecebido);
        vendedores.add(vendedor);
    }

    public static void cadastroCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();

        System.out.println("Digite a idade do cliente:");
        int idade = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite a cidade do cliente:");
        String cidade = scanner.nextLine();

        System.out.println("Digite o bairro do cliente:");
        String bairro = scanner.nextLine();

        System.out.println("Digite a rua do cliente:");
        String rua = scanner.nextLine();

        Cliente cliente = new Cliente(nome, idade, cidade, bairro, rua);
        clientes.add(cliente);
    }

    public static void mostrarVendedor() {
        for (Vendedor vendedor : vendedores) {
            vendedor.apresentar();
        }
    }

    public static void mostrarCliente() {
        for (Cliente cliente : clientes) {
            cliente.apresentar();
        }
    }

    public static void calcularMediaSalarial() {
        for (Vendedor vendedor : vendedores) {
            vendedor.calcularMedia();
        }
    }

    public static void calcularSalarioComBonus() {
        for (Vendedor vendedor : vendedores) {
            System.out.println("Salário com bônus do vendedor " + vendedor.toString() + ": " + vendedor.calcularSalarioComBonus());
        }
    }

    public static void apresentarLoja() {
        System.out.println("Nome da Fantasia: " + nomeFantasia);
        System.out.println("CNPJ: " + cpnj);
        System.out.println("Endereço: " + cidade + ", " + bairro + ", " + rua);
    }

    public static int contarClientes() {
        return clientes.size();
    }

    public static int contarVendedores() {
        return vendedores.size();
    }
}
