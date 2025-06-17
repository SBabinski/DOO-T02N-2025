import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculadora Simples");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(5, 2, 10, 10)); // Layout ajustado

        JTextField campo1 = new JTextField();
        JTextField campo2 = new JTextField();
        JLabel resultado = new JLabel("Resultado: ");

        JButton btnSoma = new JButton("+");
        JButton btnSub = new JButton("-");
        JButton btnMult = new JButton("*");
        JButton btnDiv = new JButton("/");

        frame.add(new JLabel("Número 1:"));
        frame.add(campo1);

        frame.add(new JLabel("Número 2:"));
        frame.add(campo2);

        frame.add(btnSoma);
        frame.add(btnSub);
        frame.add(btnMult);
        frame.add(btnDiv);

        frame.add(new JLabel("")); // Espaço em branco
        frame.add(resultado);

        frame.setLocationRelativeTo(null); // Centraliza a janela
        frame.setVisible(true);

        ActionListener listener = e -> {
            try {
                double num1 = parseNumero(campo1.getText());
                double num2 = parseNumero(campo2.getText());
                double res = 0;

                if (e.getSource() == btnSoma) {
                    res = num1 + num2;
                }

                else if (e.getSource() == btnSub) {
                    res = num1 - num2;
                }

                else if (e.getSource() == btnMult) {
                    res = num1 * num2;
                }

                else if (e.getSource() == btnDiv) {
                    if (num2 == 0) throw new DivisaoPorZeroException("Erro: Divisão por zero.");
                    res = num1 / num2;
                }

                resultado.setText("Resultado: " + res);

            } catch (EntradaInvalidaException | DivisaoPorZeroException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        };

        btnSoma.addActionListener(listener);
        btnSub.addActionListener(listener);
        btnMult.addActionListener(listener);
        btnDiv.addActionListener(listener);
    }

    public static double parseNumero(String texto) throws EntradaInvalidaException {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Erro: Digite apenas números válidos.");
        }
    }
}
