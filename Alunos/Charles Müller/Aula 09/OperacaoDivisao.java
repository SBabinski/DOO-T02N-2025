public class OperacaoDivisao implements OperacaoMatematica {    
    @Override
    public double executar(double a, double b) throws CalculadoraException {
        if (b == 0) {
            throw new CalculadoraException("Erro: Divisão por zero não é permitida", 
                    CalculadoraException.ERRO_DIVISAO_ZERO);
        }
        double resultado = a / b;
        VerificadorMatematico.verificarOverflow(resultado);
        return resultado;
    }
    
    @Override
    public String getSimbolo() {
        return "÷";
    }
}
