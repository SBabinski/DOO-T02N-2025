package Aula11;

public class Produto {
    private String nome;
    private Double preco;

    public Produto(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public Double getPreco() {
        return preco;
    }

    public void print() {
        System.out.println("Nome: " + nome + " Preco: " + preco);
    }
}
