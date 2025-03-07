import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            menu();
        }
    }

    private static void menu() {
        System.out.println("""
                1 - Calcular Preço Total
                2 - Calcular Troco
                3 - Sair
                Digite sua escolha: """);
        var escolha = scanner.nextInt();
        escolha(escolha);
    }

    private static void escolha(int escolha) {
        switch (escolha) {
            case 1:
                calculaPreco();
                break;
            case 2:
                calculaTroco();
                break;
            case 3:
                System.exit(0);
        }
    }

    private static void calculaPreco() {
        System.out.println("Digite a quantidade que será comprada: ");
        var quantidade = scanner.nextInt();
        System.out.println("Digite o preço:");
        var preco = scanner.nextDouble();
        System.out.println("O valor a ser pago é: R$" + (quantidade * preco));
    }

    private static void calculaTroco() {
        System.out.println("Digite o valor recebido: ");
        var valorRecebido = scanner.nextDouble();
        System.out.println("Digite o valor total da compra: ");
        var valorTotal = scanner.nextDouble();
        System.out.println("O valor do troco é: R$" + (valorTotal - valorRecebido));
    }
}