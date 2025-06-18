package model;

import model.helper.JsonHelper;
import model.json.SerieJson;
import model.json.api.SerieApiJson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Serie {
    private Long id;
    private String url;
    private String nome;
    private String idioma;
    private List<String> generos;
    private Float notaGeral;
    private LocalDate dataEstreia;
    private LocalDate dataTermino;
    private String emissora;
    private List<TipoLista> listas;

    public static Serie fromSerieApiJson(SerieApiJson serieApiJson) {

        var show = serieApiJson.show;
        if (show == null) {
            return new Serie();
        }

        Serie serie = new Serie();
        serie.id = JsonHelper.buscaUltimoIdSerie();
        serie.nome = (show.name != null) ? show.name : "";
        serie.idioma = (show.language != null) ? show.language : "";
        serie.generos = (show.genres != null) ? show.genres : new ArrayList<>();
        serie.notaGeral = (show.rating != null && show.rating.average != null) ? show.rating.average.floatValue() : 0.0f;
        serie.dataEstreia = (show.premiered != null) ? LocalDate.parse(show.premiered) : null;
        serie.dataTermino = (show.ended != null) ? LocalDate.parse(show.ended) : null;
        serie.emissora = (show.network != null && show.network.name != null) ? show.network.name : "";
        serie.url = serie.nome.replace(" ", "_") + "_" + Objects.requireNonNull(serie.dataEstreia);
        serie.listas = new ArrayList<>();

        return serie;
    }

    public static Serie fromSerieJson(SerieJson serieJson) {
        Serie serie = new Serie();
        serie.id = serieJson.id;
        serie.url = serieJson.url;
        serie.nome = serieJson.nome;
        serie.idioma = serieJson.idioma;
        serie.generos = serieJson.generos != null ? new ArrayList<>(serieJson.generos) : new ArrayList<>();
        serie.notaGeral = serieJson.notaGeral;

        if (serieJson.dataEstreia != null && !serieJson.dataEstreia.isEmpty()) {
            serie.dataEstreia = LocalDate.parse(serieJson.dataEstreia);
        }

        if (serieJson.dataTermino != null && !serieJson.dataTermino.isEmpty()) {
            serie.dataTermino = LocalDate.parse(serieJson.dataTermino);
        }

        serie.emissora = serieJson.emissora;
        serie.listas = serieJson.listas;
        return serie;
    }

    public static ArrayList<Serie> getListSerieFromListSerieJSON(List<SerieApiJson> listaSerieApiJson) {
        var listaSerie = new ArrayList<Serie>();
        for (SerieApiJson serieApiJson : listaSerieApiJson) {
            listaSerie.add(Serie.fromSerieApiJson(serieApiJson));
        }
        return listaSerie;
    }

    public void printNameAndYear() {
        System.out.println(("ID: " + this.getId()) + " - " + this.getNome() + ", " + this.getDataEstreia().getYear());
    }

    public void printDetalhado() {
        System.out.println("===== Detalhes da Série =====");
        System.out.println("ID: " + this.getId());
        System.out.println("Nome: " + this.getNome());
        System.out.println("URL: " + this.getUrl());
        System.out.println("Idioma: " + this.getIdioma());

        System.out.print("Gêneros: ");
        if (this.getGeneros() != null && !this.getGeneros().isEmpty()) {
            System.out.println(String.join(", ", this.getGeneros()));
        } else {
            System.out.println("Nenhum gênero cadastrado.");
        }

        System.out.println("Nota Geral: " + (this.getNotaGeral() != null ? String.format("%.1f", this.getNotaGeral()) : "Não avaliada"));

        System.out.println("Data de Estreia: " + (this.getDataEstreia() != null ? this.getDataEstreia().toString() : "Não informada"));

        System.out.println("Data de Término: " + (this.getDataTermino() != null ? this.getDataTermino().toString() : "Ainda em exibição"));

        System.out.println("Emissora: " + (this.getEmissora() != null && !this.getEmissora().isBlank() ? this.getEmissora() : "Não informada"));

        System.out.println("=============================\n");
    }


    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
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

    public Float getNotaGeral() {
        return notaGeral;
    }

    public LocalDate getDataEstreia() {
        return dataEstreia;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public String getEmissora() {
        return emissora;
    }

    public List<TipoLista> getListas() {
        return listas;
    }
}
