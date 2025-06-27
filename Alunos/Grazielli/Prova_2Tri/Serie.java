package prova2tri;

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

    public String getNome() {
        return nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public double getNota() {
        return nota;
    }

    public String getStatus() {
        return status;
    }

    public String getDataEstreia() {
        return dataEstreia;
    }

    public String getDataFim() {
        return dataFim;
    }

    public String getEmissora() {
        return emissora;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDataEstreia(String dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }
}
