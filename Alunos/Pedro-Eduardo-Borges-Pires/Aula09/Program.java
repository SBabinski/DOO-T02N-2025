package calculadora;

import javax.swing.*;
import java.awt.*;

public class Program {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculadora");
        JTextField display = new JTextField();
        frame.setSize(350, 500);
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setPreferredSize(new Dimension(300, 60));
        frame.add(display, BorderLayout.NORTH);

        JPanel painelNumerico = new JPanel();
        painelNumerico.setLayout(new GridLayout(4, 3, 5, 5));

        String[] botoesNumericos = {
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3",
            "0", "C", "="
        };

        final String[] operador = {""};
        final double[] num1 = {0};

        for (String texto : botoesNumericos) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 20));
            painelNumerico.add(botao);

            botao.addActionListener(e -> {
                String cmd = e.getActionCommand();

                if (cmd.matches("[0-9]")) {
                    display.setText(display.getText() + cmd);
                } else if (cmd.equals("=")) {
                    try {
                        if (operador[0].isEmpty()) {
                            display.setText("Operador?");
                            return;
                        }
                        double num2 = Double.parseDouble(display.getText());
                        double resultado = 0;

                        switch (operador[0]) {
                            case "+": resultado = num1[0] + num2; break;
                            case "-": resultado = num1[0] - num2; break;
                            case "*": resultado = num1[0] * num2; break;
                            case "/":
                                if (num2 == 0) {
                                    display.setText("Erro: divisao por 0");
                                    return;
                                }
                                resultado = num1[0] / num2;
                                break;
                        }
                        display.setText(String.valueOf(resultado));
                        operador[0] = "";
                        num1[0] = resultado;
                    } catch (NumberFormatException ex) {
                        display.setText("Num Inválido");
                    } catch (Exception ex) {
                        display.setText("Erro");
                    }
                } else if (cmd.equals("C")) {
                    display.setText("");
                    operador[0] = "";
                    num1[0] = 0;
                }
            });
        }

        JPanel painelOperacoes = new JPanel();
        painelOperacoes.setLayout(new GridLayout(4, 1, 5, 5));

        String[] operacoes = {"+", "-", "*", "/"};

        for (String op : operacoes) {
            JButton botao = new JButton(op);
            botao.setFont(new Font("Arial", Font.BOLD, 20));
            painelOperacoes.add(botao);

            botao.addActionListener(e -> {
                try {
                    if (display.getText().isEmpty()) {
                        display.setText("Num?");
                        return;
                    }
                    num1[0] = Double.parseDouble(display.getText());
                    operador[0] = e.getActionCommand();
                    display.setText("");
                } catch (NumberFormatException ex) {
                    display.setText("Num Inválido");
                } catch (Exception ex) {
                    display.setText("Erro");
                }
            });
        }

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BorderLayout(5, 5));
        painelCentral.add(painelNumerico, BorderLayout.CENTER);
        painelCentral.add(painelOperacoes, BorderLayout.EAST);

        frame.add(painelCentral, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
