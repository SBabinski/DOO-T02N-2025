public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    public Cliente(int id, String nome, String cpf, String email, String telefone) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID deve ser um número positivo.");
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new IllegalArgumentException("CPF deve seguir o formato XXX.XXX.XXX-XX");
        }

        if (email == null || !email.matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Email inválido.");
        }

        if (telefone == null || !telefone.matches("\\(\\d{2}\\) \\d{4,5}-\\d{4}")) {
            throw new IllegalArgumentException("Telefone deve seguir o formato (XX) XXXXX-XXXX ou (XX) XXXX-XXXX");
        }

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }

}
