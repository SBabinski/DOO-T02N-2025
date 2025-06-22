package com.doo.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.doo.model.CityInformation;
import com.doo.service.JsonMapper;
import com.doo.service.WeatherApiClient;

import java.awt.*;

public class WeatherAppView extends JFrame {
    private WeatherApiClient weatherApiClient;
    private JsonMapper jsonMapper;

    // Componentes da interface
    private JTextField apiKeyField;
    private JTextField locationField;
    private JButton searchButton;
    private JButton clearButton;
    private JTextArea weatherInfoArea;
    private JScrollPane scrollPane;
    private JLabel statusLabel;

    public WeatherAppView() {
        jsonMapper = new JsonMapper();
        initializeComponents();
        setupLayout();
        setupWindow();
    }

    private void initializeComponents() {
        apiKeyField = new JTextField(20);
        apiKeyField.setToolTipText("Insira sua chave de API do Visual Crossing");

        locationField = new JTextField(20);
        locationField.setToolTipText("Ex: São Paulo, Brasil ou Rio de Janeiro, Brasil");

        searchButton = new JButton("Buscar Clima");
        searchButton.setPreferredSize(new Dimension(120, 30));

        clearButton = new JButton("Limpar");
        clearButton.setPreferredSize(new Dimension(120, 30));

        weatherInfoArea = new JTextArea(15, 30);
        weatherInfoArea.setEditable(false);
        weatherInfoArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        weatherInfoArea.setBackground(new Color(248, 248, 248));
        weatherInfoArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(weatherInfoArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        statusLabel = new JLabel("Pronto para buscar informações climáticas");
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 11));
        statusLabel.setForeground(new Color(100, 100, 100));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel leftPanel = createLeftPanel();

        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(statusLabel);
        statusPanel.setBorder(new EmptyBorder(5, 15, 10, 15));

        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new TitledBorder("Configurações e Busca"));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Chave API:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(apiKeyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(Box.createVerticalStrut(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Localização:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(locationField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.insets = new Insets(20, 5, 5, 5);
        formPanel.add(buttonPanel, gbc);

        leftPanel.add(formPanel, BorderLayout.NORTH);

        JPanel instructionsPanel = createInstructionsPanel();
        leftPanel.add(instructionsPanel, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String response = "";
            statusLabel.setText("Buscando informações climáticas...");
            String apiKey = getApiKey();
            try {
                if (apiKey == null || apiKey.trim().isEmpty()) {
                    throw new IllegalArgumentException("A chave da API não pode ser nula ou vazia");
                }

                weatherApiClient = new WeatherApiClient(apiKey);
                setStatusMessage("Chave de API configurada com sucesso!");

            } catch (Exception exception) {
                statusLabel.setText("Erro: " + exception.getMessage());
                return;
            }

            String location = getLocationInput();

            try {
                response = weatherApiClient.getWeatherData(location);
                CityInformation cityInformation = jsonMapper.mapJsonToCityInformation(response);
                setStatusMessage("Informações climáticas obtidas com sucesso!");
                setWeatherInfo("Informações do clima para " + locationField.getText()+ ":\n\n" +
                        cityInformation.getConditions() + "\n" +
                        "Temperatura: " + cityInformation.getTemp() + "°F\n" +
                        "Temperatura Mínima: " + cityInformation.getTempmin() + "°F\n" +
                        "Temperatura Máxima: " + cityInformation.getTempmax() + "°F\n" +
                        "Umidade: " + cityInformation.getHumidity() + "%\n" +
                        "Precipitação: " + cityInformation.getPrecip() + "mm\n" +
                        "Velocidade do Vento: " + cityInformation.getWindspeed() + " km/h\n" +
                        "Direção do Vento: " + cityInformation.getWinddir() + "°\n");
            } catch (Exception ex) {
                statusLabel.setText("Erro ao buscar clima: " + ex.getMessage());
                return;
            }
        });

        clearButton.addActionListener(e -> {
            apiKeyField.setText("");
            locationField.setText("");
            weatherInfoArea.setText("Aguardando consulta climática...\n\n" +
                    "As informações do tempo aparecerão aqui após a busca.");
            statusLabel.setText("Campos limpos. Pronto para nova busca.");
        });
        return leftPanel;
    }

    private JPanel createInstructionsPanel() {
        JPanel instructionsPanel = new JPanel(new BorderLayout());
        instructionsPanel.setBorder(new TitledBorder("Instruções"));

        String instructions = """
                Como usar:

                1. Insira sua chave de API do Visual Crossing

                2. Digite a localização desejada:
                   • Nome da cidade: "São Paulo"
                   • Cidade e país: "Rio de Janeiro, Brasil"

                3. Clique em "Buscar Clima"

                4. As informações aparecerão à direita

                Dica: Mantenha sua chave de API segura!
                """;

        JTextArea instructionsArea = new JTextArea(instructions);
        instructionsArea.setEditable(false);
        instructionsArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        instructionsArea.setBackground(new Color(250, 250, 250));
        instructionsArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        instructionsPanel.add(instructionsArea, BorderLayout.CENTER);

        return instructionsPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new TitledBorder("Informações Climáticas"));

        weatherInfoArea.setText("Aguardando consulta climática...\n\n" +
                "As informações do tempo aparecerão aqui após a busca.");

        rightPanel.add(scrollPane, BorderLayout.CENTER);

        return rightPanel;
    }

    private void setupWindow() {
        setTitle("Aplicação Climática - Visual Crossing API");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);

    }

    public String getApiKey() {
        return apiKeyField.getText().trim();
    }

    public String getLocationInput() {
        return locationField.getText().replace("\s", "+");
    }

    public void setWeatherInfo(String weatherInfo) {
        weatherInfoArea.setText(weatherInfo);
        weatherInfoArea.setCaretPosition(0);
    }

    public void appendWeatherInfo(String info) {
        weatherInfoArea.append(info);
    }

    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

}
