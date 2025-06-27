import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Aplicativo principal para consulta de informações meteorológicas
 * usando a API da Visual Crossing Weather
 *
 * @author Charles Müller
 * @version 1.0
 */
public class WeatherApp {
    private static final Logger LOGGER = Logger.getLogger(WeatherApp.class.getName());
    private WeatherService weatherService;
    private DataPersistence dataPersistence;
    private Scanner scanner;
    private ConfigManager configManager;

    public WeatherApp() {
        this.scanner = new Scanner(System.in);
        this.configManager = ConfigManager.getInstance();
        this.dataPersistence = new DataPersistence();

        // Verificar se API key está configurada
        String apiKey = configManager.getApiKey();
        if (apiKey.isEmpty() || !InputValidator.isValidApiKey(apiKey)) {
            System.out.println("API Key não encontrada ou inválida no arquivo de configuração.");
            apiKey = getApiKeyFromUser();
            configManager.setApiKey(apiKey);
            configManager.saveConfiguration();
        }

        this.weatherService = new WeatherService(apiKey);

        LOGGER.info("Aplicativo Weather iniciado com sucesso");

        // Mostrar resumo da configuração se solicitado
        if (System.getProperty("show.config") != null) {
            System.out.println(configManager.getConfigurationSummary());
        }
    }

    /**
     * Método principal que inicia a aplicação
     */
    public static void main(String[] args) {
        try {
            WeatherApp app = new WeatherApp();
            app.run();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro crítico na aplicação", e);
            System.err.println("Erro crítico: " + e.getMessage());
        }
    }

