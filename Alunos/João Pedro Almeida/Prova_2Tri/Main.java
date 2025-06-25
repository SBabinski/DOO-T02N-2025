package com.cinelume;

import com.cinelume.controller.UsuarioController;
import com.cinelume.view.MenuView;

public class Main {
    public static void main(String[] args) {
        UsuarioController controller = new UsuarioController();
        MenuView menu = new MenuView(controller);
        menu.exibirMenu();
    }
}