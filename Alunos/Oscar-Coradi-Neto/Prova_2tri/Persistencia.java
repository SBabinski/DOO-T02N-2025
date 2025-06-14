package com.seuprojeto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Persistencia {
    private static final String ARQUIVO_JSON = "dados_usuario.json";
    private final Gson gson;

    public Persistencia() {
        // Configura o Gson para formatar o JSON e lidar com LocalDate
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(java.time.LocalDate.class, new LocalDateAdapter())
                .create();
    }
    
    public void salvarDados(Usuario usuario) {
        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.err.println("ERRO: Não foi possível salvar os dados no arquivo " + ARQUIVO_JSON);
            e.printStackTrace();
        }
    }

    public Usuario carregarDados() {
        try (FileReader reader = new FileReader(ARQUIVO_JSON)) {
            Usuario usuario = gson.fromJson(reader, Usuario.class);
            if(usuario != null) {
                return usuario;
            }
        } catch (IOException | JsonSyntaxException e) {
            // Se o arquivo não existe ou está corrompido, retorna null
            // O App.java vai tratar isso criando um novo usuário
        }
        return null;
    }

    // Adaptador para que o Gson saiba como salvar e ler LocalDate
    private static class LocalDateAdapter extends com.google.gson.TypeAdapter<java.time.LocalDate> {
        @Override
        public void write(com.google.gson.stream.JsonWriter jsonWriter, java.time.LocalDate localDate) throws IOException {
            jsonWriter.value(localDate.toString());
        }

        @Override
        public java.time.LocalDate read(com.google.gson.stream.JsonReader jsonReader) throws IOException {
            return java.time.LocalDate.parse(jsonReader.nextString());
        }
    }
}