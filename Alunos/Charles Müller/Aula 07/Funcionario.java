public abstract class Funcionario extends Pessoa {
    protected String loja;
    protected Double salarioBase;
    protected Double[] salarioRecebido;

    public Funcionario(String nome, int idade, String loja, Endereco endereco,
                       Double salarioBase, Double[] salarioRecebido) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    // Getters e setters
    public String getLoja() { return loja; }
    public void setLoja(String loja) { this.loja = loja; }
    public Double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(Double salarioBase) { this.salarioBase = salarioBase; }
    public Double[] getSalarioRecebido() { return salarioRecebido; }
    public void setSalarioRecebido(Double[] salarioRecebido) { this.salarioRecebido = salarioRecebido; }

    public void calcularMedia() {
        double soma = 0.0;
        for (int i = 0; i < salarioRecebido.length; i++) {
            soma += salarioRecebido[i];
        }
        double media = soma / salarioRecebido.length;
        System.out.println("Média de salários: " + media);
    }

    public abstract void calcularBonus();
}