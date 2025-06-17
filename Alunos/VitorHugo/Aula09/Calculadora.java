package Aula09;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Exception personalizada
class CalculadoraException extends Exception {
    public CalculadoraException(String mensagem) {
        super(mensagem);
    }
}

public class Calculadora extends JFrame {

    private JTextField campoDisplay;
    private String operacao;
    private double numero1;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        campoDisplay = new JTextField();
        campoDisplay.setEditable(false);
        campoDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        campoDisplay.setFont(new Font("Arial", Font.PLAIN, 24));
        add(campoDisplay, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(5, 4, 5, 5));
        String[] botoes = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C"
        };

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 18));
            painelBotoes.add(botao);

            botao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tratarEntrada(botao.getText());
                }
            });
        }

        add(painelBotoes, BorderLayout.CENTER);
    }

    private void tratarEntrada(String entrada) {
        try {
            switch (entrada) {
                case "C":
                    campoDisplay.setText("");
                    numero1 = 0;
                    operacao = null;
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    numero1 = parseInput(campoDisplay.getText());
                    operacao = entrada;
                    campoDisplay.setText("");
                    break;
                case "=":
                    double numero2 = parseInput(campoDisplay.getText());
                    double resultado = calcular(numero1, numero2, operacao);
                    campoDisplay.setText(String.valueOf(resultado));
                    operacao = null;
                    break;
                default:
                    campoDisplay.setText(campoDisplay.getText() + entrada);
            }
        } catch (CalculadoraException ex) {
            mostrarErro(ex.getMessage());
        } catch (NumberFormatException ex) {
            mostrarErro("Digite apenas números válidos.");
        }
    }

    private double parseInput(String input) throws CalculadoraException {
        if (input.isEmpty()) {
            throw new CalculadoraException("Entrada vazia!");
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new CalculadoraException("Entrada inválida: '" + input + "'");
        }
    }

    private double calcular(double n1, double n2, String op) throws CalculadoraException {
        if (op == null) throw new CalculadoraException("Nenhuma operação selecionada.");
        switch (op) {
            case "+": return n1 + n2;
            case "-": return n1 - n2;
            case "*": return n1 * n2;
            case "/":
                if (n2 == 0) throw new CalculadoraException("Não é possível dividir por zero.");
                return n1 / n2;
            default: throw new CalculadoraException("Operação inválida.");
        }
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
        campoDisplay.setText("");
        numero1 = 0;
        operacao = null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculadora().setVisible(true);
        });
    }
}
