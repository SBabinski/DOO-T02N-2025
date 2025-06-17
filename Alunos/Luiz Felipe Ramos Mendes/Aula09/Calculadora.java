import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculadora extends JFrame {
    private JTextField campoTexto;
    private double primeiroNumero = 0;
    private String operacao = "";
    private boolean novoNumero = true;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        campoTexto = new JTextField();
        campoTexto.setEditable(false);
        campoTexto.setHorizontalAlignment(SwingConstants.RIGHT);
        campoTexto.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel painelBotoes = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] botoes = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 18));
            botao.addActionListener(new BotaoListener());
            painelBotoes.add(botao);
        }

        add(campoTexto, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);
        setVisible(true);
    }

    private class BotaoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            if (comando.matches("[0-9]")) {
                if (novoNumero) {
                    campoTexto.setText(comando);
                    novoNumero = false;
                } else {
                    campoTexto.setText(campoTexto.getText() + comando);
                }
            } else if (comando.matches("[+\\-*/]")) {
                try {
                    primeiroNumero = Double.parseDouble(campoTexto.getText());
                    operacao = comando;
                    novoNumero = true;
                } catch (NumberFormatException ex) {
                    campoTexto.setText("Erro");
                }
            } else if (comando.equals("=")) {
                try {
                    double segundoNumero = Double.parseDouble(campoTexto.getText());
                    double resultado = calcular(primeiroNumero, segundoNumero, operacao);
                    campoTexto.setText(String.valueOf(resultado));
                    novoNumero = true;
                } catch (EntradaInvalidaException ex) {
                    campoTexto.setText(ex.getMessage());
                } catch (Exception ex) {
                    campoTexto.setText("Erro");
                }
            }
        }

        private double calcular(double a, double b, String op) throws EntradaInvalidaException {
            switch (op) {
                case "+": return a + b;
                case "-": return a - b;
                case "*": return a * b;
                case "/":
                    if (b == 0) throw new EntradaInvalidaException("Divisão por zero não é permitida.");
                    return a / b;
                default:
                    throw new EntradaInvalidaException("Operação inválida.");
            }
        }
    }

    public static void main(String[] args) {
        new Calculadora();
    }
}
