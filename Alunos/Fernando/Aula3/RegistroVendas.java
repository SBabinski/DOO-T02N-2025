package Aula3;;

public class RegistroVendas {

    private int quantidade;
    private double preco;
    private double total;
    private boolean comDesconto;

    public RegistroVendas(int quantidade, double preco, double total, boolean comDesconto) {
        this.quantidade = quantidade;
        this.preco = preco;
        this.total = total;
        this.comDesconto = comDesconto;
    }

    public RegistroVendas() {
       
    }

    public int getQuantidade() {
         return quantidade;
    }

    public double getPreco() {
         return preco;
    }

    public double getTotal() {
         return total;
    }

    public boolean getComDesconto() {
         return comDesconto;
    }

    public void setQuantidade(int quantidade) {
         this.quantidade = quantidade;
    }

    public void setPreco(double preco) {
         this.preco = preco;
    }

    public void setTotal(double total) {
         this.total = total;
    }

    public void setComDesconto(boolean comDesconto) {
         this.comDesconto = comDesconto;
    }

    @Override
    public String toString() {
        if (comDesconto == true) {
            return "[quantidade: " + quantidade + ", preco: " + preco + ", total: " + total + ", com 5% de desconto";
        } else {
            return "[quantidade: " + quantidade + ", preco: " + preco + ", total: " + total + "]";
        }
    }
    
}