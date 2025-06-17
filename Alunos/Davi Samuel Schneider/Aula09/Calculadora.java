import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculadora {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculadora (Apenas 2 operações)");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.red);

        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setAlignmentY(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.BOLD, 32));
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.WHITE);
        textField.setPreferredSize(new Dimension(400, 100));
        textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.add(textField, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(5, 4, 2, 2));
        painelBotoes.setBackground(Color.DARK_GRAY);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        String[] calculadoraItems = {"", "", "C", "", "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ",", "=", "+"};

        for (String item : calculadoraItems) {
            JButton botao = new JButton(item);
            botao.setFont(new Font("Arial", Font.BOLD, 24));
            botao.setForeground(Color.WHITE);
            botao.setBackground(item.equals("=") ? Color.PINK : Color.GRAY);
            botao.setBorder(null);

            botao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String comando = e.getActionCommand();
                    String text = textField.getText();
                    if (!text.isEmpty() && comando.equals(",") && !text.contains(",")) {
                        textField.setText(text + comando);
                    } else if (comando.equals("C")) {
                        textField.setText("");
                    } else if (!text.isEmpty() && comando.equals("=") && !isOperator(text.charAt(text.length() - 1))) {
                        try {
                            double result = operacao(text);
                            textField.setText(Double.toString(result).replace(".", ","));
                        } catch (InvalidOperation ex) {
                            ex.printStackTrace();
                            textField.setText("0");
                        }

                    } else if (comando.matches("\\d")) {
                        textField.setText(text + comando);
                    } else if (!text.isEmpty() && !isOperator(text.charAt(text.length() - 1)) && !comando.equals(",")) {
                        textField.setText(text + comando);
                    }
                }
            });

            painelBotoes.add(botao);
        }

        panelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        frame.setContentPane(panelPrincipal);
        frame.setVisible(true);
    }

    public static double operacao(String text) throws InvalidOperation {
        Pattern pattern = Pattern.compile("(\\d+(?:,\\d+)?)([+-/*])(\\d+(?:,\\d+)?)");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            double group1 = Double.parseDouble(matcher.group(1).replace(",", "."));
            double group3 = Double.parseDouble(matcher.group(3).replace(",", "."));

            switch (matcher.group(2)) {
                case "+":
                    return group1 + group3;

                case "-":
                    return group1 - group3;

                case "/":
                    if (group3 == 0) {
                        throw new InvalidOperation();
                    }
                    return group1 / group3;

                case "*":
                    return group1 * group3;

                default:
                    return 0;
            }


        }
        return 0;
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '/' || c == '*';
    }
}
