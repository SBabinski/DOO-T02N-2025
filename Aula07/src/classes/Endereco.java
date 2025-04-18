package classes;

public class Endereco {
	
	private String estado;
	private String cidade;
	private String bairro;
	private String rua;
	private String complemento;
	private int numero;
	


	public Endereco(String estado, String cidade, String bairro, String rua, String complemento, int numero) {
		super();
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.complemento = complemento;
		this.numero = numero;
	}






	public Endereco(String estado, String cidade, String bairro, String rua, int numero) {
		super();
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.numero = numero;
	}






	public String getRua() {
		return rua;
	}






	public void setRua(String rua) {
		this.rua = rua;
	}






	public String getEstado() {
		return estado;
	}






	public void setEstado(String estado) {
		this.estado = estado;
	}






	public String getCidade() {
		return cidade;
	}






	public void setCidade(String cidade) {
		this.cidade = cidade;
	}






	public String getBairro() {
		return bairro;
	}






	public void setBairro(String bairro) {
		this.bairro = bairro;
	}






	public String getComplemento() {
		return complemento;
	}






	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}






	public int getNumero() {
		return numero;
	}






	public void setNumero(int numero) {
		this.numero = numero;
	}




	public  void apresentarLogradouro() {
		
		System.out.println("Endereço:\nBairro: " + bairro + "\nRua: " + rua + ", n: " + numero + " (" + complemento + ")\nCidade: " + cidade + " / Estado: " + estado);
		
		
		
	}


	public static void main(String[] args) {
		
		Endereco endereco01 = new Endereco("Parana", "Cascavel", "Santo Inacio", "Av. Fag", "Apt 808", 403);		
		endereco01.apresentarLogradouro();

	}

}
