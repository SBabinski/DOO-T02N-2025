public class Vendedor extends Funcionario {
    public Vendedor(String nome, int idade, String loja, Endereco endereco,
                   Double salarioBase, Double[] salarioRecebido) {
        super(nome, idade, loja, endereco, salarioBase, salarioRecebido);
    }

    @Override
    public void apresentarse() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja);
    }

    @Override
    public void calcularBonus() {
        double bonus = salarioBase * 0.2;
        System.out.println("Bônus: " + bonus);
    }
}