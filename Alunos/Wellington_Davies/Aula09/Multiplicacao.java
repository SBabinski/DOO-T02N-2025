public class Multiplicacao {

    public static String multiplicacao(int a, int b) {
        if (a == 0 || b == 0) {
            return "Multiplicação por zero não é permitida.";
        }
        return String.valueOf(a * b);
    }
}
