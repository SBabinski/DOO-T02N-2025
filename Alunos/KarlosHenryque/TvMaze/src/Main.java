import javax.swing.*;
import controller.SerieController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            SerieController controller = new SerieController();
            controller.iniciar();
        });
    }
}