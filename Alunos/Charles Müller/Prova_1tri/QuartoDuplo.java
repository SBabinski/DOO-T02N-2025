public class QuartoDuplo extends Quarto {
    public QuartoDuplo(int numero, double valorDiaria) {
        super(numero, valorDiaria);
    }

    @Override
    public int getCapacidadeMaxima() {
        return 4;
    }

    @Override
    public String getTipo() {
        return "Duplo";
    }
}
