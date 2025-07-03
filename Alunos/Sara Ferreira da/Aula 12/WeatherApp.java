import java.util.Scanner;

public class WeatherApp {

    private static final String API_KEY = "UTZ285KKNFLBG4LX5Q9JJFY85"; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();

        WeatherService.buscarClima(cidade, API_KEY);

        scanner.close();
    }
}
