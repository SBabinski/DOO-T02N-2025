import javax.swing.*;

public class Calculadora {

    private JTextField campoNumero1;
    private JTextField campoNumero2;
    private JLabel labelResultado;

    public Calculadora() {
        JFrame frame = new JFrame("Calculadora");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label1 = new JLabel("Número 1:");
        label1.setBounds(20, 20, 80, 20);
        frame.add(label1);

        campoNumero1 = new JTextField();
        campoNumero1.setBounds(100, 20, 150, 20);
        frame.add(campoNumero1);

        JLabel label2 = new JLabel("Número 2:");
        label2.setBounds(20, 50, 80, 20);
        frame.add(label2);

        campoNumero2 = new JTextField();
        campoNumero2.setBounds(100, 50, 150, 20);
        frame.add(campoNumero2);

        JButton btnSoma = new JButton("+");
        btnSoma.setBounds(20, 80, 50, 25);
        frame.add(btnSoma);

        JButton btnSubtrai = new JButton("-");
        btnSubtrai.setBounds(80, 80, 50, 25);
        frame.add(btnSubtrai);

        JButton btnMultiplica = new JButton("*");
        btnMultiplica.setBounds(140, 80, 50, 25);
        frame.add(btnMultiplica);

        JButton btnDivide = new JButton("/");
        btnDivide.setBounds(200, 80, 50, 25);
        frame.add(btnDivide);

        labelResultado = new JLabel("Resultado:");
        labelResultado.setBounds(20, 120, 250, 20);
        frame.add(labelResultado);

        frame.setVisible(true);

        btnSoma.addActionListener(e -> calcular('+'));
        btnSubtrai.addActionListener(e -> calcular('-'));
        btnMultiplica.addActionListener(e -> calcular('*'));
        btnDivide.addActionListener(e -> calcular('/'));
    }

    private void calcular(char operacao) {
        try {
            double num1 = Double.parseDouble(campoNumero1.getText());
            double num2 = Double.parseDouble(campoNumero2.getText());
            double resultado = 0;

            if (operacao == '+') {
                resultado = num1 + num2;
            } else if (operacao == '-') {
                resultado = num1 - num2;
            } else if (operacao == '*') {
                resultado = num1 * num2;
            } else if (operacao == '/') {
                if (num2 == 0) {
                    labelResultado.setText("Erro: divisão por zero");
                    return;
                }
                resultado = num1 / num2;
            }

            labelResultado.setText("Resultado: " + String.format("%.2f", resultado));
        } catch (NumberFormatException e) {
            labelResultado.setText("Erro: digite números válidos");
        }
    }

    public static void main(String[] args) {
        new Calculadora();
    }
}
