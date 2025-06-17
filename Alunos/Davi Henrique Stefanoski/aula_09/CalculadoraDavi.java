package pack;
import javax.swing.*;
import java.awt.*;

class EntradaInvalidaException extends Exception {
    public EntradaInvalidaException(String mensagem) {
        super(mensagem);
    }
}

public class CalculadoraDavi extends JFrame {
    private JTextField campo1;
    private JTextField campo2;
    private JLabel resultado;

    public CalculadoraDavi() {
        setTitle("Calculadora");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        campo1 = new JTextField();
        campo2 = new JTextField();
        resultado = new JLabel("Resultado: ");

        JButton btnSomar = new JButton("+");
        JButton btnSubtrair = new JButton("-");
        JButton btnMultiplicar = new JButton("×");
        JButton btnDividir = new JButton("÷");

        add(new JLabel("Número 1:"));
        add(campo1);
        add(new JLabel("Número 2:"));
        add(campo2);
        add(btnSomar);
        add(btnSubtrair);
        add(btnMultiplicar);
        add(btnDividir);
        add(resultado);

        btnSomar.addActionListener(e -> calcular('+'));
        btnSubtrair.addActionListener(e -> calcular('-'));
        btnMultiplicar.addActionListener(e -> calcular('*'));
        btnDividir.addActionListener(e -> calcular('/'));

        setVisible(true);
    }

    private void calcular(char operacao) {
        try {
            double num1 = lerNumero(campo1.getText());
            double num2 = lerNumero(campo2.getText());
            double res = 0;

            switch (operacao) {
                case '+': res = num1 + num2; break;
                case '-': res = num1 - num2; break;
                case '*': res = num1 * num2; break;
                case '/':
                    if (num2 == 0) {
                        throw new EntradaInvalidaException("Divisão por zero não é permitida.");
                    }
                    res = num1 / num2;
                    break;
            }

            resultado.setText("Resultado: " + res);
        } catch (EntradaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite apenas números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double lerNumero(String texto) throws EntradaInvalidaException {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Entrada inválida: " + texto);
        }
    }

    public static void main(String[] args) {
        new CalculadoraDavi();
    }
}
