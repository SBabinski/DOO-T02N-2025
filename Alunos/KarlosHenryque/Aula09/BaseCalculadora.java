import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BaseCalculadora extends JFrame implements ActionListener {
    private JTextField campoTexto;
    private double num1 = 0, num2 = 0, resultado = 0;
    private char operador;
    private boolean erro = false;

    public BaseCalculadora() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        campoTexto = new JTextField();
        campoTexto.setEditable(false);
        campoTexto.setFont(new Font("Arial", Font.BOLD, 24));
        add(campoTexto, BorderLayout.NORTH);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] botoes = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 20));
            botao.addActionListener(this);
            painel.add(botao);
        }

        add(painel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (erro) {
            campoTexto.setText("");
            erro = false;
        }

        if (comando.matches("[0-9]")) {
            campoTexto.setText(campoTexto.getText() + comando);
        } else if (comando.equals("C")) {
            campoTexto.setText("");
            num1 = num2 = resultado = 0;
            erro = false;
        } else if (comando.equals("=")) {
            try {
                if (campoTexto.getText().isEmpty()) throw new NumberFormatException();
                num2 = Double.parseDouble(campoTexto.getText());
                switch (operador) {
                    case '+': resultado = num1 + num2; break;
                    case '-': resultado = num1 - num2; break;
                    case '*': resultado = num1 * num2; break;
                    case '/':
                        if (num2 == 0) {
                            throw new ArithmeticException("Divisão por zero.");
                        }
                        resultado = num1 / num2;
                        break;
                    default:
                        throw new IllegalStateException("Operador inválido.");
                }
                campoTexto.setText(String.valueOf(resultado));
            } catch (NumberFormatException ex) {
                campoTexto.setText("");
                erro = true;
                JOptionPane.showMessageDialog(this, "Entrada inválida! Digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (ArithmeticException ex) {
                campoTexto.setText("");
                erro = true;
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                campoTexto.setText("");
                erro = true;
                JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try {
                if (campoTexto.getText().isEmpty()) {
                    throw new NumberFormatException();
                }
                operador = comando.charAt(0);
                num1 = Double.parseDouble(campoTexto.getText());
                campoTexto.setText("");
            } catch (NumberFormatException ex) {
                campoTexto.setText("");
                erro = true;
                JOptionPane.showMessageDialog(this, "Digite um número antes de usar um operador.", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
