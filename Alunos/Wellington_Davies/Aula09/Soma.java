public class Soma {
    
    public static String soma(int a, int b) {
        if (a < 0 || b < 0) {
            return "Soma de números negativos não é permitida.";
        }
        return String.valueOf(a + b);
    }
}
