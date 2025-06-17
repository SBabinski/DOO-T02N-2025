package calculadora;

public class Calculadora {
    
    public double calcular(double num1, double num2, String operacao) throws CalculadoraException {
        switch (operacao) {
            case "+":
                return somar(num1, num2);
            case "-":
                return subtrair(num1, num2);
            case "×":
                return multiplicar(num1, num2);
            case "÷":
                return dividir(num1, num2);
            default:
                throw new CalculadoraException("Operação inválida.");
        }
    }
    
    private double somar(double a, double b) {
        return a + b;
    }
    
    private double subtrair(double a, double b) {
        return a - b;
    }
    
    private double multiplicar(double a, double b) {
        return a * b;
    }
    
    private double dividir(double a, double b) throws CalculadoraException {
        if (b == 0) {
            throw new CalculadoraException("Divisão por zero não é permitida.");
        }
        return a / b;
    }
    
    public double parseNumber(String texto) throws CalculadoraException, NumberFormatException {
        if (texto.trim().isEmpty()) {
            throw new CalculadoraException("Por favor, preencha ambos os campos.");
        }
        return Double.parseDouble(texto);
    }
}
