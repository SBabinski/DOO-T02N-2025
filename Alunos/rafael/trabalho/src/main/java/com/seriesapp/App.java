package com.seriesapp;

import com.seriesapp.controller.MenuController;
import com.seriesapp.model.Usuario;
import com.seriesapp.repository.SerieRepository;

public class App {
    public static void main(String[] args) {
        try {
            SerieRepository repository = new SerieRepository();
            Usuario usuario = repository.carregarDados();

            MenuController menuController = new MenuController(usuario);
            menuController.iniciar();

            repository.salvarDados(usuario);
        } catch (Exception e) {
            System.err.println("Erro fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
