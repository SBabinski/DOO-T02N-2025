package sistemaSerie;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritas = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> paraAssistir = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
    public List<Serie> getFavoritas() { return favoritas; }
    public List<Serie> getAssistidas() { return assistidas; }
    public List<Serie> getParaAssistir() { return paraAssistir; }

    public void adicionarSerie(List<Serie> lista, Serie serie) {
        if (!lista.contains(serie)) lista.add(serie);
    }

    public void removerSerie(List<Serie> lista, Serie serie) {
        lista.remove(serie);
    }
}
