import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CalculadoraException extends Exception {
    public CalculadoraException(String message) {
        super(message);
    }
}

public class CalculadoraSwing extends JFrame {
    private JTextField campoNumero1;
    private JTextField campoNumero2;
    private JLabel labelResultado;
    private JButton botaoSomar;
    private JButton botaoSubtrair;
    private JButton botaoMultiplicar;
    private JButton botaoDividir;

    public CalculadoraSwing() {
        // Configuração da janela principal
        setTitle("Calculadora Simples");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Componentes da interface
        add(new JLabel("Número 1:"));
        campoNumero1 = new JTextField();
        add(campoNumero1);

        add(new JLabel("Número 2:"));
        campoNumero2 = new JTextField();
        add(campoNumero2);

        add(new JLabel("Resultado:"));
        labelResultado = new JLabel("");
        add(labelResultado);

        // Botões de operação
        botaoSomar = new JButton("Somar (+)");
        botaoSubtrair = new JButton("Subtrair (-)");
        botaoMultiplicar = new JButton("Multiplicar (×)");
        botaoDividir = new JButton("Dividir (÷)");

        add(botaoSomar);
        add(botaoSubtrair);
        add(botaoMultiplicar);
        add(botaoDividir);

        // Configurar os listeners dos botões
        configurarListeners();

        // Centralizar a janela
        setLocationRelativeTo(null);
    }

    private void configurarListeners() {
        botaoSomar.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                try {
                    double resultado = calcular('+');
                    labelResultado.setText(String.valueOf(resultado));
                } catch (CalculadoraException ex) {
                    JOptionPane.showMessageDialog(CalculadoraSwing.this, 
                        ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoSubtrair.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                try {
                    double resultado = calcular('-');
                    labelResultado.setText(String.valueOf(resultado));
                } catch (CalculadoraException ex) {
                    JOptionPane.showMessageDialog(CalculadoraSwing.this, 
                        ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoMultiplicar.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                try {
                    double resultado = calcular('×');
                    labelResultado.setText(String.valueOf(resultado));
                } catch (CalculadoraException ex) {
                    JOptionPane.showMessageDialog(CalculadoraSwing.this, 
                        ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoDividir.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                try {
                    double resultado = calcular('÷');
                    labelResultado.setText(String.valueOf(resultado));
                } catch (CalculadoraException ex) {
                    JOptionPane.showMessageDialog(CalculadoraSwing.this, 
                        ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private double calcular(char operacao) throws CalculadoraException {
        try {
            double num1 = Double.parseDouble(campoNumero1.getText());
            double num2 = Double.parseDouble(campoNumero2.getText());

            switch (operacao) {
                case '+':
                    return num1 + num2;
                case '-':
                    return num1 - num2;
                case '×':
                    return num1 * num2;
                case '÷':
                    if (num2 == 0) {
                        throw new CalculadoraException("Divisão por zero não é permitida!");
                    }
                    return num1 / num2;
                default:
                    throw new CalculadoraException("Operação inválida!");
            }
        } catch (NumberFormatException e) {
            throw new CalculadoraException("Por favor, insira apenas números válidos!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculadoraSwing().setVisible(true);
            }
        });
    }
}