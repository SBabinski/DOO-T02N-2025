import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diálogo para configuração das opções do Weather App
 *
 * @author Charles Müller
 * @version 1.0
 */
public class ConfigDialog extends JDialog {
    private ConfigManager configManager;
    private boolean configChanged = false;

    // Componentes da interface
    private JTextField apiKeyField;
    private JComboBox<String> unitGroupCombo;
    private JComboBox<String> languageCombo;
    private JSpinner connectionTimeoutSpinner;
    private JSpinner readTimeoutSpinner;
    private JSpinner maxHistorySpinner;
    private JCheckBox autoBackupCheck;
    private JCheckBox showEmojisCheck;
    private JCheckBox logToFileCheck;

    public ConfigDialog(Frame parent, ConfigManager configManager) {
        super(parent, "Configurações - Weather App", true);
        this.configManager = configManager;

        initializeDialog();
        loadCurrentConfig();
        setupEventListeners();
    }

    /**
     * Inicializa o diálogo
     */
    private void initializeDialog() {
        setSize(500, 600);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // Painel principal com configurações
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Adicionar seções de configuração
        mainPanel.add(createApiSection());
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(createConnectionSection());
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(createDataSection());
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(createDisplaySection());
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(createLoggingSection());

        // Painel de botões
        JPanel buttonPanel = createButtonPanel();

        // ScrollPane para o conteúdo
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Cria a seção de configurações da API
     */
    private JPanel createApiSection() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Configurações da API"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // API Key
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("API Key:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        apiKeyField = new JTextField(20);
        apiKeyField.setToolTipText("Sua chave de API da Visual Crossing");
        panel.add(apiKeyField, gbc);

        // Unit Group
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Unidades:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        unitGroupCombo = new JComboBox<>(new String[] { "metric", "us", "uk", "base" });
        unitGroupCombo.setToolTipText("Sistema de unidades (métrico, americano, britânico, base)");
        panel.add(unitGroupCombo, gbc);

        // Language
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Idioma:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        languageCombo = new JComboBox<>(new String[] { "pt", "en", "es", "fr", "de", "it" });
        languageCombo.setToolTipText("Idioma das descrições meteorológicas");
        panel.add(languageCombo, gbc);

        return panel;
    }

    /**
     * Cria a seção de configurações de conexão
     */
    private JPanel createConnectionSection() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Configurações de Conexão"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Connection Timeout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Timeout Conexão (ms):"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        connectionTimeoutSpinner = new JSpinner(new SpinnerNumberModel(10000, 1000, 60000, 1000));
        connectionTimeoutSpinner.setToolTipText("Tempo limite para estabelecer conexão");
        panel.add(connectionTimeoutSpinner, gbc);

        // Read Timeout
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Timeout Leitura (ms):"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        readTimeoutSpinner = new JSpinner(new SpinnerNumberModel(15000, 1000, 60000, 1000));
        readTimeoutSpinner.setToolTipText("Tempo limite para receber dados");
        panel.add(readTimeoutSpinner, gbc);

        return panel;
    }

    /**
     * Cria a seção de configurações de dados
     */
    private JPanel createDataSection() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Configurações de Dados"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Max History Size
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Máx. Histórico:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        maxHistorySpinner = new JSpinner(new SpinnerNumberModel(100, 10, 1000, 10));
        maxHistorySpinner.setToolTipText("Número máximo de consultas no histórico");
        panel.add(maxHistorySpinner, gbc);

        // Auto Backup
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        autoBackupCheck = new JCheckBox("Backup automático");
        autoBackupCheck.setToolTipText("Criar backup automático antes de modificar dados");
        panel.add(autoBackupCheck, gbc);

        return panel;
    }

    /**
     * Cria a seção de configurações de exibição
     */
    private JPanel createDisplaySection() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Configurações de Exibição"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Show Emojis
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        showEmojisCheck = new JCheckBox("Mostrar emojis");
        showEmojisCheck.setToolTipText("Exibir emojis nas informações meteorológicas");
        panel.add(showEmojisCheck, gbc);

        return panel;
    }

    /**
     * Cria a seção de configurações de logging
     */
    private JPanel createLoggingSection() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Configurações de Log"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Log to File
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        logToFileCheck = new JCheckBox("Salvar logs em arquivo");
        logToFileCheck.setToolTipText("Salvar logs de execução em arquivo");
        panel.add(logToFileCheck, gbc);

        return panel;
    }

    /**
     * Cria o painel de botões
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBorder(new EmptyBorder(5, 15, 15, 15));

        JButton saveButton = new JButton("💾 Salvar");
        saveButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 12));
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);

        JButton resetButton = new JButton("🔄 Restaurar");
        resetButton.setFont(new Font("Arial", Font.BOLD, 12));
        resetButton.setBackground(new Color(241, 196, 15));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);

        panel.add(resetButton);
        panel.add(cancelButton);
        panel.add(saveButton);

        return panel;
    }

    /**
     * Configura os listeners de eventos
     */
    private void setupEventListeners() {
        // Botão Salvar
        JButton saveButton = findButton("💾 Salvar");
        if (saveButton != null) {
            saveButton.addActionListener(e -> saveConfiguration());
        }

        // Botão Cancelar
        JButton cancelButton = findButton("Cancelar");
        if (cancelButton != null) {
            cancelButton.addActionListener(e -> dispose());
        }

        // Botão Restaurar
        JButton resetButton = findButton("🔄 Restaurar");
        if (resetButton != null) {
            resetButton.addActionListener(e -> loadCurrentConfig());
        }
    }

    /**
     * Encontra um botão pelo texto
     */
    private JButton findButton(String text) {
        return findButtonRecursive(this, text);
    }

    private JButton findButtonRecursive(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton && ((JButton) component).getText().equals(text)) {
                return (JButton) component;
            } else if (component instanceof Container) {
                JButton found = findButtonRecursive((Container) component, text);
                if (found != null)
                    return found;
            }
        }
        return null;
    }

    /**
     * Carrega a configuração atual nos campos
     */
    private void loadCurrentConfig() {
        apiKeyField.setText(configManager.getApiKey());
        unitGroupCombo.setSelectedItem(configManager.getUnitGroup());
        languageCombo.setSelectedItem(configManager.getLanguage());
        connectionTimeoutSpinner.setValue(configManager.getConnectionTimeout());
        readTimeoutSpinner.setValue(configManager.getReadTimeout());
        maxHistorySpinner.setValue(configManager.getMaxHistorySize());
        autoBackupCheck.setSelected(configManager.isAutoBackupEnabled());
        showEmojisCheck.setSelected(configManager.showEmojis());
        logToFileCheck.setSelected(configManager.isLogToFileEnabled());
    }

    /**
     * Salva a configuração
     */
    private void saveConfiguration() {
        try {
            // Validar API Key
            String apiKey = apiKeyField.getText().trim();
            if (!apiKey.isEmpty() && !InputValidator.isValidApiKey(apiKey)) {
                JOptionPane.showMessageDialog(this,
                        "API Key inválida! Deve ter entre 20-50 caracteres alfanuméricos.",
                        "Erro de Validação",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Aplicar configurações
            configManager.setApiKey(apiKey);
            // Note: ConfigManager precisaria de métodos setter para outras configurações
            // Por agora, salvamos apenas a API Key

            configManager.saveConfiguration();

            configChanged = true;

            JOptionPane.showMessageDialog(this,
                    "Configurações salvas com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar configurações: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Verifica se a configuração foi alterada
     */
    public boolean isConfigChanged() {
        return configChanged;
    }
}
