package com.fag.doo_series.view;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.fag.doo_series.utils.*;
import com.fag.doo_series.service.SeriesApiClient;
import com.fag.doo_series.model.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;
import java.util.List;

public class SeriesAppGUI {
    // Componentes principais
    private JFrame mainFrame;
    private JFrame loginFrame;
    private MongoConnection mongoConnection;
    private SeriesApiClient apiClient;
    private SearchHistory searchHistory;
    private Usuario currentUser;
    private JsonUserLoader jsonUserLoader;
    private boolean isTestUser = false;

    // Componentes da interface principal
    private JPanel resultPanel;
    private JTextArea resultTextArea;
    private JTextArea searchHistoryTextArea;
    private JComboBox<String> listSelector;
    private JComboBox<String> sortSelector;

    public SeriesAppGUI() {
        mongoConnection = new MongoConnection();
        searchHistory = new SearchHistory();
        apiClient = new SeriesApiClient();
        jsonUserLoader = new JsonUserLoader();

        // Iniciar com a tela de login
        showLoginScreen();
    }

    private void showLoginScreen() {
        loginFrame = new JFrame("Series App - Login");
        loginFrame.setSize(400, 350);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Bem-Vindo à API de Séries");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setMaximumSize(new Dimension(400, 50));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Cadastro");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        JPanel testButtonPanel = new JPanel();
        testButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        testButtonPanel.setMaximumSize(new Dimension(400, 50));

        JButton testUserButton = new JButton("Carregar Usuário de Teste");
        testUserButton.setBackground(new Color(255, 165, 0));
        testUserButton.setForeground(Color.BLACK);
        testButtonPanel.add(testUserButton);

        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(buttonPanel);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(testButtonPanel);

        loginFrame.add(loginPanel);

        // Ação para botão de login
        loginButton.addActionListener(e -> showLoginForm());

        // Ação para botão de cadastro
        registerButton.addActionListener(e -> showRegisterForm());

        testUserButton.addActionListener(e -> loadTestUser());

