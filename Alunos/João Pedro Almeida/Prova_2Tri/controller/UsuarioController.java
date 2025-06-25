// UsuarioController.java
package com.cinelume.controller;

import com.cinelume.model.Serie;
import java.util.*;

public class UsuarioController {
    private final Map<String, List<Serie>> listasDoUsuario;

    public UsuarioController() {
        this.listasDoUsuario = new HashMap<>();
        this.listasDoUsuario.put("favoritos", new ArrayList<>()); // Lista padrão
    }

    // Métodos existentes...
    public void adicionarSerie(String lista, Serie serie) {
        listasDoUsuario.computeIfAbsent(lista, k -> new ArrayList<>()).add(serie);
    }

    public List<Serie> getLista(String lista) {
        return listasDoUsuario.getOrDefault(lista, new ArrayList<>());
    }

    // --- Novos métodos para gerenciamento de listas ---
    public Map<String, List<Serie>> getTodasListas() {
        return new HashMap<>(listasDoUsuario); // Retorna cópia para evitar modificações externas
    }

    public boolean criarLista(String nomeLista) {
        if (listasDoUsuario.containsKey(nomeLista)) {
            return false; // Lista já existe
        }
        listasDoUsuario.put(nomeLista, new ArrayList<>());
        return true;
    }

    public boolean renomearLista(String nomeAntigo, String nomeNovo) {
        if (!listasDoUsuario.containsKey(nomeAntigo) {
            return false; // Lista original não existe
        }
        List<Serie> series = listasDoUsuario.remove(nomeAntigo);
        listasDoUsuario.put(nomeNovo, series);
        return true;
    }

    public boolean deletarLista(String nomeLista) {
        if (nomeLista.equals("favoritos")) {
            return false; // Não permite deletar favoritos
        }
        return listasDoUsuario.remove(nomeLista) != null;
    }
}