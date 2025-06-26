import java.util.List;

public class Usuario {
    public List<Serie> assistidas;
    public List<Serie> desejoAssistir;
    public List<Serie> favoritos;

    public Usuario(List<Serie> assistidas, List<Serie> desejoAssistir, List<Serie> favoritos) {
        this.assistidas = assistidas;
        this.desejoAssistir = desejoAssistir;
        this.favoritos = favoritos;
    }

    public Usuario() {
        
    }
}
