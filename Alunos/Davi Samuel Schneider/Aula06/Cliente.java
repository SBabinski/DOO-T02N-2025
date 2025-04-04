public class Cliente {
    private String nome;
    private int idade;
    private String cidade, bairro, rua;

    public Cliente(String nome, int idade, String cidade, String bairro, String rua) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idade=" + idade +
                ", nome=" + nome + '\'' +
                '}';
    }
}
