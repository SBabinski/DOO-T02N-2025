import java.time.LocalDateTime;

/**
 * Classe para testar as funcionalidades básicas do Weather App
 *
 * @author Charles Müller
 * @version 1.0
 */
public class WeatherAppTest {

    public static void main(String[] args) {
        System.out.println("=== TESTE DO WEATHER APP ===\n");

        // Teste 1: Validação de entrada
        testInputValidation();

        // Teste 2: Criação de WeatherData
        testWeatherData();

        // Teste 3: Parser JSON
        testJsonParser();

        // Teste 4: Persistência (simulada)
        testDataPersistence();

        System.out.println("\n=== TODOS OS TESTES CONCLUÍDOS ===");
    }

    private static void testInputValidation() {
        System.out.println("1. Testando validação de entrada...");

        // Testes de cidade
        String[] testCities = {
                "São Paulo, SP", // Válido
                "London, UK", // Válido
                "New York, NY", // Válido
                "", // Inválido - vazio
                "A", // Inválido - muito curto
                "x".repeat(101), // Inválido - muito longo
                "Test<>City", // Inválido - caracteres especiais
                "40.7128,-74.0060", // Válido - coordenadas
                "10001" // Válido - CEP
        };

        for (String city : testCities) {
            boolean isValid = InputValidator.isValidLocation(city);
            System.out.printf("  %-20s -> %s\n",
                    city.length() > 20 ? city.substring(0, 17) + "..." : city,
                    isValid ? "VÁLIDO" : "INVÁLIDO");
        }

        // Testes de API Key
        String[] testApiKeys = {
                "ABC123DEF456GHI789JKL012", // Válido
                "short", // Inválido - muito curto
                "", // Inválido - vazio
                "ABC123-DEF456", // Inválido - hífen
                "x".repeat(25) // Válido - comprimento adequado
        };

        System.out.println("\n  Teste de API Keys:");
        for (String apiKey : testApiKeys) {
            boolean isValid = InputValidator.isValidApiKey(apiKey);
            System.out.printf("  %-30s -> %s\n",
                    apiKey.length() > 30 ? apiKey.substring(0, 27) + "..." : apiKey,
                    isValid ? "VÁLIDO" : "INVÁLIDO");
        }

        System.out.println("  ✓ Validação de entrada testada\n");
    }

    private static void testWeatherData() {
        System.out.println("2. Testando criação de WeatherData...");

        // Criar dados válidos
        WeatherData validData = new WeatherData(
                "São Paulo, SP", 25.5, 30.0, 20.0, 70.0,
                "Ensolarado", 0.0, 15.0, 180.0);

        System.out.println("  Dados válidos criados:");
        System.out.println("  " + validData);
        System.out.println("  Válido: " + validData.isValid());

        // Criar dados inválidos
        WeatherData invalidData = new WeatherData(
                "", -50.0, 20.0, 30.0, 150.0, // temp max < min, humidade > 100
                null, -5.0, -10.0, 400.0 // precipitação negativa, direção > 360
        );

        System.out.println("\n  Dados inválidos criados:");
        System.out.println("  Válido: " + invalidData.isValid());

        System.out.println("  ✓ WeatherData testado\n");
    }

    private static void testJsonParser() {
        System.out.println("3. Testando parser JSON...");

        String testJson = """
                {
                    "currentConditions": {
                        "temp": 25.5,
                        "humidity": 70.0,
                        "conditions": "Sunny"
                    },
                    "days": [
                        {
                            "tempmax": 30.0,
                            "tempmin": 20.0,
                            "windspeed": 15.0
                        }
                    ]
                }
                """;

        SimpleJsonParser parser = new SimpleJsonParser(testJson);

        // Testar extração de valores
        double temp = parser.getDouble("currentConditions.temp", 0.0);
        double humidity = parser.getDouble("currentConditions.humidity", 0.0);
        String conditions = parser.getString("currentConditions.conditions", "N/A");
        double tempMax = parser.getDouble("days[0].tempmax", 0.0);

        System.out.printf("  Temperatura: %.1f°C\n", temp);
        System.out.printf("  Humidade: %.0f%%\n", humidity);
        System.out.printf("  Condições: %s\n", conditions);
        System.out.printf("  Temp. Máxima: %.1f°C\n", tempMax);

        // Testar tamanho do array
        int arraySize = parser.getArraySize("days");
        System.out.printf("  Tamanho do array 'days': %d\n", arraySize);

        System.out.println("  ✓ Parser JSON testado\n");
    }

    private static void testDataPersistence() {
        System.out.println("4. Testando persistência de dados...");

        try {
            DataPersistence persistence = new DataPersistence();

            // Carregar histórico (pode estar vazio)
            var historico = persistence.loadWeatherHistory();
            System.out.printf("  Histórico carregado: %d entradas\n", historico.size());

            // Criar dados de teste
            WeatherData testData = new WeatherData(
                    "Teste City", 20.0, 25.0, 15.0, 60.0,
                    "Teste", 0.0, 10.0, 90.0);

            System.out.println("  Dados de teste criados (não serão salvos no arquivo real)");
            System.out.println("  " + testData);

            System.out.println("  ✓ Persistência testada (simulação)\n");

        } catch (Exception e) {
            System.out.println("  ⚠ Erro ao testar persistência: " + e.getMessage());
        }
    }
}
