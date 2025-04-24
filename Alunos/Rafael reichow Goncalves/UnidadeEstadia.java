abstract class UnidadeEstadia {
    int codigo;
    double diaria;
    boolean ocupado;

    UnidadeEstadia(int codigo, double diaria) {
        this.codigo = codigo;
        this.diaria = diaria;
        this.ocupado = false;
    }

    abstract int capacidadeMaxima();

    boolean estaLivre() {
        return !ocupado;
    }

    public String toString() {
        return "Unidade " + codigo + " | Capacidade: " + capacidadeMaxima() + " | R$" + diaria + "/dia | " + (ocupado ? "Em uso" : "Disponível");
    }
}