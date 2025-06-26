
import java.util.*;
import java.util.stream.Collectors;

public class BlogApp {
    private static Usuario usuario;
    private static List<Noticia> noticias = new ArrayList<>();

    public static void main(String[] args) {
        Map<String, Object> dados = NoticiaRepository.carregar();
        usuario = (Usuario) dados.getOrDefault("usuario", null);

        if (usuario == null) {
            String nome = Util.entrada("Digite seu nome: ");
            String apelido = Util.entrada("Digite seu apelido: ");
            usuario = new Usuario(nome, apelido);
        }

        boolean executando = true;
        while (executando) {
            System.out.println("\nBem-vindo(a), " + usuario.getApelido());
            System.out.println("1. Buscar notícias");
            System.out.println("2. Favoritos");
            System.out.println("3. Lidas");
            System.out.println("4. Para ler depois");
            System.out.println("5. Sair");
            String op = Util.entrada("Escolha: ");

            switch (op) {
                case "1" -> buscarNoticias();
                case "2" -> mostrarLista(n -> n.isFavorita(), "Favoritos");
                case "3" -> mostrarLista(n -> n.isLida(), "Lidas");
                case "4" -> mostrarLista(n -> n.isParaLerDepois(), "Para Ler Depois");
                case "5" -> executando = false;
                default -> System.out.println("Opção inválida");
            }
        }

        Map<String, Object> salvar = new HashMap<>();
        salvar.put("usuario", usuario);
        salvar.put("noticias", noticias);
        NoticiaRepository.salvar(salvar);
        System.out.println("Dados salvos. Até logo!");
    }

    private static void buscarNoticias() {
        String palavra = Util.entrada("Digite palavra-chave para busca: ");
        try {
            noticias = IBGEApiClient.buscarNoticias(palavra);
            int i = 1;
            for (Noticia n : noticias) {
                System.out.println(i++ + ". " + n.getTitulo() + " [" + n.getData_publicacao() + "]");
            }

            String escolha = Util.entrada("Escolha o número da notícia para ver detalhes ou ENTER para voltar: ");
            if (!escolha.isBlank()) {
                int idx = Integer.parseInt(escolha) - 1;
                if (idx >= 0 && idx < noticias.size()) {
                    mostrarDetalhes(noticias.get(idx));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar notícias: " + e.getMessage());
        }
    }

    private static void mostrarDetalhes(Noticia n) {
        System.out.println("\nTítulo: " + n.getTitulo());
        System.out.println("Data: " + n.getData_publicacao());
        System.out.println("Tipo: " + n.getTipo());
        System.out.println("Introdução: " + n.getIntroducao());
        System.out.println("Link: " + n.getLink());
        System.out.println("Fonte: IBGE");

        System.out.println("\n1. Marcar como lida");
        System.out.println("2. Adicionar aos favoritos");
        System.out.println("3. Adicionar para ler depois");
        System.out.println("Outro: voltar");
        String escolha = Util.entrada("Escolha: ");
        switch (escolha) {
            case "1" -> n.setLida(true);
            case "2" -> n.setFavorita(true);
            case "3" -> n.setParaLerDepois(true);
        }
    }

    private static void mostrarLista(java.util.function.Predicate<Noticia> filtro, String titulo) {
        List<Noticia> lista = noticias.stream().filter(filtro).collect(Collectors.toList());
        if (lista.isEmpty()) {
            System.out.println(titulo + " está vazio.");
            return;
        }

        System.out.println("\n--- " + titulo + " ---");
        for (Noticia n : lista) {
            System.out.println("- " + n.getTitulo() + " (" + n.getData_publicacao() + ")");
        }
    }
}
