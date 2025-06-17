import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculadora extends JFrame {
    private JTextField campoNumero1;
    private JTextField campoNumero2;
    private JLabel resultado;

    public Calculadora() {
        setTitle("Calculadora Simples");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 2));

        campoNumero1 = new JTextField();
        campoNumero2 = new JTextField();
        resultado = new JLabel("Resultado: ");

        JButton botaoSoma = new JButton("+");
        JButton botaoSubtrai = new JButton("-");
        JButton botaoMultiplica = new JButton("×");
        JButton botaoDivide = new JButton("÷");

        painel.add(new JLabel("Número 1:"));
        painel.add(campoNumero1);
        painel.add(new JLabel("Número 2:"));
        painel.add(campoNumero2);

        painel.add(botaoSoma);
        painel.add(botaoSubtrai);
        painel.add(botaoMultiplica);
        painel.add(botaoDivide);
        painel.add(resultado);

        add(painel);

        // Listeners dos botões
        botaoSoma.addActionListener(e -> calcular("+"));
        botaoSubtrai.addActionListener(e -> calcular("-"));
        botaoMultiplica.addActionListener(e -> calcular("*"));
        botaoDivide.addActionListener(e -> calcular("/"));
    }

    private void calcular(String operacao) {
        try {
            double num1 = validarEntrada(campoNumero1.getText());
            double num2 = validarEntrada(campoNumero2.getText());

            double res = 0;

            switch (operacao) {
                case "+":
                    res = num1 + num2;
                    break;
                case "-":
                    res = num1 - num2;
                    break;
                case "*":
                    res = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new EntradaInvalidaException("Séloko, divisão por zero dá não pai.");
                    }
                    res = num1 / num2;
                    break;
            }

            resultado.setText("Resultado: " + res);

        } catch (EntradaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double validarEntrada(String entrada) throws EntradaInvalidaException {
        try {
            return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Epa, só números!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculadora().setVisible(true));
    }
}