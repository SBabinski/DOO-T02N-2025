import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {
    public static void main(String[] args) {
        // Cria a janela principal da calculadora
        JFrame frame = new JFrame("Super Calculadora");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Campo de texto para mostrar os números e resultados
        JTextField display = new JTextField();
        display.setEditable(false); // Não permite digitação direta
        display.setHorizontalAlignment(JTextField.CENTER); // Alinha texto ao centro

        // Painel para os botões
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 6, 6)); // 4x4 botões, com espaçamento

        // Lista dos textos dos botões
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        // Cria e adiciona os botões ao painel
        for (String text : buttons) {
            JButton botao = new JButton(text);
            // Adiciona ação ao botão
            botao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String comando = botao.getText(); // Pega o texto do botão clicado
                    if (comando.equals("C")) {
                        // Limpa o display se for o botão C
                        display.setText("");
                    } else if (comando.equals("=")) {
                        // Quando clicar em "=", avalia a expressão do display
                        try {
                            display.setText("" + calculo(display.getText()));
                        } catch (Exception ex) {
                            display.setText("Erro");
                        }
                    } else {
                        // Para números e operadores, adiciona ao texto do display
                        display.setText(display.getText() + comando);
                    }
                }
            });
            panel.add(botao); // Adiciona o botão ao painel
        }

        // Adiciona o display e o painel de botões à janela
        frame.setLayout(new BorderLayout());
        frame.add(display, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true); // Exibe a janela
    }

    // Método para avaliar expressões simples usando as minhas classes de operação
    private static String calculo(String expr) {
        String[] tokens;
        if (expr.contains("+")) {
            tokens = expr.split("\\+");
            return Soma.soma(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
        } else if (expr.contains("-")) {
            tokens = expr.split("-");
            return Subtracao.subtracao(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
        } else if (expr.contains("*")) {
            tokens = expr.split("\\*");
            return Multiplicacao.multiplicacao(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
        } else if (expr.contains("/")) {
            tokens = expr.split("/");
            return Divisao.divisao(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
        }
        return "0";
    }
}