package seriesTV;

import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApiTvMazeService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final Scanner scanner = new Scanner(System.in);

    public Serie buscarSeriePorNome(String nomeBusca) {
        while (true) {
            String url = "https://api.tvmaze.com/search/shows?q=" + nomeBusca.replace(" ", "%20");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonArray resultados = JsonParser.parseString(response.body()).getAsJsonArray();

                    if (resultados.size() == 0) {
                        System.out.println("Nenhuma série encontrada com esse nome.");
                        return null;
                    }

                    String termoMinusculo = nomeBusca.toLowerCase();
                    List<JsonObject> resultadosExatos = new ArrayList<>();

                    for (JsonElement elem : resultados) {
                        JsonObject showObj = elem.getAsJsonObject().getAsJsonObject("show");
                        if (showObj.get("name").getAsString().toLowerCase().equals(termoMinusculo)) {
                            resultadosExatos.add(showObj);
                        }
                    }

                    List<JsonObject> listaParaMostrar;
                    boolean usandoExatos = false;

                    if (!resultadosExatos.isEmpty()) {
                        listaParaMostrar = resultadosExatos;
                        usandoExatos = true;
                        System.out.println("\nResultados com nome exato:");
                    } else {
                        listaParaMostrar = new ArrayList<>();
                        int max = Math.min(resultados.size(), 10);
                        for (int i = 0; i < max; i++) {
                            JsonObject showObj = resultados.get(i).getAsJsonObject().getAsJsonObject("show");
                            listaParaMostrar.add(showObj);
                        }
                        System.out.println("\nSéries encontradas (resultados parciais):");
                    }

                    for (int i = 0; i < listaParaMostrar.size(); i++) {
                        System.out.println((i + 1) + ". " + listaParaMostrar.get(i).get("name").getAsString());
                    }

                    System.out.print("\nDigite o número da série que deseja selecionar (0 se nenhuma): ");
                    int escolha;
                    try {
                        escolha = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Cancelando busca.");
                        return null;
                    }

                    if (escolha > 0 && escolha <= listaParaMostrar.size()) {
                        JsonObject serieSelecionada = listaParaMostrar.get(escolha - 1);


                        String nome = serieSelecionada.get("name").getAsString();
                        String idioma = serieSelecionada.get("language").getAsString();

                        List<String> generos = new ArrayList<>();
                        JsonArray generosArray = serieSelecionada.get("genres").getAsJsonArray();
                        for (JsonElement genero : generosArray) {
                            generos.add(genero.getAsString());
                        }

                        double nota = serieSelecionada.get("rating").getAsJsonObject().get("average").isJsonNull() ?
                                0.0 : serieSelecionada.get("rating").getAsJsonObject().get("average").getAsDouble();

                        String status = serieSelecionada.get("status").getAsString();

                        LocalDate dataEstreia = serieSelecionada.get("premiered").isJsonNull() ?
                                null : converterStringParaData(serieSelecionada.get("premiered").getAsString());

                        LocalDate dataFim = serieSelecionada.get("ended").isJsonNull() ?
                                null : converterStringParaData(serieSelecionada.get("ended").getAsString());

                        String emissora = serieSelecionada.has("network") && !serieSelecionada.get("network").isJsonNull() ?
                                serieSelecionada.get("network").getAsJsonObject().get("name").getAsString() :
                                "Independente/Streaming";

                        return new Serie(nome, idioma, generos, nota, status, dataEstreia, dataFim, emissora);

                    } else if (escolha == 0) {
                        String resposta = "";
                        while (true) {
                            System.out.print("Deseja refinar a busca? (S/N): ");
                            try {
                                resposta = scanner.nextLine().trim().toLowerCase();
                                if (resposta.equals("s") || resposta.equals("sim")) {
                                    resposta = "S";
                                    break;
                                } else if (resposta.equals("n") || resposta.equals("não") || resposta.equals("nao")) {
                                    resposta = "N";
                                    break;
                                } else {
                                    System.out.println("Entrada inválida. Digite 'S', 'Sim', 'N' ou 'Não'.");
                                }
                            } catch (Exception e) {
                                System.out.println("Erro ao ler a entrada, tente novamente.");
                            }
                        }

                        if (resposta.equals("S")) {
                            System.out.print("Digite um termo mais específico para busca: ");
                            nomeBusca = scanner.nextLine();
                            continue;
                        } else {
                            System.out.println("Busca cancelada.");
                            return null;
                        }
                    } else {
                        System.out.println("Opção inválida. Cancelando busca.");
                        return null;
                    }
                } else {
                    System.out.println("Erro ao buscar série. Código HTTP: " + response.statusCode());
                    return null;
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Erro na requisição HTTP: " + e.getMessage());
                return null;
            }
        }
    }

    public LocalDate converterStringParaData(String dataString) {
        if (dataString == null || dataString.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dataString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido: " + dataString);
            return null;
        }
    }
}
