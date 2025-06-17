public interface OperacaoMatematica {
    double executar(double a, double b) throws CalculadoraException;
    
    String getSimbolo();
}
