package com.monitoradeseries.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
 
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.monitoradeseries.model.Usuario;


public class JsonDataManager {

    private static final String FILE_NAME = "dados_usuario.json";
    private final Gson gson;

    public JsonDataManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void salvarDados(Usuario usuario) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(usuario, writer);
            System.out.println("Dados do usuário salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados do usuário: " + e.getMessage());
        }
    }

    public Usuario carregarDados() {
        if (!Files.exists(Paths.get(FILE_NAME))) {
            System.out.println("Arquivo de dados não encontrado. Criando novo usuário...");
            return null;
        }

        try (Reader reader = new FileReader(FILE_NAME)) {
           
            Type usuarioType = new TypeToken<Usuario>(){}.getType();
            Usuario usuario = gson.fromJson(reader, usuarioType);
            System.out.println("Dados do usuário carregados com sucesso.");
            return usuario;
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados do usuário: " + e.getMessage());
            return null;
        }
    }
}