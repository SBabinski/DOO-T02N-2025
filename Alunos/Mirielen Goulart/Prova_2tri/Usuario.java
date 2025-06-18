package seriesTV;

public class Usuario {

    private String nome;
    private ListaSeries listas;

    public Usuario(String nome) {
        this.nome = nome;
        this.listas = new ListaSeries();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ListaSeries getListas() {
        return listas;
    }

    public void setListas(ListaSeries listas) {
        this.listas = listas;
    }

    @Override
    public String toString() {
        return "Usuario: '" + nome + "'";
    }
}
