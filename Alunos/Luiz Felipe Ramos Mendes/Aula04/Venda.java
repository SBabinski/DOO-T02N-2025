import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Venda {
    int quantPlanta;
    int valorPlanta;
    int valorTotal;
    double desconto;
    double valorComDesconto;
    String data;

    public Venda(int quantPlanta, int valorPlanta, int valorTotal, double desconto, double valorComDesconto, String data) {
        this.quantPlanta = quantPlanta;
        this.valorPlanta = valorPlanta;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
        this.valorComDesconto = valorComDesconto;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data: " + data + ", Quantidade: " + quantPlanta + ", Preço Unidade: R$ " + valorPlanta + ", Valor Total: R$ " + valorTotal + ", Desconto: R$ " + desconto + ", Total com Desconto: R$ " + valorComDesconto;
    }
}