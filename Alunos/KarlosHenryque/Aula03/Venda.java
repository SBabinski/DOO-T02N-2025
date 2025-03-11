public class Venda {
    int quantidade;
    double valorUnitario;
    double valorTotal;
    double valorComDesconto;

    public Venda(int quantidade, double valorUnitario, double valorTotal, double valorComDesconto){
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
        this.valorComDesconto = valorComDesconto;
        
    }
    
    public void exibirVenda() {
        System.out.println("Quantidade de plantas: " + quantidade);
        System.out.println("Valor unitário: R$ " + valorUnitario);
        System.out.println("Valor total sem desconto: R$ " + valorTotal);
        System.out.println("Valor com desconto (se aplicável): R$ " + valorComDesconto);
        System.out.println("---------------------------------------------------");
    }
}