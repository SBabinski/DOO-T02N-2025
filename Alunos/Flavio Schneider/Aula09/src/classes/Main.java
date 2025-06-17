package classes;

import javax.swing.SwingUtilities;
import classes.Calculadora;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Calculadora calculadora = new Calculadora();

            calculadora.CalculadoraSwing();
            calculadora.setVisible(true);
        });
    }
}


