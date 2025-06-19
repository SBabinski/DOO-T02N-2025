package view;

import model.Series;
import controller.SerieController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SeriesTVInterface extends JFrame implements SerieView {
    private JTextField buscarSerieField;
    private JTextField idSerieField;
    private JTextField idRemoverField;
    private JComboBox<String> listaAdicionarCombo;
    private JComboBox<String> listaRemoverCombo;
    private JComboBox<String> listaListarCombo;
    private JComboBox<String> criterioOrdenacaoCombo;
    private JTextArea resultadosArea;
    private JScrollPane scrollPaneResultados;
    private JPanel catalogPanel;
    private JScrollPane scrollPaneCatalog;
    private JButton clearResultsButton;

    private SerieController controller;

    public SeriesTVInterface(SerieController controller) {
        super("Sistema de Séries TV");
        this.controller = controller;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 750);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.salvarEEncerrar();
            }
        });

        inicializaComponentes();
    }

    @Override
    public String pedirNomeUsuario() {
        return JOptionPane.showInputDialog(this,
                "Bem-vindo ao sistema de busca de séries TV!\nPor favor, informe seu nome:",
                "Bem-vindo",
                JOptionPane.QUESTION_MESSAGE);
    }

    @Override
    public void exibirBoasVindas(String nomeUsuario) {
        JOptionPane.showMessageDialog(this,
                "Seja bem-vindo ao sistema, " + nomeUsuario + "!",
                "Boas-vindas",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Mensagem", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void exibirMensagem(String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }

    @Override
    public void fecharScanner() {
    }

    @Override
    public String pedirNomeSerieBusca() {
        return buscarSerieField.getText().trim();
    }

    @Override
    public void exibirSeriesEncontradas(List<Series> series) {
        if (series != null && !series.isEmpty()) {
            resultadosArea.setText("Busca concluída. " + series.size() + " séries encontradas.\n");
            displaySeriesCatalog(series);
        } else {
            resultadosArea.setText("Nenhuma série encontrada para sua busca.\n");
            displaySeriesCatalog(series);
        }
    }

    @Override
    public int pedirIdSerieParaAdicionar(String nomeLista) {
        try {
            return Integer.parseInt(idSerieField.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public int pedirIdSerieParaRemover(String nomeLista) {
        try {
            return Integer.parseInt(idRemoverField.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public int pedirCriterioOrdenacao(String nomeLista) {
        return criterioOrdenacaoCombo.getSelectedIndex();
    }

    @Override
    public String getCriterionName(int criterio) {
        switch (criterio) {
            case 1: return "Nome (A-Z)";
            case 2: return "Nota geral (maior para menor)";
            case 3: return "Status da Série";
            case 4: return "Data de Estreia";
            default: return "Sem Ordenação";
        }
    }

    @Override
    public void exibirListaSeries(List<Series> series, String nomeLista, String criterioOrdenacao) {
        StringBuilder sb = new StringBuilder();
        if (series.isEmpty()) {
            sb.append("A lista de ").append(nomeLista).append(" está vazia.\n");
        } else {
            for (Series serie : series) {
                sb.append("ID: ").append(serie.getId())
                        .append(" | Nome: ").append(serie.getName())
                        .append(" | Nota: ").append((serie.getRating() != null && serie.getRating().getAverage() != null ? String.format("%.1f", serie.getRating().getAverage()) : "N/A"))
                        .append(" | Status: ").append((serie.getStatus() != null ? serie.getStatus() : "N/A"))
                        .append(" | Estreia: ").append((serie.getPremiered() != null ? serie.getPremiered() : "N/A"))
                        .append("\n");
            }
        }
        resultadosArea.setText("--- " + nomeLista + " (Ordenado por " + criterioOrdenacao + ") ---\n" + sb.toString() + "\n");
        displaySeriesCatalog(series);
    }

    @Override
    public void showMainApplication() {
        this.setVisible(true);
    }

    @Override
    public int showConfirmDialog(String message, String title, int optionType) {
        return JOptionPane.showConfirmDialog(this, message, title, optionType);
    }

    private void inicializaComponentes() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.25);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Buscar Série"));
        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.insets = new Insets(5, 5, 5, 5);
        gbcSearch.fill = GridBagConstraints.HORIZONTAL;
        gbcSearch.gridx = 0; gbcSearch.gridy = 0; gbcSearch.weightx = 1.0;
        buscarSerieField = new JTextField("Nome da Série", 15);
        buscarSerieField.setForeground(Color.GRAY);
        buscarSerieField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (buscarSerieField.getText().equals("Nome da Série")) {
                    buscarSerieField.setText("");
                    buscarSerieField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (buscarSerieField.getText().isEmpty()) {
                    buscarSerieField.setText("Nome da Série");
                    buscarSerieField.setForeground(Color.GRAY);
                }
            }
        });
        searchPanel.add(buscarSerieField, gbcSearch);
        gbcSearch.gridx = 0; gbcSearch.gridy = 1; gbcSearch.gridwidth = 1;
        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(e -> {
            String nomeSerie = buscarSerieField.getText().trim();
            if (nomeSerie.isEmpty() || nomeSerie.equals("Nome da Série")) {
                exibirMensagem("Por favor, digite o nome da série para buscar.");
                return;
            }
            resultadosArea.setText("Buscando séries para: \"" + nomeSerie + "\"...\n");
            catalogPanel.removeAll();
            catalogPanel.revalidate();
            catalogPanel.repaint();
            controller.buscarSeries(nomeSerie);
        });
        searchPanel.add(buscarButton, gbcSearch);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.weightx = 1.0; leftPanel.add(searchPanel, gbc);

        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBorder(BorderFactory.createTitledBorder("Adicionar Série à Lista"));
        GridBagConstraints gbcAdd = new GridBagConstraints();
        gbcAdd.insets = new Insets(5, 5, 5, 5);
        gbcAdd.fill = GridBagConstraints.HORIZONTAL;

        gbcAdd.gridx = 0; gbcAdd.gridy = 0; gbcAdd.weightx = 1.0;
        idSerieField = new JTextField("ID da Série", 10);
        idSerieField.setForeground(Color.GRAY);
        idSerieField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idSerieField.getText().equals("ID da Série")) {
                    idSerieField.setText("");
                    idSerieField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (idSerieField.getText().isEmpty()) {
                    idSerieField.setText("ID da Série");
                    idSerieField.setForeground(Color.GRAY);
                }
            }
        });
        addPanel.add(idSerieField, gbcAdd);
        gbcAdd.gridx = 0; gbcAdd.gridy = 1;
        listaAdicionarCombo = new JComboBox<>(new String[]{"Favoritos", "Assistidas", "Deseja Assistir"});
        addPanel.add(listaAdicionarCombo, gbcAdd);
        gbcAdd.gridx = 0; gbcAdd.gridy = 2;
        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(e -> {
            try {
                int idAdicionar = Integer.parseInt(idSerieField.getText().trim());
                String tipoLista = (String) listaAdicionarCombo.getSelectedItem();
                controller.adicionarSerie(idAdicionar, tipoLista);
            } catch (NumberFormatException ex) {
                exibirMensagem("ID inválido. Por favor, insira um número válido para adicionar a série.");
            }
        });
        addPanel.add(adicionarButton, gbcAdd);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; leftPanel.add(addPanel, gbc);

        JPanel removePanel = new JPanel(new GridBagLayout());
        removePanel.setBorder(BorderFactory.createTitledBorder("Remover Série da Lista"));
        GridBagConstraints gbcRemove = new GridBagConstraints();
        gbcRemove.insets = new Insets(5, 5, 5, 5);
        gbcRemove.fill = GridBagConstraints.HORIZONTAL;

        gbcRemove.gridx = 0; gbcRemove.gridy = 0; gbcRemove.weightx = 1.0;
        idRemoverField = new JTextField("ID da Série", 10);
        idRemoverField.setForeground(Color.GRAY);
        idRemoverField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idRemoverField.getText().equals("ID da Série")) {
                    idRemoverField.setText("");
                    idRemoverField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (idRemoverField.getText().isEmpty()) {
                    idRemoverField.setText("ID da Série");
                    idRemoverField.setForeground(Color.GRAY);
                }
            }
        });
        removePanel.add(idRemoverField, gbcRemove);
        gbcRemove.gridx = 0; gbcRemove.gridy = 1;
        listaRemoverCombo = new JComboBox<>(new String[]{"Favoritos", "Assistidas", "Deseja Assistir"});
        removePanel.add(listaRemoverCombo, gbcRemove);
        gbcRemove.gridx = 0; gbcRemove.gridy = 2;
        JButton removerButton = new JButton("Remover");
        removerButton.addActionListener(e -> {
            try {
                int idRemover = Integer.parseInt(idRemoverField.getText().trim());
                String tipoLista = (String) listaRemoverCombo.getSelectedItem();
                controller.removerSerie(idRemover, tipoLista);
            } catch (NumberFormatException ex) {
                exibirMensagem("ID inválido. Por favor, insira um número válido para remover a série.");
            }
        });
        removePanel.add(removerButton, gbcRemove);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; leftPanel.add(removePanel, gbc);

        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Listar Séries"));
        GridBagConstraints gbcList = new GridBagConstraints();
        gbcList.insets = new Insets(5, 5, 5, 5);
        gbcList.fill = GridBagConstraints.HORIZONTAL;

        gbcList.gridx = 0; gbcList.gridy = 0; gbcList.weightx = 1.0;
        listaListarCombo = new JComboBox<>(new String[]{"Favoritos", "Assistidas", "Deseja Assistir"});
        listPanel.add(listaListarCombo, gbcList);
        gbcList.gridx = 0; gbcList.gridy = 1;
        criterioOrdenacaoCombo = new JComboBox<>(new String[]{"Sem Ordenação", "Nome (A-Z)", "Nota Geral (Maior para Menor)", "Status", "Data de Estreia"});
        listPanel.add(criterioOrdenacaoCombo, gbcList);
        gbcList.gridx = 0; gbcList.gridy = 2;
        JButton listarButton = new JButton("Listar");
        listarButton.addActionListener(e -> {
            String tipoLista = (String) listaListarCombo.getSelectedItem();
            int criterioOrdenacao = criterioOrdenacaoCombo.getSelectedIndex();
            controller.listarSeries(tipoLista, criterioOrdenacao);
        });
        listPanel.add(listarButton, gbcList);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        leftPanel.add(listPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        JButton exitButton = new JButton("Sair do Sistema");
        exitButton.addActionListener(e -> controller.salvarEEncerrar());
        leftPanel.add(exitButton, gbc);


        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));

        catalogPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        catalogPanel.setBackground(new Color(64, 64, 64));
        scrollPaneCatalog = new JScrollPane(catalogPanel);
        scrollPaneCatalog.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Catálogo de Séries", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLUE));
        scrollPaneCatalog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneCatalog.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.add(scrollPaneCatalog, BorderLayout.CENTER);

        resultadosArea = new JTextArea(8, 50);
        resultadosArea.setEditable(false);
        resultadosArea.setLineWrap(true);
        resultadosArea.setWrapStyleWord(true);
        resultadosArea.setBackground(new Color(240, 240, 240));
        scrollPaneResultados = new JScrollPane(resultadosArea);

        JPanel resultsAndClearPanel = new JPanel(new BorderLayout());
        resultsAndClearPanel.add(scrollPaneResultados, BorderLayout.CENTER);

        clearResultsButton = new JButton("Limpar");
        clearResultsButton.setToolTipText("Limpar campo de resultados de texto e catálogo visual.");
        clearResultsButton.addActionListener(e -> {
            resultadosArea.setText("");
            catalogPanel.removeAll();
            catalogPanel.revalidate();
            catalogPanel.repaint();
        });
        JPanel clearButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        clearButtonPanel.add(clearResultsButton);
        resultsAndClearPanel.add(clearButtonPanel, BorderLayout.SOUTH);

        rightPanel.add(resultsAndClearPanel, BorderLayout.SOUTH);

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        add(splitPane, BorderLayout.CENTER);
    }

    @Override
    public void displaySeriesCatalog(List<Series> seriesList) {
        catalogPanel.removeAll();

        if (seriesList == null || seriesList.isEmpty()) {
            JPanel messagePanel = new JPanel(new GridBagLayout());
            messagePanel.setBackground(catalogPanel.getBackground());
            JLabel noSeriesLabel = new JLabel("Nenhuma série para exibir no catálogo.");
            noSeriesLabel.setForeground(Color.WHITE);
            noSeriesLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            messagePanel.add(noSeriesLabel);
            catalogPanel.add(messagePanel);
        } else {
            for (Series serie : seriesList) {
                if (serie != null) {
                    JPanel card = createSeriesCard(serie);
                    catalogPanel.add(card);
                }
            }
        }

        catalogPanel.revalidate();
        catalogPanel.repaint();
        SwingUtilities.invokeLater(() -> scrollPaneCatalog.getVerticalScrollBar().setValue(0));
    }

    private JPanel createSeriesCard(Series serie) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true));
        card.setBackground(new Color(90, 90, 90));
        card.setPreferredSize(new Dimension(200, 350));

        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(180, 200));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBackground(Color.DARK_GRAY);
        imageLabel.setOpaque(true);

        new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                String imageUrl = null;
                if (serie.getImage() != null) {
                    if (serie.getImage().getMedium() != null && !serie.getImage().getMedium().isEmpty()) {
                        imageUrl = serie.getImage().getMedium();
                    } else if (serie.getImage().getOriginal() != null && !serie.getImage().getOriginal().isEmpty()) {
                        imageUrl = serie.getImage().getOriginal();
                    }
                }

                BufferedImage img = null;
                if (imageUrl != null) {
                    try {
                        img = ImageIO.read(new URL(imageUrl));
                    } catch (MalformedURLException e) {
                        System.err.println("URL malformada para a série " + serie.getName() + ": " + imageUrl);
                    } catch (IOException e) {
                        System.err.println("Erro ao carregar imagem para a série " + serie.getName() + " de " + imageUrl + ": " + e.getMessage());
                    }
                }

                if (img == null) {
                    URL placeholderUrl = getClass().getResource("/no_image_placeholder.png");
                    if (placeholderUrl != null) {
                        try {
                            img = ImageIO.read(placeholderUrl);
                        } catch (IOException e) {
                            System.err.println("Erro ao carregar placeholder de imagem: " + e.getMessage());
                        }
                    } else {
                        System.err.println("no_image_placeholder.png não encontrado nos recursos.");
                        img = new BufferedImage(imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g = img.createGraphics();
                        g.setColor(Color.WHITE);
                        g.fillRect(0, 0, img.getWidth(), img.getHeight());
                        g.setColor(Color.BLACK);
                        g.drawString("No Image Available", 5, img.getHeight() / 2);
                        g.dispose();
                    }
                }

                if (img != null) {
                    Image scaledImg = img.getScaledInstance(imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaledImg);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icon = get();
                    if (icon != null) {
                        imageLabel.setIcon(icon);
                        imageLabel.setText("");
                    } else {
                        imageLabel.setIcon(null);
                        imageLabel.setText("No Image Available");
                        imageLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
                        imageLabel.setForeground(Color.RED);
                        imageLabel.setBackground(Color.DARK_GRAY);
                        imageLabel.setOpaque(true);
                    }
                    imageLabel.revalidate();
                    imageLabel.repaint();
                    card.revalidate();
                    card.repaint();
                } catch (Exception e) {
                    System.err.println("Erro no SwingWorker.done() para a série " + serie.getName() + ": " + e.getMessage());
                    e.printStackTrace();
                    imageLabel.setText("Erro ao carregar imagem");
                    imageLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
                    imageLabel.setForeground(Color.ORANGE);
                    imageLabel.setBackground(Color.DARK_GRAY);
                    imageLabel.setOpaque(true);
                    imageLabel.revalidate();
                    imageLabel.repaint();
                }
            }
        }.execute();

        card.add(imageLabel, BorderLayout.CENTER);

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBackground(card.getBackground());
        infoArea.setForeground(Color.WHITE);
        infoArea.setFont(new Font("SansSerif", Font.PLAIN, 10));

        String genres = (serie.getGenres() != null && !serie.getGenres().isEmpty()) ? String.join(", ", serie.getGenres()) : "N/A";
        String rating = (serie.getRating() != null && serie.getRating().getAverage() != null) ? String.format("%.1f", serie.getRating().getAverage()) : "N/A";
        String status = (serie.getStatus() != null && !serie.getStatus().isEmpty()) ? serie.getStatus() : "N/A";
        String premiered = (serie.getPremiered() != null && !serie.getPremiered().isEmpty()) ? serie.getPremiered() : "Desconhecida";
        String ended = (serie.getEnded() != null && !serie.getEnded().isEmpty()) ? serie.getEnded() : "Desconhecida";
        String networkName = (serie.getNetwork() != null && serie.getNetwork().getName() != null && !serie.getNetwork().getName().isEmpty()) ? serie.getNetwork().getName() : "Desconhecida";
        String language = (serie.getLanguage() != null && !serie.getLanguage().isEmpty()) ? serie.getLanguage() : "N/A";


        String info = String.format(
                "ID: %d\n" +
                        "Nome: %s\n" +
                        "Idioma: %s\n" +
                        "Gêneros: %s\n" +
                        "Nota: %s\n" +
                        "Status: %s\n" +
                        "Estreia: %s\n" +
                        "Término: %s\n" +
                        "Emissora: %s",
                serie.getId(),
                serie.getName(),
                language,
                genres,
                rating,
                status,
                premiered,
                ended,
                networkName
        );
        infoArea.setText(info);
        infoArea.setCaretPosition(0);

        card.add(infoArea, BorderLayout.SOUTH);

        return card;
    }
}