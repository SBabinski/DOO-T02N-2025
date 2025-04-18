package classes;

import java.util.List;
import java.util.Scanner;

public class Gerente extends Vendedor{
	
	public static Scanner scan = new Scanner(System.in);
	

	public Gerente(String nome, int idade, int loja, Endereco endereco, double salarioBase,
			List<Double> salarioRecebido) {
		super(nome, idade, loja, endereco, salarioBase, salarioRecebido);
		// TODO Auto-generated constructor stub
	}

	public Gerente(String nome, int idade, int loja, Endereco endereco, double salarioBase) {
		super(nome, idade, loja, endereco, salarioBase);
		// TODO Auto-generated constructor stub
	}
	
	
	public void apresentarse() {
		
		Endereco enderecoFabio = new Endereco("Parana", "Cascavel", "centro", "sla", "Apt 206", 100);
		Gerente gerente = new Gerente("Fabio", 25, 01, enderecoFabio, 2500);
		
	    System.out.println("Nome: " + gerente.getNome() + "\nIdade: " + gerente.getIdade() + 
					"\nLoja: " + gerente.getLoja());
			
		}

	
	
	
	@Override
	public void calcularBonus() {
		
		Endereco enderecoFabio = new Endereco("Parana", "Cascavel", "centro", "sla", "Apt 206", 100);
		Gerente gerente = new Gerente("Fabio", 25, 01, enderecoFabio, 2500, null);
		
		System.out.println("O vendedor bateu a meta mensal?");
		System.out.println("1-sim");
		System.out.println("2-Nao");
		int op = scan.nextInt();
		
		if(op == 1) {
			
			double salario = (gerente.getSalarioBase() * 0.35) + gerente.getSalarioBase();
			gerente.getSalarioRecebido().add(salario);
			
		}else if(op == 2) {
			
			gerente.getSalarioRecebido().add(gerente.getSalarioBase());
			
		}else {
		    System.out.println("opcao invalida");
		}
		
		 System.out.println("Salário recebido: " + gerente.getSalarioRecebido());
		

	}
	
	
	public void calcularMedia() {
		
		Endereco enderecoFabio = new Endereco("Parana", "Cascavel", "centro", "sla", "Apt 206", 100);
		Gerente gerente = new Gerente("Fabio", 25, 01, enderecoFabio, 2500, null);
		gerente.getSalarioRecebido().add(2500.00);
		gerente.getSalarioRecebido().add(3000.00);
		gerente.getSalarioRecebido().add(2500.00);
		double total = 0;
		int c = 0;
		
		for(double salario : gerente.getSalarioRecebido()) {
			
			total = total + salario;
			
			c++;
		}
		
		double media = 0;
		media = total/c;
		
		//System.out.println(vendedor01.getSalarioRecebido());
		System.out.println(media);	
	}

	public static void main(String[] args) {
		
		
	
	}

}
