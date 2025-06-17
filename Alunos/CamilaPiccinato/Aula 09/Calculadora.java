import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
public class Calculadora {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame janela = new JFrame();

        JLabel resultado = new JLabel("Resultado:");
        resultado.setBounds(50,100,300,30);

        JTextField campo1 = new JTextField();
        campo1.setBounds(50, 50, 120, 30);
        JTextField campo2 = new JTextField();
        campo2.setBounds(200, 50, 120, 30);

        JButton botaoSoma = new JButton("+");
        JButton botaoSubtracao = new JButton("-");
        JButton botaoDivisao = new JButton("/");
        JButton botaoMultiplicacao = new JButton("*");
        botaoSoma.setBounds(40,280,50,50);
        botaoSubtracao.setBounds(120, 280, 50, 50);
        botaoDivisao.setBounds(200, 280, 50, 50);
        botaoMultiplicacao.setBounds(280, 280, 50, 50);

        janela.add(botaoMultiplicacao);
        janela.add(botaoSoma);
        janela.add(botaoSubtracao);
        janela.add(botaoDivisao);
        janela.add(resultado);
        janela.add(campo1);
        janela.add(campo2);
       
        
        botaoSoma.addActionListener(e -> {
            try {
                double num1 = Double.parseDouble(campo1.getText());
                double num2 = Double.parseDouble(campo2.getText());
                double res = num1 + num2;
                resultado.setText("Resultado: " + res);
            } catch (NumberFormatException erro) {
                resultado.setText("Erro: insira apenas números.");
            }
        });

        // Subtração
        botaoSubtracao.addActionListener(e -> {
            try {
                double num1 = Double.parseDouble(campo1.getText());
                double num2 = Double.parseDouble(campo2.getText());
                double res = num1 - num2;
                resultado.setText("Resultado: " + res);
            } catch (NumberFormatException erro) {
                resultado.setText("Erro: insira apenas números.");
            }
        });

        // Multiplicação
        botaoMultiplicacao.addActionListener(e -> {
            try {
                double num1 = Double.parseDouble(campo1.getText());
                double num2 = Double.parseDouble(campo2.getText());
                double res = num1 * num2;
                resultado.setText("Resultado: " + res);
            } catch (NumberFormatException erro) {
                resultado.setText("Erro: insira apenas números.");
            }
        });

        // Divisão
        botaoDivisao.addActionListener(e -> {
            try {
                double num1 = Double.parseDouble(campo1.getText());
                double num2 = Double.parseDouble(campo2.getText());
                if (num2 == 0) {
                 resultado.setText("Erro: não é possível dividir por zero.");
        } else {
                double res = num1 / num2;
                resultado.setText("Resultado: " + res);
        }
    }    catch (NumberFormatException ex) {
                resultado.setText("Erro: insira apenas números.");
    }
});
            
        janela.setLayout(null);
        janela.setBounds(0, 0, 400, 400);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    } 
    
}
