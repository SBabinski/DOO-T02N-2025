import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

// inicia o programa
public class Main {
    public static void main(String[] args) {
        // garante que a interface gráfica será criada na thread Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // abre a janela principal
                new SerieApp().inicializar();
            } catch (Exception e) {
                // se acontecer algum erro inesperado ao iniciar, mostra uma mensagem na tela
                JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage());
            }
        });
    }
}