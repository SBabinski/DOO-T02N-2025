package Aula09;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EmptyStackException;


public class Calculadora extends JFrame implements ActionListener {
    private JTextField campoTexto;
    private String operador;
    private double valor1, valor2;

    public static final Color cinza = new Color(44, 44, 44);

    public Calculadora() {

        setTitle("Calculadora");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        campoTexto = new JTextField();
        campoTexto.setEditable(false);
        campoTexto.setFont(new Font("Arial", Font.PLAIN, 46));
        campoTexto.setMargin(new Insets(15, 10, 15, 10));
        campoTexto.setForeground(Color.orange);
        campoTexto.setBackground(cinza);
        campoTexto.setHorizontalAlignment(SwingConstants.LEFT);
        add(campoTexto, BorderLayout.NORTH);
        JPanel painelBotoes = new JPanel(new GridLayout(4, 4));
        String[] botoes = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.PLAIN, 36));
            botao.setForeground(Color.orange);
            botao.setBackground(cinza);
            botao.addActionListener(this);
            painelBotoes.add(botao);
        }

        add(painelBotoes, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if ("0123456789".contains(comando)) {
            campoTexto.setText(campoTexto.getText() + comando);
        } else if (comando.equals("C")) {
            campoTexto.setText("");
            operador = null;
            valor1 = valor2 = 0;
        } else if (comando.equals("=")) {
            try {
                valor2 = Double.parseDouble(campoTexto.getText());
                double resultado = calcular(valor1, valor2, operador);
                //para remover o decimal se o mesmo for inteiro
                if (resultado == (long) resultado) {
                    campoTexto.setText(String.format("%d", (long) resultado));
                } else {
                    campoTexto.setText(String.format("%s", resultado));
                }
            } catch (RuntimeException ex) {
                campoTexto.setText("Erro: " + ex.getMessage());
            }
        } else {
            try {
                valor1 = Double.parseDouble(campoTexto.getText());
                if (!campoTexto.getText().isEmpty()) {
                    operador = comando;
                    campoTexto.setText("");
                } else {
                    throw new IllegalArgumentException("Informe algum valor!");
                }
            } catch (Exception ex) {
                campoTexto.setText("Erro:" + ex.getMessage());
            }
        }
    }

    private double calcular(double a, double b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) {
                    throw new ArithmeticException("Divisão por zero não é permitida");
                }
                yield a / b;
            }
            default -> 0;
        };
    }

    public static void main(String[] args) {
        new Calculadora();
    }


}
