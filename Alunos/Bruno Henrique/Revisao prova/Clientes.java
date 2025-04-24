import java.util.List;

public class Clientes {
    private String nome;
    private int idade;
    private String ocupacao;
    private List<Veiculo> listaVeiculos;

    public Clientes(String nome, int idade, String ocupacao, List<Veiculo> listaVeiculos) {
        this.nome = nome;
        this.idade = idade;
        this.ocupacao = ocupacao;
        this.listaVeiculos = listaVeiculos;
    }

    public Clientes() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public List<Veiculo> getListaVeiculos() {
        return listaVeiculos;
    }

    public void setListaVeiculos(List<Veiculo> listaVeiculos) {
        this.listaVeiculos = listaVeiculos;
    }
}
