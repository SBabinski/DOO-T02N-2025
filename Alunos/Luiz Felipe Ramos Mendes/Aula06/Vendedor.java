class Vendedor {

    private String nome;
    private int idade;
    private String loja;
    private String cidade;
    private String bairro;
    private String rua;
    private double salarioBase;
    private double[] salarioRecebido;

    public Vendedor(String nome, int idade, String loja, String cidade, String bairro, String rua, double salarioBase, double[] salarioRecebido) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    public void apresentar() {
        System.out.println("Nome: " + this.nome);
        System.out.println("Idade: " + this.idade);
        System.out.println("Loja: " + this.loja);
    }

    public void calcularMedia() {
        double media = 0;
        for (double salario : salarioRecebido) {
            media += salario;
        }
        media /= salarioRecebido.length;
        System.out.println("Média dos salários: " + media);
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }

    public double calcularSalarioComBonus() {
        return salarioBase + calcularBonus();
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", loja='" + loja + '\'' +
                ", cidade='" + cidade + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", salarioBase=" + salarioBase +
                '}';
    }
}