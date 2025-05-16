import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraSwing extends JFrame {
    private JTextField campoNumero1;
    private JTextField campoNumero2;
    private JLabel labelResultado;

    public CalculadoraSwing() {
        setTitle("Calculadora Simples");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Número 1:"));
        campoNumero1 = new JTextField();
        add(campoNumero1);

        add(new JLabel("Número 2:"));
        campoNumero2 = new JTextField();
        add(campoNumero2);

        JButton botaoSomar = new JButton("Somar (+)");
        botaoSomar.addActionListener(new OperacaoListener("+"));
        add(botaoSomar);

        JButton botaoSubtrair = new JButton("Subtrair (-)");
        botaoSubtrair.addActionListener(new OperacaoListener("-"));
        add(botaoSubtrair);

        JButton botaoMultiplicar = new JButton("Multiplicar (×)");
        botaoMultiplicar.addActionListener(new OperacaoListener("*"));
        add(botaoMultiplicar);

        JButton botaoDividir = new JButton("Dividir (÷)");
        botaoDividir.addActionListener(new OperacaoListener("/"));
        add(botaoDividir);

        add(new JLabel("Resultado:"));
        labelResultado = new JLabel("0");
        add(labelResultado);

        setLocationRelativeTo(null);
    }

    private class OperacaoListener implements ActionListener {
        private String operacao;

        public OperacaoListener(String operacao) {
            this.operacao = operacao;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double num1 = parseNumber(campoNumero1.getText());
                double num2 = parseNumber(campoNumero2.getText());
                double resultado = calcular(num1, num2, operacao);
                labelResultado.setText(String.format("%.2f", resultado));
            } catch (CalculadoraException ex) {
                JOptionPane.showMessageDialog(CalculadoraSwing.this,
                        ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CalculadoraSwing.this,
                        "Por favor, insira números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(CalculadoraSwing.this,
                        "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private double parseNumber(String texto) throws CalculadoraException {
            if (texto.trim().isEmpty()) {
                throw new CalculadoraException("Por favor, preencha ambos os campos.");
            }
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                throw new CalculadoraException("Entrada inválida. Use apenas números.");
            }
        }

        private double calcular(double num1, double num2, String operacao) throws CalculadoraException {
            switch (operacao) {
                case "+":
                    return num1 + num2;
                case "-":
                    return num1 - num2;
                case "*":
                    return num1 * num2;
                case "/":
                    if (num2 == 0) {
                        throw new CalculadoraException("Divisão por zero não é permitida.");
                    }
                    return num1 / num2;
                default:
                    throw new CalculadoraException("Operação inválida.");
            }
        }
    }
}