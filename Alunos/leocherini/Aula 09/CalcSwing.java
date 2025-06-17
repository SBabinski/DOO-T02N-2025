package calcJava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalcSwing extends JFrame {
    private JTextField campo1, campo2, resultado;
    private JButton somar, subtrair, multiplicar, dividir;

    public CalcSwing() {
        setTitle("Calculadora Simples");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        campo1 = new JTextField();
        campo2 = new JTextField();
        resultado = new JTextField();
        resultado.setEditable(false);

        somar = new JButton("+");
        subtrair = new JButton("-");
        multiplicar = new JButton("×");
        dividir = new JButton("÷");

        add(new JLabel("Número 1:"));
        add(campo1);
        add(new JLabel("Número 2:"));
        add(campo2);

        add(somar);
        add(subtrair);
        add(multiplicar);
        add(dividir);

        add(new JLabel("Resultado:"));
        add(resultado);

        somar.addActionListener(e -> calcular('+'));
        subtrair.addActionListener(e -> calcular('-'));
        multiplicar.addActionListener(e -> calcular('*'));
        dividir.addActionListener(e -> calcular('/'));

        setVisible(true);
    }

    private void calcular(char operador) {
        try {
            double num1 = Double.parseDouble(campo1.getText());
            double num2 = Double.parseDouble(campo2.getText());
            double res = 0;

            switch (operador) {
                case '+':
                    res = num1 + num2;
                    break;
                case '-':
                    res = num1 - num2;
                    break;
                case '*':
                    res = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        throw new CalcException("Divisão por zero não permitida.");
                    }
                    res = num1 / num2;
                    break;
            }

            resultado.setText(String.valueOf(res));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Entrada inválida. Digite números.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (CalcException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Cálculo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new CalcSwing();
    }
}