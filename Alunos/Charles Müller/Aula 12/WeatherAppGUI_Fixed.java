import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Interface gráfica principal do Weather App usando Swing
 * Versão corrigida e funcional
 *
 * @author Charles Müller
 * @version 1.1
 */
public class WeatherAppGUI_Fixed extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(WeatherAppGUI_Fixed.class.getName());

    // Componentes principais
    private ConfigManager configManager;
    private WeatherService weatherService;
    private DataPersistence dataPersistence;

    // Componentes da interface
    private JTextField cityField;
    private JButton searchButton;
    private JButton configButton;
    private JButton historyButton;
    private JButton clearHistoryButton;
    private JTextArea resultArea;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private JPanel weatherPanel;

    // Painéis de informações meteorológicas
    private JLabel cityLabel;
    private JLabel dateLabel;
    private JLabel tempCurrentLabel;
    private JLabel tempMaxLabel;
    private JLabel tempMinLabel;
    private JLabel humidityLabel;
    private JLabel conditionLabel;
    private JLabel precipitationLabel;
    private JLabel windSpeedLabel;
    private JLabel windDirectionLabel;

    public WeatherAppGUI_Fixed() {
        initializeServices();
        initializeGUI();
        setupEventListeners();

        LOGGER.info("Interface gráfica inicializada com sucesso");
    }

    /**
     * Inicializa os serviços necessários
     */
    private void initializeServices() {
        try {
            configManager = ConfigManager.getInstance();
            dataPersistence = new DataPersistence();

            String apiKey = configManager.getApiKey();
            if (apiKey.isEmpty() || !InputValidator.isValidApiKey(apiKey)) {
                showApiKeyDialog();
            } else {
                weatherService = new WeatherService(apiKey);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao inicializar serviços", e);
            showErrorDialog("Erro de Inicialização", "Erro ao inicializar serviços: " + e.getMessage());
        }
    }

    /**
     * Inicializa a interface gráfica
     */
    private void initializeGUI() {
        setTitle("Weather App - Consulta Meteorológica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Layout principal
        setLayout(new BorderLayout(10, 10));

        // Criar painéis
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        // Aplicar tema
        applyTheme();
    }

    /**
     * Cria o painel superior com campo de busca
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(new EmptyBorder(15, 15, 10, 15));

        // Título
        JLabel titleLabel = new JLabel("🌤️ Weather App", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(41, 128, 185));

        // Painel de busca
        JPanel searchPanel = new JPanel(new BorderLayout(10, 5));
        searchPanel.setBorder(new TitledBorder("Consultar Clima"));

        JLabel searchLabel = new JLabel("Cidade:");
        cityField = new JTextField();
        cityField.setFont(new Font("Arial", Font.PLAIN, 14));
        cityField.setToolTipText("Digite o nome da cidade (ex: São Paulo, SP ou London, UK)");

        searchButton = new JButton("🔍 Buscar");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        JPanel fieldPanel = new JPanel(new BorderLayout(5, 0));
        fieldPanel.add(searchLabel, BorderLayout.WEST);
        fieldPanel.add(cityField, BorderLayout.CENTER);
        fieldPanel.add(searchButton, BorderLayout.EAST);

        searchPanel.add(fieldPanel, BorderLayout.CENTER);

        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        return topPanel;
    }

    /**
     * Cria o painel central com informações meteorológicas
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Painel de informações meteorológicas
        weatherPanel = createWeatherInfoPanel();

        // Área de resultados detalhados
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        resultArea.setBackground(new Color(248, 249, 250));
        resultArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(new TitledBorder("Detalhes Completos"));
        scrollPane.setPreferredSize(new Dimension(400, 200));

        // Painel principal com split
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, weatherPanel, scrollPane);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.6);

        centerPanel.add(splitPane, BorderLayout.CENTER);

        return centerPanel;
    }

    /**
     * Cria painel com informações meteorológicas visuais
     */
    private JPanel createWeatherInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Informações Meteorológicas"));

        // Painel principal com informações
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Cidade e data
        cityLabel = createInfoLabel("Cidade não selecionada", 18, Font.BOLD);
        dateLabel = createInfoLabel("", 12, Font.PLAIN);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        infoPanel.add(cityLabel, gbc);
        gbc.gridy = 1;
        infoPanel.add(dateLabel, gbc);

        // Temperatura atual (destaque)
        tempCurrentLabel = createInfoLabel("-- °C", 36, Font.BOLD);
        tempCurrentLabel.setForeground(new Color(231, 76, 60));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        infoPanel.add(tempCurrentLabel, gbc);

        // Temperaturas máxima e mínima
        tempMaxLabel = createInfoLabel("Máx: -- °C", 14, Font.PLAIN);
        tempMinLabel = createInfoLabel("Mín: -- °C", 14, Font.PLAIN);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 3;
        infoPanel.add(tempMaxLabel, gbc);
        gbc.gridx = 1;
        infoPanel.add(tempMinLabel, gbc);

        // Condição e umidade
        conditionLabel = createInfoLabel("Condição: --", 14, Font.PLAIN);
        humidityLabel = createInfoLabel("Umidade: --%", 14, Font.PLAIN);

        gbc.gridx = 0; gbc.gridy = 4;
        infoPanel.add(conditionLabel, gbc);
        gbc.gridx = 1;
        infoPanel.add(humidityLabel, gbc);

        // Precipitação e vento
        precipitationLabel = createInfoLabel("Precipitação: -- mm", 14, Font.PLAIN);
        windSpeedLabel = createInfoLabel("Vento: -- km/h", 14, Font.PLAIN);
        windDirectionLabel = createInfoLabel("Direção: --", 14, Font.PLAIN);

        gbc.gridx = 0; gbc.gridy = 5;
        infoPanel.add(precipitationLabel, gbc);
        gbc.gridx = 1;
        infoPanel.add(windSpeedLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        infoPanel.add(windDirectionLabel, gbc);

        panel.add(infoPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Utilitário para criar labels com formatação
     */
    private JLabel createInfoLabel(String text, int fontSize, int fontStyle) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", fontStyle, fontSize));
        return label;
    }

    /**
     * Cria o painel inferior com botões e status
     */
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(new EmptyBorder(10, 15, 15, 15));

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        configButton = new JButton("⚙️ Configurações");
        historyButton = new JButton("📝 Histórico");
        clearHistoryButton = new JButton("🗑️ Limpar Histórico");

        // Estilização dos botões
        JButton[] buttons = {configButton, historyButton, clearHistoryButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.PLAIN, 12));
            button.setFocusPainted(false);
        }

        configButton.setBackground(new Color(155, 89, 182));
        configButton.setForeground(Color.WHITE);

        historyButton.setBackground(new Color(46, 204, 113));
        historyButton.setForeground(Color.WHITE);

        clearHistoryButton.setBackground(new Color(231, 76, 60));
        clearHistoryButton.setForeground(Color.WHITE);

        buttonPanel.add(configButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(clearHistoryButton);

        // Barra de progresso e status
        JPanel statusPanel = new JPanel(new BorderLayout(10, 5));

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);

        statusLabel = new JLabel("Pronto para consultas");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statusLabel.setForeground(Color.GRAY);

        statusPanel.add(progressBar, BorderLayout.CENTER);
        statusPanel.add(statusLabel, BorderLayout.SOUTH);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);

        return bottomPanel;
    }

    /**
     * Configura os event listeners
     */
    private void setupEventListeners() {
        // Busca ao pressionar Enter no campo
        cityField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        // Botão de busca
        searchButton.addActionListener(e -> performSearch());

        // Botão de configurações
        configButton.addActionListener(e -> showConfigDialog());

        // Botão de histórico
        historyButton.addActionListener(e -> showHistory());

        // Botão de limpar histórico
        clearHistoryButton.addActionListener(e -> clearHistory());
    }

    /**
     * Executa a busca meteorológica
     */
    private void performSearch() {
        String city = cityField.getText().trim();

        if (city.isEmpty()) {
            showErrorDialog("Entrada Inválida", "Por favor, digite o nome de uma cidade.");
            return;
        }

        if (!InputValidator.isValidCityName(city)) {
            showErrorDialog("Cidade Inválida", "Nome da cidade deve ter pelo menos 2 caracteres e conter apenas letras, espaços e vírgulas.");
            return;
        }

        // Executar busca em thread separada
        SwingUtilities.invokeLater(() -> {
            setUIBusy(true);

            SwingWorker<WeatherData, Void> worker = new SwingWorker<WeatherData, Void>() {
                @Override
                protected WeatherData doInBackground() throws Exception {
                    if (weatherService == null) {
                        throw new WeatherServiceException("Serviço meteorológico não inicializado. Configure a API Key.");
                    }
                    return weatherService.getCurrentWeather(city);
                }

                @Override
                protected void done() {
                    try {
                        WeatherData weather = get();
                        displayWeatherData(weather);

                        // Salvar no histórico
                        dataPersistence.saveWeatherData(weather);

                        statusLabel.setText("Consulta realizada com sucesso");

                    } catch (Exception e) {
                        LOGGER.log(Level.WARNING, "Erro na consulta meteorológica", e);
                        showErrorDialog("Erro na Consulta", "Erro ao consultar informações: " + e.getMessage());
                        statusLabel.setText("Erro na consulta");
                    } finally {
                        setUIBusy(false);
                    }
                }
            };

            worker.execute();
        });
    }

    /**
     * Exibe os dados meteorológicos na interface
     */
    private void displayWeatherData(WeatherData weather) {
        // Atualizar labels visuais
        cityLabel.setText(weather.getCidade());
        dateLabel.setText("Atualizado em: " + weather.getDataConsulta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        tempCurrentLabel.setText(String.format("%.1f°C", weather.getTemperaturaAtual()));
        tempMaxLabel.setText(String.format("Máx: %.1f°C", weather.getTemperaturaMaxima()));
        tempMinLabel.setText(String.format("Mín: %.1f°C", weather.getTemperaturaMinima()));
        humidityLabel.setText(String.format("Umidade: %.1f%%", weather.getHumidade()));
        conditionLabel.setText("Condição: " + weather.getCondicaoTempo());
        precipitationLabel.setText(String.format("Precipitação: %.1f mm", weather.getPrecipitacao()));
        windSpeedLabel.setText(String.format("Vento: %.1f km/h", weather.getVelocidadeVento()));
        windDirectionLabel.setText(String.format("Direção: %.1f°", weather.getDirecaoVento()));

        // Atualizar área de detalhes
        StringBuilder details = new StringBuilder();
        details.append("=== INFORMAÇÕES METEOROLÓGICAS ===\n\n");
        details.append("Cidade: ").append(weather.getCidade()).append("\n");
        details.append("Data/Hora: ").append(weather.getDataConsulta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n\n");
        details.append("TEMPERATURA:\n");
        details.append("  Atual: ").append(String.format("%.1f°C", weather.getTemperaturaAtual())).append("\n");
        details.append("  Máxima: ").append(String.format("%.1f°C", weather.getTemperaturaMaxima())).append("\n");
        details.append("  Mínima: ").append(String.format("%.1f°C", weather.getTemperaturaMinima())).append("\n\n");
        details.append("CONDIÇÕES:\n");
        details.append("  Condição: ").append(weather.getCondicaoTempo()).append("\n");
        details.append("  Umidade: ").append(String.format("%.1f%%", weather.getHumidade())).append("\n");
        details.append("  Precipitação: ").append(String.format("%.1f mm", weather.getPrecipitacao())).append("\n\n");
        details.append("VENTO:\n");
        details.append("  Velocidade: ").append(String.format("%.1f km/h", weather.getVelocidadeVento())).append("\n");
        details.append("  Direção: ").append(String.format("%.1f°", weather.getDirecaoVento())).append("\n");

        resultArea.setText(details.toString());
        resultArea.setCaretPosition(0);
    }

    /**
     * Define o estado ocupado da interface
     */
    private void setUIBusy(boolean busy) {
        searchButton.setEnabled(!busy);
        cityField.setEnabled(!busy);
        configButton.setEnabled(!busy);
        historyButton.setEnabled(!busy);
        clearHistoryButton.setEnabled(!busy);

        progressBar.setVisible(busy);
        progressBar.setIndeterminate(busy);

        if (busy) {
            statusLabel.setText("Consultando informações meteorológicas...");
        }
    }

    /**
     * Mostra diálogo de configuração da API Key
     */
    private void showApiKeyDialog() {
        String apiKey = JOptionPane.showInputDialog(
            this,
            "Configure sua API Key da Visual Crossing:\n\n" +
            "1. Acesse: https://www.visualcrossing.com/weather-api\n" +
            "2. Crie uma conta gratuita\n" +
            "3. Copie sua API Key\n\n" +
            "API Key:",
            "Configuração Necessária",
            JOptionPane.QUESTION_MESSAGE
        );

        if (apiKey != null && !apiKey.trim().isEmpty()) {
            try {
                configManager.setApiKey(apiKey.trim());
                weatherService = new WeatherService(apiKey.trim());
                JOptionPane.showMessageDialog(this, "API Key configurada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                showErrorDialog("Erro de Configuração", "Erro ao salvar API Key: " + e.getMessage());
            }
        } else {
            showErrorDialog("Configuração Obrigatória", "A API Key é necessária para o funcionamento da aplicação.");
        }
    }

    /**
     * Mostra diálogo de configurações completo
     */
    private void showConfigDialog() {
        try {
            ConfigDialog dialog = new ConfigDialog(this, configManager);
            dialog.setVisible(true);

            // Recriar weatherService se a API Key foi alterada
            String newApiKey = configManager.getApiKey();
            if (weatherService == null || !newApiKey.isEmpty()) {
                weatherService = new WeatherService(newApiKey);
            }

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Erro ao abrir configurações", e);
            showErrorDialog("Erro", "Erro ao abrir configurações: " + e.getMessage());
        }
    }

    /**
     * Mostra o histórico de consultas
     */
    private void showHistory() {
        try {
            List<WeatherData> history = dataPersistence.loadWeatherHistory();

            if (history.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma consulta realizada ainda.", "Histórico Vazio", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder historyText = new StringBuilder();
            historyText.append("=== HISTÓRICO DE CONSULTAS ===\n\n");

            for (int i = 0; i < history.size(); i++) {
                WeatherData weather = history.get(i);
                historyText.append(String.format("%d. %s - %.1f°C (%s)\n",
                    i + 1,
                    weather.getCidade(),
                    weather.getTemperaturaAtual(),
                    weather.getDataConsulta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                ));
            }

            JTextArea textArea = new JTextArea(historyText.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Consolas", Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 400));

            JOptionPane.showMessageDialog(this, scrollPane, "Histórico de Consultas", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Erro ao carregar histórico", e);
            showErrorDialog("Erro", "Erro ao carregar histórico: " + e.getMessage());
        }
    }

    /**
     * Limpa o histórico de consultas
     */
    private void clearHistory() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja limpar todo o histórico de consultas?",
            "Confirmar Limpeza",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            try {
                dataPersistence.clearHistory();
                JOptionPane.showMessageDialog(this, "Histórico limpo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                statusLabel.setText("Histórico limpo");
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Erro ao limpar histórico", e);
                showErrorDialog("Erro", "Erro ao limpar histórico: " + e.getMessage());
            }
        }
    }

    /**
     * Mostra diálogo de erro
     */
    private void showErrorDialog(String title, String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
        });
    }

    /**
     * Aplica tema visual
     */
    private void applyTheme() {
        try {
            // Usar Look and Feel do sistema (removido por incompatibilidade)
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            // SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            // Usar tema padrão se falhar
        }

        // Cores personalizadas
        getContentPane().setBackground(Color.WHITE);
    }

    /**
     * Método principal para executar a aplicação
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Configurar Look and Feel (removido por incompatibilidade)
                // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());

                // Criar e mostrar a interface
                WeatherAppGUI_Fixed app = new WeatherAppGUI_Fixed();
                app.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erro ao inicializar interface gráfica: " + e.getMessage());
            }
        });
    }
}
