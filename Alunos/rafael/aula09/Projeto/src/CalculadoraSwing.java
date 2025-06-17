import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

public class CalculadoraSwing extends JFrame implements ActionListener {

    private JTextField campoNumero1, campoNumero2;
    private JButton botaoSoma, botaoSubtrai, botaoMultiplica, botaoDivide;
    private JLabel labelResultado;

    public CalculadoraSwing() {
        setTitle("Calculadora Simples");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 2, 5, 5));

        add(new JLabel("Número 1:"));
        campoNumero1 = new JTextField();
        add(campoNumero1);

        add(new JLabel("Número 2:"));
        campoNumero2 = new JTextField();
        add(campoNumero2);

        botaoSoma = new JButton("+");
        botaoSoma.addActionListener(this);
        add(botaoSoma);

        botaoSubtrai = new JButton("-");
        botaoSubtrai.addActionListener(this);
        add(botaoSubtrai);

        botaoMultiplica = new JButton("×");
        botaoMultiplica.addActionListener(this);
        add(botaoMultiplica);

        botaoDivide = new JButton("÷");
        botaoDivide.addActionListener(this);
        add(botaoDivide);

        add(new JLabel("Resultado:"));
        labelResultado = new JLabel("");
        labelResultado.setFont(new Font("Arial", Font.BOLD, 16));
        add(labelResultado);

        setVisible(true);
    }

    private double obterNumero(String texto) throws InvalidInputException {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Entrada inválida! Informe um número.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double num1 = obterNumero(campoNumero1.getText());
            double num2 = obterNumero(campoNumero2.getText());
            double resultado = 0;

            if (e.getSource() == botaoSoma) {
                resultado = num1 + num2;
            } else if (e.getSource() == botaoSubtrai) {
                resultado = num1 - num2;
            } else if (e.getSource() == botaoMultiplica) {
                resultado = num1 * num2;
            } else if (e.getSource() == botaoDivide) {
                if (num2 == 0) {
                    throw new InvalidInputException("Erro: Divisão por zero não é permitida.");
                }
                resultado = num1 / num2;
            }

            labelResultado.setText(String.format("%.2f", resultado));

        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            labelResultado.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraSwing());
    }
}
