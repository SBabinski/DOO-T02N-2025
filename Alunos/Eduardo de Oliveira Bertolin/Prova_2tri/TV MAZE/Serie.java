import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Serie implements Serializable {
    private int id;
    private String nome;
    private String idioma;
    private List<String> generos;
    private double notaGeral;
    private String estado; // Status: Running, Ended, etc.
    private String dataEstreia;
    private String dataTermino;
    private String emissora;
    private String imagemUrl;

    public Serie(int id, String nome, String idioma, List<String> generos, double notaGeral, String estado,
            String dataEstreia, String dataTermino, String emissora, String imagemUrl) {
        this.id = id;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.notaGeral = notaGeral;
        this.estado = estado;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = emissora;
        this.imagemUrl = imagemUrl;
    }

    // getters

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public double getNotaGeral() {
        return notaGeral;
    }

    public String getEstado() {
        return estado;
    }

    public String getDataEstreia() {
        return dataEstreia;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public String getEmissora() {
        return emissora;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    @Override
    public String toString() {
        return nome + " | " + idioma + " | " + String.join(", ", generos) + " | Nota: " + notaGeral + " | " + estado;
    }

    // implementação de equals e hashCode para garantir unicidade baseada no id
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // se for o mesmo objeto, true
        if (obj == null || getClass() != obj.getClass()) return false; // se o objeto passado for null ou não for da mesma classe retorna false
        Serie other = (Serie) obj; // faz cast para Serie
        return id == other.id; // compara os ids, se forem iguais, considera os objetos iguais
}

    @Override
        public int hashCode() {
        return Objects.hash(id); // gera um hash baseado no id
}
}
