package Prova_2tri;

import java.util.List;
import java.util.Objects;

public class Serie {

    private String nome;
    private String idioma;
    private List<String> generos;
    private double notaGeral;
    private String status;
    private String dataEstreia;
    private String dataFim;
    private String emissora;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public double getNotaGeral() {
        return notaGeral;
    }

    public void setNotaGeral(double notaGeral) {
        this.notaGeral = notaGeral;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataEstreia() {
        return dataEstreia;
    }

    public void setDataEstreia(String dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getEmissora() {
        return emissora;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }

    @Override
    public String toString() {
        return "Série: " + nome +
                "\nIdioma: " + idioma +
                "\nGêneros: " + generos +
                "\nNota Geral: " + notaGeral +
                "\nStatus: " + status +
                "\nData de Estreia: " + dataEstreia +
                "\nData de Fim: " + dataFim +
                "\nEmissora: " + emissora +
                "\n";
    }
    
    // Override da função equals e hashCode para comparar séries pelo nome
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return Objects.equals(nome, serie.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
