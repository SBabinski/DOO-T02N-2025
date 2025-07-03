package com.cinelume.util;

import com.cinelume.model.Usuario;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonDataManager {
    private static final String DIR_PATH = "data";
    private static final String FILE_PATH = DIR_PATH + "/usuarios.json";
    private static final Gson gson = new Gson();

    static {
        new File(DIR_PATH).mkdirs();
    }

    public static void salvarUsuario(Usuario usuario) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(usuario, writer);
        }
    }

    public static Usuario carregarUsuario(String nomeUsuario) throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return null;
        }
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, Usuario.class);
        }
    }
}