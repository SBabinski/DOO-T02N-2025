import javax.swing.*;

public class Calculadora extends JFrame {
    JTextField campo1, campo2;
    JLabel resultado;

    public Calculadora() {
        setTitle("Calculadora Simples");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        
        // Campos de texto
        campo1 = new JTextField();
        campo2 = new JTextField();
        campo1.setBounds(30, 20, 100, 25);
        campo2.setBounds(150, 20, 100, 25);

        // Botões
        JButton botaoSoma = new JButton("+");
        JButton botaoSub = new JButton("-");
        JButton botaoMult = new JButton("*");
        JButton botaoDiv = new JButton("/");

        botaoSoma.setBounds(30, 60, 50, 25);
        botaoSub.setBounds(90, 60, 50, 25);
        botaoMult.setBounds(150, 60, 50, 25);
        botaoDiv.setBounds(210, 60, 50, 25);

        // Resultado
        resultado = new JLabel("Resultado: ");
        resultado.setBounds(30, 100, 200, 25);

        // Adicionando componentes
        add(campo1);
        add(campo2);
        add(botaoSoma);
        add(botaoSub);
        add(botaoMult);
        add(botaoDiv);
        add(resultado);

        // Ações dos botões
        botaoSoma.addActionListener(e -> calcular("+"));
        botaoSub.addActionListener(e -> calcular("-"));
        botaoMult.addActionListener(e -> calcular("*"));
        botaoDiv.addActionListener(e -> calcular("/"));

        setVisible(true);
    }

    public void calcular(String operacao) {
        try {
            double num1 = Double.parseDouble(campo1.getText());
            double num2 = Double.parseDouble(campo2.getText());
            double res = 0;

            if (operacao.equals("+")) {
                res = LogicaCalculadora.somar(num1, num2);
            } else if (operacao.equals("-")) {
                res = LogicaCalculadora.subtrair(num1, num2);
            } else if (operacao.equals("*")) {
                res = LogicaCalculadora.multiplicar(num1, num2);
            } else if (operacao.equals("/")) {
                res = LogicaCalculadora.dividir(num1, num2);
            }

            resultado.setText("Resultado: " + res);

        } catch (NumberFormatException e) {
            resultado.setText("Erro: Digite apenas números.");
        } catch (ErroCalculadora e) {
            resultado.setText(e.getMessage());
        }
    }
}
