public class VerificadorMatematico {
    public static void verificarOverflow(double resultado) throws CalculadoraException {
        if (Double.isInfinite(resultado) || Double.isNaN(resultado)) {
            throw new CalculadoraException("Erro: Resultado fora dos limites permitidos", 
                    CalculadoraException.ERRO_OVERFLOW);
        }
    }
}
