import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Precototal {

    private int quantidadevendas;
    private double valorvenda;
    private double valordesconto;
    private LocalDate data;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static List<Precototal> listavendas = new ArrayList<>();

    public Precototal(int quantidadevendas, double valorvenda, double valordesconto) {
        this.quantidadevendas = quantidadevendas;
        this.valorvenda = valorvenda;
        this.valordesconto = valordesconto;
        this.data = LocalDate.now();
    }

    public static void calcularPrecototal() {


        Scanner sc = new Scanner(System.in);

        double valortotal = 0;
        double valordesconto = 0;

        System.out.println("Quantas plantas foram vendidas?");
        int vendas = sc.nextInt();


        System.out.println("Qual o valor de cada planta?");
        double valor = sc.nextDouble();

        valortotal = valor * vendas;

        if (vendas > 10) {

            valordesconto = valortotal * 0.05;
            valortotal = valortotal - valordesconto;
        }


        LocalDate data = LocalDate.now();

        Precototal venda = new Precototal(vendas, valortotal, valordesconto);

        listavendas.add(venda);


        System.out.println(venda);

        CalcularTroco();
    }

    public static void CalcularTroco() {
        Scanner sc = new Scanner(System.in);


        if (listavendas.isEmpty()) {
            System.out.printf(".Nenhuma venda registrada. Lista vazia\n");
            return;
        }

        Precototal ultimavenda = listavendas.get(listavendas.size() - 1);
        System.out.println("O valor total da venda é:" + ultimavenda.getValorvenda());

        double troco = -1;


            System.out.println("Digite o valor que o cliente pagou:");
            double valorpagocliente = sc.nextDouble();


            troco = valorpagocliente - ultimavenda.getValorvenda();



        while (troco < 0){

            double falta = -troco;


            System.out.println("Valor incorreto, ainda faltam:" + "R$" + falta);

            System.out.println("Digite o valor que o cliente pagou novamente:");
            valorpagocliente = sc.nextDouble();

            troco = valorpagocliente - ultimavenda.getValorvenda();
        }

        if (troco > 0) {
            System.out.println("Valor do troco: " + "R$" + troco);
        }

        Main.Menu.mostrarMenu();
    }

    public static void PesquisarData() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o mês para pesquisa:");
        int mes = sc.nextInt();
        while (mes < 1 || mes > 12) {
            System.out.println("Mês inválido digite o mês de (1 a 12)");
            mes = sc.nextInt();
        }

        System.out.println("Deseja buscar um dia especifico? (S/N)");
        char opcao = sc.next().toUpperCase().charAt(0);
        int dia = 0;

        if (opcao == 'S') {
            System.out.println("Digite um número de (1 - 31)");
            dia = sc.nextInt();

            while (dia < 1 || dia > 31) {
                System.out.println("Dia Inválido. Digite um valor entre 1 e 31");
                dia = sc.nextInt();
            }
        }

        int Totalvendas = 0;
        double totalValor = 0;
        boolean vendasencontradas = false;

        System.out.println("\nVendas encontradas:");
        for (Precototal venda : listavendas) {
            int mesVenda = venda.getData().getMonthValue();
            int diaVenda = venda.getData().getDayOfMonth();

            if (mesVenda == mes && (opcao == 'N' || diaVenda == dia)) {
                System.out.println(venda.toString());
                Totalvendas += venda.getQuantidadevendas();
                totalValor += venda.getValorvenda();
                vendasencontradas = true;
            }
        }


        if (!vendasencontradas) {
            System.out.println("Nenhuma venda encontrada para o valor informado!");
        } else {
            System.out.println("\n------- Venda encontrada com sucesso! -------");
            System.out.println("\n----Resumo Vendas----");
            System.out.println("\nTotal de vendas:" + Totalvendas);
            System.out.println("Valor total das vendas:" + totalValor +"\n\n");
        }


        Main.Menu.mostrarMenu();
    }

    @Override
    public String toString() {


        return "Precototal{" +
                "Quantidade de Plantas=" + quantidadevendas +
                ", Valor Total=" + valorvenda +
                ", Valor do Desconto=" + valordesconto +
                ", Data=" + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                '}';
    }

    public int getQuantidadevendas() {
        return quantidadevendas;
    }

    public void setQuantidadevendas(int quantidadevendas) {
        this.quantidadevendas = quantidadevendas;
    }

    public double getValorvenda() {
        return valorvenda;
    }

    public void setValorvenda(double valorvenda) {
        this.valorvenda = valorvenda;
    }

    public double getValordesconto() {
        return valordesconto;
    }

    public void setValordesconto(double valordesconto) {
        this.valordesconto = valordesconto;
    }

    public static List<Precototal> getListavendas() {
        return listavendas;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setListavendas(List<Precototal> listavendas) {
        Precototal.listavendas = listavendas;
    }
}
