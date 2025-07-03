import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritos;
    private List<Serie> assistidas;
    private List<Serie> desejoAssistir;

    public Usuario() {
        this.favoritos = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.desejoAssistir = new ArrayList<>();
    }

    public Usuario(String nome) {
        this();
        this.nome = nome;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Serie> favoritos) {
        this.favoritos = favoritos != null ? favoritos : new ArrayList<>();
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public void setAssistidas(List<Serie> assistidas) {
        this.assistidas = assistidas != null ? assistidas : new ArrayList<>();
    }

    public List<Serie> getDesejoAssistir() {
        return desejoAssistir;
    }

    public void setDesejoAssistir(List<Serie> desejoAssistir) {
        this.desejoAssistir = desejoAssistir != null ? desejoAssistir : new ArrayList<>();
    }

    // Métodos para gerenciar favoritos
    public boolean adicionarFavorito(Serie serie) {
        if (serie != null && !favoritos.contains(serie)) {
            return favoritos.add(serie);
        }
        return false;
    }

    public boolean removerFavorito(Serie serie) {
        return favoritos.remove(serie);
    }

    // Métodos para gerenciar assistidas
    public boolean adicionarAssistida(Serie serie) {
        if (serie != null && !assistidas.contains(serie)) {
            return assistidas.add(serie);
        }
        return false;
    }

    public boolean removerAssistida(Serie serie) {
        return assistidas.remove(serie);
    }

    // Métodos para gerenciar desejo assistir
    public boolean adicionarDesejoAssistir(Serie serie) {
        if (serie != null && !desejoAssistir.contains(serie)) {
            return desejoAssistir.add(serie);
        }
        return false;
    }

    public boolean removerDesejoAssistir(Serie serie) {
        return desejoAssistir.remove(serie);
    }
}