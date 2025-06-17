import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraSwing extends JFrame {

    private JTextField campoNumero1, campoNumero2, campoResultado;
    private JButton btnSomar, btnSubtrair, btnMultiplicar, btnDividir;

    public CalculadoraSwing() {
        setTitle("Calculadora Simples");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        campoNumero1 = new JTextField();
        campoNumero2 = new JTextField();
        campoResultado = new JTextField();
        campoResultado.setEditable(false);

        btnSomar = new JButton("+");
        btnSubtrair = new JButton("-");
        btnMultiplicar = new JButton("×");
        btnDividir = new JButton("÷");

        add(new JLabel("Número 1:"));
        add(campoNumero1);
        add(new JLabel("Número 2:"));
        add(campoNumero2);

        add(btnSomar);
        add(btnSubtrair);
        add(btnMultiplicar);
        add(btnDividir);

        add(new JLabel("Resultado:"));
        add(campoResultado);

        btnSomar.addActionListener(e -> calcular("+"));
        btnSubtrair.addActionListener(e -> calcular("-"));
        btnMultiplicar.addActionListener(e -> calcular("*"));
        btnDividir.addActionListener(e -> calcular("/"));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void calcular(String operacao) {
        try {
            double num1 = lerNumero(campoNumero1.getText());
            double num2 = lerNumero(campoNumero2.getText());
            double resultado = 0;

            switch (operacao) {
                case "+":
                    resultado = num1 + num2;
                    break;
                case "-":
                    resultado = num1 - num2;
                    break;
                case "*":
                    resultado = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new CalculadoraException("Divisão por zero não é permitida.");
                    }
                    resultado = num1 / num2;
                    break;
            }

            campoResultado.setText(String.valueOf(resultado));
        } catch (CalculadoraException ce) {
            mostrarErro(ce.getMessage());
        } catch (NumberFormatException nfe) {
            mostrarErro("Entrada inválida. Digite apenas números.");
        } catch (Exception ex) {
            mostrarErro("Erro inesperado: " + ex.getMessage());
        }
    }

    private double lerNumero(String texto) throws CalculadoraException {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new CalculadoraException("Entrada inválida: '" + texto + "'");
        }
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
        campoResultado.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraSwing::new);
    }
}