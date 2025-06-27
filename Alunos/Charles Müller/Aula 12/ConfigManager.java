import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Gerenciador de configurações que lê e mantém as configurações do aplicativo
 * a partir do arquivo config.properties
 *
 * @author Charles Müller
 * @version 1.0
 */
public class ConfigManager {
    private static final Logger LOGGER = Logger.getLogger(ConfigManager.class.getName());
    private static final String CONFIG_FILE = "config.properties";
    private static ConfigManager instance;

    private Properties properties;
    private boolean configLoaded;

    /**
     * Construtor privado para implementar Singleton
     */
    private ConfigManager() {
        this.properties = new Properties();
        this.configLoaded = false;
        loadConfiguration();
    }

    /**
     * Obtém a instância única do ConfigManager (Singleton)
     */
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * Carrega as configurações do arquivo
     */
    private void loadConfiguration() {
        try {
            Path configPath = Paths.get(CONFIG_FILE);

            if (!Files.exists(configPath)) {
                LOGGER.warning("Arquivo de configuração não encontrado: " + CONFIG_FILE);
                createDefaultConfig();
                return;
            }

            try (InputStream input = Files.newInputStream(configPath)) {
                properties.load(input);
                configLoaded = true;
                LOGGER.info("Configuração carregada com sucesso");
            }

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Erro ao carregar configuração", e);
            loadDefaultValues();
        }
    }

    /**
     * Cria um arquivo de configuração padrão se não existir
     */
    private void createDefaultConfig() {
        LOGGER.info("Criando arquivo de configuração padrão");
        loadDefaultValues();
        saveConfiguration();
    }

