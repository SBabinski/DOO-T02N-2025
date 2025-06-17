package Aula09;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

public class Calculadora extends JFrame {
    private JTextField campo1, campo2; 
    private JLabel resultado;

     public Calculadora() {
        setTitle("Calculadora");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelCentro = new JPanel(new GridLayout(4, 2, 5, 5));

        campo1 = new JTextField();
        campo2 = new JTextField();
        resultado = new JLabel("Resultado: ");
        resultado.setFont(new Font("Arial", Font.BOLD, 16)); // Deixa o texto maior e mais visível

        JButton soma = new JButton("+");
        JButton subtrai = new JButton("-");
        JButton multiplica = new JButton("*");
        JButton divide = new JButton("/");

        painelCentro.add(new JLabel("Valor 1: "));
        painelCentro.add(campo1);
        painelCentro.add(new JLabel("Valor 2: "));
        painelCentro.add(campo2);
        painelCentro.add(soma);
        painelCentro.add(subtrai);
        painelCentro.add(multiplica);
        painelCentro.add(divide);

        add(painelCentro, BorderLayout.CENTER);

        // Painel para o resultado com padding (margem)
        JPanel painelResultado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelResultado.setBorder(new EmptyBorder(10, 15, 10, 15)); // margem superior, esquerda, inferior, direita
        painelResultado.add(resultado);

        add(painelResultado, BorderLayout.SOUTH);

        soma.addActionListener(e -> calcular('+'));
        subtrai.addActionListener(e -> calcular('-'));
        multiplica.addActionListener(e -> calcular('*'));
        divide.addActionListener(e -> calcular('/'));

        setVisible(true);
    }
    

    private void calcular(char operador) {
        try {
            double num1 = lerNumero(campo1.getText());
            double num2 = lerNumero(campo2.getText());
            double res;

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
                    if (num2 == 0) throw new EntradaInvalida("Erro: divisão por zero.");
                    res = num1 / num2;
                    break;
                default:
                    throw new EntradaInvalida("Operação inválida.");
            }

            resultado.setText("Resultado: " + res);

        } catch (EntradaInvalida ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double lerNumero(String texto) throws EntradaInvalida {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new EntradaInvalida("Por favor, digite um número válido.");
        }
    }

    public static void main(String[] args) {
        new Calculadora();
    }
}