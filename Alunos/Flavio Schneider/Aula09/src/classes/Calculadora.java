package classes;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Calculadora extends JFrame {

    public void CalculadoraSwing() {

        setTitle("Calculadora");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel numero01 = new JLabel("numero 1:");
        numero01.setBounds(30, 20, 80, 25);
        add(numero01);

        JTextField campoNumero01 = new JTextField();
        campoNumero01.setBounds(120, 20, 200, 25);
        add(campoNumero01);

        JLabel numero02 = new JLabel("numero 2:");
        numero02.setBounds(30, 50, 80, 25);
        add(numero02);

        JTextField campoNumero02 = new JTextField();
        campoNumero02.setBounds(120, 50, 200, 25);
        add(campoNumero02);


        JButton botaoSoma = new JButton("+");
        botaoSoma.setBounds(30, 100, 60, 30);
        add(botaoSoma);

        JButton botaoMenos = new JButton("-");
        botaoMenos.setBounds(120, 100, 60, 30);
        add(botaoMenos);

        JButton botaoMultiplicacao = new JButton("*");
        botaoMultiplicacao.setBounds(220, 100, 60, 30);
        add(botaoMultiplicacao);

        JButton botaodivisao = new JButton("/");
        botaodivisao.setBounds(320, 100, 60, 30);
        add(botaodivisao);

        JLabel tituloResultado = new JLabel("Resultado: ");
        tituloResultado.setBounds(30, 150, 300, 25);
        add(tituloResultado);

        JTextField campoResultado = new JTextField();
        campoResultado.setBounds(120, 150, 200, 25);
        campoResultado.setEditable(false);
        add(campoResultado);

        botaoSoma.addActionListener(e -> calcular('+', campoNumero01, campoNumero02, campoResultado));
        botaoMenos.addActionListener(e -> calcular('-', campoNumero01, campoNumero02, campoResultado));
        botaoMultiplicacao.addActionListener(e -> calcular('*', campoNumero01, campoNumero02, campoResultado));
        botaodivisao.addActionListener(e -> calcular('/', campoNumero01, campoNumero02, campoResultado));
    }

    private void calcular(char operador, JTextField campoNumero01, JTextField campoNumero02, JTextField campoResultado) {


        try {

            String texto01 = campoNumero01.getText();
            String texto02 = campoNumero02.getText();

            if (!texto01.matches("\\d+(\\.\\d+)?") || !texto02.matches("\\d+(\\.\\d+)?")) {
                throw new Erro("Digite apenas números válidos.");
            }

            double num1 = Double.parseDouble(texto01);
            double num2 = Double.parseDouble(texto02);
            double resultado = 0;

            switch(operador) {

                case '+': resultado = num1 + num2; break;
                case '-': resultado = num1 - num2; break;
                case '*': resultado = num1 * num2; break;
                case '/':
                    if(num2 == 0) {
                        throw new ArithmeticException("Divisão por zero não é permitida.");
                    }
                    resultado = num1 / num2; break;
            }

            campoResultado.setText(String.valueOf(resultado));

        } catch (Erro ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }



}
