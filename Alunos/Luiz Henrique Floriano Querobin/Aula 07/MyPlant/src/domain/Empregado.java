package domain;

import java.util.ArrayList;

public class Empregado {

    private String nome;
    private Integer idade;
    private Loja loja;
    private Endereco endereco;
    private Double salarioBase;
    private ArrayList<Double> salarioRecebido;

    public Empregado(String nome, Integer idade, Loja loja, Endereco endereco, Double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.endereco = endereco;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new ArrayList<>();
    }

    public void apresentarse() {
        System.out.printf("Nome: %s - Idade: %s\n" , nome, idade);
    }

    public void calcularMedia() {
        double media = 0;
        for (Double s : salarioRecebido) {
            media += s;
        }
        media = media / salarioRecebido.size();
        System.out.println("Média dos salários recebidos: " + media);
    }

    public void calcularBonus() {
        System.out.println("Bônus: " + salarioBase * 0.2);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(Double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public ArrayList<Double> getSalarioRecebido() {
        return salarioRecebido;
    }

    public void setSalarioRecebido(ArrayList<Double> salarioRecebido) {
        this.salarioRecebido = salarioRecebido;
    }
}
