import java.io.File;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import models.Serie;
import models.User;
import utils.JsonHandler;

public class App {
    static User user;
    static ArrayList<Serie> favorites = new ArrayList<>();
    static ArrayList<Serie> toWatch = new ArrayList<>();
    static ArrayList<Serie> watching = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            carregarDados();
            InterfaceGrafica gui = new InterfaceGrafica(user, favorites, toWatch, watching);
            gui.setVisible(true);
        });
    }

    static void carregarDados() {
        if (!new File("data/user.json").exists()) {
            String nome = JOptionPane.showInputDialog(null, "Digite seu nome ou apelido:", "Bem-vindo!",
                    JOptionPane.QUESTION_MESSAGE);
            if (nome == null || nome.trim().isEmpty()) {
                nome = "Usuário";
            }
            user = new User();
            user.setName(nome);
            favorites = new ArrayList<>();
            toWatch = new ArrayList<>();
            watching = new ArrayList<>();
        } else {
            user = JsonHandler.readJson();
            if (user != null) {
                favorites = user.getFavorites() != null ? user.getFavorites() : new ArrayList<>();
                toWatch = user.getToWatch() != null ? user.getToWatch() : new ArrayList<>();
                watching = user.getWatching() != null ? user.getWatching() : new ArrayList<>();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao carregar dados do usuário. Criando novo perfil.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                user = new User();
                user.setName("Usuário");
                favorites = new ArrayList<>();
                toWatch = new ArrayList<>();
                watching = new ArrayList<>();
            }
        }
    }

    public static void salvarDados() {
        // Atualiza listas do usuário antes de salvar
        user.setFavorites(favorites);
        user.setToWatch(toWatch);
        user.setWatching(watching);
        JsonHandler.writeJson(user);
    }
}
