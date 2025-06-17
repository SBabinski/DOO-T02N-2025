public class Divisao {
    
    public static String divisao(int a, int b) {
        if (b == 0) {
            return "Divisão por zero não é permitida.";
        }
        return String.valueOf((double) a / b);
    }
}
