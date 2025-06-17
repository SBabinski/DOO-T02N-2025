import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraSwing extends JFrame {
    private JTextField campoNumero1, campoNumero2;
    private JButton botaoSomar, botaoSubtrair, botaoMultiplicar, botaoDividir;
    private JLabel resultado;

    public CalculadoraSwing() {
        super("Calculadora Simples");

        
        setLayout(new GridLayout(5, 2));

        campoNumero1 = new JTextField();
        campoNumero2 = new JTextField();
        botaoSomar = new JButton("+");
        botaoSubtrair = new JButton("-");
        botaoMultiplicar = new JButton("×");
        botaoDividir = new JButton("÷");
        resultado = new JLabel("Resultado: ");

        add(new JLabel("Número 1:"));
        add(campoNumero1);
        add(new JLabel("Número 2:"));
        add(campoNumero2);
        add(botaoSomar);
        add(botaoSubtrair);
        add(botaoMultiplicar);
        add(botaoDividir);
        add(resultado);

        botaoSomar.addActionListener(e -> calcular('+'));
        botaoSubtrair.addActionListener(e -> calcular('-'));
        botaoMultiplicar.addActionListener(e -> calcular('*'));
        botaoDividir.addActionListener(e -> calcular('/'));

       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setVisible(true);
    }

    private void calcular(char operacao) {
        try {
            double numero1 = lerNumero(campoNumero1.getText());
            double numero2 = lerNumero(campoNumero2.getText());
            double resultadoCalculado;

            switch (operacao) {
                case '+':
                    resultadoCalculado = numero1 + numero2;
                    break;
                case '-':
                    resultadoCalculado = numero1 - numero2;
                    break;
                case '*':
                    resultadoCalculado = numero1 * numero2;
                    break;
                case '/':
                    if (numero2 == 0) {
                        throw new CalculadoraException("Divisão por zero não é permitida.");
                    }
                    resultadoCalculado = numero1 / numero2;
                    break;
                default:
                    throw new CalculadoraException("Operação inválida.");
            }

            resultado.setText("Resultado: " + resultadoCalculado);
        } catch (CalculadoraException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite apenas números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private double lerNumero(String texto) throws CalculadoraException {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new CalculadoraException("Entrada inválida: " + texto);
        }
    }

   
    public static void main(String[] args) {
        new CalculadoraSwing();
    }
}
