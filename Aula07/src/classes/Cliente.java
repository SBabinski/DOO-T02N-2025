package classes;

import java.util.Scanner;

public class Cliente {
	
	public static Scanner scan = new Scanner (System.in);
	
	private String nome;
	private int idade;
	private Endereco endereco;


	public Cliente(String nome, int idade, Endereco endereco) {
		super();
		this.nome = nome;
		this.idade = idade;
		this.endereco = endereco;
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

public Endereco getEndereco() {
		return endereco;
	}




	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


public static void cadastrarCliente() {
	
	Endereco endereco = new Endereco(null, null, null, null, null, 0);
	Cliente cliente = new Cliente(null, 0, endereco);
	
	System.out.println("Nome do cliente?");
	cliente.setNome(scan.nextLine());
	System.out.println("Qual a idade do cliente?");
	cliente.setIdade(scan.nextInt());
	scan.nextLine();
	System.out.println("Estado onde reside?");
	endereco.setEstado(scan.nextLine());
	System.out.println("Cidade?");
	endereco.setCidade(scan.nextLine());
	System.out.println("Bairro?");
	endereco.setBairro(scan.nextLine());
	System.out.println("Rua?");
	endereco.setRua(scan.nextLine());
	System.out.println("Numero?");
	endereco.setNumero(scan.nextInt());
	scan.nextLine();
	System.out.println("Complemento");
	endereco.setRua(scan.nextLine());
	cliente.setEndereco(endereco);
	
	
	
}

public static void apresentarse() {
	
	Endereco enderecoEduarda = new Endereco("Parana", "Cascavel", "Aquele", "Sem saida", "Apt 1000", 2);
	Cliente cliente01 = new Cliente("Eduarda", 20, enderecoEduarda);
	
	System.out.println("Nome: " + cliente01.getNome() + "\nIdade: " + cliente01.getIdade());
	
}


	public static void main(String[] args) {

		
		apresentarse();
		
	}

}
