import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<VendaHistorico> vendasHistorico = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            menu();
        }
    }

    private static void menu() {
        System.out.println("""
                1 - Realizar Venda
                2 - Calcular Troco
                3 - Ver Histórico de Venda
                4 - Ver Vendas Por Dia e Mes
                0 - Sair
                Digite sua escolha:\s""");
        var escolha = scanner.nextInt();
        escolha(escolha);
    }

    private static void escolha(int escolha) {
        switch (escolha) {
            case 1:
                realizarVenda();
                break;
            case 2:
                calculaTroco();
                break;
            case 3:
                verHistorico();
                break;
            case 4:
                verVendasPorDiaEMes();
                break;
            case 0:
                System.exit(0);
        }
    }

    private static void verVendasPorDiaEMes() {

        String opcoes = """
                Selecione o mês:
                1 - Janeiro
                2 - Fevereiro
                3 - Março
                4 - Abril
                5 - Maio
                6 - Junho
                7 - Julho
                8 - Agosto
                9 - Setembro
                10 - Outubro
                11 - Novembro
                12 - Dezembro
                """;

        System.out.println(opcoes);
        System.out.print("Digite o número do mês: ");

        int mes = scanner.nextInt();

        String mesSelecionado = switch (mes) {
            case 1 -> "Janeiro";
            case 2 -> "Fevereiro";
            case 3 -> "Março";
            case 4 -> "Abril";
            case 5 -> "Maio";
            case 6 -> "Junho";
            case 7 -> "Julho";
            case 8 -> "Agosto";
            case 9 -> "Setembro";
            case 10 -> "Outubro";
            case 11 -> "Novembro";
            case 12 -> "Dezembro";
            default -> null;
        };

        if (mesSelecionado != null) {
            System.out.println("Você selecionou: " + mesSelecionado);
        } else {
            System.out.println("Mês inválido! Escolha um número entre 1 e 12.");
        }

        System.out.println("Digite o número do dia: ");

        int dia = scanner.nextInt();

        var vendaFiltradaEOrdenada = new ArrayList<VendaHistorico>();
        for (VendaHistorico vendaHistorico : vendasHistorico) {
            if ((vendaHistorico.getLocalDateTime().getDayOfMonth())== dia && vendaHistorico.getLocalDateTime().getMonthValue() == mes) {
                vendaFiltradaEOrdenada.add(vendaHistorico);
            }
        }
        vendaFiltradaEOrdenada.sort(Comparator.comparing(VendaHistorico::getLocalDateTime));
        System.out.println(vendaFiltradaEOrdenada);
    }

    private static void verHistorico() {
        vendasHistorico.forEach(System.out::println);
    }

    private static void realizarVenda() {
        System.out.println("Digite a quantidade que será comprada: ");
        var quantidade = scanner.nextInt();
        System.out.println("Digite o preço:");
        var preco = scanner.nextDouble();
        var valorASerPago = quantidade * preco;
        var desconto = 0.0;
        if (quantidade > 10) {
            desconto = ((valorASerPago / 100) * 5);
            valorASerPago -= desconto;
        }
        System.out.println("O valor a ser pago é: R$" + valorASerPago);
        vendasHistorico.add(new VendaHistorico(quantidade, valorASerPago, desconto));
    }

    private static void calculaTroco() {
        System.out.println("Digite o valor recebido: ");
        var valorRecebido = scanner.nextDouble();
        System.out.println("Digite o valor total da compra: ");
        var valorTotal = scanner.nextDouble();
        System.out.println("O valor do troco é: R$" + (valorRecebido - valorTotal));
    }
}