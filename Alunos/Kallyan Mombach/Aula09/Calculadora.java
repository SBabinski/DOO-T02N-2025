import javax.swing.*;
import java.awt.*;

public class Calculadora extends JFrame {
     public static void main(String[] args) {
        new Calculadora().setVisible(true);
    }

    JTextField num1, num2;
    JLabel textoResultado;
    JLabel valorResultado;
    JButton igual;

    char op = ' ';

    public Calculadora() {
        setTitle("Calculadora");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 3));


        num1 = new JTextField();
        num2 = new JTextField();
        igual = new JButton("=");
        textoResultado = new JLabel("RESULTADO: ");
        valorResultado = new JLabel("0");

        JButton soma = new JButton("+");
        JButton sub = new JButton("-");
        JButton mult = new JButton("x");
        JButton div = new JButton("/");
        


        add(new JLabel("Número 1:")); add(num1);
         add(new JLabel("Número 2:")); add(num2);
         add(soma); add(sub);
         add(mult); add(div);
         add(igual);
         add(textoResultado);
         add(valorResultado);

        soma.addActionListener(e -> op = '+');
        sub.addActionListener(e -> op = '-');
        mult.addActionListener(e -> op = 'x');
        div.addActionListener(e -> op = '/');
        igual.addActionListener(e -> calcular());

    }
        void calcular() {
            try {
                double n1 = lerNum(num1.getText());
                double n2 = lerNum(num2.getText());
                double resultado = 0;

                switch (op) {
                    case '+': resultado = n1 + n2; break;
                    case '-': resultado = n1 - n2; break;
                    case 'x': resultado = n1 * n2; break;
                    case '/':
                    if(n2 == 0){
                        throw new MyException("Divisão por Zero!!");
                    }
                    resultado = n1 / n2; break;
                
                }

                valorResultado.setText(String.valueOf(resultado));

            } catch (MyException mensagem1) { 
                JOptionPane.showMessageDialog(this, mensagem1.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException mensagem2){ 
                JOptionPane.showMessageDialog(this,"Digite apenas números!", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        }

        double lerNum(String txt) throws MyException { 
            if(txt.trim().isEmpty()) {
                throw new MyException("Campo vazio!");
            }
            return Double.parseDouble(txt);
        }

    
}