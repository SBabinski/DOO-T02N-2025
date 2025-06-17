package aula9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {

    private JTextField numero1Field, numero2Field;
    private JLabel resultadoLabel;
    private JButton adicionarBtn, subtrairBtn, multiplicarBtn, dividirBtn;

    public Calculadora() {
        setTitle("Calculadora Simples");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Número 1:"));
        numero1Field = new JTextField();
        add(numero1Field);

        add(new JLabel("Número 2:"));
        numero2Field = new JTextField();
        add(numero2Field);

        adicionarBtn = new JButton("+");
        subtrairBtn = new JButton("-");
        multiplicarBtn = new JButton("×");
        dividirBtn = new JButton("÷");

        adicionarBtn.addActionListener(this);
        subtrairBtn.addActionListener(this);
        multiplicarBtn.addActionListener(this);
        dividirBtn.addActionListener(this);

        add(adicionarBtn);
        add(subtrairBtn);
        add(multiplicarBtn);
        add(dividirBtn);

        add(new JLabel("Resultado:"));
        resultadoLabel = new JLabel("");
        add(resultadoLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double num1 = parseNumber(numero1Field.getText());
            double num2 = parseNumber(numero2Field.getText());
            double resultado;

            if (e.getSource() == adicionarBtn) {
                resultado = num1 + num2;
            } else if (e.getSource() == subtrairBtn) {
                resultado = num1 - num2;
            } else if (e.getSource() == multiplicarBtn) {
                resultado = num1 * num2;
            } else if (e.getSource() == dividirBtn) {
                if (num2 == 0) {
                    throw new CalculadoraException("Não é possível dividir por zero!");
                }
                resultado = num1 / num2;
            } else {
                resultado = 0;
            }
            if (resultado == (long) resultado) { // Verifica se o resultado é um número inteiro
                resultadoLabel.setText(String.valueOf((long) resultado)); // Converte para long (inteiro) e exibe
            } else {
                resultadoLabel.setText(String.valueOf(resultado)); // Exibe com ponto decimal
            }
        } catch (CalculadoraException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro na Calculadora", JOptionPane.ERROR_MESSAGE);
            resultadoLabel.setText("Erro!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Entrada inválida. Por favor, insira apenas números.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            resultadoLabel.setText("Erro!");
        }
    }

    private double parseNumber(String text) throws CalculadoraException {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw new CalculadoraException("Entrada inválida: '" + text + "' não é um número válido.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculadora());
    }
}