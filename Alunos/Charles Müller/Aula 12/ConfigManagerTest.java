/**
 * Teste específico para o ConfigManager
 *
 * @author Charles Müller
 * @version 1.0
 */
public class ConfigManagerTest {

    public static void main(String[] args) {
        System.out.println("=== TESTE DO CONFIG MANAGER ===\n");

        // Teste do Singleton
        testSingleton();

        // Teste de carregamento de configuração
        testConfigurationLoading();

        // Teste de validação
        testValidation();

        // Teste de modificação de configuração
        testConfigurationModification();

        System.out.println("\n=== TESTE DO CONFIG MANAGER CONCLUÍDO ===");
    }

    private static void testSingleton() {
        System.out.println("1. Testando padrão Singleton...");

        ConfigManager config1 = ConfigManager.getInstance();
        ConfigManager config2 = ConfigManager.getInstance();

        boolean isSameInstance = config1 == config2;
        System.out.println("  Mesma instância: " + (isSameInstance ? "SIM" : "NÃO"));
        System.out.println("  ✓ Singleton testado\n");
    }

    private static void testConfigurationLoading() {
        System.out.println("2. Testando carregamento de configuração...");

        ConfigManager config = ConfigManager.getInstance();

        System.out.println("  Configuração carregada: " + (config.isConfigLoaded() ? "SIM" : "NÃO"));
        System.out.println("  API Key: " + (config.getApiKey().isEmpty() ? "VAZIA" : "CONFIGURADA"));
        System.out.println("  URL Base: " + config.getBaseUrl());
        System.out.println("  Grupo de Unidades: " + config.getUnitGroup());
        System.out.println("  Idioma: " + config.getLanguage());
        System.out.println("  Timeout Conexão: " + config.getConnectionTimeout() + "ms");
        System.out.println("  Timeout Leitura: " + config.getReadTimeout() + "ms");
        System.out.println("  Max Histórico: " + config.getMaxHistorySize());
        System.out.println("  ✓ Carregamento testado\n");
    }

    private static void testValidation() {
        System.out.println("3. Testando validação de configuração...");

        ConfigManager config = ConfigManager.getInstance();

        boolean isValid = config.validateConfiguration();
        System.out.println("  Configuração válida: " + (isValid ? "SIM" : "NÃO"));

        if (!isValid) {
            System.out.println("  Possíveis problemas:");
            if (config.getApiKey().isEmpty()) {
                System.out.println("    - API Key não configurada");
            }
            if (config.getConnectionTimeout() <= 0) {
                System.out.println("    - Timeout de conexão inválido");
            }
            if (config.getMaxHistorySize() <= 0) {
                System.out.println("    - Tamanho máximo do histórico inválido");
            }
        }

        System.out.println("  ✓ Validação testada\n");
    }

    private static void testConfigurationModification() {
        System.out.println("4. Testando modificação de configuração...");

        ConfigManager config = ConfigManager.getInstance();

        // Salvar API Key original
        String originalApiKey = config.getApiKey();

        // Testar mudança de API Key
        String testApiKey = "TEST123456789TEST123456789";
        config.setApiKey(testApiKey);

        String newApiKey = config.getApiKey();
        boolean apiKeyChanged = testApiKey.equals(newApiKey);

        System.out.println("  Mudança de API Key: " + (apiKeyChanged ? "SUCESSO" : "FALHOU"));

        // Restaurar API Key original
        config.setApiKey(originalApiKey);

        System.out.println("  ✓ Modificação testada\n");
    }
}
