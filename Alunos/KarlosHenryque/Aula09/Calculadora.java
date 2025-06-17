public class Calculadora {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater( () -> {
            new BaseCalculadora();
        });
    }
}