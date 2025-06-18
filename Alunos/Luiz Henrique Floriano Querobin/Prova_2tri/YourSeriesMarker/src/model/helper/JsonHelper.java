package model.helper;

import com.google.gson.Gson;
import model.Serie;
import model.Usuario;
import model.json.SerieJson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    private static Gson gson = new Gson();

    public static void gravarJsonSerie(Serie serie) {
        SerieJson serieJson = SerieJson.fromSerie(serie);
        Gson gson = new Gson();
        String serieJsonString = gson.toJson(serieJson);
        String caminhoCompleto = "file/json/" + serie.getUrl() + ".json";

        try {
            Path path = Paths.get(caminhoCompleto).getParent();
            if (path != null) {
                Files.createDirectories(path);
            }

            try (FileWriter file = new FileWriter(caminhoCompleto)) {
                file.write(serieJsonString);
                file.flush();
                System.out.println("Arquivo JSON gravado com sucesso!");
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar arquivo: " + e.getMessage());
        }
    }

    public static void gravarJsonUsuario(Usuario usuario) {
        Gson gson = new Gson();
        String usuarioJsonString = gson.toJson(usuario);
        String caminhoCompleto = "file/json/usuario/usuario.json";

        try {
            Path path = Paths.get(caminhoCompleto).getParent();
            if (path != null) {
                Files.createDirectories(path);
            }

            try (FileWriter file = new FileWriter(caminhoCompleto)) {
                file.write(usuarioJsonString);
                file.flush();
                System.out.println("Usuário salvo em: " + caminhoCompleto);
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar usuário: " + e.getMessage());
        }
    }

    public static Usuario buscaUsuarioGravado() {
        String caminhoCompleto = "file/json/usuario/usuario.json";

        if (!Files.exists(Paths.get(caminhoCompleto))) {
            return null;
        }

        try (FileReader reader = new FileReader(caminhoCompleto)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            return null;
        }
    }

    public static List<Serie> buscaSeriesGravadas() {
        Path pasta = Paths.get("file/json");
        List<Serie> returnSerieList = new ArrayList<>();

        if (!Files.exists(pasta) || !Files.isDirectory(pasta)) {
            return returnSerieList;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(pasta)) {
            boolean hasFiles = false;

            for (Path arquivo : stream) {
                hasFiles = true;
                if (Files.isRegularFile(arquivo)) {
                    try (FileReader reader = new FileReader(arquivo.toFile())) {
                        SerieJson serieJson = gson.fromJson(reader, SerieJson.class);
                        returnSerieList.add(Serie.fromSerieJson(serieJson));
                    } catch (IOException e) {
                        System.err.println("Erro ao ler arquivo: " + e.getMessage());
                    }
                }
            }

            if (!hasFiles) {
                return returnSerieList;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return returnSerieList;
    }

    public static long buscaUltimoIdSerie() {
        Path pasta = Paths.get("file/json");
        List<Serie> serieList = new ArrayList<>();
        long returnId = 0;

        if (!Files.exists(pasta) || !Files.isDirectory(pasta)) {
            return returnId + 1;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(pasta)) {
            boolean hasFiles = false;

            for (Path arquivo : stream) {
                hasFiles = true;
                if (Files.isRegularFile(arquivo)) {
                    try (FileReader reader = new FileReader(arquivo.toFile())) {
                        SerieJson serieJson = gson.fromJson(reader, SerieJson.class);
                        serieList.add(Serie.fromSerieJson(serieJson));
                    } catch (IOException e) {
                        System.err.println("Erro ao ler arquivo: " + e.getMessage());
                    }
                }
            }

            if (!hasFiles) {
                return returnId + 1;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Serie serie : serieList) {
            if (returnId < serie.getId()) {
                returnId = serie.getId();
            }
        }

        return returnId + 1;
    }
}
