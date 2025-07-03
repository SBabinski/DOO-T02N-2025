import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Serie {

    @JsonProperty("name")
    private String titulo;

    @JsonProperty("language")
    private String idioma;

    private String[] generos;
    private String nota;

    @JsonProperty("status")
    private String situacao;

    @JsonProperty("premiered")
    private Date estreia;

    @JsonProperty("ended")
    private Date termino;

    private String emissora;

    @JsonProperty("rating")
    private void mapearNota(Object ratingObj) {
        if (ratingObj instanceof Map<?, ?> mapa) {
            Object media = mapa.get("average");
            this.nota = media != null ? media.toString() : "N/A";
        }
    }

    @JsonProperty("network")
    private void mapearEmissora(Object emissoraObj) {
        if (emissoraObj instanceof Map<?, ?> mapa) {
            Object nome = mapa.get("name");
            this.emissora = nome != null ? nome.toString() : "";
        }
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public String[] getGeneros() { return generos; }
    public void setGeneros(String[] generos) { this.generos = generos; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public Date getEstreia() { return estreia; }
    public void setEstreia(Date estreia) { this.estreia = estreia; }

    public Date getTermino() { return termino; }
    public void setTermino(Date termino) { this.termino = termino; }

    public String getEmissora() { return emissora; }
    public void setEmissora(String emissora) { this.emissora = emissora; }

    @Override
    public String toString() {
        String generosStr = (generos != null && generos.length > 0) ? String.join(", ", generos) : "N/A";

        return "Título: " + titulo +
                "\nIdioma: " + idioma +
                "\nGêneros: " + generosStr +
                "\nNota: " + nota +
                "\nSituação: " + situacao +
                "\nEstreia: " + estreia +
                "\nTérmino: " + termino +
                "\nEmissora: " + emissora;
    }

    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Serie serie = (Serie) o;

    return titulo != null ? titulo.equalsIgnoreCase(serie.titulo) : serie.titulo == null;
}

@Override
public int hashCode() {
    return titulo != null ? titulo.toLowerCase().hashCode() : 0;
}

}
    
