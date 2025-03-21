import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Calculadora {
    static LocalDate date = LocalDate.now();
    static Scanner sc = new Scanner(System.in);
    static List<Produto> registros = new ArrayList<Produto>();

    public static void calcularPreco() {
        System.out.println("Qual valor do produto: ");
        double preco = sc.nextDouble();
        System.out.println("Quantidade do produto: ");
        int quantidade = sc.nextInt();
        double total = verificarDesconto(quantidade, preco);
        System.out.println("Total da compra: R$ " + total);
        System.out.println();

        Produto produto = new Produto(quantidade, preco, total, date);
        registros.add(produto);
    }

    public static void calcularTroco() {
        System.out.println("Valor da compra: ");
        double valorCompra = sc.nextDouble();
        System.out.println("Valor pago: ");
        double valorPago = sc.nextDouble();

        if (valorCompra > valorPago) {
            System.out.println("Faltam: R$ " + (valorCompra - valorPago));
        } else {
            System.out.println("Troco: R$ " + (valorPago - valorCompra));
        }
    }

    public static double verificarDesconto(int quantidade, double preco) {
        if (quantidade > 10) {
            return (preco * quantidade) * 0.95;
        } else {
            return preco * quantidade;
        }
    }

    public static void imprimirRegistros() {
        if (!registros.isEmpty()) {
            for (Produto produto : registros) {
                System.out.println(produto);
            }
        }
        System.out.println();
    }

    public static void buscarVendasData() {
        System.out.println("Buscar vendas: Insira uma data no formato dd/MM/yyyy");
        String data = sc.next();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate dataFormatada = LocalDate.parse(data, dateFormat);
            if (!registros.isEmpty()) {
                for (Produto produto : registros) {
                    if (produto.getDate().equals(dataFormatada)) {
                        System.out.println(produto);
                    } else {
                        System.out.println("Nenhuma venda neste dia");
                        System.out.println();
                    }
                }
                System.out.println();
            } else {
                System.out.println("Lista de vendas vazia!");
                System.out.println();
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    public static void fecharScanner() {
        sc.close();
    }
}


