import java.time.LocalDate;

public class Venda {
    int quantidade;
    double valorUnitario;
    double valorTotal;
    double valorComDesconto;
    LocalDate dataVenda;

    public Venda(int quantidade, double valorUnitario, double valorTotal, double valorComDesconto, LocalDate dataVenda){
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
        this.valorComDesconto = valorComDesconto;
        this.dataVenda = dataVenda;
    }
    
    public void exibirVenda() {
        System.out.println("Data da venda: " + dataVenda);
        System.out.println("Quantidade de plantas: " + quantidade);
        System.out.println("Valor unitário: R$ " + valorUnitario);
        System.out.println("Valor total sem desconto: R$ " + valorTotal);
        System.out.println("Valor com desconto (se aplicável): R$ " + valorComDesconto);
        System.out.println("---------------------------------------------------");
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }
}
