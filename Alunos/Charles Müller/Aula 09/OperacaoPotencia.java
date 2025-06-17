public class OperacaoPotencia implements OperacaoMatematica {
    @Override
    public double executar(double a, double b) throws CalculadoraException {
        double resultado = Math.pow(a, b);
        VerificadorMatematico.verificarOverflow(resultado);
        return resultado;
    }
    
    @Override
    public String getSimbolo() {
        return "^";
    }
}
