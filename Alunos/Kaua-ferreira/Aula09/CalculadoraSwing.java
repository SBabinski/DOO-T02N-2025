import java.awt.*;
import javax.swing.*;

public class CalculadoraSwing extends JFrame {
    private JTextField campo1, campo2;
    private JLabel resultado;

    public CalculadoraSwing() {
        setTitle("Calculadora Simples");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 5, 5));

        campo1 = new JTextField();
        campo2 = new JTextField();
        resultado = new JLabel("Resultado: ");

        JButton btnSoma = new JButton("+");
        JButton btnSubtrai = new JButton("-");
        JButton btnMultiplica = new JButton("×");
        JButton btnDivide = new JButton("÷");

        btnSoma.addActionListener(e -> calcular('+'));
        btnSubtrai.addActionListener(e -> calcular('-'));
        btnMultiplica.addActionListener(e -> calcular('*'));
        btnDivide.addActionListener(e -> calcular('/'));

        add(new JLabel("Número 1:"));
        add(campo1);
        add(new JLabel("Número 2:"));
        add(campo2);
        add(btnSoma);
        add(btnSubtrai);
        add(btnMultiplica);
        add(btnDivide);
        add(resultado);

        setLocationRelativeTo(null); // Centraliza a janela
        setVisible(true);
    }

    private void calcular(char operacao) {
        try {
            double num1 = parseNumero(campo1.getText());
            double num2 = parseNumero(campo2.getText());

            double res;
            switch (operacao) {
                case '+': res = num1 + num2; break;
                case '-': res = num1 - num2; break;
                case '*': res = num1 * num2; break;
                case '/':
                    if (num2 == 0) {
                        throw new CalculadoraException("Divisão por zero não é permitida.");
                    }
                    res = num1 / num2;
                    break;
                default:
                    throw new CalculadoraException("Operação inválida.");
            }

            resultado.setText("Resultado: " + res);

        } catch (CalculadoraException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double parseNumero(String texto) throws CalculadoraException {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new CalculadoraException("Entrada inválida: digite apenas números.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraSwing());
    }
}
