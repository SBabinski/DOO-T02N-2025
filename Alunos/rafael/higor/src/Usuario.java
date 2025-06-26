
public class Usuario {
    private String nome;
    private String apelido;

    public Usuario() {}
    public Usuario(String nome, String apelido) {
        this.nome = nome;
        this.apelido = apelido;
    }

    public String getNome() { return nome; }
    public String getApelido() { return apelido; }
    public void setNome(String nome) { this.nome = nome; }
    public void setApelido(String apelido) { this.apelido = apelido; }
}
