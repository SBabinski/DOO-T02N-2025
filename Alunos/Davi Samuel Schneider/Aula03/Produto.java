public class Produto {
    private int quantidade;
    private double preco;
    private double total;

    public Produto(int quantidade, double preco, double total) {
        this.quantidade = quantidade;
        this.preco = preco;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "quantidade=" + quantidade +
                ", preco=" + preco +
                ", total=" + total +
                '}';
    }
}
