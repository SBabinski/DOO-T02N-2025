public class Venda {

    private int id;
    private int qtd;
    private double valor;
    private double desconto;

     private int novoId;

    public Venda(int qtd, double valor, double desconto) {
        this.id = ++novoId;
        this.qtd = qtd;
        this.valor = valor;
        this.desconto = desconto;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id: " + id +
                " | valor: " + valor +
                " | quantidade: " + qtd +
                " | desconto:" + desconto +
                '}';
    }
}
