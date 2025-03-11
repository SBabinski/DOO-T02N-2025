public class Calculadora {
    public static double precoTotal(int quantidade, double valor) {
        return quantidade * valor;
    }

    public static double aplicarDesconto(int quantidade, double precoTotal) {
        double desconto = 0;
    
        if (quantidade >= 10) {
            desconto = precoTotal * 0.05;
            precoTotal *= 0.95;
        }
    
        System.out.println("Desconto aplicado: " + desconto);
        
        return precoTotal;
    }
    

    public static double troco(double valorPago, double valorTotal) {
        return valorPago - valorTotal;
    }
}