import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Classe responsável pela persistência dos dados meteorológicos em formato JSON
 *
 * @author Charles Müller
 * @version 1.0
 */
public class DataPersistence {
    private static final Logger LOGGER = Logger.getLogger(DataPersistence.class.getName());
    private static final String DATA_FILE = "weather_history.json";
    private static final String BACKUP_FILE = "weather_history_backup.json";

    private final ConfigManager configManager;

    public DataPersistence() {
        this.configManager = ConfigManager.getInstance();
    }

    /**
     * Salva os dados meteorológicos no arquivo JSON
     */
    public void saveWeatherData(WeatherData weatherData) throws DataPersistenceException {
        if (weatherData == null) {
            throw new DataPersistenceException("Dados meteorológicos não podem ser nulos");
        }

        try {
            // Carregar histórico existente
            List<WeatherData> history = loadWeatherHistory();

            // Adicionar novo dado
            history.add(weatherData);

            // Limitar histórico baseado na configuração
            int maxSize = configManager.getMaxHistorySize();
            if (history.size() > maxSize) {
                history = history.subList(history.size() - maxSize, history.size());
            }

            // Salvar histórico atualizado
            saveWeatherHistory(history);

            LOGGER.info("Dados meteorológicos salvos com sucesso");

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Erro ao salvar dados meteorológicos", e);
            throw new DataPersistenceException("Erro ao salvar dados: " + e.getMessage(), e);
        }
    }

