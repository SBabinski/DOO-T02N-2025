public class CalculadoraException extends Exception {
    
    public static final int ERRO_DIVISAO_ZERO = 1;
    public static final int ERRO_ENTRADA_INVALIDA = 2;
    public static final int ERRO_CAMPO_VAZIO = 3;
    public static final int ERRO_OVERFLOW = 4;
    
    private int tipoErro;
    
    public CalculadoraException(String mensagem) {
        super(mensagem);
        this.tipoErro = 0;
    }
      public CalculadoraException(String mensagem, int tipoErro) {
        super(mensagem);
        this.tipoErro = tipoErro;
    }
    
    public int getTipoErro() {
        return tipoErro;
    }
}
