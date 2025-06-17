package calculadora;


import javax.swing.*;

	public class TesteSwing extends JFrame {
	    public TesteSwing() {
	    	setTitle("Teste de Título");
	        setSize(300, 200);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);

	        JLabel label = new JLabel("Swing está funcionando!", SwingConstants.CENTER);
	        add(label); 

	        setVisible(true);
	    }
	    public static void main(String[] args) {
	        new TesteSwing();
	    }
	}
