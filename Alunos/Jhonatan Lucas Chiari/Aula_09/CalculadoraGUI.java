import javax.swing.*;
import java.awt.*;

public class CalculadoraGUI extends JFrame {
    private JTextField visor;
    private double numero1 = 0;
    private String operacao = "";
    private boolean novoNumero = true;

    public CalculadoraGUI() {
        setTitle("Calculadora Aula 09");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        visor = new JTextField("0");
        visor.setFont(new Font("Arial", Font.PLAIN, 40));
        visor.setHorizontalAlignment(SwingConstants.RIGHT);
        visor.setEditable(false);
        visor.setBackground(Color.BLACK);
        visor.setForeground(Color.WHITE);
        visor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(visor, BorderLayout.NORTH);

        JPanel botoes = new JPanel(new GridLayout(5, 4, 5, 5));
        botoes.setBackground(Color.BLACK);
        botoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] textos = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", ""
        };

        for (String texto : textos) {
            if (texto.isEmpty()) {
                botoes.add(new JLabel()); //espaço vazio
                continue;
            }

            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 22));
            botao.setFocusPainted(false);
            botao.setOpaque(true);
            botao.setBorderPainted(false);

            if (texto.matches("[0-9.]")) {
                botao.setBackground(new Color(80, 80, 80));
                botao.setForeground(Color.WHITE);
                botao.addActionListener(e -> inserirNumero(texto));
            } else if (texto.equals("AC") || texto.equals("+/-") || texto.equals("%")) {
                botao.setBackground(new Color(165, 165, 165));
                botao.setForeground(Color.BLACK);
                botao.addActionListener(e -> acaoEspecial(texto));
            } else {
                botao.setBackground(new Color(255, 149, 0));
                botao.setForeground(Color.WHITE);
                botao.addActionListener(e -> operacao(texto));
            }

            botoes.add(botao);
        }

        add(botoes, BorderLayout.CENTER);
        setVisible(true);
    }

    private void inserirNumero(String valor) {
        if (novoNumero) {
            visor.setText(valor.equals(".") ? "0." : valor);
            novoNumero = false;
        } else {
            if (valor.equals(".") && visor.getText().contains(".")) return;
            visor.setText(visor.getText() + valor);
        }
    }

    private void acaoEspecial(String acao) {
        switch (acao) {
            case "AC":
                visor.setText("0");
                numero1 = 0;
                operacao = "";
                novoNumero = true;
                break;
            case "+/-":
                if (!visor.getText().equals("0")) {
                    double valor = Double.parseDouble(visor.getText());
                    visor.setText(String.valueOf(-valor));
                }
                break;
            case "%":
                double valor = Double.parseDouble(visor.getText()) / 100;
                visor.setText(String.valueOf(valor));
                break;
        }
    }

    private void operacao(String op) {
        try {
            if (op.equals("=")) {
                double numero2 = Double.parseDouble(visor.getText());
                double resultado = calcular(numero1, numero2, operacao);
                visor.setText(String.valueOf(resultado));
                operacao = "";
                novoNumero = true;
            } else {
                numero1 = Double.parseDouble(visor.getText());
                operacao = op;
                novoNumero = true;
            }
        } catch (CalculadoraException ex) {
            visor.setText("Erro informe um número válido");
        } catch (NumberFormatException ex) {
            visor.setText("Inválido");
        }
    }

    private double calcular(double n1, double n2, String op) throws CalculadoraException {
        switch (op) {
            case "+": return n1 + n2;
            case "-": return n1 - n2;
            case "×": return n1 * n2;
            case "÷":
                if (n2 == 0) throw new CalculadoraException("Divisão por zero!");
                return n1 / n2;
            default: return n2;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraGUI::new);
    }
}