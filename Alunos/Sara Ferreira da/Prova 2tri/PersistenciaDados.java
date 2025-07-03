package com.faculdade.tv_series;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PersistenciaDados {
    private static final String DATA_FILE = "dados_usuario.json"; 
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create(); 

    
    public static void salvarUsuario(Usuario usuario) {
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            gson.toJson(usuario, writer);
            System.out.println("Dados do usuário salvos com sucesso em " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados do usuário: " + e.getMessage());
        }
    }

   
    public static Usuario carregarUsuario() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (FileReader reader = new FileReader(DATA_FILE)) {
                Usuario usuario = gson.fromJson(reader, Usuario.class);
                if (usuario != null) {
                    System.out.println("Dados do usuário carregados de " + DATA_FILE);
                    return usuario;
                }
            } catch (IOException | JsonSyntaxException e) {
                System.err.println("Erro ao carregar dados do usuário ou arquivo corrompido: " + e.getMessage());
                
            }
        }
        System.out.println("Nenhum dado de usuário existente encontrado. Criando novo usuário.");
        return null; 
    }
}