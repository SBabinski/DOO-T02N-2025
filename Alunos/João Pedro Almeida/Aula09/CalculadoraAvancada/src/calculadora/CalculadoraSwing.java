package calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraSwing extends JFrame {
    private JTextField campoNumero1;
    private JTextField campoNumero2;
    private JLabel resultadoLabel;
    private Calculadora calculadora;
    
    public CalculadoraSwing() {
        calculadora = new Calculadora();
        
        // Configuração da janela
        setTitle("Calculadora Avançada");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));
        
        // Componentes
        add(new JLabel("Número 1:"));
        campoNumero1 = new JTextField();
        add(campoNumero1);
        
        add(new JLabel("Número 2:"));
        campoNumero2 = new JTextField();
        add(campoNumero2);
        
        // Botões
        add(criarBotaoOperacao("+"));
        add(criarBotaoOperacao("-"));
        add(criarBotaoOperacao("×"));
        add(criarBotaoOperacao("÷"));
        
        // Resultado
        add(new JLabel("Resultado:"));
        resultadoLabel = new JLabel("");
        add(resultadoLabel);
        
        setLocationRelativeTo(null);
    }
    
    private JButton criarBotaoOperacao(String operacao) {
        JButton botao = new JButton(operacao);
        botao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double num1 = calculadora.parseNumber(campoNumero1.getText());
                    double num2 = calculadora.parseNumber(campoNumero2.getText());
                    double resultado = calculadora.calcular(num1, num2, operacao);
                    resultadoLabel.setText(String.format("%.2f", resultado));
                } catch (CalculadoraException ex) {
                    JOptionPane.showMessageDialog(CalculadoraSwing.this, 
                        ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    resultadoLabel.setText("Erro");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CalculadoraSwing.this, 
                        "Por favor, insira números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    resultadoLabel.setText("Erro");
                }
            }
        });
        return botao;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraSwing calculadora = new CalculadoraSwing();
            calculadora.setVisible(true);
        });
    }
}
