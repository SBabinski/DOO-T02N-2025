package sistemaSerie;

import java.util.List;

public class Serie {
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String status;
    private String dataEstreia;
    private String dataFim;
    private String emissora;

    public Serie() {}

    public Serie(String nome, String idioma, List<String> generos, double nota, String status, String dataEstreia, String dataFim, String emissora) {
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.dataEstreia = dataEstreia;
        this.dataFim = dataFim;
        this.emissora = emissora;
    }

    public String getNome() { return nome; }
    public String getIdioma() { return idioma; }
    public List<String> getGeneros() { return generos; }
    public double getNota() { return nota; }
    public String getStatus() { return status; }
    public String getDataEstreia() { return dataEstreia; }
    public String getDataFim() { return dataFim; }
    public String getEmissora() { return emissora; }

    @Override
    public String toString() {
        return "Série: " + nome +
                "\nIdioma: " + idioma +
                "\nGêneros: " + generos +
                "\nNota: " + nota +
                "\nStatus: " + status +
                "\nEstreia: " + dataEstreia +
                "\nFim: " + dataFim +
                "\nEmissora: " + emissora + "\n";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return nome != null && nome.equalsIgnoreCase(serie.nome);
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.toLowerCase().hashCode() : 0;
    }
}