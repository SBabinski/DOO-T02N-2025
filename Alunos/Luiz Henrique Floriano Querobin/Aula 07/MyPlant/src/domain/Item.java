package domain;

public class Item {
    private Integer id;
    private String nome;
    private String tipo;
    private Double valor;

    public Item(Integer id, String nome, String tipo, Double valor) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    public void gerarDescricao() {
        System.out.printf("Id do Item: %s - Nome: %s - Tipo: %s - Valor: %.2f\n", id, nome, tipo, valor);
    }

    public Double getValor() {
        return valor;
    }
}
