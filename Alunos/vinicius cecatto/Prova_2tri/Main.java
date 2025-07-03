package prova2tri;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite seu nome ou apelido: ");
        String nomeUsuario = scanner.nextLine().trim();

        String nomeArquivo = nomeUsuario.toLowerCase() + ".json";
        User user = JsonStorage.loadUser(nomeArquivo);

        if (user == null) {
            user = new User(nomeUsuario);
            System.out.println("Novo usuário criado.");
        } else {
            System.out.println("Bem-vindo de volta, " + user.getNome() + "!");
        }

        SerieManager manager = new SerieManager(user);
        manager.menu();

        try {
            JsonStorage.saveUser(user, nomeArquivo);
            System.out.println("Dados salvos com sucesso para " + nomeUsuario);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}
