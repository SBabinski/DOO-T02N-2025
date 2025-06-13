package Trabalho2;

import java.util.Objects;

public class Noticia {
    
    private String titulo;
    private String introducao;
    private String dataPublicacao;
    private String linkCompleto;
    private String tipo;
    private String fonte;

    public Noticia() {
        
    }

    public Noticia(String titulo, String introducao, String dataPublicacao, String linkCompleto, String tipo, String fonte) {
        this.titulo = titulo;
        this.introducao = introducao;
        this.dataPublicacao = dataPublicacao;
        this.linkCompleto = linkCompleto;
        this.tipo = tipo;
        this.fonte = "IBGE";
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIntroducao() {
        return introducao;
    }

    public void setIntroducao(String introducao) {
        this.introducao = introducao;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getLinkCompleto() {
        return linkCompleto;
    }

    public void setLinkCompleto(String linkCompleto) {
        this.linkCompleto = linkCompleto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    @Override
    public String toString() {
        return "\nNoticia{" +
                "titulo='" + titulo + '\'' +
                ", introducao='" + introducao + '\'' +
                ", dataPublicacao='" + dataPublicacao + '\'' +
                ", linkCompleto='" + linkCompleto + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fonte='" + fonte + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Noticia)) return false;
        Noticia other = (Noticia) obj;
        return titulo != null && titulo.trim().equalsIgnoreCase(other.titulo.trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo != null ? titulo.trim().toLowerCase() : null);
    }
}