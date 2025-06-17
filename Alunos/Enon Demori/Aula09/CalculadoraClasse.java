package calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraClasse extends JFrame {

    private JTextField campo1, campo2;
    private JLabel resultadoLabel;

    public CalculadoraClasse() {
        setTitle("Calculadora Simples");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        
        campo1 = new JTextField();
        campo2 = new JTextField();
        resultadoLabel = new JLabel("Resultado: ", JLabel.CENTER);

        JButton btnSoma = new JButton("+");
        JButton btnSubtracao = new JButton("-");
        JButton btnMultiplicacao = new JButton("×");
        JButton btnDivisao = new JButton("÷");

        add(new JLabel("Número 1:"));
        add(campo1);
        add(new JLabel("Número 2:"));
        add(campo2);
        add(btnSoma);
        add(btnSubtracao);
        add(btnMultiplicacao);
        add(btnDivisao);
        add(new JLabel()); 
        add(resultadoLabel);

        btnSoma.addActionListener(e -> realizarOperacao('+'));
        btnSubtracao.addActionListener(e -> realizarOperacao('-'));
        btnMultiplicacao.addActionListener(e -> realizarOperacao('*'));
        btnDivisao.addActionListener(e -> realizarOperacao('/'));

        setVisible(true);
    }

    private void realizarOperacao(char operador) {
        try {
            double num1 = lerNumero(campo1.getText());
            double num2 = lerNumero(campo2.getText());
            double resultado;

            switch (operador) {
                case '+': resultado = num1 + num2; break;
                case '-': resultado = num1 - num2; break;
                case '*': resultado = num1 * num2; break;
                case '/':
                    if (num2 == 0) {
                        throw new EntradaInvalidaException("Divisão por zero não é permitida.");
                    }
                    resultado = num1 / num2; break;
                default:
                    throw new EntradaInvalidaException("Operação inválida.");
            }

            resultadoLabel.setText("Resultado: " + resultado);

        } catch (EntradaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double lerNumero(String texto) throws EntradaInvalidaException {
        try {
            return Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Entrada inválida. Digite apenas números naturais.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraClasse());
    }
}
