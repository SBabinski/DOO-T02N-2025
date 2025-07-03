import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.Gson;

public class TVMazeSearchGson {
    public static Show buscarAPI(Scanner scanner) throws URISyntaxException, IOException, InterruptedException {
        System.out.print("Digite o nome da série: ");
        String serie = scanner.nextLine();
        System.out.println();

        String url = "https://api.tvmaze.com/singlesearch/shows?q=" + serie.replace(" ", "%20");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String body = response.body();
            Gson gson = new Gson();
            Show show = gson.fromJson(body, Show.class);

            System.out.println("Nome: " + show.name);
            System.out.println("Idioma: " + show.language);
            System.out.print("Gêneros: ");
            for (String genre : show.genres) {
                System.out.print(genre + " ");
            }
            System.out.println();
            System.out.println("Nota média: " + (show.rating != null && show.rating.average != null ? show.rating.average : "Não disponível"));
            System.out.println("Status: " + show.status);
            System.out.println("Data de estreia: " + show.premiered);
            System.out.println("Data de término: " + show.ended);
            System.out.println("Emissora: " +
                    (show.network != null ? show.network.name :
                            (show.webChannel != null ? show.webChannel.name : "Não encontrada")));
            String resumoLimpo = show.summary != null ? show.summary.replaceAll("\\<.*?\\>", "") : "Resumo não disponível";
            System.out.println("Resumo: " + resumoLimpo);
            System.out.println();

            return show;
        } else {
            System.out.println("Erro: " + response.statusCode());
            return null;
        }
    }
}



// Classe para mapear o JSON
class Show {
    public int id;
    public String name;
    public String language;
    public String[] genres;
    public String summary;
    public Rating rating;
    public String status;
    public String premiered;
    public String ended;
    public Network network;
    public WebChannel webChannel;

    public static class Rating {
        public Double average;
    }

    public static class Network {
        public String name;
    }

    public static class WebChannel {
        public String name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Show s) {
            return this.id == s.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}