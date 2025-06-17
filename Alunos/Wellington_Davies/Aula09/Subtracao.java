public class Subtracao {
    
    public static String subtracao(int a, int b) {
        if (a < 0 || b < 0) {
            return "Subtração de números negativos não é permitida.";
        }
        return String.valueOf(a - b);
    }
}