    /**
     * Loop principal da aplicação
     */
    public void run() {
        System.out.println("=== Aplicativo de Consulta Meteorológica ===");
        System.out.println("Bem-vindo ao sistema de consulta do tempo!");

        while (true) {
            try {
                showMenu();
                int choice = getMenuChoice();

                switch (choice) {
                    case 1:
                        consultarClima();
                        break;
                    case 2:
                        visualizarHistorico();
                        break;
                    case 3:
                        limparHistorico();
                        break;
                    case 4:
                        mostrarConfiguracoes();
                        break;
                    case 5:
                        alterarApiKey();
                        break;
                    case 6:
                        System.out.println("Obrigado por usar o Weather App!");
                        return;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Erro durante operação do menu", e);
                System.err.println("Erro: " + e.getMessage());
                System.out.println("Tente novamente...");
            }
        }
    }

    /**
     * Exibe o menu principal
     */
    private void showMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Consultar clima de uma cidade");
        System.out.println("2. Visualizar histórico de consultas");
        System.out.println("3. Limpar histórico");
        System.out.println("4. Ver configurações");
        System.out.println("5. Alterar API Key");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Lê a escolha do menu do usuário
     */
    private int getMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Opção inválida
        }
    }

    /**
     * Solicita e obtém a API key do usuário
     */
    private String getApiKeyFromUser() {
        String apiKey;

        while (true) {
            System.out.print("Digite sua API Key da Visual Crossing: ");
            apiKey = scanner.nextLine().trim();

            if (InputValidator.isValidApiKey(apiKey)) {
                return apiKey;
            } else {
                System.out.println("API Key inválida! A chave deve ter entre 20-50 caracteres alfanuméricos.");
                System.out.print("Deseja tentar novamente? (S/N): ");
                String retry = scanner.nextLine().trim().toLowerCase();
                if (!retry.equals("s") && !retry.equals("sim")) {
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Executa a consulta do clima para uma cidade
     */
    private void consultarClima() {
        String cidade;

        while (true) {
            System.out.print("Digite o nome da cidade (ex: São Paulo, SP ou London, UK): ");
            cidade = scanner.nextLine().trim();

            if (cidade.isEmpty()) {
                System.out.println("Nome da cidade não pode estar vazio!");
                continue;
            }

            // Sanitizar entrada
            cidade = InputValidator.sanitizeInput(cidade);

            if (!InputValidator.isValidLocation(cidade)) {
                System.out.println("Formato de cidade inválido!");
                System.out.println(InputValidator.getCityFormatSuggestion());
                System.out.print("Deseja tentar novamente? (S/N): ");
                String retry = scanner.nextLine().trim().toLowerCase();
                if (!retry.equals("s") && !retry.equals("sim")) {
                    return;
                }
                continue;
            }

            break; // Entrada válida
        }

        try {
            System.out.println("Consultando dados meteorológicos para: " + cidade);
            System.out.println("Aguarde...");

            WeatherData weatherData = weatherService.getCurrentWeather(cidade);

            if (weatherData != null) {
                displayWeatherInfo(weatherData);
                dataPersistence.saveWeatherData(weatherData);
                System.out.println("\nDados salvos no histórico com sucesso!");
            } else {
                System.out.println("Não foi possível obter dados para esta cidade.");
            }

        } catch (WeatherServiceException e) {
            System.err.println("Erro no serviço meteorológico: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Erro inesperado durante consulta", e);
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }

    /**
     * Exibe as informações meteorológicas formatadas
     */
    private void displayWeatherInfo(WeatherData data) {
        System.out.println("\n=== INFORMAÇÕES METEOROLÓGICAS ===");
        System.out.println("Cidade: " + data.getCidade());
        System.out.println("Data/Hora da consulta: " + data.getDataConsulta());
        System.out.println();

        System.out.printf("   Temperatura atual: %.1f°C\n", data.getTemperaturaAtual());
        System.out.printf(">> Máxima do dia: %.1f°C\n", data.getTemperaturaMaxima());
        System.out.printf("<< Mínima do dia: %.1f°C\n", data.getTemperaturaMinima());
        System.out.printf("   Humidade: %.0f%%\n", data.getHumidade());
        System.out.println("   Condição: " + data.getCondicaoTempo());

        if (data.getPrecipitacao() > 0) {
            System.out.printf("~~ Precipitação: %.1f mm\n", data.getPrecipitacao());
        }

        System.out.printf("-> Vento: %.1f km/h\n", data.getVelocidadeVento());
        System.out.printf("   Direção do vento: %.0f°\n", data.getDirecaoVento());
    }

    /**
     * Visualiza o histórico de consultas salvas
     */
    private void visualizarHistorico() {
        try {
            var historico = dataPersistence.loadWeatherHistory();

            if (historico.isEmpty()) {
                System.out.println("Nenhuma consulta encontrada no histórico.");
                return;
            }

            System.out.println("\n=== HISTÓRICO DE CONSULTAS ===");
            System.out.printf("Total de consultas: %d\n\n", historico.size());

            for (int i = 0; i < historico.size(); i++) {
                WeatherData data = historico.get(i);
                System.out.printf("%d. %s - %s (%.1f°C)\n",
                        i + 1,
                        data.getCidade(),
                        data.getDataConsulta().toString(),
                        data.getTemperaturaAtual());
            }

            System.out.print("\nDeseja ver detalhes de alguma consulta? (número ou 0 para voltar): ");
            try {
                int escolha = Integer.parseInt(scanner.nextLine().trim());
                if (escolha > 0 && escolha <= historico.size()) {
                    displayWeatherInfo(historico.get(escolha - 1));
                }
            } catch (NumberFormatException e) {
                // Ignora entrada inválida
            }

        } catch (DataPersistenceException e) {
            System.err.println("Erro ao carregar histórico: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Erro inesperado ao visualizar histórico", e);
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }

    /**
     * Limpa o histórico de consultas
     */
    private void limparHistorico() {
        System.out.print("Tem certeza que deseja limpar todo o histórico? (s/N): ");
        String confirmacao = scanner.nextLine().trim().toLowerCase();

        if (confirmacao.equals("s") || confirmacao.equals("sim")) {
            try {
                dataPersistence.clearHistory();
                System.out.println("Histórico limpo com sucesso!");
            } catch (DataPersistenceException e) {
                System.err.println("Erro ao limpar histórico: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    /**
     * Mostra as configurações atuais
     */
    private void mostrarConfiguracoes() {
        System.out.println("\n" + configManager.getConfigurationSummary());

        System.out.print("Deseja recarregar configurações do arquivo? (S/N): ");
        String reload = scanner.nextLine().trim().toLowerCase();
        if (reload.equals("s") || reload.equals("sim")) {
            configManager.reloadConfiguration();
            System.out.println("Configurações recarregadas!");
        }
    }

    /**
     * Permite alterar a API Key
     */
    private void alterarApiKey() {
        System.out.println("\nAPI Key atual: " +
                (configManager.getApiKey().isEmpty() ? "NÃO CONFIGURADA" : "CONFIGURADA"));

        System.out.print("Deseja alterar a API Key? (S/N): ");
        String alterar = scanner.nextLine().trim().toLowerCase();

        if (alterar.equals("s") || alterar.equals("sim")) {
            String novaApiKey = getApiKeyFromUser();
            configManager.setApiKey(novaApiKey);
            configManager.saveConfiguration();

            // Recriar o serviço com a nova API key
            this.weatherService = new WeatherService(novaApiKey);

            System.out.println("API Key atualizada com sucesso!");
        }
    }
}
