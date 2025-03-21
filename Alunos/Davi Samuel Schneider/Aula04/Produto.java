import java.time.LocalDate;

public class Produto {
    private final LocalDate date;
    private int quantidade;
    private double preco;
    private double total;

    public Produto(int quantidade, double preco, double total, LocalDate date) {
        this.quantidade = quantidade;
        this.preco = preco;
        this.total = total;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "Produto{" +
                "date=" + date +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", total=" + total +
                '}';
    }
}
