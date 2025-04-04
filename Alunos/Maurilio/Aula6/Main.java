import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Loja loja = new Loja("Nome Fantasia", "Razão Social", "00.000.000/0001-00", "Cidade", "Bairro", "Rua");
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("1 - Apresentar Loja");
            System.out.println("2 - Cadastrar Vendedor");
            System.out.println("3 - Cadastrar Cliente");
            System.out.println("4 - Contar Vendedores");
            System.out.println("5 - Contar Clientes");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    loja.apresentarse();
                    break;
                case 2:
                    loja.cadastrarVendedor();
                    break;
                case 3:
                    loja.cadastrarCliente();
                    break;
                case 4:
                    loja.contarVendedores();
                    break;
                case 5:
                    loja.contarClientes();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }
}
