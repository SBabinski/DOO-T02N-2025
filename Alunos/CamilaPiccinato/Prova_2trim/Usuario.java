import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nome;
    private List<Serie> favoritos = new ArrayList<>();
    private List<Serie> assistidos = new ArrayList<>();
    private List<Serie> paraAssistir = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public List<Serie> getAssistidos() {
        return assistidos;
    }

    public List<Serie> getParaAssistir() {
        return paraAssistir;
    }
    
}
