import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraSwing calculadora = new CalculadoraSwing();
            calculadora.setVisible(true);
        });
    }
}