import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class EntradaInvalidaException extends Exception {
    public EntradaInvalidaException(String mensagem) {
        super(mensagem);
    }
}

public class CalculadoraGUI extends JFrame {

    private JTextField campoNumero1;
    private JTextField campoNumero2;
    private JLabel labelResultado;

    public CalculadoraGUI() {
        setTitle("Calculadora Simples");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

      
        campoNumero1 = new JTextField(10);
        campoNumero2 = new JTextField(10);
        labelResultado = new JLabel("Resultado: ");
        
        JButton botaoSomar = new JButton("+");
        JButton botaoSubtrair = new JButton("-");
        JButton botaoMultiplicar = new JButton("×");
        JButton botaoDividir = new JButton("÷");

       
        JPanel painelEntrada = new JPanel();
        painelEntrada.add(new JLabel("Número 1:"));
        painelEntrada.add(campoNumero1);
        painelEntrada.add(new JLabel("Número 2:"));
        painelEntrada.add(campoNumero2);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(botaoSomar);
        painelBotoes.add(botaoSubtrair);
        painelBotoes.add(botaoMultiplicar);
        painelBotoes.add(botaoDividir);

        JPanel painelResultado = new JPanel();
        painelResultado.add(labelResultado);

       
        setLayout(new BorderLayout());
        add(painelEntrada, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);
        add(painelResultado, BorderLayout.SOUTH);

        
        botaoSomar.addActionListener(e -> calcular('+'));
        botaoSubtrair.addActionListener(e -> calcular('-'));
        botaoMultiplicar.addActionListener(e -> calcular('*'));
        botaoDividir.addActionListener(e -> calcular('/'));
    }

    private void calcular(char operador) {
        try {
            double num1 = parseInput(campoNumero1.getText());
            double num2 = parseInput(campoNumero2.getText());
            double resultado;

            switch (operador) {
                case '+':
                    resultado = num1 + num2;
                    break;
                case '-':
                    resultado = num1 - num2;
                    break;
                case '*':
                    resultado = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        throw new EntradaInvalidaException("Erro: Divisão por zero não é permitida.");
                    }
                    resultado = num1 / num2;
                    break;
                default:
                    throw new EntradaInvalidaException("Operação desconhecida.");
            }

            labelResultado.setText("Resultado: " + resultado);

        } catch (EntradaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double parseInput(String input) throws EntradaInvalidaException {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Entrada inválida: use apenas números.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculadoraGUI().setVisible(true);
        });
    }
}
