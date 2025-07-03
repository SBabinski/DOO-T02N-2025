package com.faculdade.tvseries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PersistenciaDados {
    private static final String ARQUIVO = "usuario.json";
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    
    public static void salvarUsuario(Usuario usuario) {
        try (FileWriter escritor = new FileWriter(ARQUIVO)) {
            gson.toJson(usuario, escritor);
           
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    
    public static Usuario carregarUsuario() {
        try (FileReader leitor = new FileReader(ARQUIVO)) {
            
            return gson.fromJson(leitor, Usuario.class);
        } catch (IOException e) {
            
            System.out.println("Arquivo de dados não encontrado ou erro de leitura: " + e.getMessage() + ". Um novo usuário será criado.");
            return null;
        } catch (JsonSyntaxException e) {
            
            System.err.println("Erro de sintaxe no arquivo JSON: " + e.getMessage() + ". Um novo usuário será criado.");
            return null;
        }
    }
}