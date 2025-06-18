package model.json;

import model.Serie;
import model.TipoLista;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SerieJson {
    public Long id;
    public String url;
    public String nome;
    public String idioma;
    public List<String> generos;
    public Float notaGeral;
    public String status;
    public String dataEstreia;
    public String dataTermino;
    public String emissora;
    public List<TipoLista> listas;

    public SerieJson(Long id, String url, String nome, String idioma, List<String> generos, Float notaGeral, String dataEstreia, String dataTermino, String emissora, List<TipoLista> listas) {
        this.id = id;
        this.url = url;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.notaGeral = notaGeral;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = emissora;
        this.listas = listas;
    }

    public static SerieJson fromSerie(Serie serie) {
        var dataEstreiaStr = serie.getDataEstreia() != null ?
                serie.getDataEstreia().format(DateTimeFormatter.ISO_LOCAL_DATE) :
                null;

        var dataTerminoStr = serie.getDataTermino() != null ?
                serie.getDataTermino().format(DateTimeFormatter.ISO_LOCAL_DATE) :
                null;

        return new SerieJson(
                serie.getId(),
                serie.getUrl(),
                serie.getNome(),
                serie.getIdioma(),
                new ArrayList<>(serie.getGeneros()),
                serie.getNotaGeral(),
                dataEstreiaStr,
                dataTerminoStr,
                serie.getEmissora(),
                serie.getListas()
        );
    }
}
