package org.example.servico;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.dto.SerieDto;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ServicoUsuario {

    private static final String ARQUIVO_DADOS = "dados_usuario.json";

    private String nomeUsuario;
    private List<SerieDto> favoritos;
    private List<SerieDto> assistidas;
    private List<SerieDto> paraAssistir;

    public ServicoUsuario() {
        favoritos = new ArrayList<>();
        assistidas = new ArrayList<>();
        paraAssistir = new ArrayList<>();
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public List<SerieDto> getFavoritos() {
        return favoritos;
    }

    public List<SerieDto> getAssistidas() {
        return assistidas;
    }

    public List<SerieDto> getParaAssistir() {
        return paraAssistir;
    }

    public void salvarDados() {
        try (FileWriter writer = new FileWriter(ARQUIVO_DADOS)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this, writer);
        } catch (Exception e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public static ServicoUsuario carregarDados() {
        ServicoUsuario usuario;
        try {
            File arquivo = new File(ARQUIVO_DADOS);
            if (arquivo.exists()) {
                try (FileReader reader = new FileReader(arquivo)) {
                    Gson gson = new Gson();
                    usuario = gson.fromJson(reader, ServicoUsuario.class);
                }
            } else {
                usuario = new ServicoUsuario();
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
            usuario = new ServicoUsuario();
        }
        return usuario;
    }
}
