
import java.util.ArrayList;

public class Gerente extends Funcionario{

	public Gerente(String nome, int idade, String loja, Endereco endereco, double salarioBase,
			ArrayList<Double> salarioRecebido) {
		super(nome, idade, loja, endereco, salarioBase, salarioRecebido);
	}
	
	public double calcularMedia() {
        double soma = 0;
        for (double salario : getSalarioRecebido()) {
            soma += salario;
        }
        return getSalarioRecebido().isEmpty() ? 0 : soma / getSalarioRecebido().size();
    }
	
	public void calcularBonus() {
        System.out.println("Bônus: " + (getSalarioBase() * 0.35));
    }
	
	public void apresentarse() {
		System.out.println("========= GERENTES ============");
		System.out.println("Nome: "+getNome()+ "Idade: " +getIdade()+ "Loja: "+getLoja());
	}
}
