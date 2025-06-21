package seriestv.pack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Usuario {
    public static Scanner sc = new Scanner(System.in);
    private String nome;
    private List<Serie> desejo;
    private List<Serie> assistidas;
    private List<Serie> favoritas;

    public Usuario(String nome, List<Serie> desejo, List<Serie> assistidas, List<Serie> favoritas) {
        this.nome = nome;
        this.desejo = desejo;
        this.assistidas = assistidas;
        this.favoritas = favoritas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Serie> getDesejo() {
        return desejo;
    }

    public void setDesejo(List<Serie> desejo) {
        this.desejo = desejo;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public void setAssistidas(List<Serie> assistidas) {
        this.assistidas = assistidas;
    }

    public List<Serie> getFavoritas() {
        return favoritas;
    }

    public void setFavoritas(List<Serie> favoritas) {
        this.favoritas = favoritas;
    }

    public Usuario() {
        desejo = new ArrayList<>();
        assistidas = new ArrayList<>();
        favoritas = new ArrayList<>();
    }

    public Usuario(String nome) {
        this();
        this.nome = nome;
    }

    public static void salvarUsuario(Usuario usuario) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("usuario.json")) {
            gson.toJson(usuario, writer);
            System.out.println("Usuário salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static Usuario carregarUsuario() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("usuario.json")) {
            Usuario usuario = gson.fromJson(reader, Usuario.class);
            System.out.println();
            System.out.println("Bem-vindo(a) de volta " + usuario.getNome() + "!");
            System.out.println();
            return usuario;
        } catch (IOException e) {
            System.out.println("Nenhum usuário salvo encontrado. Criando novo...");
            return new Usuario("NovoUsuario");
        }
    }

    public static Usuario iniciarUsuario() {
        File arquivo = new File("usuario.json");

        if (arquivo.exists()) {
            // Carrega usuário existente
            return carregarUsuario();
        }

        // Caso seja primeiro acesso
        System.out.println("Seja muito bem-vindo(a) ao BMDI!");
        System.out.println("Antes de entrarmos neste magnífico mundo das séries, preciso saber o seu nome!");
        System.out.println();

        String nome;

        while (true) {
            System.out.print("Digite o seu nome: ");
            nome = sc.nextLine();

            if (nome.matches(".*[0-9].*")) {
                System.out.println("O nome não pode conter números.");
            } else if (nome.length() < 3) {
                System.out.println("O nome deve conter ao menos 3 caracteres.");
            } else {
                break;
            }
        }
        
    
        Usuario novoUsuario = new Usuario(nome);
        salvarUsuario(novoUsuario);
        System.out.println("Usuário cadastrado com sucesso!");

        return novoUsuario;
    }
    
}
