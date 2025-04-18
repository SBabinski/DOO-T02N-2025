package classes;

import java.util.ArrayList;
import java.util.List;
import classes.Cliente;
import classes.Vendedor;
import classes.Endereco;

public class Loja {
	
	public static List<Cliente> listaClientes = new ArrayList<>();
	public static List<Vendedor> listaVendedores = new ArrayList<>();
	
	private String nome;
	private String razaoSocial;
	private String cnpj;
	private Endereco endereco;
	

	public Loja(String nome, String razaoSocial, String cnpj, Endereco endereco) {
		super();
		this.nome = nome;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.endereco = endereco;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getRazaoSocial() {
		return razaoSocial;
	}



	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}



	public String getCnpj() {
		return cnpj;
	}



	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	

public Endereco getEndereco() {
		return endereco;
	}



	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}



public static void adicionarPersonagens() {
		
	    Endereco enderecoEduarda = new Endereco("Parana", "Cascavel", "Aquele", "Sem saida", "Apt 1000", 2);
	    Cliente cliente01 = new Cliente("Eduarda", 20, enderecoEduarda);
		Cliente cliente02= new Cliente("Marcus", 20, enderecoEduarda);
		listaClientes.add(cliente01);
		listaClientes.add(cliente02);
		
		
		Endereco enderecoFabio = new Endereco("Parana", "Cascavel", "centro", "sla", "Apt 206", 100);
		Vendedor vendedor01 = new Vendedor("Fabio", 25, 01, enderecoFabio, 2500, null);
		Vendedor vendedor02 = new Vendedor("Jane", 28, 02, enderecoFabio, 2500, null);
		Vendedor vendedor03 = new Vendedor("Laila", 20, 03, enderecoFabio, 2500, null);
		listaVendedores.add(vendedor01);
		listaVendedores.add(vendedor02);
		listaVendedores.add(vendedor03);
		
	}
	
	public static void contarVendedores() {
		
		
		
		int contadorVendedores = 0;
		
		for(int i = 0; i < listaVendedores.size(); i++ ) {
			
			contadorVendedores++;
			
		}
		
		System.out.println("Tem " + contadorVendedores + " vendedores na sua loja." );
		
	}
	
	public static void contarClientes() {
		
		
		
		 int contadorClientes = 0;
			
			for(int i = 0; i < listaClientes.size(); i++ ) {
				
				contadorClientes++;
				
			}
			
			System.out.println("Tem " + contadorClientes + " clientes na sua loja." );
		
	}
	
	
	public static void apresentarse() {
		
		Endereco enderecoLoja = new Endereco("Parana", "Cascavel", "Cataratas", "Sla", 999);
		Loja loja01 = new Loja("My Plant", "Dona Gabrielinha LTDA", "0001", enderecoLoja);
		
		System.out.println("Nome: " + loja01.getNome() + "\nCNPJ: " + loja01.getCnpj());
		enderecoLoja.apresentarLogradouro();
		
	}

	public static void main(String[] args) {
		
		adicionarPersonagens();
		contarClientes();
		System.out.println("  ");
		contarVendedores();
		System.out.println("  ");
		apresentarse();
		

	}

} 