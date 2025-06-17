public class QuartoSimples extends Quarto {
    public QuartoSimples(int numero, double valorDiaria) {
        super(numero, valorDiaria);
    }

    @Override
    public int getCapacidadeMaxima() {
        return 2;
    }

    @Override
    public String getTipo() {
        return "Simples";
    }
}
