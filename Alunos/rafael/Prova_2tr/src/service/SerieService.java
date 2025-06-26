package service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.SerieDTO;

public class SerieService {
    private List<SerieDTO> favoritos;
    private List<SerieDTO> assistidos;
    private List<SerieDTO> paraAssistir;

    private final Gson gson = new Gson();
    private final String pasta = "dados";

    public SerieService() {
        criarPastaSeNaoExistir();
        favoritos = carregarLista("favoritos.json");
        assistidos = carregarLista("assistidos.json");
        paraAssistir = carregarLista("paraAssistir.json");
    }

    private void criarPastaSeNaoExistir() {
        File dir = new File(pasta);
        if (!dir.exists()) dir.mkdir();
    }

    private List<SerieDTO> carregarLista(String nomeArquivo) {
        try {
            File file = new File(pasta + "/" + nomeArquivo);
            if (!file.exists()) return new ArrayList<>();
            FileReader reader = new FileReader(file);
            List<SerieDTO> lista = gson.fromJson(reader, new TypeToken<List<SerieDTO>>() {}.getType());
            reader.close();
            return lista != null ? lista : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Erro ao carregar " + nomeArquivo + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void salvarLista(List<SerieDTO> lista, String nomeArquivo) {
        try (FileWriter writer = new FileWriter(pasta + "/" + nomeArquivo)) {
            gson.toJson(lista, writer);
        } catch (Exception e) {
            System.out.println("Erro ao salvar " + nomeArquivo + ": " + e.getMessage());
        }
    }

    public void adicionarFavorito(SerieDTO serie) {
        favoritos.add(serie);
        salvarLista(favoritos, "favoritos.json");
    }

    public void adicionarAssistido(SerieDTO serie) {
        assistidos.add(serie);
        salvarLista(assistidos, "assistidos.json");
    }

    public void adicionarParaAssistir(SerieDTO serie) {
        paraAssistir.add(serie);
        salvarLista(paraAssistir, "paraAssistir.json");
    }

    public void removerFavorito(int indice) {
        if (indice >= 0 && indice < favoritos.size()) {
            favoritos.remove(indice);
            salvarLista(favoritos, "favoritos.json");
        }
    }

    public void removerAssistido(int indice) {
        if (indice >= 0 && indice < assistidos.size()) {
            assistidos.remove(indice);
            salvarLista(assistidos, "assistidos.json");
        }
    }

    public void removerParaAssistir(int indice) {
        if (indice >= 0 && indice < paraAssistir.size()) {
            paraAssistir.remove(indice);
            salvarLista(paraAssistir, "paraAssistir.json");
        }
    }

    public List<SerieDTO> getFavoritos() {
        return favoritos;
    }

    public List<SerieDTO> getAssistidos() {
        return assistidos;
    }

    public List<SerieDTO> getParaAssistir() {
        return paraAssistir;
    }
}