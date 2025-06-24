import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Perfil {

    private String nome;
    private ArrayList<Serie> favoritos;
    private ArrayList<Serie> jaVistas;
    private ArrayList<Serie> desejoVer;

    public Perfil() {
        this.favoritos = new ArrayList<>();
        this.jaVistas = new ArrayList<>();
        this.desejoVer = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Serie> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(ArrayList<Serie> favoritos) {
        this.favoritos = favoritos;
    }

    public ArrayList<Serie> getJaVistas() {
        return jaVistas;
    }

    public void setJaVistas(ArrayList<Serie> jaVistas) {
        this.jaVistas = jaVistas;
    }

    public ArrayList<Serie> getDesejoVer() {
        return desejoVer;
    }

    public void setDesejoVer(ArrayList<Serie> desejoVer) {
        this.desejoVer = desejoVer;
    }
}
