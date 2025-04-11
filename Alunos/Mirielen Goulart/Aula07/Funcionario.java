
import java.util.ArrayList;

public class Funcionario {
	
	 String nome;
	private int idade;
	private String loja;
	private Endereco endereco;
	private double salarioBase;
	private ArrayList<Double> salarioRecebido;
	
	public Funcionario(String nome, int idade, String loja, Endereco endereco, double salarioBase,
			ArrayList<Double> salarioRecebido) {
		super();
		this.nome = nome;
		this.idade = idade;
		this.loja = loja;
		this.endereco = endereco;
		this.salarioBase = salarioBase;
		this.salarioRecebido = salarioRecebido;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getLoja() {
		return loja;
	}

	public void setLoja(String loja) {
		this.loja = loja;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public double getSalarioBase() {
		return salarioBase;
	}

	public void setSalarioBase(double salarioBase) {
		this.salarioBase = salarioBase;
	}

	public ArrayList<Double> getSalarioRecebido() {
		return salarioRecebido;
	}

	public void setSalarioRecebido(ArrayList<Double> salarioRecebido) {
		this.salarioRecebido = salarioRecebido;
	}
	
}
