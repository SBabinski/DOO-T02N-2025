public abstract class Quarto {
    private int numero;
    private double valorDiaria;
    private boolean disponivel;

    public Quarto(int numero, double valorDiaria) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Número do quarto deve ser maior que zero.");
        }
        if (valorDiaria <= 0) {
            throw new IllegalArgumentException("Valor da diária deve ser maior que zero.");
        }
        if (String.valueOf(numero).length() > 4) {
            throw new IllegalArgumentException("Número do quarto deve ter no máximo 4 dígitos.");
        }

        this.numero = numero;
        this.valorDiaria = valorDiaria;
        this.disponivel = true;
    }

    public int getNumero() {
        return numero;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public abstract int getCapacidadeMaxima();

    public abstract String getTipo();

    @Override
    public String toString() {
        return "Quarto " + numero + " - " + getTipo() + " - R$ " + valorDiaria + " - " +
               (disponivel ? "Disponível" : "Ocupado");
    }
}