        loginFrame.setVisible(true);
    }

    private void loadTestUser() {
        Usuario testUser = jsonUserLoader.loadUserFromJson();

        if (testUser != null) {
            currentUser = testUser;
            isTestUser = true; // Set flag to indicate this is a test user

            JOptionPane.showMessageDialog(loginFrame,
                    "Usuário de teste carregado com sucesso!\n" +
                            "Nome: " + testUser.getName() + "\n" +
                            "CPF: " + testUser.getCpf(),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            loginFrame.dispose();
            showMainScreen();
        } else {
            JOptionPane.showMessageDialog(loginFrame,
                    "Erro ao carregar usuário de teste.\n" +
                            "Verifique se o arquivo assets/data/test.json existe.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showLoginForm() {
        JPanel loginFormPanel = new JPanel();
        loginFormPanel.setLayout(new BoxLayout(loginFormPanel, BoxLayout.Y_AXIS));
        loginFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel cpfLabel = new JLabel("Informe seu CPF:");
        JTextField cpfField = new JTextField(15);
        cpfField.setMaximumSize(new Dimension(Integer.MAX_VALUE, cpfField.getPreferredSize().height));

        JButton submitButton = new JButton("Entrar");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginFormPanel.add(cpfLabel);
        loginFormPanel.add(Box.createVerticalStrut(5));
        loginFormPanel.add(cpfField);
        loginFormPanel.add(Box.createVerticalStrut(20));
        loginFormPanel.add(submitButton);

        // Limpa o frame e adiciona o formulário
        loginFrame.getContentPane().removeAll();
        loginFrame.add(loginFormPanel);
        loginFrame.revalidate();
        loginFrame.repaint();

        // Ação para o botão de submit
        submitButton.addActionListener(e -> {
            String cpf = cpfField.getText().trim();

            // Validar CPF
            if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                JOptionPane.showMessageDialog(loginFrame,
                        "O CPF deve estar no formato XXX.XXX.XXX-XX",
                        "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Buscar usuário no MongoDB
            Usuario user = mongoConnection.getUserFromMongoByCpf(cpf);

            if (user != null) {
                currentUser = user;
                JOptionPane.showMessageDialog(loginFrame,
                        "Login realizado com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                loginFrame.dispose();
                showMainScreen();
            } else {
                JOptionPane.showMessageDialog(loginFrame,
                        "Usuário não encontrado. Verifique o CPF informado.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                cpfField.setText("");
            }
        });
    }

    private void showRegisterForm() {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));
        registerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Nome ou Apelido:");
        JTextField nameField = new JTextField(15);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));

        JLabel cpfLabel = new JLabel("CPF (XXX.XXX.XXX-XX):");
        JTextField cpfField = new JTextField(15);
        cpfField.setMaximumSize(new Dimension(Integer.MAX_VALUE, cpfField.getPreferredSize().height));

        JLabel genresLabel = new JLabel("Gêneros Favoritos (separados por vírgula):");
        JTextField genresField = new JTextField(15);
        genresField.setMaximumSize(new Dimension(Integer.MAX_VALUE, genresField.getPreferredSize().height));

        JButton submitButton = new JButton("Cadastrar");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerPanel.add(nameLabel);
        registerPanel.add(Box.createVerticalStrut(5));
        registerPanel.add(nameField);
        registerPanel.add(Box.createVerticalStrut(10));
        registerPanel.add(cpfLabel);
        registerPanel.add(Box.createVerticalStrut(5));
        registerPanel.add(cpfField);
        registerPanel.add(Box.createVerticalStrut(10));
        registerPanel.add(genresLabel);
        registerPanel.add(Box.createVerticalStrut(5));
        registerPanel.add(genresField);
        registerPanel.add(Box.createVerticalStrut(20));
        registerPanel.add(submitButton);

        // Limpa o frame e adiciona o formulário
        loginFrame.getContentPane().removeAll();
        loginFrame.add(registerPanel);
        loginFrame.revalidate();
        loginFrame.repaint();

        // Ação para o botão de cadastro
        submitButton.addActionListener(e -> {
            String nome = nameField.getText().trim();
            String cpf = cpfField.getText().trim();
            String generos = genresField.getText().trim();

            // Validações
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame,
                        "O nome não pode estar vazio",
                        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                JOptionPane.showMessageDialog(loginFrame,
                        "O CPF deve estar no formato XXX.XXX.XXX-XX",
                        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                cpfField.setText("");
                return;
            }

            // Processar gêneros favoritos
            List<Object> favoriteGenres = new ArrayList<>();
            if (!generos.isEmpty()) {
                String[] generosFavoritos = generos.split(",");
                for (String g : generosFavoritos) {
                    favoriteGenres.add(g.trim());
                }
            }

            // Criar novo usuário
            Usuario newUser = new Usuario(nome, cpf, favoriteGenres);
            currentUser = newUser;

            // Salvar no MongoDB
            Map<String, Object> userJsonRegisterInformation = newUser.toJson();
            mongoConnection.putUserOnMongo(userJsonRegisterInformation);

            JOptionPane.showMessageDialog(loginFrame,
                    "Cadastro realizado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            loginFrame.dispose();
            showMainScreen();
        });
    }

    private void showMainScreen() {
        mainFrame = new JFrame("Series App - " + currentUser.getName());
        mainFrame.setSize(1000, 700);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        // Layout principal com 2 colunas e 1 linha
        mainFrame.setLayout(new GridLayout(1, 2, 10, 10));

        // Painel esquerdo (controles)
        JPanel leftPanel = createLeftPanel();

        // Painel direito (área de resultados)
        JPanel rightPanel = createRightPanel();

        mainFrame.add(leftPanel);
        mainFrame.add(rightPanel);

        mainFrame.setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Seção de informações do usuário
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBorder(BorderFactory.createTitledBorder("Informações do Usuário"));

        JLabel userNameLabel = new JLabel("Nome: " + currentUser.getName());
        JLabel userCpfLabel = new JLabel("CPF: " + currentUser.getCpf());

        JButton editNameButton = new JButton("Editar Nome");
        editNameButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        userPanel.add(userNameLabel);
        userPanel.add(Box.createVerticalStrut(5));
        userPanel.add(userCpfLabel);
        userPanel.add(Box.createVerticalStrut(10));
        userPanel.add(editNameButton);

        // Seção de pesquisa de séries
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Buscar Séries"));

        JPanel searchInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Buscar");

        searchInputPanel.add(searchField);
        searchInputPanel.add(searchButton);

        searchPanel.add(searchInputPanel);

        // Seção de adicionar séries às listas
        JPanel addToListPanel = new JPanel();
        addToListPanel.setLayout(new BoxLayout(addToListPanel, BoxLayout.Y_AXIS));
        addToListPanel.setBorder(BorderFactory.createTitledBorder("Adicionar Série à Lista"));

        JPanel seriesIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel seriesIdLabel = new JLabel("ID da Série:");
        JTextField seriesIdField = new JTextField(5);

        seriesIdPanel.add(seriesIdLabel);
        seriesIdPanel.add(seriesIdField);

        JPanel listTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel listTypeLabel = new JLabel("Lista:");
        String[] listTypes = { "Séries Favoritas", "Séries para Assistir", "Séries Assistidas" };
        JComboBox<String> listTypeCombo = new JComboBox<>(listTypes);

        listTypePanel.add(listTypeLabel);
        listTypePanel.add(listTypeCombo);

        JButton addToListButton = new JButton("Adicionar à Lista");
        addToListButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        addToListPanel.add(seriesIdPanel);
        addToListPanel.add(listTypePanel);
        addToListPanel.add(Box.createVerticalStrut(5));
        addToListPanel.add(addToListButton);

        // Seção de Remover séries das listas
        JPanel removeToListPanel = new JPanel();
        removeToListPanel.setLayout(new BoxLayout(removeToListPanel, BoxLayout.Y_AXIS));
        removeToListPanel.setBorder(BorderFactory.createTitledBorder("Remover Série da Lista"));

        JPanel seriesIdRemovePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel seriesIdRemoveLabel = new JLabel("ID da Série:");
        JTextField seriesIdRemoveField = new JTextField(5);

        seriesIdRemoveField.add(seriesIdRemoveLabel);
        seriesIdRemovePanel.add(seriesIdRemoveField);

        JPanel listTypePanelRemove = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel listTypeLabelRemove = new JLabel("Lista:");
        String[] listTypesRemove = { "Séries Favoritas", "Séries para Assistir", "Séries Assistidas" };
        JComboBox<String> listTypeComboRemove = new JComboBox<>(listTypesRemove);

        listTypePanelRemove.add(listTypeLabelRemove);
        listTypePanelRemove.add(listTypeComboRemove);

        JButton removeToListButton = new JButton("Remover da Lista");
        removeToListButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        removeToListPanel.add(seriesIdRemovePanel);
        removeToListPanel.add(listTypePanelRemove);
        removeToListPanel.add(Box.createVerticalStrut(5));
        removeToListPanel.add(removeToListButton);

        // Seção de visualização e ordenação de listas
        JPanel viewListPanel = new JPanel();
        viewListPanel.setLayout(new BoxLayout(viewListPanel, BoxLayout.Y_AXIS));
        viewListPanel.setBorder(BorderFactory.createTitledBorder("Visualizar Lista"));

        JPanel listSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel viewListLabel = new JLabel("Selecione a Lista:");
        listSelector = new JComboBox<>(listTypes);

        listSelectorPanel.add(viewListLabel);
        listSelectorPanel.add(listSelector);

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel sortLabel = new JLabel("Ordenar por:");
        String[] sortOptions = {
                "Ordem Alfabética",
                "Nota Geral",
                "Estado da Série",
                "Data de Estreia"
        };
        sortSelector = new JComboBox<>(sortOptions);

        sortPanel.add(sortLabel);
        sortPanel.add(sortSelector);

        JButton viewListButton = new JButton("Visualizar Lista");
        viewListButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        viewListPanel.add(listSelectorPanel);
        viewListPanel.add(sortPanel);
        viewListPanel.add(Box.createVerticalStrut(5));
        viewListPanel.add(viewListButton);

        // Seção de histórico de pesquisa
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Histórico de Pesquisa"));

        searchHistoryTextArea = new JTextArea(5, 20);
        searchHistoryTextArea.setEditable(false);
        JScrollPane historyScrollPane = new JScrollPane(searchHistoryTextArea);

        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        // Adicionar todos os painéis ao painel principal esquerdo
        panel.add(userPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(searchPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addToListPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(removeToListPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(viewListPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(historyPanel);

        // Adicionar listeners para os botões
        editNameButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(mainFrame,
                    "Digite o novo nome:",
                    currentUser.getName());

            if (newName != null && !newName.trim().isEmpty()) {
                currentUser.setName(newName.trim());
                if (isTestUser) {
                    jsonUserLoader.saveUserToJson(currentUser);
                } else {
                    mongoConnection.updateUserNameByCpf(currentUser.getCpf(), newName.trim());
                }

                userNameLabel.setText("Nome: " + newName.trim());
                mainFrame.setTitle("Series App - " + newName.trim());
                JOptionPane.showMessageDialog(mainFrame,
                        "Nome atualizado com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            if (!searchTerm.isEmpty()) {
                searchSeriesByName(searchTerm);
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        "Por favor, digite um termo de busca",
                        "Campo vazio", JOptionPane.WARNING_MESSAGE);
            }
        });

        addToListButton.addActionListener(e -> {
            try {
                int seriesId = Integer.parseInt(seriesIdField.getText().trim());
                if (seriesId <= 0) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "O ID deve ser um número inteiro positivo",
                            "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Series series = searchHistory.searchSeriesById(seriesId);
                if (series == null) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "Série não encontrada. Verifique o ID ou faça uma busca primeiro.",
                            "Série não encontrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int selectedListIndex = listTypeCombo.getSelectedIndex();
                switch (selectedListIndex) {
                    case 0: // Favoritas
                        // TODO: Verificar se a série já está na lista. Se sim (Mostrar Mensagem ->
                        // Serie já foi adicionada a lista)

                        currentUser.addSeriesOnfavoriteList(series);
                        if (isTestUser) {
                            jsonUserLoader.saveUserToJson(currentUser);
                        } else {
                            mongoConnection.updateFavoriteList(currentUser.getCpf(), series);
                        }
                        break;
                    case 1: // Para assistir
                        // TODO: Verificar se a série já está na lista. Se sim (Mostrar Mensagem ->
                        // Serie já foi adicionada a lista)
                        currentUser.addSeriesOnWatchLaterList(series);
                        if (isTestUser) {
                            jsonUserLoader.saveUserToJson(currentUser);
                        } else {
                            mongoConnection.updateWatchLaterList(currentUser.getCpf(), series);
                        }
                        break;
                    case 2: // Assistidas
                        // TODO: Verificar se a série já está na lista. Se sim (Mostrar Mensagem ->
                        // Serie já foi adicionada a lista)
                        currentUser.addSeriesOnWatchedList(series);
                        if (isTestUser) {
                            jsonUserLoader.saveUserToJson(currentUser);
                        } else {
                            mongoConnection.updateWatchedList(currentUser.getCpf(), series);
                        }
                        break;
                }

                JOptionPane.showMessageDialog(mainFrame,
                        "Série adicionada com sucesso à lista " + listTypeCombo.getSelectedItem(),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame,
                        "O ID da série deve ser um número inteiro",
                        "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeToListButton.addActionListener(e -> {
            try {
                int seriesIdToRemove = Integer.parseInt(seriesIdRemoveField.getText().trim());
                if (seriesIdToRemove <= 0) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "O ID deve ser um número inteiro positivo",
                            "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int selectedListToRemoveIndex = listTypeComboRemove.getSelectedIndex();
                switch (selectedListToRemoveIndex) {
                    case 0: // Favoritas
                        Series series = currentUser.getFavoriteSeries().stream()
                                .filter(s -> s.getId() == seriesIdToRemove)
                                .findFirst()
                                .orElse(null);
                        if (series == null) {
                            JOptionPane.showMessageDialog(mainFrame,
                                    "Série não encontrada. Verifique o ID ou faça uma busca primeiro.",
                                    "Série não encontrada", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        currentUser.removeSeriesOnfavoriteList(series);
                        if (isTestUser) {
                            jsonUserLoader.saveUserToJson(currentUser);
                        } else {
                            mongoConnection.removeSeriesFromFavoriteList(currentUser.getCpf(), series);
                        }
                        break;
                    case 1: // Para assistir
                        Series seriesWatchLater = currentUser.getSeriesWatchLater().stream()
                                .filter(s -> s.getId() == seriesIdToRemove)
                                .findFirst()
                                .orElse(null);
                        if (seriesWatchLater == null) {
                            JOptionPane.showMessageDialog(mainFrame,
                                    "Série não encontrada. Verifique o ID ou faça uma busca primeiro.",
                                    "Série não encontrada", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        currentUser.removeSeriesOnWatchLaterList(seriesWatchLater);
                        if (isTestUser) {
                            jsonUserLoader.saveUserToJson(currentUser);
                        } else {
                            mongoConnection.removeSeriesFromWatchLaterList(currentUser.getCpf(), seriesWatchLater);
                        }

                        break;
                    case 2: // Assistidas
                        Series seriesWatched = currentUser.getSeriesWatched().stream()
                                .filter(s -> s.getId() == seriesIdToRemove)
                                .findFirst()
                                .orElse(null);
                        if (seriesWatched == null) {
                            JOptionPane.showMessageDialog(mainFrame,
                                    "Série não encontrada. Verifique o ID ou faça uma busca primeiro.",
                                    "Série não encontrada", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        currentUser.removeSeriesOnWatchedList(seriesWatched);
                        if (isTestUser) {
                            jsonUserLoader.saveUserToJson(currentUser);
                        } else {
                            mongoConnection.removeSeriesFromWatchedList(currentUser.getCpf(), seriesWatched);
                        }
                        break;
                }
                JOptionPane.showMessageDialog(mainFrame,
                        "Série removida com sucesso da lista " + listTypeCombo.getSelectedItem(),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame,
                        "O ID da série deve ser um número inteiro",
                        "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewListButton.addActionListener(e -> {
            int selectedListIndex = listSelector.getSelectedIndex();
            int selectedSortIndex = sortSelector.getSelectedIndex();

            List<Series> listToShow = null;
            String listName = "";

            // Selecionar a lista
            switch (selectedListIndex) {
                case 0: // Favoritas
                    listToShow = currentUser.getFavoriteSeries();
                    listName = "Séries Favoritas";
                    break;
                case 1: // Para assistir
                    listToShow = currentUser.getSeriesWatchLater();
                    listName = "Séries para Assistir";
                    break;
                case 2: // Assistidas
                    listToShow = currentUser.getSeriesWatched();
                    listName = "Séries Assistidas";
                    break;
            }

            if (listToShow == null || listToShow.isEmpty()) {
                resultPanel.removeAll();
                JLabel noSeriesFoundLabel = new JLabel("A lista " + listName + " está vazia.");
                noSeriesFoundLabel.setFont(new Font("Arial", Font.BOLD, 14));
                noSeriesFoundLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                resultPanel.add(noSeriesFoundLabel);
                resultPanel.add(Box.createVerticalStrut(10));
                return;
            }

            // Ordenar a lista
            switch (selectedSortIndex) {
                case 0: // Ordem Alfabética
                    Collections.sort(listToShow, Comparator.comparing(Series::getName));
                    break;
                case 1: // Nota Geral
                    Collections.sort(listToShow, Comparator.comparing(Series::getRatingAvarage).reversed());
                    break;
                case 2: // Estado da Série
                    Collections.sort(listToShow, Comparator.comparing(Series::getState));
                    break;
                case 3: // Data de Estreia
                    Collections.sort(listToShow, Comparator.comparing(Series::getPremieredDate).reversed());
                    break;
            }

            // Exibir a lista ordenada
            resultPanel.removeAll();
            JLabel headerLabel = new JLabel(listName + "(Ordenada por " + sortSelector.getSelectedItem() + "):\n\n");
            headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            headerLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            resultPanel.add(headerLabel);
            resultPanel.add(Box.createVerticalStrut(10));

            displaySeriesList(listToShow);
        });

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título do painel de resultados
        JLabel resultsTitle = new JLabel("Resultados");
        resultsTitle.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(resultsTitle, BorderLayout.NORTH);

        // Área de texto para exibição de resultados
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void searchSeriesByName(String name) {
        try {
            List<Series> seriesList = apiClient.searchSeriesByName(name);

            if (seriesList.isEmpty()) {
                JLabel noResultsLabel = new JLabel("Nenhuma notícia encontrada para: \"" + name + "\"");
                noResultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultPanel.add(noResultsLabel);
                resultTextArea.setText("Nenhuma série encontrada com o nome: " + name);
            } else {
                searchHistory.addSearchOnHistory(name, seriesList);
                updateSearchHistoryArea();
                resultPanel.removeAll();
                JLabel headerLabel = new JLabel("Séries encontradas para \"" + name + "\":");
                headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
                headerLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
                resultPanel.add(headerLabel);
                resultPanel.add(Box.createVerticalStrut(10));
                displaySeriesList(seriesList);

            }
        } catch (Exception ex) {
            resultTextArea.setText("Erro ao buscar séries: " + ex.getMessage());
        }
    }

    private void displaySeriesList(List<Series> seriesList) {
        for (Series series : seriesList) {
            JPanel newsPanel = createSeriesPanel(series);
            resultPanel.add(newsPanel);
            resultPanel.add(Box.createVerticalStrut(10));
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private JPanel createSeriesPanel(Series series) {
        JPanel seriesPanel = new JPanel();
        seriesPanel.setLayout(new BorderLayout());
        seriesPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel idLabel = new JLabel("ID: " + series.getId());
        JLabel nameLabel = new JLabel("Nome: " + series.getName());
        JLabel language = new JLabel("Idioma: " + series.getLanguage());
        JLabel genresLabel = new JLabel("Gêneros: " + String.join(", ", series.getGenres()));
        JLabel ratingAvarageLabel = new JLabel("Nota: " + series.getRatingAvarage());
        JLabel stateLabel = new JLabel("Estado: " + series.getState());
        JLabel premieredDateLabel = new JLabel("Data de Estreia: " + series.getStringPremieredDate());
        JLabel endedDateLabel = new JLabel("Data de Término: " + series.getStringEndedDate());
        JLabel broadcastingStationNameLabel = new JLabel("Emissora: " + series.getBroadcastingStationName());

        infoPanel.add(idLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(language);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(genresLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(ratingAvarageLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(stateLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(premieredDateLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(endedDateLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(broadcastingStationNameLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));

        try {
            String imageUrl = series.getImageLink();

            if (imageUrl != null && !imageUrl.isEmpty()) {
                URL url = new URL(imageUrl);
                BufferedImage image = ImageIO.read(url);

                if (image != null) {
                    int maxWidth = 150;
                    int maxHeight = 200;

                    int originalWidth = image.getWidth();
                    int originalHeight = image.getHeight();

                    double widthRatio = (double) maxWidth / originalWidth;
                    double heightRatio = (double) maxHeight / originalHeight;
                    double ratio = Math.min(widthRatio, heightRatio);

                    int scaledWidth = (int) (originalWidth * ratio);
                    int scaledHeight = (int) (originalHeight * ratio);

                    Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                    JLabel imageLabel = new JLabel(imageIcon);

                    imagePanel.add(imageLabel);
                } else {
                    JLabel noImageLabel = new JLabel("Imagem não disponível");
                    noImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    imagePanel.add(noImageLabel);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, "Erro ao carregar imagem", "ERRO", JOptionPane.ERROR_MESSAGE);
            JLabel errorLabel = new JLabel("Erro ao carregar imagem");
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imagePanel.add(errorLabel);
        }

        seriesPanel.add(infoPanel, BorderLayout.CENTER);
        seriesPanel.add(imagePanel, BorderLayout.EAST);

        return seriesPanel;
    }

    private void updateSearchHistoryArea() {
        StringBuilder history = new StringBuilder();
        history.append("Histórico de Buscas:\n");

        Map<String, Object> historyMap = searchHistory.getSeries_wanted();
        for (Map.Entry<String, Object> entry : historyMap.entrySet()) {
            if (entry.getValue() instanceof List) {
                List<?> resultList = (List<?>) entry.getValue();

                history.append("- \"").append(entry.getKey()).append("\"")
                        .append(" (").append(resultList.size()).append(" resultados)\n");
            }
        }

        searchHistoryTextArea.setText(history.toString());
    }
}
