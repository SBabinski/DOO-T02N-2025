import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Persistencia persistencia = new Persistencia();
        
        System.out.println("=".repeat(50));
        System.out.println("          SISTEMA DE SERIES TV");
        System.out.println("=".repeat(50));
        
        System.out.print("Digite seu nome ou apelido: ");
        String nome = scanner.nextLine().trim();
        
        if (nome.isEmpty()) {
            nome = "Usuario";
        }
        
        // Tentar carregar usuario existente
        Usuario usuario = persistencia.carregarUsuario(nome);
        
        if (usuario == null) {
            // Criar novo usuario
            usuario = new Usuario(nome);
            System.out.println("Novo usuario criado!");
        } else {
            System.out.printf("Carregando - Favoritos: %d, Assistidas: %d, Desejo assistir: %d\n",
                             usuario.getFavoritos().size(),
                             usuario.getAssistidas().size(), 
                             usuario.getDesejoAssistir().size());
            System.out.println("Usuario carregado com sucesso!");
        }
        
        System.out.printf("\nBem-vindo(a), %s!\n", nome);
        
        // Iniciar gerenciador
        GerenciadorSeries gerenciador = new GerenciadorSeries(usuario);
        gerenciador.iniciar();
        
        scanner.close();
    }
}