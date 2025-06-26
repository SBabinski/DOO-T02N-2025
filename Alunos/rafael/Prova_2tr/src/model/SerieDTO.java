package model;

import java.util.List;

public class SerieDTO {
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String status;
    private String dataEstreia;
    private String dataFim;
    private String emissora;

    public SerieDTO(String nome, String idioma, List<String> generos, double nota, String status,
                    String dataEstreia, String dataFim, String emissora) {
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.dataEstreia = dataEstreia;
        this.dataFim = dataFim;
        this.emissora = emissora;
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
}
