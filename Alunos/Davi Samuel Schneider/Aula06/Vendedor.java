public class Vendedor {
    private String nome;
    private int idade;
    private Loja loja;
    private String cidade, bairro, rua;
    private double salarioBase;
    private double[] salarioRecebido = {2000, 2500, 3000};

    public Vendedor(String nome, int idade, String cidade, String bairro, String rua, double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
    }

    public double calcularMedia() {
        double media = 0;
        for (int i = 0; i < salarioRecebido.length; i++) {
            media += salarioRecebido[i];
        }
        return media / salarioRecebido.length;
    }

    public double calcularBonus() {
        return  salarioBase * 0.2;
    }

    public String getNome() {
        return this.nome;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", loja=" + loja +
                '}';
    }
}
