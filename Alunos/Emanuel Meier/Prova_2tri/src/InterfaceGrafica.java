import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

import services.SerieMapper;
import services.ApiRequester;

import models.Serie;
import models.User;

public class InterfaceGrafica extends JFrame {
    private User user;
    private ArrayList<Serie> favorites;
    private ArrayList<Serie> toWatch;
    private ArrayList<Serie> watching;

    private JTextArea textArea;
    private JTextField buscaField;
    private JButton buscarButton;

    private JButton btnFavoritos;
    private JButton btnAssistidas;
    private JButton btnDesejo;
    private JButton btnRemover;
    private JComboBox<String> ordenarCombo;
    private ArrayList<Serie> listaAtual;

    private JLabel welcomeLabel;

    public InterfaceGrafica(User user, ArrayList<Serie> favorites, ArrayList<Serie> toWatch, ArrayList<Serie> watching) {
        this.user = user;
        this.favorites = favorites;
        this.toWatch = toWatch;
        this.watching = watching;

        setTitle("Sistema de Séries");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout());

        // Top - label boas-vindas à esquerda e busca à direita com espaçamento
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        welcomeLabel = new JLabel("Bem-vindo, " + user.getName() + "!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JPanel buscaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buscaField = new JTextField(25);
        buscarButton = new JButton("Buscar Série");
        buscaPanel.add(buscaField);
        buscaPanel.add(buscarButton);

        topPanel.add(buscaPanel, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        // Center - área de texto
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        panel.add(scroll, BorderLayout.CENTER);

        // Bottom - botões e combo ordenar
        JPanel bottomPanel = new JPanel();

        btnFavoritos = new JButton("Favoritos");
        btnAssistidas = new JButton("Já Assistidas");
        btnDesejo = new JButton("Desejo Assistir");
        btnRemover = new JButton("Remover Série");

        String[] opcoesOrdenar = {"Ordem Alfabética", "Nota Geral", "Estado da Série", "Data de Estreia"};
        ordenarCombo = new JComboBox<>(opcoesOrdenar);

        bottomPanel.add(btnFavoritos);
        bottomPanel.add(btnAssistidas);
        bottomPanel.add(btnDesejo);
        bottomPanel.add(new JLabel("Ordenar por:"));
        bottomPanel.add(ordenarCombo);
        bottomPanel.add(btnRemover);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        // Eventos
        buscarButton.addActionListener(e -> buscarSerie());

        btnFavoritos.addActionListener(e -> mostrarLista(favorites));
        btnAssistidas.addActionListener(e -> mostrarLista(watching));
        btnDesejo.addActionListener(e -> mostrarLista(toWatch));

        ordenarCombo.addActionListener(e -> {
            if (listaAtual != null) ordenarLista(listaAtual);
        });

        btnRemover.addActionListener(e -> removerSerie());
    }

    private void buscarSerie() {
        String query = buscaField.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome da série para buscar.");
            return;
        }

        Serie serie = SerieMapper.singleSerieMapper(ApiRequester.getSingle(query.replace(" ", "+")));
        if (serie == null) {
            JOptionPane.showMessageDialog(this, "Série não encontrada!");
            return;
        }

        textArea.setText(serieDetalhada(serie));

        String[] opcoes = {"Favoritos", "Já assistidas", "Desejo assistir", "Cancelar"};
        int escolha = JOptionPane.showOptionDialog(
                this,
                "Adicionar a qual lista?",
                "Adicionar Série",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[3]
        );

        switch (escolha) {
            case 0 -> favorites.add(serie);
            case 1 -> watching.add(serie);
            case 2 -> toWatch.add(serie);
            default -> JOptionPane.showMessageDialog(this, "Série não adicionada.");
        }

        atualizarEguardarDados();
    }

    private void mostrarLista(ArrayList<Serie> lista) {
        if (lista == null || lista.isEmpty()) {
            textArea.setText("Lista vazia.");
            listaAtual = null;
            return;
        }
        listaAtual = lista;
        ordenarLista(listaAtual);
    }

    private void ordenarLista(ArrayList<Serie> lista) {
        int op = ordenarCombo.getSelectedIndex();
        switch (op) {
            case 0 -> lista.sort(Comparator.comparing(Serie::getName));
            case 1 -> lista.sort(Comparator.comparing(Serie::getRating).reversed());
            case 2 -> lista.sort(Comparator.comparing(Serie::getStatus));
            case 3 -> lista.sort(Comparator.comparing(Serie::getPremiered));
            default -> {}
        }
        StringBuilder sb = new StringBuilder();
        for (Serie s : lista) {
            sb.append(serieDetalhada(s)).append("\n\n");
        }
        textArea.setText(sb.toString());
    }

    private void removerSerie() {
        String[] opcoes = {"Favoritos", "Já assistidas", "Desejo assistir", "Cancelar"};
        int escolha = JOptionPane.showOptionDialog(
                this,
                "De qual lista você quer remover a série?",
                "Remover Série",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[3]
        );

        ArrayList<Serie> listaRemover = null;
        switch (escolha) {
            case 0 -> listaRemover = favorites;
            case 1 -> listaRemover = watching;
            case 2 -> listaRemover = toWatch;
            default -> {
                JOptionPane.showMessageDialog(this, "Remoção cancelada.");
                return;
            }
        }

        if (listaRemover == null || listaRemover.isEmpty()) {
            JOptionPane.showMessageDialog(this, "A lista selecionada está vazia.");
            return;
        }

        String nome = JOptionPane.showInputDialog(this, "Digite o nome da série que deseja remover:");
        if (nome == null || nome.trim().isEmpty()) {
            return;
        }

        boolean removido = listaRemover.removeIf(s -> s.getName().equalsIgnoreCase(nome.trim()));

        if (removido) {
            JOptionPane.showMessageDialog(this, "Série removida com sucesso.");
            if (listaAtual == listaRemover) {
                ordenarLista(listaAtual);
            }
            atualizarEguardarDados();
        } else {
            JOptionPane.showMessageDialog(this, "Série não encontrada na lista selecionada.");
        }
    }

    private String serieDetalhada(Serie serie) {
        return "Nome: " + serie.getName() +
                "\nIdioma: " + serie.getLanguage() +
                "\nGêneros: " + String.join(", ", serie.getGenres()) +
                "\nNota Geral: " + serie.getRating() +
                "\nEstado: " + serie.getStatus() +
                "\nData de Estreia: " + (serie.getPremiered() != null ? timestampParaData(serie.getPremiered()) : "N/A") +
                "\nData de Término: " + (serie.getEnded() != null ? timestampParaData(serie.getEnded()) : "N/A") +
                "\nEmissora: " + (serie.getNetwork() != null ? serie.getNetwork() : "N/A");
    }

    private String timestampParaData(Long timestamp) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new java.util.Date(timestamp));
    }

    private void atualizarEguardarDados() {
        user.setFavorites(favorites);
        user.setWatching(watching);
        user.setToWatch(toWatch);

        App.salvarDados();
    }
}
