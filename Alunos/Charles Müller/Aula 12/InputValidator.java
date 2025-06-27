import java.util.regex.Pattern;

/**
 * Classe utilitária para validação de entradas do usuário
 *
 * @author Charles Müller
 * @version 1.0
 */
public class InputValidator {

    // Padrões de validação
    private static final Pattern CITY_NAME_PATTERN = Pattern.compile("^[a-zA-ZÀ-ÿ\\s\\-\\.,']+$");
    private static final Pattern COORDINATES_PATTERN = Pattern.compile("^-?\\d{1,3}\\.\\d+,-?\\d{1,3}\\.\\d+$");
    private static final Pattern ZIP_CODE_PATTERN = Pattern.compile("^\\d{5}(-\\d{4})?$");

    /**
     * Valida se o nome da cidade é válido
     */
    public static boolean isValidCityName(String cityName) {
        if (cityName == null || cityName.trim().isEmpty()) {
            return false;
        }

        String trimmed = cityName.trim();

        // Verificar comprimento
        if (trimmed.length() < 2 || trimmed.length() > 100) {
            return false;
        }

        // Verificar se contém apenas caracteres válidos
        return CITY_NAME_PATTERN.matcher(trimmed).matches();
    }

    /**
     * Valida se é um formato de coordenadas válido (lat,lng)
     */
    public static boolean isValidCoordinates(String coordinates) {
        if (coordinates == null || coordinates.trim().isEmpty()) {
            return false;
        }

        return COORDINATES_PATTERN.matcher(coordinates.trim()).matches();
    }

    /**
     * Valida se é um CEP americano válido
     */
    public static boolean isValidZipCode(String zipCode) {
        if (zipCode == null || zipCode.trim().isEmpty()) {
            return false;
        }

        return ZIP_CODE_PATTERN.matcher(zipCode.trim()).matches();
    }

    /**
     * Valida qualquer tipo de localização aceita pela API
     */
    public static boolean isValidLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }

        String trimmed = location.trim();

        return isValidCityName(trimmed) ||
                isValidCoordinates(trimmed) ||
                isValidZipCode(trimmed);
    }

    /**
     * Sanitiza a entrada do usuário removendo caracteres perigosos
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }

        return input.trim()
                .replaceAll("[<>\"'&]", "") // Remove caracteres perigosos
                .replaceAll("\\s+", " "); // Normaliza espaços
    }

    /**
     * Valida se a API key tem um formato básico válido
     */
    public static boolean isValidApiKey(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            return false;
        }

        String trimmed = apiKey.trim();

        // API keys da Visual Crossing geralmente têm 25-40 caracteres alfanuméricos
        return trimmed.length() >= 20 &&
                trimmed.length() <= 50 &&
                trimmed.matches("^[A-Za-z0-9]+$");
    }

    /**
     * Valida um número inteiro dentro de um range
     */
    public static boolean isValidInteger(String input, int min, int max) {
        try {
            int value = Integer.parseInt(input.trim());
            return value >= min && value <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Fornece sugestões de formato para entrada de cidade
     */
    public static String getCityFormatSuggestion() {
        return "Exemplos de formatos aceitos:\n" +
                "- São Paulo, SP\n" +
                "- Rio de Janeiro, RJ\n" +
                "- London, UK\n" +
                "- New York, NY\n" +
                "- 40.7128,-74.0060 (coordenadas)\n" +
                "- 10001 (CEP americano)";
    }
}
