
import java.awt.*;
import javax.swing.*;

// Exceção personalizada
class CalculadoraException extends Exception {

    public CalculadoraException(String mensagem) {
        super(mensagem);
    }
}

public class CalculadoraSwing extends JFrame {

    private JTextField campoNumero1;
    private JTextField campoNumero2;
    private JLabel resultado;

    public CalculadoraSwing() {
        setTitle("Calculadora Simples");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 2, 10, 10));

        // Componentes da interface
        campoNumero1 = new JTextField();
        campoNumero2 = new JTextField();
        resultado = new JLabel("Resultado: ", JLabel.CENTER);

        JButton botaoSomar = new JButton("+");
        JButton botaoSubtrair = new JButton("-");
        JButton botaoMultiplicar = new JButton("×");
        JButton botaoDividir = new JButton("÷");

        // Adiciona componentes ao painel
        painel.add(new JLabel("Número 1:"));
        painel.add(campoNumero1);
        painel.add(new JLabel("Número 2:"));
        painel.add(campoNumero2);

        painel.add(botaoSomar);
        painel.add(botaoSubtrair);
        painel.add(botaoMultiplicar);
        painel.add(botaoDividir);

        painel.add(resultado);

        add(painel);

        // Adiciona listeners
        botaoSomar.addActionListener(e -> realizarOperacao('+'));
        botaoSubtrair.addActionListener(e -> realizarOperacao('-'));
        botaoMultiplicar.addActionListener(e -> realizarOperacao('*'));
        botaoDividir.addActionListener(e -> realizarOperacao('/'));
    }

    private void realizarOperacao(char operacao) {
        resultado.setText("Resultado: ");

        try {
            double num1 = lerNumero(campoNumero1.getText());
            double num2 = lerNumero(campoNumero2.getText());

            // Validação: nenhum número pode ser zero
            if (Math.abs(num1) < 1e-10 || Math.abs(num2) < 1e-10) {
                throw new CalculadoraException("Erro: nenhum dos números pode ser zero.");
            }

            double res;

            switch (operacao) {
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
                    res = num1 / num2;
                    break;
                default:
                    throw new CalculadoraException("Operação inválida.");
            }

            resultado.setText("Resultado: " + res);

        } catch (CalculadoraException ex) {
            mostrarErro(ex.getMessage());
        } catch (Exception ex) {
            mostrarErro("Entrada inválida. Use apenas números.");
        }
    }

    private double lerNumero(String texto) throws CalculadoraException {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new CalculadoraException("Entrada inválida: " + texto);
        }
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculadoraSwing().setVisible(true);
        });
    }
}
