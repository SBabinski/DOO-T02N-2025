package Alunos.FelipeHeringer.Aula09;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Calculadora_frame extends JFrame implements ActionListener {
    private Calculadora calculadora;

    JTextField num1_Field;
    JTextField num2_Field;

    JButton clearButton;
    JButton somaButton;
    JButton subtracaoButton;
    JButton multiplicacaoButton;
    JButton divisaoButton;

    JTextField resultado;
    JButton resultadoButton;

    public Calculadora_frame() {
        this.calculadora = new Calculadora();
        setTitle("Calculadora da Aula 09");

        this.num1_Field = new JTextField();
        this.num2_Field = new JTextField();

        this.clearButton = new JButton(" c ");
        this.clearButton.setFont(new Font("Arial", Font.BOLD, 16));

        this.somaButton = new JButton(" + ");
        this.somaButton.setFont(new Font("Arial", Font.BOLD, 16));

        this.subtracaoButton = new JButton(" - ");
        this.subtracaoButton.setFont(new Font("Arial", Font.BOLD, 16));

        this.multiplicacaoButton = new JButton(" * ");
        this.multiplicacaoButton.setFont(new Font("Arial", Font.BOLD, 16));

        this.divisaoButton = new JButton(" / ");
        this.divisaoButton.setFont(new Font("Arial", Font.BOLD, 16));

        this.resultado = new JTextField();
        this.resultadoButton = new JButton(" = ");
        this.resultadoButton.setFont(new Font("Arial", Font.BOLD, 16));

        clearButton.addActionListener(this);
        somaButton.addActionListener(this);
        subtracaoButton.addActionListener(this);
        multiplicacaoButton.addActionListener(this);
        divisaoButton.addActionListener(this);
        resultadoButton.addActionListener(this);
    }

    public void construct() {
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        addElements();
        setVisible(true);
    }

    private void addElements() {
        GridBagConstraints gbc = new GridBagConstraints();
        resultado.setEditable(false);
        resultado.setFont(new Font("Arial", Font.BOLD, 24));
        resultado.setHorizontalAlignment(SwingConstants.RIGHT);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.2;
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 20;
        add(resultado, gbc);

        gbc.ipady = 0;
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.1;
        add(num1_Field, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(num2_Field, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        add(clearButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        add(resultadoButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(somaButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(subtracaoButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        add(multiplicacaoButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        add(divisaoButton, gbc);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String command = e.getActionCommand();
            switch (command.trim()) {
                case "+":
                    if (validateFields()) {
                        double[] numbers = parseValuesToDouble(num1_Field.getText(), num2_Field.getText());
                        double resultadoSoma = calculadora.somarNumeros(numbers[0], numbers[1]);
                        resultado.setText("Resultado da soma é : " + resultadoSoma);
                    }
                    break;
                case "-":
                    if (validateFields()) {
                        double[] numbers = parseValuesToDouble(num1_Field.getText(), num2_Field.getText());
                        double resultadoSubtracao = calculadora.subtrairNumeros(numbers[0], numbers[1]);
                        resultado.setText("Resultado da subtração é : " + resultadoSubtracao);
                    }
                    break;

                case "*":
                    if (validateFields()) {
                        double[] numbers = parseValuesToDouble(num1_Field.getText(), num2_Field.getText());
                        double resultadoMultiplicacao = calculadora.multiplicarNumeros(numbers[0], numbers[1]);
                        resultado.setText("Resultado da multiplicação é : " + resultadoMultiplicacao);
                    }
                    break;

                case "/":
                    if (validateFields()) {
                        double[] numbers = parseValuesToDouble(num1_Field.getText(), num2_Field.getText());
                        double resultadoDivisao = calculadora.dividirNumeros(numbers[0], numbers[1]);
                        resultado.setText("Resultado da divisão é : " + resultadoDivisao);
                    }
                    break;

                case "=":
                    break;

                case "c":
                    resultado.setText("");
                    num1_Field.setText("");
                    num2_Field.setText("");
                    break;

                default:
                    break;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateFields() {
        boolean validFields = true;
        if (num1_Field.getText().trim().isEmpty() || num2_Field.getText().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Para realizar uma operação, todos os campos deve ser preenchidos corretamente");
        }

        String numberPattern =  "^-?\\d+(\\.\\d+)?$";

        if (!num1_Field.getText().matches(numberPattern) || !num2_Field.getText().matches(numberPattern)) {
            throw new IllegalArgumentException("Para realizar uma operação, deve ter apenas números");
        }

        if (num1_Field.getText().length() > 8 || num2_Field.getText().length() > 8) {
            throw new IllegalArgumentException("O Numéro de digitos excedeu o limite");

        }

        return validFields;
    }

    private double[] parseValuesToDouble(String number1Str, String number2Str) {
        double[] numbersParsed = new double[2];

        numbersParsed[0] = Double.parseDouble(number1Str);
        numbersParsed[1] = Double.parseDouble(number2Str);

        return numbersParsed;
    }
}
