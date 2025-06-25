public class Chave {
     // Substitua pela sua chave da API Visual Crossing
    private static final String API_KEY = "SUA_CHAVE_AQUI";
    
    public static String getApiKey() {
        if (API_KEY == null || API_KEY.equals("SUA_CHAVE_AQUI")) {
            throw new RuntimeException("Chave da API não configurada! Por favor, configure sua chave no arquivo Chave.java");
        }
        return API_KEY;
    }
    
    public static boolean isChaveConfigurada() {
        return API_KEY != null && !API_KEY.equals("SUA_CHAVE_AQUI") && !API_KEY.trim().isEmpty();
    }
    
    public static boolean validarFormatoChave() {
        return isChaveConfigurada() && API_KEY.length() > 10;
    }
}