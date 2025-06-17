import javax.swing.*;
import java.awt.event.*;;

public class Calculadora {

    static String operacao = "+";

    public static void main(String[] args) throws Exception {
        JFrame janela = new JFrame();

        janela.setLayout(null);

        // Labels
        JLabel labelTitulo = new JLabel("Calculadora");
        labelTitulo.setBounds(105, -130, 100, 300);

        JLabel labelResultado = new JLabel("Resultado");
        labelResultado.setBounds(90, 240, 200, 30);

        JLabel labelOperador = new JLabel("+");
        labelOperador.setBounds(135, 65, 30, 30);


        // TextField
        JTextField campoN1 = new JTextField();
        campoN1.setBounds(90, 40, 100, 30);

        JTextField campoN2 = new JTextField();
        campoN2.setBounds(90, 90, 100, 30);


        // Botões
        JButton calcular = new JButton("Calcular");
        calcular.setBounds(90, 200, 100, 30);

        JButton adicao = new JButton("+");
        adicao.setBounds(25, 135, 45, 45);

        JButton sub = new JButton("-");
        sub.setBounds(85, 135, 45, 45);

        JButton mult = new JButton("*");
        mult.setBounds(145, 135, 45, 45);

        JButton div = new JButton("/");
        div.setBounds(205, 135, 45, 45);


        adicao.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                operacao = "+";
                labelOperador.setText(operacao);
            }      
        });

        sub.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                operacao = "-";
                labelOperador.setText(operacao);
            }      
        });

        mult.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                operacao = "*";
                labelOperador.setText(operacao);
            }      
        });

        div.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                operacao = "/";
                labelOperador.setText(operacao);
            }      
        });

        calcular.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    double n1 = Double.parseDouble(campoN1.getText());
                    double n2 = Double.parseDouble(campoN2.getText());
                    double resultado = 0;

                    if (operacao.equals("+")){

                        resultado = n1 + n2;

                    } else if (operacao.equals("-")){

                        resultado = n1 - n2;
 
                    } else if (operacao.equals("*")){

                        resultado = n1 * n2;
                    
                    } else if (operacao.equals("/")){

                        if (n2 == 0){

                            JOptionPane.showMessageDialog(null, "Divisão por zero!", "Erro", JOptionPane.ERROR_MESSAGE);

                        }
                           
                        resultado = n1 / n2;

                    }

                    labelResultado.setText("Resultado: " + resultado);

                } catch (NumberFormatException ex) {
                    
                    JOptionPane.showMessageDialog(null, "Digite apenas números!", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                
                }

            }

        });

        janela.add(calcular);
        janela.add(labelTitulo);
        janela.add(campoN1);
        janela.add(campoN2);
        janela.add(labelResultado);
        janela.add(adicao);
        janela.add(sub);
        janela.add(labelOperador);
        janela.add(mult);
        janela.add(div);

        
        // Dimensões da janela
        janela.setBounds(500, 200, 300, 400);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
        janela.setTitle("Calculadora");

    }

}