    /**
     * Carrega o histórico de dados meteorológicos do arquivo JSON
     */
    public List<WeatherData> loadWeatherHistory() throws DataPersistenceException {
        try {
            Path filePath = Paths.get(DATA_FILE);

            if (!Files.exists(filePath)) {
                LOGGER.info("Arquivo de histórico não existe, criando novo");
                return new ArrayList<>();
            }

            String jsonContent = Files.readString(filePath, StandardCharsets.UTF_8);

            if (jsonContent.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return parseWeatherHistory(jsonContent);

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Erro ao carregar histórico", e);

            // Tentar carregar backup
            try {
                return loadBackup();
            } catch (Exception backupError) {
                throw new DataPersistenceException("Erro ao carregar histórico e backup: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Limpa o histórico de dados
     */
    public void clearHistory() throws DataPersistenceException {
        try {
            // Criar backup antes de limpar
            createBackup();

            // Criar arquivo vazio
            saveWeatherHistory(new ArrayList<>());

            LOGGER.info("Histórico limpo com sucesso");

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Erro ao limpar histórico", e);
            throw new DataPersistenceException("Erro ao limpar histórico: " + e.getMessage(), e);
        }
    }

    /**
     * Salva a lista completa de dados meteorológicos
     */
    private void saveWeatherHistory(List<WeatherData> history) throws IOException {
        // Criar backup se habilitado
        if (configManager.isAutoBackupEnabled()) {
            createBackup();
        }

        String jsonContent = buildJsonFromHistory(history);

        Path filePath = Paths.get(DATA_FILE);
        Files.writeString(filePath, jsonContent, StandardCharsets.UTF_8);
    }

    /**
     * Constrói JSON a partir da lista de dados meteorológicos
     */
    private String buildJsonFromHistory(List<WeatherData> history) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"version\": \"1.0\",\n");
        json.append("  \"created\": \"").append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .append("\",\n");
        json.append("  \"count\": ").append(history.size()).append(",\n");
        json.append("  \"data\": [\n");

        for (int i = 0; i < history.size(); i++) {
            WeatherData data = history.get(i);
            json.append("    {\n");
            json.append("      \"cidade\": \"").append(escapeJson(data.getCidade())).append("\",\n");
            json.append("      \"dataConsulta\": \"")
                    .append(data.getDataConsulta().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\",\n");
            json.append("      \"temperaturaAtual\": ").append(data.getTemperaturaAtual()).append(",\n");
            json.append("      \"temperaturaMaxima\": ").append(data.getTemperaturaMaxima()).append(",\n");
            json.append("      \"temperaturaMinima\": ").append(data.getTemperaturaMinima()).append(",\n");
            json.append("      \"humidade\": ").append(data.getHumidade()).append(",\n");
            json.append("      \"condicaoTempo\": \"").append(escapeJson(data.getCondicaoTempo())).append("\",\n");
            json.append("      \"precipitacao\": ").append(data.getPrecipitacao()).append(",\n");
            json.append("      \"velocidadeVento\": ").append(data.getVelocidadeVento()).append(",\n");
            json.append("      \"direcaoVento\": ").append(data.getDirecaoVento()).append("\n");
            json.append("    }");

            if (i < history.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("  ]\n");
        json.append("}\n");

        return json.toString();
    }

    /**
     * Faz parse do JSON para lista de dados meteorológicos
     */
    private List<WeatherData> parseWeatherHistory(String jsonContent) throws DataPersistenceException {
        List<WeatherData> history = new ArrayList<>();

        try {
            SimpleJsonParser parser = new SimpleJsonParser(jsonContent);

            int count = (int) parser.getDouble("count", 0);

            for (int i = 0; i < count; i++) {
                String prefix = "data[" + i + "]";

                WeatherData data = new WeatherData();
                data.setCidade(parser.getString(prefix + ".cidade", ""));

                String dateStr = parser.getString(prefix + ".dataConsulta", "");
                if (!dateStr.isEmpty()) {
                    data.setDataConsulta(LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                }

                data.setTemperaturaAtual(parser.getDouble(prefix + ".temperaturaAtual", 0.0));
                data.setTemperaturaMaxima(parser.getDouble(prefix + ".temperaturaMaxima", 0.0));
                data.setTemperaturaMinima(parser.getDouble(prefix + ".temperaturaMinima", 0.0));
                data.setHumidade(parser.getDouble(prefix + ".humidade", 0.0));
                data.setCondicaoTempo(parser.getString(prefix + ".condicaoTempo", ""));
                data.setPrecipitacao(parser.getDouble(prefix + ".precipitacao", 0.0));
                data.setVelocidadeVento(parser.getDouble(prefix + ".velocidadeVento", 0.0));
                data.setDirecaoVento(parser.getDouble(prefix + ".direcaoVento", 0.0));

                if (data.isValid()) {
                    history.add(data);
                } else {
                    LOGGER.warning("Dados inválidos ignorados durante carregamento: " + data);
                }
            }

            LOGGER.info("Histórico carregado com sucesso: " + history.size() + " entradas");
            return history;

        } catch (Exception e) {
            throw new DataPersistenceException("Erro ao fazer parse do JSON: " + e.getMessage(), e);
        }
    }

    /**
     * Cria backup do arquivo atual
     */
    private void createBackup() {
        try {
            Path originalPath = Paths.get(DATA_FILE);
            Path backupPath = Paths.get(BACKUP_FILE);

            if (Files.exists(originalPath)) {
                Files.copy(originalPath, backupPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("Backup criado com sucesso");
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Erro ao criar backup", e);
        }
    }

    /**
     * Carrega dados do backup
     */
    private List<WeatherData> loadBackup() throws DataPersistenceException {
        try {
            Path backupPath = Paths.get(BACKUP_FILE);

            if (!Files.exists(backupPath)) {
                return new ArrayList<>();
            }

            String jsonContent = Files.readString(backupPath, StandardCharsets.UTF_8);

            if (jsonContent.trim().isEmpty()) {
                return new ArrayList<>();
            }

            LOGGER.info("Carregando dados do backup");
            return parseWeatherHistory(jsonContent);

        } catch (Exception e) {
            throw new DataPersistenceException("Erro ao carregar backup: " + e.getMessage(), e);
        }
    }

    /**
     * Escapa caracteres especiais para JSON
     */
    private String escapeJson(String value) {
        if (value == null)
            return "";

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
