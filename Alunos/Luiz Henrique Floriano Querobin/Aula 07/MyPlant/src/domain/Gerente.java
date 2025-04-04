package domain;

import java.util.ArrayList;

public class Gerente extends Empregado {

    public Gerente(String nome, Integer idade, Loja loja, Endereco endereco, Double salarioBase) {
        super(nome, idade, loja, endereco, salarioBase);
        ArrayList<Double> salarios = new ArrayList<>();
        salarios.add(5000.0);
        salarios.add(5200.0);
        salarios.add(5300.0);
        this.setSalarioRecebido(salarios);
    }

    @Override
    public void apresentarse() {
        System.out.printf("Nome: %s - Idade: %s - Loja: %s\n", getNome(), getIdade(), getLoja().getNomeFantasia());
    }

    @Override
    public void calcularBonus() {
        System.out.println("Bônus: " + getSalarioBase() * 0.35);
    }
}
