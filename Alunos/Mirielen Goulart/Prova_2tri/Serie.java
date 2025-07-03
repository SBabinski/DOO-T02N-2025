package seriesTV;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Serie {

    public String nome;
    public String idioma;
    public List<String> generos;
    public double nota;
    public String status;
    public LocalDate dataEstreia;
    public LocalDate dataFim;
    public String emissora;

    public Serie(String nome, String idioma, List<String> generos, double nota, String status, LocalDate dataEstreia, LocalDate dataFim, String emissora) {
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

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataEstreia() {
        return dataEstreia;
    }

    public void setDataEstreia(LocalDate dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
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
        return "\nNome: '" + nome + "'" +
                "\nIdioma: '" + idioma + "'" +
                "\nGênero: " + String.join(", ", generos) +
                "\nNota: " + nota +
                "\nStatus: '" + status + "'" +
                "\nData de Estreia: " + (dataEstreia != null ? dataEstreia.toString() : "N/A") +
                "\nData de fim: " + (dataFim != null ? dataFim.toString() : "N/A") +
                "\nEmissora: '" + emissora + "'";
    }

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
