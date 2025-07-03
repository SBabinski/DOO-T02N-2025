package org.series;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import org.series.service.SerieService;

public class Serie {
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String status;
    private String dataEstreia;
    private String dataTermino;
    private String emissora;

    public Serie(String nome, String idioma, List<String> generos, double nota, String status, String dataEstreia, String dataTermino, String emissora) {
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
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

    public String getDataTermino() {
        return dataTermino;
    }

    public String getEmissora() {
        return emissora;
    }

    @Override
    public String toString() {
        return "\n Nome: " + nome +
                "\n Idioma: " + idioma +
                "\n Gêneros: " + String.join(", ", generos) +
                "\n Nota geral: " + nota +
                "\n Estado: " + status +
                "\n Estreia: " + formatarData(dataEstreia) +
                "\n Término: " + formatarData(dataTermino) +
                "\n Emissora: " + emissora + "\n";
    }

    public static String formatarData(String data) {
        try {
            if (data.equals("N/A")) return "N/A";
            LocalDate date = LocalDate.parse(data);
            return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            return data;
        }

    }
}