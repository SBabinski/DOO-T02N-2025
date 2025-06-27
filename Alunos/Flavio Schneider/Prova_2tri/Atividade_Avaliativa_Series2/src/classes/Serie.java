package classes;

import java.util.List;

public class Serie {
	
	private int id;
	private String nome;
	private String idioma;
	private List<String> generos;
	private double nota;
	private String estado;
	private String dataEstreia;
	private String dataFim;
	private String emissora;
	
	
	public Serie(int id, String nome, String idioma, List<String> generos, double nota, String estado,
			String dataEstreia, String dataFim, String emissora) {
		super();
		this.id = id;
		this.nome = nome;
		this.idioma = idioma;
		this.generos = generos;
		this.nota = nota;
		this.estado = estado;
		this.dataEstreia = dataEstreia;
		this.dataFim = dataFim;
		this.emissora = emissora;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
		return "Serie: \nId=" + id + "\nNome=" + nome + "\nIdioma=" + idioma + "\nGeneros=" + generos + "\nNota=" + nota
				+ "\nEstado=" + estado + "\nDataEstreia=" + dataEstreia + "\nDataFim=" + dataFim + "\nEmissora="
				+ emissora + "";
	}
	
	
	
	

}
