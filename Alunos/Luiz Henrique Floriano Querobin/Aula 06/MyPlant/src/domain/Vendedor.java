package domain;

import java.util.ArrayList;

public class Vendedor {

    private String nome;
    private Integer idade;
    private Loja loja;
    private String cidade;
    private String bairro;
    private String rua;
    private Double salarioBase;
    private ArrayList<Double> salarioRecebido;

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

    public void apresentarse() {
        System.out.printf("Nome: %s - Idade: %s - Loja: %s\n", nome, idade, loja.getNomeFantasia());
    }

    public Vendedor() {
    }

    public Vendedor(String nome, Integer idade, Loja loja, String cidade, String bairro, String rua, Double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new ArrayList<Double>();
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

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
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

    @Override
    public String toString() {
        return "Vendedor{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", loja=" + loja +
                ", cidade='" + cidade + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", salarioRecebido=" + salarioRecebido +
                ", salarioBase=" + salarioBase +
                '}';
    }
}
