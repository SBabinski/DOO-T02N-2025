package Trabalho2;

import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class IbgeClient {

    // Método auxiliar para leitura do JSON de uma URL
    private JsonObject lerJsonDeUrl(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) URI.create(url).toURL().openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            return JsonParser.parseReader(in).getAsJsonObject();
        }
    }

    // Método para converter o JSON em uma lista de notícias
    private List<Noticia> parseNoticias(JsonObject obj) {
        List<Noticia> noticias = new ArrayList<>();
        JsonArray items = obj.getAsJsonArray("items");

        for (JsonElement el : items) {
            JsonObject o = el.getAsJsonObject();
            Noticia noticia = new Noticia();
            noticia.setTitulo(o.get("titulo").getAsString());
            noticia.setIntroducao(o.get("introducao").getAsString());
            noticia.setDataPublicacao(o.get("data_publicacao").getAsString());
            noticia.setLinkCompleto(o.get("link").getAsString());
            noticia.setTipo(o.get("tipo").getAsString());
            noticia.setFonte("IBGE"); // Fonte fixa como IBGE
            noticias.add(noticia);
        }

        return noticias;
    }

    // Retorna apenas a primeira notícia da lista
    private Noticia parsePrimeiraNoticia(JsonObject obj) {
        List<Noticia> noticias = parseNoticias(obj);
        return noticias.isEmpty() ? null : noticias.get(0);
    }

    // Buscar por título
    public Noticia buscarNoticiaPorTitulo(String titulo) throws IOException {
        String url = "https://servicodados.ibge.gov.br/api/v3/noticias/?busca=" + URLEncoder.encode(titulo, "UTF-8");
        return parsePrimeiraNoticia(lerJsonDeUrl(url));
    }

    // Buscar por palavra-chave
    public Noticia buscarNoticiaPorPalavraChave(String palavraChave) throws IOException {
        String url = "https://servicodados.ibge.gov.br/api/v3/noticias/?busca=" + URLEncoder.encode(palavraChave, "UTF-8");
        return parsePrimeiraNoticia(lerJsonDeUrl(url));
    }

    // Buscar por data (formato esperado: yyyy-MM-dd)
    public Noticia buscarNoticiaPorData(String dataPublicacao) throws IOException {
        String url = "https://servicodados.ibge.gov.br/api/v3/noticias/?de=" + dataPublicacao + "&ate=" + dataPublicacao;
        return parsePrimeiraNoticia(lerJsonDeUrl(url));
    }

    // (Opcional) Retorna uma lista de todas as notícias encontradas por palavra-chave
    public List<Noticia> buscarTodasNoticiasPorPalavraChave(String palavraChave) throws IOException {
        String url = "https://servicodados.ibge.gov.br/api/v3/noticias/?busca=" + URLEncoder.encode(palavraChave, "UTF-8");
        return parseNoticias(lerJsonDeUrl(url));
    }
}