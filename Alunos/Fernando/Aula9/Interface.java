package Aula9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface extends JFrame implements ActionListener {

    private JTextField campoTexto;
    private double numero1, numero2;
    private String operador;
    private Calculadora calculadora;

    public Interface() {
        calculadora = new Calculadora();

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

        try {
            if (comando.matches("[0-9]")) {
                campoTexto.setText(campoTexto.getText() + comando);
            } else if (comando.matches("[\\+\\-\\*/]")) {
                numero1 = Double.parseDouble(campoTexto.getText());
                operador = comando;
                campoTexto.setText("");
            } else if (comando.equals("=")) {
                numero2 = Double.parseDouble(campoTexto.getText());
                double resultado = 0;

                switch (operador) {
                    case "+": resultado = calculadora.somar(numero1, numero2); break;
                    case "-": resultado = calculadora.subtrair(numero1, numero2); break;
                    case "*": resultado = calculadora.multiplicar(numero1, numero2); break;
                    case "/": resultado = calculadora.dividir(numero1, numero2); break;
                }

                campoTexto.setText(String.valueOf(resultado));
            } else if (comando.equals("C")) {
                campoTexto.setText("");
                numero1 = numero2 = 0;
                operador = "";
            }
        } catch (NumberFormatException ex) {
            campoTexto.setText("Erro");
        } catch (ArithmeticException ex) {
            campoTexto.setText("Erro: " + ex.getMessage());
        }
    }
        
}
