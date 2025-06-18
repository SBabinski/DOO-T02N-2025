import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um usuário do sistema
 */
public class Usuario {
    private String nome;
    private List<Serie> favoritos;
    private List<Serie> assistidas;
    private List<Serie> paraAssistir;

    public Usuario(String nome) {
        this.nome = nome;
        this.favoritos = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.paraAssistir = new ArrayList<>();
    }

    // Construtor padrão para JSON
    public Usuario() {
        this.favoritos = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.paraAssistir = new ArrayList<>();
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Serie> getFavoritos() { return favoritos; }
    public void setFavoritos(List<Serie> favoritos) { this.favoritos = favoritos; }

    public List<Serie> getAssistidas() { return assistidas; }
    public void setAssistidas(List<Serie> assistidas) { this.assistidas = assistidas; }

    public List<Serie> getParaAssistir() { return paraAssistir; }
    public void setParaAssistir(List<Serie> paraAssistir) { this.paraAssistir = paraAssistir; }

    // Métodos para gerenciar favoritos
    public boolean adicionarFavorito(Serie serie) {
        if (!favoritos.contains(serie)) {
            favoritos.add(serie);
            return true;
        }
        return false;
    }

    public boolean removerFavorito(Serie serie) {
        return favoritos.remove(serie);
    }

    public boolean isFavorito(Serie serie) {
        return favoritos.contains(serie);
    }

    // Métodos para gerenciar séries assistidas
    public boolean adicionarAssistida(Serie serie) {
        if (!assistidas.contains(serie)) {
            assistidas.add(serie);
            return true;
        }
        return false;
    }

    public boolean removerAssistida(Serie serie) {
        return assistidas.remove(serie);
    }

    public boolean isAssistida(Serie serie) {
        return assistidas.contains(serie);
    }

    // Métodos para gerenciar séries para assistir
    public boolean adicionarParaAssistir(Serie serie) {
        if (!paraAssistir.contains(serie)) {
            paraAssistir.add(serie);
            return true;
        }
        return false;
    }

    public boolean removerParaAssistir(Serie serie) {
        return paraAssistir.remove(serie);
    }

    public boolean isParaAssistir(Serie serie) {
        return paraAssistir.contains(serie);
    }

    /**
     * Exibe estatísticas do usuário
     */
    public void exibirEstatisticas() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("👤 Estatísticas de " + nome);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("⭐ Favoritos: " + favoritos.size() + " séries");
        System.out.println("✅ Assistidas: " + assistidas.size() + " séries");
        System.out.println("📝 Para assistir: " + paraAssistir.size() + " séries");
        System.out.println("📊 Total gerenciado: " + (favoritos.size() + assistidas.size() + paraAssistir.size()) + " séries");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    @Override
    public String toString() {
        return "Usuário: " + nome;
    }
}
