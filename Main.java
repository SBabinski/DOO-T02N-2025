import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConexaoClima conexao = new ConexaoClima();
        ConversorClima conversor = new ConversorClima();

        System.out.println("=== Consulta de Clima ===");
        System.out.print("Informe o nome da cidade: ");
        String cidade = scanner.nextLine();


        String apiKey = Config.get("api.key");

        try {
            String json = conexao.buscarClimaJson(cidade, apiKey);
            Clima clima = conversor.converterJsonParaClima(json);
            System.out.println(clima);
        } catch (Exception e) {
            System.err.println("Erro ao buscar clima: " + e.getMessage());
        }
    }
}
