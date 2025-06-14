package com.seuprojeto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Serie {
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String status;
    private LocalDate dataEstreia;
    private LocalDate dataTermino;
    private String emissora;

    // Formatter para converter String para LocalDate
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Construtor, Getters e Setters
    public Serie(String nome, String idioma, List<String> generos, double nota, String status, String dataEstreia, String dataTermino, String emissora) {
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.dataEstreia = (dataEstreia != null) ? LocalDate.parse(dataEstreia, FORMATTER) : null;
        this.dataTermino = (dataTermino != null) ? LocalDate.parse(dataTermino, FORMATTER) : null;
        this.emissora = emissora;
    }

    // Getters
    public String getNome() { return nome; }
    public double getNota() { return nota; }
    public String getStatus() { return status; }
    public LocalDate getDataEstreia() { return dataEstreia; }

    @Override
    public String toString() {
        return "----------------------------------------\n" +
                "  Nome: " + nome + "\n" +
                "  Idioma: " + (idioma != null ? idioma : "N/A") + "\n" +
                "  Gêneros: " + (generos != null && !generos.isEmpty() ? String.join(", ", generos) : "N/A") + "\n" +
                "  Nota: " + (nota > 0 ? nota : "N/A") + "\n" +
                "  Status: " + (status != null ? status : "N/A") + "\n" +
                "  Data de Estreia: " + (dataEstreia != null ? dataEstreia.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A") + "\n" +
                "  Data de Término: " + (dataTermino != null ? dataTermino.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A") + "\n" +
                "  Emissora: " + (emissora != null ? emissora : "N/A") + "\n" +
                "----------------------------------------";
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