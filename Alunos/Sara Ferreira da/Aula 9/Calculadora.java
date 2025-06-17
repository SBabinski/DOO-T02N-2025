import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculadora extends JFrame {
    private JTextField numero1Field, numero2Field;
    private JLabel resultadoLabel;

    public Calculadora() {
        setTitle("Calculadora Simples");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(5, 2));

        painel.add(new JLabel("Número 1:"));
        numero1Field = new JTextField();
        painel.add(numero1Field);

        painel.add(new JLabel("Número 2:"));
        numero2Field = new JTextField();
        painel.add(numero2Field);

        JButton somaBtn = new JButton("+");
        JButton subBtn = new JButton("-");
        JButton multBtn = new JButton("×");
        JButton divBtn = new JButton("÷");

        painel.add(somaBtn);
        painel.add(subBtn);
        painel.add(multBtn);
        painel.add(divBtn);

        resultadoLabel = new JLabel("Resultado: ");
        painel.add(resultadoLabel);

        add(painel);

        somaBtn.addActionListener(e -> calcular('+'));
        subBtn.addActionListener(e -> calcular('-'));
        multBtn.addActionListener(e -> calcular('*'));
        divBtn.addActionListener(e -> calcular('/'));

        setVisible(true);
    }

    private void calcular(char operacao) {
        try {
            double num1 = lerNumero(numero1Field.getText());
            double num2 = lerNumero(numero2Field.getText());
            double resultado = switch (operacao) {
                case '+' -> num1 + num2;
                case '-' -> num1 - num2;
                case '*' -> num1 * num2;
                case '/' -> {
                    if (num2 == 0) {
                        throw new EntradaInvalidaException("Divisão por zero não é permitida.");
                    }
                    yield num1 / num2;
                }
                default -> 0;
            };
            resultadoLabel.setText("Resultado: " + resultado);
        } catch (EntradaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double lerNumero(String texto) throws EntradaInvalidaException {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Entrada inválida: '" + texto + "' não é um número válido.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculadora::new);
    }
}