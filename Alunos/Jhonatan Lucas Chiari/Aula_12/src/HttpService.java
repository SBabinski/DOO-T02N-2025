import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class HttpService {
    private static final int TIMEOUT_CONEXAO = 10000; // 10 segundos
    private static final int TIMEOUT_LEITURA = 15000; // 15 segundos
    private static final String USER_AGENT = "WeatherApp/1.0";
    
    /**
     * Faz uma requisição HTTP GET para a URL especificada
     * @param urlString URL para fazer a requisição
     * @return String contendo a resposta JSON da API
     * @throws Exception se houver erro na requisição
     */
    public String fazerRequisicao(String urlString) throws Exception {
        HttpURLConnection conexao = null;
        BufferedReader reader = null;
        
        try {
            // Criar conexão
            URL url = new URL(urlString);
            conexao = (HttpURLConnection) url.openConnection();
            
            // Configurar requisição
            configurarRequisicao(conexao);
            
            // Verificar código de resposta HTTP
            int codigoResposta = conexao.getResponseCode();
            
            // Processar resposta baseada no código HTTP
            return processarResposta(conexao, codigoResposta);
            
        } catch (SocketTimeoutException e) {
            throw new Exception("Timeout na conexão - Verifique sua internet", e);
        } catch (IOException e) {
            throw new Exception("Erro de conexão: " + e.getMessage(), e);
        } finally {
            // Limpeza de recursos
            fecharRecursos(reader, conexao);
        }
    }

    private void configurarRequisicao(HttpURLConnection conexao) throws IOException {
        conexao.setRequestMethod("GET");
        conexao.setConnectTimeout(TIMEOUT_CONEXAO);
        conexao.setReadTimeout(TIMEOUT_LEITURA);
        conexao.setRequestProperty("Accept", "application/json");
        conexao.setRequestProperty("User-Agent", USER_AGENT);
        conexao.setRequestProperty("Accept-Charset", "UTF-8");
    }
    
    private String processarResposta(HttpURLConnection conexao, int codigoResposta) throws Exception {
        switch (codigoResposta) {
            case HttpURLConnection.HTTP_OK: // 200
                return lerResposta(conexao.getInputStream());
                
            case HttpURLConnection.HTTP_BAD_REQUEST: // 400
                String erroDetalhado = lerResposta(conexao.getErrorStream());
                throw new Exception("Requisição inválida (400) - Parâmetros incorretos: " + erroDetalhado);
                
            case HttpURLConnection.HTTP_UNAUTHORIZED: // 401
                throw new Exception("Chave de API inválida (401) - Verifique sua API key no arquivo Chave.java");
                
            case HttpURLConnection.HTTP_FORBIDDEN: // 403
                throw new Exception("Acesso negado (403) - Limite de requisições excedido ou conta suspensa");
                
            case HttpURLConnection.HTTP_NOT_FOUND: // 404
                throw new Exception("Cidade não encontrada (404) - Verifique o nome da cidade e tente novamente");
                
            case 429: // TOO_MANY_REQUESTS
                throw new Exception("Muitas requisições (429) - Limite de requisições por minuto/dia excedido");
                
            case HttpURLConnection.HTTP_INTERNAL_ERROR: // 500
                throw new Exception("Erro interno do servidor (500) - Tente novamente em alguns instantes");
                
            default:
                String mensagemErro = conexao.getResponseMessage();
                throw new Exception("Erro HTTP " + codigoResposta + ": " + mensagemErro);
        }
    }
    
    private String lerResposta(java.io.InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }
        
        StringBuilder resposta = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            
            String linha;
            while ((linha = reader.readLine()) != null) {
                resposta.append(linha);
            }
        }
        
        return resposta.toString();
    }
    
    private void fecharRecursos(BufferedReader reader, HttpURLConnection conexao) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar BufferedReader: " + e.getMessage());
            }
        }
        
        if (conexao != null) {
            conexao.disconnect();
        }
    }
    
    public boolean validarUrl(String urlString) {
        try {
            new URL(urlString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}