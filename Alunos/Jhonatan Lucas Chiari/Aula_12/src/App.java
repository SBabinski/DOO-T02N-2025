import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static HttpService httpService = new HttpService();
    private static JSONParserService jsonParser = new JSONParserService();

    public static void main(String[] args) {
        System.out.println("=== APLICATIVO DE CONSULTA DE CLIMA ===");
        System.out.println("Bem-vindo ao Weather App!");
        System.out.println();

        if (!validarConfiguracao()) {
            return;
        }

        boolean continuar = true;

        while (continuar) {
            try {
                System.out.print("Digite o nome da cidade (ou '0' para sair): ");
                String entrada = scanner.nextLine().trim();

                if (!validarEntrada(entrada)) {
                    continue;
                }

                if (entrada.equals("0")) {
                    continuar = false;
                    System.out.println("\nObrigado por usar o Weather App!");
                    break;
                }

                consultarClima(entrada);

            } catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());
                System.out.println("Tente novamente.\n");
            }
        }

        scanner.close();
    }

    private static boolean validarConfiguracao() {
        try {
            if (!Chave.isChaveConfigurada()) {
                System.err.println("ERRO: Chave da API não configurada!");
                System.err.println("Configure sua chave no arquivo Chave.java");
                return false;
            }

            if (!Chave.validarFormatoChave()) {
                System.err.println("ERRO: Formato da chave da API parece inválido!");
                System.err.println("Verifique sua chave no arquivo Chave.java");
                return false;
            }

            return true;

        } catch (Exception e) {
            System.err.println("ERRO na configuração: " + e.getMessage());
            return false;
        }
    }

    private static boolean validarEntrada(String entrada) {
        if (entrada == null || entrada.isEmpty()) {
            System.out.println("Por favor, digite um nome de cidade válido ou '0' para sair.\n");
            return false;
        }

        if (entrada.matches("\\d+") && !entrada.equals("0")) {
            System.out.println("Por favor, digite um nome de cidade válido (não apenas números).\n");
            return false;
        }

        if (!entrada.equals("0") && entrada.length() < 2) {
            System.out.println("Nome da cidade deve ter pelo menos 2 caracteres.\n");
            return false;
        }

        if (entrada.length() > 100) {
            System.out.println("Nome da cidade muito longo. Digite um nome válido.\n");
            return false;
        }

        if (entrada.matches(".*[<>\"'&].*")) {
            System.out.println("Nome da cidade contém caracteres inválidos.\n");
            return false;
        }

        return true;
    }

    private static void consultarClima(String cidade) {
        try {
            System.out.println("\nBuscando informações do clima para: " + cidade);
            System.out.println("Aguarde...\n");

            // Construir URL
            String url = UrlBuilder.construirUrl(cidade);

            // Fazer requisição HTTP
            String jsonResponse = httpService.fazerRequisicao(url);

            // Validar resposta
            if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
                throw new Exception("Resposta vazia da API");
            }

            // Parsear JSON e exibir resultado
            WeatherData weatherData = jsonParser.parseWeatherData(jsonResponse);

            if (!weatherData.isValido()) {
                throw new Exception("Dados do clima incompletos ou inválidos");
            }

            exibirResultado(weatherData);

        } catch (Exception e) {
            tratarErroConsulta(e, cidade);
        }
    }

    /**
     * Trata erros específicos na consulta do clima
     */
    private static void tratarErroConsulta(Exception e, String cidade) {
        String mensagem = e.getMessage().toLowerCase();

        if (mensagem.contains("400") || mensagem.contains("bad request")) {
            System.err.println("Erro: Nome da cidade inválido ou mal formatado.");
            System.out.println("Tente usar o nome completo da cidade ou adicione o país (ex: 'Paris, France').\n");

        } else if (mensagem.contains("401") || mensagem.contains("unauthorized")) {
            System.err.println("Erro: Chave da API inválida ou expirada.");
            System.out.println("Verifique sua chave no arquivo Chave.java.\n");

        } else if (mensagem.contains("404") || mensagem.contains("not found")) {
            System.err.println("Erro: Cidade '" + cidade + "' não encontrada.");
            System.out.println("Verifique a grafia ou tente uma cidade próxima.\n");

        } else if (mensagem.contains("429") || mensagem.contains("too many")) {
            System.err.println("Erro: Limite de requisições excedido.");
            System.out.println("Aguarde alguns minutos antes de tentar novamente.\n");

        } else if (mensagem.contains("timeout") || mensagem.contains("conexão")) {
            System.err.println("Erro: Problema de conexão com a internet.");
            System.out.println("Verifique sua conexão e tente novamente.\n");

        } else {
            System.err.println("Erro ao consultar clima: " + e.getMessage());
            System.out.println("Tente novamente com outro nome de cidade.\n");
        }
    }

    private static void exibirResultado(WeatherData weather) {
        try {
            System.out.println("=== INFORMAÇÕES DO CLIMA ===");
            System.out.printf("Cidade informada: %s%n", weather.getCidade());
            System.out.printf("Temperatura Atual: %.1f°C%n", weather.getTemperaturaAtual());
            System.out.printf("Temperatura Máxima: %.1f°C%n", weather.getTemperaturaMaxima());
            System.out.printf("Temperatura Mínima: %.1f°C%n", weather.getTemperaturaMinima());
            System.out.printf("Umidade ar: %.1f%%%n", weather.getUmidade());
            System.out.printf("Condição: %s%n", WeatherData.traduzirCondicao(weather.getCondicaoTempo()));
            System.out.printf("Precipitação: %.1fmm%n", weather.getPrecipitacao());
            System.out.printf("Vento: %.1f km/h - %s%n", weather.getVelocidadeVento(), weather.getDirecaoVento());
            System.out.println("==============================\n");

        } catch (Exception e) {
            System.err.println("Erro ao exibir resultado: " + e.getMessage());
            System.out.println("Dados obtidos, mas houve problema na exibição.\n");
        }
    }
}