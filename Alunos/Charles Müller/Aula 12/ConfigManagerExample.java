/**
 * Exemplo de como estender o ConfigManager para adicionar novas configurações
 *
 * Este arquivo demonstra como você pode facilmente adicionar novas
 * configurações
 * ao sistema sem modificar o código principal.
 *
 * @author Charles Müller
 * @version 1.0
 */
public class ConfigManagerExample {

    public static void main(String[] args) {
        System.out.println("=== EXEMPLO DE USO DO CONFIG MANAGER ===\n");

        // Exemplo 1: Uso básico
        exemploUsoBasico();

        // Exemplo 2: Modificação de configurações
        exemploModificacao();

        // Exemplo 3: Validação e tratamento de erros
        exemploValidacao();

        // Exemplo 4: Integração com outras classes
        exemploIntegracao();
    }

    private static void exemploUsoBasico() {
        System.out.println("1. Uso básico do ConfigManager:");

        // Obter instância única (Singleton)
        ConfigManager config = ConfigManager.getInstance();

        // Ler configurações
        System.out.println("  API Key configurada: " + !config.getApiKey().isEmpty());
        System.out.println("  Unidade de temperatura: " + config.getTemperatureUnit());
        System.out.println("  Mostrar emojis: " + config.showEmojis());
        System.out.println("  Tamanho máximo do histórico: " + config.getMaxHistorySize());
        System.out.println("  Timeout de conexão: " + config.getConnectionTimeout() + "ms");

        System.out.println("  ✓ Uso básico demonstrado\n");
    }

    private static void exemploModificacao() {
        System.out.println("2. Modificação de configurações:");

        ConfigManager config = ConfigManager.getInstance();

        // Salvar valores originais
        String originalApiKey = config.getApiKey();
        boolean originalShowEmojis = config.showEmojis();

        // Modificar configurações temporariamente
        config.setApiKey("EXEMPLO_API_KEY_TEMPORARIA");
        System.out.println("  API Key modificada temporariamente");

        // Verificar mudança
        System.out.println("  Nova API Key: " + config.getApiKey());

        // Restaurar valores originais
        config.setApiKey(originalApiKey);
        System.out.println("  API Key restaurada");

        // Salvar configurações (se necessário)
        // config.saveConfiguration();

        System.out.println("  ✓ Modificação demonstrada\n");
    }

    private static void exemploValidacao() {
        System.out.println("3. Validação e tratamento de erros:");

        ConfigManager config = ConfigManager.getInstance();

        // Validar configuração atual
        boolean isValid = config.validateConfiguration();
        System.out.println("  Configuração válida: " + isValid);

        if (!isValid) {
            System.out.println("  Problemas encontrados:");

            if (config.getApiKey().isEmpty()) {
                System.out.println("    - API Key não configurada");
            }

            if (config.getConnectionTimeout() <= 0) {
                System.out.println("    - Timeout de conexão inválido");
            }

            if (config.getMaxHistorySize() <= 0) {
                System.out.println("    - Tamanho do histórico inválido");
            }
        }

        // Verificar se configuração foi carregada com sucesso
        if (!config.isConfigLoaded()) {
            System.out.println("  ⚠ Configuração não foi carregada - usando valores padrão");
        }

        System.out.println("  ✓ Validação demonstrada\n");
    }

    private static void exemploIntegracao() {
        System.out.println("4. Integração com outras classes:");

        ConfigManager config = ConfigManager.getInstance();

        // Simular uso em WeatherService
        System.out.println("  === Configurações para WeatherService ===");
        System.out.println("  URL Base: " + config.getBaseUrl());
        System.out.println("  Unidades: " + config.getUnitGroup());
        System.out.println("  Idioma: " + config.getLanguage());
        System.out.println("  Timeout Conexão: " + config.getConnectionTimeout() + "ms");
        System.out.println("  Timeout Leitura: " + config.getReadTimeout() + "ms");

        // Simular uso em DataPersistence
        System.out.println("\n  === Configurações para DataPersistence ===");
        System.out.println("  Max Histórico: " + config.getMaxHistorySize());
        System.out.println("  Backup Automático: " + config.isAutoBackupEnabled());
        System.out.println("  Frequência Backup: " + config.getBackupFrequency());

        // Simular uso na interface
        System.out.println("\n  === Configurações para Interface ===");
        System.out.println("  Mostrar Emojis: " + config.showEmojis());
        System.out.println("  Unidade Temperatura: " + config.getTemperatureUnit());
        System.out.println("  Unidade Vento: " + config.getWindSpeedUnit());

        System.out.println("  ✓ Integração demonstrada\n");
    }

    /**
     * Exemplo de como criar um método auxiliar para configurações customizadas
     */
    public static void configureForDevelopment(ConfigManager config) {
        // Configurações específicas para desenvolvimento
        config.setApiKey("DEV_API_KEY");
        // Configurar timeouts menores para testes rápidos
        // (Nota: seria necessário adicionar métodos setter no ConfigManager)

        System.out.println("Configuração de desenvolvimento aplicada");
    }

    /**
     * Exemplo de como criar um método auxiliar para configurações de produção
     */
    public static void configureForProduction(ConfigManager config) {
        // Configurações específicas para produção
        // Validar se todas as configurações críticas estão definidas
        if (!config.validateConfiguration()) {
            throw new IllegalStateException("Configuração inválida para produção");
        }

        System.out.println("Configuração de produção validada");
    }
}
