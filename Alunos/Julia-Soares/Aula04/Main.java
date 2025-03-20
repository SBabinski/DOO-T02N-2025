import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        calcular();
    }

    public static void calcular() {
        Calculos calculos = new Calculos();
        Scanner scanner = new Scanner(System.in);

        boolean executando = true;

        while (executando) {
            System.out.println("\n--Iniciando Calculadora--\n");
            System.out.println("1 - Calcular valor total da compra");
            System.out.println("2 - Mostrar troco à ser devolvido");
            System.out.println("3 - Mostrar registros de vendas");
            System.out.println("4 - Buscar quantidade de vendas por dia/mês");
            System.out.println("5 - Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    calculos.calcularPrecoTotal();
                    break;
                case 2:
                    calculos.calcularTroco();
                    break;
                case 3:
                    calculos.mostrarVendas();
                    break;
                case 4:
                    calculos.buscarVendaPorData();
                    break;
                case 5:
                    executando = false;
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente!");
                    break;
            }
        }
    }
}
