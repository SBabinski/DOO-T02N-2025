import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class InputInvalidoException extends Exception {
    public InputInvalidoException(String mensagem) {
        super(mensagem);
    }
}

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculadora Centralizada");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.PINK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        JLabel label1 = new JLabel("Número 1:");
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField textField1 = new JTextField();
        textField1.setMaximumSize(new Dimension(150, 30));
        textField1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label2 = new JLabel("Número 2:");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField textField2 = new JTextField();
        textField2.setMaximumSize(new Dimension(150, 30));
        textField2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        botoesPanel.setOpaque(false); // mantém o fundo rosa
        JButton bMais = new JButton("+");
        JButton bMenos = new JButton("-");
        JButton bVezes = new JButton("*");
        JButton bDivisao = new JButton("/");

        botoesPanel.add(bMais);
        botoesPanel.add(bMenos);
        botoesPanel.add(bVezes);
        botoesPanel.add(bDivisao);

        JLabel resultadoLabel = new JLabel("Resultado: ");
        resultadoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField textField3 = new JTextField();
        textField3.setMaximumSize(new Dimension(150,30));
        textField3.setAlignmentX(Component.CENTER_ALIGNMENT);

        bMais.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String texto1 = textField1.getText();
                    String texto2 = textField2.getText();

                    int n1 = Integer.parseInt(texto1);
                    int n2= Integer.parseInt(texto2);

                    int soma = n1 + n2;

                    textField3.setText(String.valueOf(soma));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Não complica. Só trabalhamos com número.", "Erro de entrada", JOptionPane.WARNING_MESSAGE);
                }


            }
        });

        bMenos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String texto1 = textField1.getText();
                    String texto2 = textField2.getText();

                    int n1 = Integer.parseInt(texto1);
                    int n2= Integer.parseInt(texto2);

                    int subtracao = n1 - n2;

                    textField3.setText(String.valueOf(subtracao));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Não complica. Só trabalhamos com número.", "Erro de entrada", JOptionPane.WARNING_MESSAGE);
                }


            }
        });

        bVezes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String texto1 = textField1.getText();
                    String texto2 = textField2.getText();

                    int n1 = Integer.parseInt(texto1);
                    int n2= Integer.parseInt(texto2);

                    int multiplicacao = n1 * n2;

                    textField3.setText(String.valueOf(multiplicacao));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Não complica. Só trabalhamos com número.", "Erro de entrada", JOptionPane.WARNING_MESSAGE);
                }


            }
        });

        bDivisao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String texto1 = textField1.getText();
                    String texto2 = textField2.getText();

                    int n1 = Integer.parseInt(texto1);
                    int n2 = Integer.parseInt(texto2);

                    if (n2 == 0) {
                        throw new InputInvalidoException("Opa, opa. Dividindo por 0? Tente novamente! 👍");
                    }

                    int resultado = n1 / n2;
                    textField3.setText(String.valueOf(resultado));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Não complica. Só trabalhamos com número.", "Erro de entrada", JOptionPane.WARNING_MESSAGE);
                } catch (InputInvalidoException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro de cálculo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(botoesPanel);
        panel.add(resultadoLabel);
        panel.add(textField3);


        frame.setVisible(true);
    }
}
