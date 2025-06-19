package sistemaSerie;

import java.util.List;

public class Serie {
    private int id;
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String status;
    private String dataEstreia;
    private String dataFim;
    private String emissora;

    public Serie(int id, String nome, String idioma, List<String> generos, double nota, String status, String dataEstreia, String dataFim, String emissora) {
        this.id = id;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.dataEstreia = dataEstreia;
        this.dataFim = dataFim;
        this.emissora = emissora;
    }

    public int getId() { return id; }
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
        return String.format("""
            Nome: %s
            Idioma: %s
            Gêneros: %s
            Nota: %.1f
            Status: %s
            Estreia: %s
            Fim: %s
            Emissora: %s
            """, nome, idioma, generos, nota, status, dataEstreia, dataFim, emissora);
    }
}