    /**
     * Carrega valores padrão
     */
    private void loadDefaultValues() {
        // API Settings
        properties.setProperty("API_KEY", "");
        properties.setProperty("BASE_URL",
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/");
        properties.setProperty("UNIT_GROUP", "metric");
        properties.setProperty("LANGUAGE", "pt");

        // Connection Settings
        properties.setProperty("CONNECTION_TIMEOUT", "10000");
        properties.setProperty("READ_TIMEOUT", "15000");
        properties.setProperty("MAX_RETRIES", "3");

        // Data Settings
        properties.setProperty("MAX_HISTORY_SIZE", "100");
        properties.setProperty("AUTO_BACKUP", "true");
        properties.setProperty("BACKUP_FREQUENCY", "daily");

        // Display Settings
        properties.setProperty("TEMPERATURE_UNIT", "celsius");
        properties.setProperty("WIND_SPEED_UNIT", "kmh");
        properties.setProperty("PRESSURE_UNIT", "mb");
        properties.setProperty("SHOW_EMOJIS", "true");

        // Logging Settings
        properties.setProperty("LOG_LEVEL", "INFO");
        properties.setProperty("LOG_TO_FILE", "false");
        properties.setProperty("LOG_FILE", "weather_app.log");

        // Validation Settings
        properties.setProperty("MIN_CITY_LENGTH", "2");
        properties.setProperty("MAX_CITY_LENGTH", "100");
        properties.setProperty("VALIDATE_COORDINATES", "true");
        properties.setProperty("ALLOW_ZIP_CODES", "true");

        // Cache Settings
        properties.setProperty("ENABLE_CACHE", "false");
        properties.setProperty("CACHE_DURATION_MINUTES", "10");
        properties.setProperty("MAX_CACHE_SIZE", "50");

        configLoaded = true;
    }

    /**
     * Salva as configurações no arquivo
     */
    public void saveConfiguration() {
        try {
            Path configPath = Paths.get(CONFIG_FILE);

            try (OutputStream output = Files.newOutputStream(configPath)) {
                properties.store(output, "Weather App Configuration");
                LOGGER.info("Configuração salva com sucesso");
            }

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Erro ao salvar configuração", e);
        }
    }

    // === MÉTODOS PARA OBTER CONFIGURAÇÕES ===

    /**
     * Obtém a API Key
     */
    public String getApiKey() {
        return getString("API_KEY", "");
    }

    /**
     * Define a API Key
     */
    public void setApiKey(String apiKey) {
        properties.setProperty("API_KEY", apiKey != null ? apiKey : "");
    }

    /**
     * Obtém a URL base da API
     */
    public String getBaseUrl() {
        return getString("BASE_URL",
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/");
    }

    /**
     * Obtém o grupo de unidades (metric, us, uk, base)
     */
    public String getUnitGroup() {
        return getString("UNIT_GROUP", "metric");
    }

    /**
     * Obtém o idioma
     */
    public String getLanguage() {
        return getString("LANGUAGE", "pt");
    }

    /**
     * Obtém o timeout de conexão em milissegundos
     */
    public int getConnectionTimeout() {
        return getInt("CONNECTION_TIMEOUT", 10000);
    }

    /**
     * Obtém o timeout de leitura em milissegundos
     */
    public int getReadTimeout() {
        return getInt("READ_TIMEOUT", 15000);
    }

    /**
     * Obtém o número máximo de tentativas
     */
    public int getMaxRetries() {
        return getInt("MAX_RETRIES", 3);
    }

    /**
     * Obtém o tamanho máximo do histórico
     */
    public int getMaxHistorySize() {
        return getInt("MAX_HISTORY_SIZE", 100);
    }

    /**
     * Verifica se o backup automático está habilitado
     */
    public boolean isAutoBackupEnabled() {
        return getBoolean("AUTO_BACKUP", true);
    }

    /**
     * Obtém a frequência de backup
     */
    public String getBackupFrequency() {
        return getString("BACKUP_FREQUENCY", "daily");
    }

    /**
     * Obtém a unidade de temperatura
     */
    public String getTemperatureUnit() {
        return getString("TEMPERATURE_UNIT", "celsius");
    }

    /**
     * Obtém a unidade de velocidade do vento
     */
    public String getWindSpeedUnit() {
        return getString("WIND_SPEED_UNIT", "kmh");
    }

    /**
     * Obtém a unidade de pressão
     */
    public String getPressureUnit() {
        return getString("PRESSURE_UNIT", "mb");
    }

    /**
     * Verifica se deve mostrar emojis
     */
    public boolean showEmojis() {
        return getBoolean("SHOW_EMOJIS", true);
    }

    /**
     * Obtém o nível de log
     */
    public String getLogLevel() {
        return getString("LOG_LEVEL", "INFO");
    }

    /**
     * Verifica se deve salvar log em arquivo
     */
    public boolean isLogToFileEnabled() {
        return getBoolean("LOG_TO_FILE", false);
    }

    /**
     * Obtém o nome do arquivo de log
     */
    public String getLogFile() {
        return getString("LOG_FILE", "weather_app.log");
    }

    /**
     * Obtém o tamanho mínimo do nome da cidade
     */
    public int getMinCityLength() {
        return getInt("MIN_CITY_LENGTH", 2);
    }

    /**
     * Obtém o tamanho máximo do nome da cidade
     */
    public int getMaxCityLength() {
        return getInt("MAX_CITY_LENGTH", 100);
    }

    /**
     * Verifica se deve validar coordenadas
     */
    public boolean isValidateCoordinatesEnabled() {
        return getBoolean("VALIDATE_COORDINATES", true);
    }

    /**
     * Verifica se permite CEPs
     */
    public boolean isAllowZipCodesEnabled() {
        return getBoolean("ALLOW_ZIP_CODES", true);
    }

    /**
     * Verifica se o cache está habilitado
     */
    public boolean isCacheEnabled() {
        return getBoolean("ENABLE_CACHE", false);
    }

    /**
     * Obtém a duração do cache em minutos
     */
    public int getCacheDurationMinutes() {
        return getInt("CACHE_DURATION_MINUTES", 10);
    }

    /**
     * Obtém o tamanho máximo do cache
     */
    public int getMaxCacheSize() {
        return getInt("MAX_CACHE_SIZE", 50);
    }

    // === MÉTODOS AUXILIARES ===

    /**
     * Obtém uma string das propriedades
     */
    private String getString(String key, String defaultValue) {
        if (!configLoaded) {
            return defaultValue;
        }
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Obtém um inteiro das propriedades
     */
    private int getInt(String key, int defaultValue) {
        try {
            String value = getString(key, String.valueOf(defaultValue));
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            LOGGER.warning("Valor inválido para " + key + ", usando padrão: " + defaultValue);
            return defaultValue;
        }
    }

    /**
     * Obtém um boolean das propriedades
     */
    private boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key, String.valueOf(defaultValue));
        return "true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value) || "1".equals(value);
    }

    /**
     * Verifica se a configuração foi carregada
     */
    public boolean isConfigLoaded() {
        return configLoaded;
    }

    /**
     * Recarrega a configuração do arquivo
     */
    public void reloadConfiguration() {
        loadConfiguration();
    }

    /**
     * Valida se as configurações essenciais estão presentes
     */
    public boolean validateConfiguration() {
        if (!configLoaded) {
            return false;
        }

        // Verificar se API Key está definida
        String apiKey = getApiKey();
        if (apiKey == null || apiKey.trim().isEmpty()) {
            LOGGER.warning("API Key não configurada");
            return false;
        }

        // Verificar URL base
        String baseUrl = getBaseUrl();
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            LOGGER.warning("URL base não configurada");
            return false;
        }

        // Verificar valores numéricos
        if (getConnectionTimeout() <= 0 || getReadTimeout() <= 0) {
            LOGGER.warning("Timeouts inválidos");
            return false;
        }

        if (getMaxHistorySize() <= 0) {
            LOGGER.warning("Tamanho máximo do histórico inválido");
            return false;
        }

        return true;
    }

    /**
     * Obtém um resumo das configurações atuais
     */
    public String getConfigurationSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== CONFIGURAÇÕES DO WEATHER APP ===\n");
        summary.append("API Key: ").append(getApiKey().isEmpty() ? "NÃO CONFIGURADA" : "CONFIGURADA").append("\n");
        summary.append("Unidades: ").append(getUnitGroup()).append("\n");
        summary.append("Idioma: ").append(getLanguage()).append("\n");
        summary.append("Timeout Conexão: ").append(getConnectionTimeout()).append("ms\n");
        summary.append("Timeout Leitura: ").append(getReadTimeout()).append("ms\n");
        summary.append("Histórico Máximo: ").append(getMaxHistorySize()).append(" entradas\n");
        summary.append("Backup Automático: ").append(isAutoBackupEnabled() ? "SIM" : "NÃO").append("\n");
        summary.append("Mostrar Emojis: ").append(showEmojis() ? "SIM" : "NÃO").append("\n");
        summary.append("Configuração Válida: ").append(validateConfiguration() ? "SIM" : "NÃO").append("\n");
        return summary.toString();
    }
}
