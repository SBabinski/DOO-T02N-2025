import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.awt.Label;

public class Calculadora extends JFrame {
    private JTextField campo1, campo2;
    private JLabel resultadoJLabel;

    public Calculadora() {
        setTitle("Calculadora Simples");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Número 1: "));
        campo1 = new JTextField();
        panel.add(campo1);

        panel.add(new JLabel("Número 2: "));
        campo2 = new JTextField();
        panel.add(campo2);

        JButton somarBtn = new JButton("+");
        JButton subtrairBtn = new JButton("-");
        JButton multiplicarBtn = new JButton("×");
        JButton dividirBtn = new JButton("÷");

        panel.add(somarBtn);
        panel.add(subtrairBtn);
        panel.add(multiplicarBtn);
        panel.add(dividirBtn);

        resultadoJLabel = new JLabel("Resultado: ");
        resultadoJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(resultadoJLabel);

        add(panel);

        somarBtn.addActionListener(e -> calcular('+'));
        subtrairBtn.addActionListener(e -> calcular('-'));
        multiplicarBtn.addActionListener(e -> calcular('*'));
        dividirBtn.addActionListener(e -> calcular('/'));
    }

    private void calcular(char operador) {
        try {
            double num1 = parseNumero(campo1.getText());
            double num2 = parseNumero(campo2.getText());

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
                        throw new EntradaInvalidaException("Erro: Divisão por zero.");
                    }
                    resultado = num1 / num2;
                    break;
                default:
                    throw new IllegalStateException("Operação desconhecida.");
            }

            resultadoJLabel.setText("Resultado: " + resultado);

        } catch (EntradaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double parseNumero(String texto) throws EntradaInvalidaException {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Erro: Entrada inválida. Insira apenas números.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculadora().setVisible(true));
    }
}