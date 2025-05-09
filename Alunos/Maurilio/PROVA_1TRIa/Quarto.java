import java.util.ArrayList;
import java.util.List;

public abstract class Quarto {
    private int tipo;
    private int num;
    private double valor;
    private boolean disponibilidade;
    private List<Cliente> listquartocliente;

    public Quarto(int tipo, int num, double valor) {
        this.tipo = tipo;
        this.num = num;
        this.valor = valor;
        this.disponibilidade = true;
        this.listquartocliente = new ArrayList<>();
    }

    public int getTipo() {
        return tipo;
    }

    public int getNum() {
        return num;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public List<Cliente> getListquartocliente() {
        return listquartocliente;
    }

    public void adicionarClienteQuarto(Cliente cliente) {
        listquartocliente.add(cliente);
    }

    public void removerCliente(Cliente cliente) {
        listquartocliente.remove(cliente);
    }

    @Override
    public String toString() {
        return "Quarto{" +
                "tipo=" + tipo +
                ", número=" + num +
                ", valor=" + valor +
                ", disponibilidade=" + disponibilidade +
                ", clientes=" + listquartocliente +
                '}';
    }
}
