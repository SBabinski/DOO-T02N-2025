public class VendaDiaria {
    private int dia;
    private int mes;
    private int quantidade;

    public VendaDiaria(int dia, int mes, int quantidade) {
        this.dia = dia;
        this.mes = mes;
        this.quantidade = quantidade;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void adicionarQuantidade(int qtd) {
        this.quantidade += qtd;
    }
}
