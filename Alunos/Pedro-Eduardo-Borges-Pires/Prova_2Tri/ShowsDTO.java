package spring_boot_api.tvmaze_api;

import java.util.List;

public class ShowsDTO {
	
    private String nome;
    private String idioma;
    private List<String> generos;
    private double notaGeral;
    private String estado;
    private String dataEstreia;
    private String dataTermino;
    private String emissora;
	
    
    public ShowsDTO() {
		
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
	public double getNotaGeral() {
		return notaGeral;
	}
	public void setNotaGeral(double notaGeral) {
		this.notaGeral = notaGeral;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDataEstreia() {
		return dataEstreia;
	}
	public void setDataEstreia(String dataEstreia) {
		this.dataEstreia = dataEstreia;
	}
	public String getDataTermino() {
		return dataTermino;
	}
	public void setDataTermino(String dataTermino) {
		this.dataTermino = dataTermino;
	}
	public String getEmissora() {
		return emissora;
	}
	public void setEmissora(String emissora) {
		this.emissora = emissora;
	}


	@Override
	public String toString() {
		return "Nome= " + nome + ", Idioma= " + idioma + ", Generos= " + generos + ", NotaGeral= " + notaGeral
				+ ", Estado= " + estado + ", Data de Estreia= " + dataEstreia + ", Data de Termino= " + dataTermino + ", Emissora= "
				+ emissora;
	}

	
    
}
