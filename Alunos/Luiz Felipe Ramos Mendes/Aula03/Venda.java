import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Venda {
    int quantPlanta;
    int valorPlanta;
    int valorTotal;
    double desconto;
    double valorComDesconto;

    public Venda(int quantPlanta, int valorPlanta, int valorTotal, double desconto, double valorComDesconto) {
        this.quantPlanta = quantPlanta;
        this.valorPlanta = valorPlanta;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
        this.valorComDesconto = valorComDesconto;
    }

    @Override
    public String toString() {
        return "Quantidade: " + quantPlanta + ", Preço Unidade: R$ " + valorPlanta + ", Valor Total: R$ " + valorTotal + ", Desconto: R$ " + desconto + ", Total com Desconto: R$ " + valorComDesconto;
    }
}