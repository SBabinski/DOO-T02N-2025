class RegistroHospede {
    String identificador;
    String nomeCompleto;

    RegistroHospede(String nome, String cpf) {
        this.nomeCompleto = nome;
        this.identificador = cpf;
    }

    public String toString() {
        return nomeCompleto + " | CPF: " + identificador;
    }
}