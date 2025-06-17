import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsultaMoedas {

    public static void main(String[] args) {
        try {
            System.out.println("==== Moedas Selecionadas ====");
            fazerRequisicao("https://economia.awesomeapi.com.br/json/last/USD-BRL,EUR-BRL");

            System.out.println("\n==== Fechamento dos últimos 2 dias ====");
            fazerRequisicao("https://economia.awesomeapi.com.br/json/daily/USD-BRL/2");

            System.out.println("\n==== Fechamento período específico ====");
            fazerRequisicao("https://economia.awesomeapi.com.br/json/daily/USD-BRL/?start_date=20240520&end_date=20240525");

            System.out.println("\n==== Cotações sequenciais EUR-BRL (5) ====");
            fazerRequisicao("https://economia.awesomeapi.com.br/json/daily/EUR-BRL/5");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fazerRequisicao(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            int status = conexao.getResponseCode();
            System.out.println("Status HTTP: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String inputLine;
            StringBuilder conteudo = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                conteudo.append(inputLine);
            }

            in.close();
            conexao.disconnect();

            System.out.println("Resposta: ");
            System.out.println(conteudo.toString());
        } catch (Exception e) {
            System.out.println("Erro na requisição: " + e.getMessage());
        }
    }
}
