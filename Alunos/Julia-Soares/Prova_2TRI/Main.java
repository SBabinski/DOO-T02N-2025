package org.series;

import org.series.manager.SerieManager;
import org.series.json.JsonUtil;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite seu nome ou apelido: ");
        String nome = scanner.nextLine().trim().toLowerCase();
        String caminho = "data/usuarios/" + nome + ".json";

        Usuario usuario = JsonUtil.carregarUsuario(caminho);

        if (usuario == null) {
            usuario = new Usuario(nome);
            System.out.println("Novo usuário criado: " + nome);
        } else {
            System.out.println("Bem-vindo de volta, " + usuario.getNome());
        }

        SerieManager manager = new SerieManager(usuario);
        manager.menu();

        JsonUtil.salvarUsuario(usuario, caminho);

    }
}
