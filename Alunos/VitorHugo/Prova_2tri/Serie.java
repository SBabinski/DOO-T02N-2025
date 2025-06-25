package Prova_2tri;

import java.util.List;
import java.util.Objects;

public class Serie {
    private int id;
    private String nome;
    private String idioma;
    private List<String> generos;
    private double notaGeral;
    private String estado;
    private String dataEstreia;
    private String dataTermino;
    private String emissora;

    public Serie(int id, String nome, String idioma, List<String> generos, double notaGeral, String estado,
                 String dataEstreia, String dataTermino, String emissora) {
        this.id = id;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.notaGeral = notaGeral;
        this.estado = estado;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = emissora;
    }

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

    @Override
    public String toString() {
        return String.format(
            "Nome: %s\nIdioma: %s\nGêneros: %s\nNota geral: %.2f\nEstado: %s\nData de estreia: %s\nData de término: %s\nEmissora: %s",
            nome, idioma, String.join(", ", generos), notaGeral, estado, dataEstreia, dataTermino, emissora
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Serie)) return false;
        Serie serie = (Serie) o;
        return id == serie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}