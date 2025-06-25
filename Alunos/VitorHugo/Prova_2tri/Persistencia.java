package Prova_2tri;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Persistencia {
    private static final String ARQUIVO = "Prova_2tri/usuarios.json";

    // Carrega todos os usuários do arquivo
    public static Map<String, Usuario> carregarUsuarios() {
        try (Reader reader = new InputStreamReader(new FileInputStream(ARQUIVO), "UTF-8")) {
            Type tipo = new TypeToken<Map<String, Usuario>>(){}.getType();
            Map<String, Usuario> usuarios = new Gson().fromJson(reader, tipo);
            return usuarios != null ? usuarios : new HashMap<>();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    // Salva todos os usuários no arquivo
    public static void salvarUsuarios(Map<String, Usuario> usuarios) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(ARQUIVO), "UTF-8")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(usuarios, writer);
        } catch (Exception e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }
}