package tvtracker;

import service.*;
import util.*;

import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PersistenciaService persistencia = new PersistenciaService();
        Usuario usuario = persistencia.carregarUsuario();
        TvMazeAPIService apiService = new TvMazeAPIService();

        System.out.println("Bem-vindo ao sistema de séries!");
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            System.out.print("Digite seu nome ou apelido: ");
            usuario.setNome(sc.nextLine());
        }

        int opcao;
        do {
            System.out.println("\n1 - Buscar série");
            System.out.println("2 - Ver listas");
            System.out.println("3 - Salvar e sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o nome da série: ");
                    String nome = sc.nextLine();
                    Serie serie = apiService.buscarSeriePorNome(nome);
                    if (serie != null) {
                        System.out.println(serie);
                        System.out.println("1 - Adicionar a favoritos");
                        System.out.println("2 - Adicionar a assistidas");
                        System.out.println("3 - Adicionar a desejadas");
                        System.out.println("0 - Nada");
                        int escolha = sc.nextInt();
                        sc.nextLine();
                        switch (escolha) {
                            case 1 -> usuario.getFavoritas().add(serie);
                            case 2 -> usuario.getAssistidas().add(serie);
                            case 3 -> usuario.getDesejadas().add(serie);
                        }
                    } else {
                        System.out.println("Série não encontrada.");
                    }
                }
                case 2 -> {
                    System.out.println("1 - Favoritas");
                    System.out.println("2 - Assistidas");
                    System.out.println("3 - Desejadas");
                    int tipoLista = sc.nextInt();
                    sc.nextLine();
                    List<Serie> lista = switch (tipoLista) {
                        case 1 -> usuario.getFavoritas();
                        case 2 -> usuario.getAssistidas();
                        case 3 -> usuario.getDesejadas();
                        default -> new ArrayList<>();
                    };
                    ListaSeries.exibir(lista);
                }
            }
        } while (opcao != 3);

        persistencia.salvarUsuario(usuario);
        System.out.println("Dados salvos. Até logo!");
    }
}

// model/Serie.java
package model;

import java.util.List;

public class Serie {
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String status;
    private String dataEstreia;
    private String dataFim;
    private String emissora;

    // Getters, setters, toString
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public List<String> getGeneros() { return generos; }
    public void setGeneros(List<String> generos) { this.generos = generos; }
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDataEstreia() { return dataEstreia; }
    public void setDataEstreia(String dataEstreia) { this.dataEstreia = dataEstreia; }
    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }
    public String getEmissora() { return emissora; }
    public void setEmissora(String emissora) { this.emissora = emissora; }

    @Override
    public String toString() {
        return "\nNome: " + nome +
                "\nIdioma: " + idioma +
                "\nGêneros: " + generos +
                "\nNota: " + nota +
                "\nStatus: " + status +
                "\nEstreia: " + dataEstreia +
                "\nFim: " + (dataFim != null ? dataFim : "em exibição") +
                "\nEmissora: " + emissora;
    }
}

// model/Usuario.java
package model;

import java.util.*;

public class Usuario {
    private String nome;
    private List<Serie> favoritas = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> desejadas = new ArrayList<>();

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Serie> getFavoritas() { return favoritas; }
    public List<Serie> getAssistidas() { return assistidas; }
    public List<Serie> getDesejadas() { return desejadas; }
}

// model/ListaSeries.java
package model;

import java.util.*;

public class ListaSeries {
    public static void exibir(List<Serie> lista) {
        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }
        for (Serie s : lista) {
            System.out.println(s);
            System.out.println("--------------------");
        }
    }
}

// service/TvMazeAPIService.java
package service;

import model.Serie;
import com.google.gson.*;
        import java.net.*;
        import java.io.*;
        import java.util.*;

public class TvMazeAPIService {
    public Serie buscarSeriePorNome(String nomeBusca) {
        try {
            String url = "https://api.tvmaze.com/singlesearch/shows?q=" + URLEncoder.encode(nomeBusca, "UTF-8");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

            Serie s = new Serie();
            s.setNome(json.get("name").getAsString());
            s.setIdioma(json.get("language").getAsString());

            List<String> generos = new ArrayList<>();
            for (JsonElement el : json.getAsJsonArray("genres")) {
                generos.add(el.getAsString());
            }
            s.setGeneros(generos);
            s.setNota(json.get("rating").getAsJsonObject().get("average").getAsDouble());
            s.setStatus(json.get("status").getAsString());
            s.setDataEstreia(json.get("premiered").getAsString());
            s.setDataFim(json.has("ended") && !json.get("ended").isJsonNull() ? json.get("ended").getAsString() : null);
            s.setEmissora(json.get("network") != null ? json.get("network").getAsJsonObject().get("name").getAsString() : "Online");
            return s;
        } catch (Exception e) {
            System.out.println("Erro ao buscar série: " + e.getMessage());
            return null;
        }
    }
}

// service/PersistenciaService.java
package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Usuario;

import java.io.*;

public class PersistenciaService {
    private final String CAMINHO = "data/usuario.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Usuario carregarUsuario() {
        try (Reader reader = new FileReader(CAMINHO)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (IOException e) {
            return new Usuario();
        }
    }

    public void salvarUsuario(Usuario usuario) {
        try (Writer writer = new FileWriter(CAMINHO)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
}
