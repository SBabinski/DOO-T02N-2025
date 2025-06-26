import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Main {

    static Map<String, Usuario> usuarios = new HashMap<>();
    static Usuario usuarioAtual;
    static final String ARQUIVO_USUARIOS = "usuarios.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        carregarUsuarios();

        System.out.print("\nDigite seu nome de usuário: ");
        String nomeUsuario = scanner.nextLine();

        if (!usuarios.containsKey(nomeUsuario)) {
            usuarios.put(nomeUsuario, new Usuario());
        }

        usuarioAtual = usuarios.get(nomeUsuario);
        if (usuarioAtual.favoritos == null) usuarioAtual.favoritos = new ArrayList<>();
        if (usuarioAtual.assistidas == null) usuarioAtual.assistidas = new ArrayList<>();
        if (usuarioAtual.desejoAssistir == null) usuarioAtual.desejoAssistir = new ArrayList<>();

        System.out.println("\n==== Bem Vindo " + nomeUsuario + "! ====\n");

        int op = -1;

        do {
            System.out.println("\n---Menu TvMaze---");
            System.out.println("1- Pesquisar série");
            System.out.println("2- Ver lista de favoritos");
            System.out.println("3- Ver séries assistidas");
            System.out.println("4- Ver séries que deseja assistir");
            System.out.println("5- Sair");
            System.out.print("\nOpção: ");
            String entrada = scanner.nextLine();

            try {
                op = Integer.parseInt(entrada);
                
            } catch (NumberFormatException e) {
                limparTerminal();
                System.out.println("\nEntrada inválida. Por favor, digite um número.");
                continue;

            }

            switch (op) {
                case 1:
                    System.out.print("\nDigite o nome da série: ");
                    String nomeSerie = scanner.nextLine();
                    buscarSerie(nomeSerie, scanner);
                    break;

                case 2:
                    limparTerminal();
                    verListaSeries(scanner, usuarioAtual.favoritos, "Séries Favoritas");
                    break;

                case 3:
                    limparTerminal();
                    verListaSeries(scanner, usuarioAtual.assistidas, "Séries Assistidas");
                    break;

                case 4:
                    limparTerminal();
                    verListaSeries(scanner, usuarioAtual.desejoAssistir, "Séries que Deseja Assistir");
                    break;

                case 5:
                    salvarUsuarios();
                    System.out.println("\nAté mais " + nomeUsuario + "!");
                    break;

                default:
                    limparTerminal();
                    System.out.println("\nOpção inválida. Tente novamente!");
                    break;

            }

        } while (op != 5);

        scanner.close();
    }

    public static void buscarSerie(String nome, Scanner scanner) {
        try {
            limparTerminal();

            String nomeCodificado = URLEncoder.encode(nome, "UTF-8");
            String url = "https://api.tvmaze.com/singlesearch/shows?q=" + nomeCodificado;

            HttpURLConnection conexao = (HttpURLConnection) new URL(url).openConnection();
            conexao.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            StringBuilder resposta = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                resposta.append(linha);
            }

            reader.close();

            Gson gson = new Gson();
            Serie serie = gson.fromJson(resposta.toString(), Serie.class);

            int op = -1;

            while (op != 4) {

            System.out.println();
            System.out.println("Nome: " + serie.name);
            System.out.println("Idioma: " + serie.language);
            System.out.println("Data de estreia: " + serie.premiered);
            System.out.println("Data de término: " + serie.ended);
            System.out.println("Status: " + traduzirStatus(serie.status));
            System.out.println("Nota: " + (serie.rating != null ? serie.rating.average : "Sem nota"));
            System.out.println("Emissora: " + (serie.network != null ? serie.network.name : "Sem emissora"));
            System.out.println("Gêneros: ");

            for (String generos : serie.genres) {
                System.out.println(" - " + generos);
            }
                
                System.out.println("\nVocê gostaria de adicionar esta série a alguma lista?");
                System.out.println("1. Adicionar aos Favoritos");
                System.out.println("2. Marcar como assistida");
                System.out.println("3. Adicionar à lista 'Desejo Assistir'");
                System.out.println("4. Voltar ao menu");
                System.out.print("\nDigite sua opção: ");


                  if (scanner.hasNextInt()) {
                    op = scanner.nextInt();
                    scanner.nextLine(); 
                    limparTerminal();

                    switch (op) {
                        case 1:
                            usuarioAtual.favoritos.add(serie);
                            System.out.println("\nSérie adicionada aos favoritos!");
                            break;

                        case 2:
                            usuarioAtual.assistidas.add(serie);
                            System.out.println("\nSérie adicionada aos assistidos!");
                            break;

                        case 3:
                            usuarioAtual.desejoAssistir.add(serie);
                            System.out.println("\nSérie adicionada à lista de desejo assistir!");
                            break;

                        case 4:
                            break;

                        default:
                            System.out.println("\nOpção inválida. Tente novamente!");
                            break;

                    } 

                } else {
                        scanner.nextLine();
                        limparTerminal();
                        System.out.println("\nEntrada inválida. Digite um número de 1 a 4.");
                    }
            }

        } catch (Exception e) {
            limparTerminal(); 
            System.out.println("\nErro ao buscar série. Verifique o nome digitado.\n");

        }
    }

    public static void limparTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void salvarUsuarios() {
        try (Writer writer = new FileWriter(ARQUIVO_USUARIOS)) {
            new Gson().toJson(usuarios, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public static void carregarUsuarios() {
        File arquivo = new File(ARQUIVO_USUARIOS);
        if (arquivo.exists()) {
            try (Reader reader = new FileReader(arquivo)) {
                usuarios = new Gson().fromJson(reader, new TypeToken<Map<String, Usuario>>(){}.getType());
                
            } catch (IOException e) {
                System.out.println("Erro ao carregar usuários: " + e.getMessage());

            }
        }
    }

    public static void verListaSeries(Scanner scanner, List<Serie> lista, String titulo) {
        int op = 0;

        if (lista == null || lista.isEmpty()) {
            System.out.println("\nNenhuma série encontrada!");
            return;
        }

        while (op != 7) {
            System.out.println("\n---" + titulo + "---");

            for (int i = 0; i < lista.size(); i++) {
                System.out.println((i + 1) + ". " + lista.get(i).name);
            }

            System.out.println("\n1. Remover série");
            System.out.println("2. Ordenar por nome (A-Z)");
            System.out.println("3. Ordenar por nota (maior para menor)");
            System.out.println("4. Ordenar por status");
            System.out.println("5. Ordenar por data de estreia (mais antiga para mais nova)");
            System.out.println("6. Ordenar por data de estreia (mais nova para mais antiga)");
            System.out.println("7. Voltar");
            System.out.print("\nOpção: ");

            if (scanner.hasNextInt()) {
                op = scanner.nextInt();
                scanner.nextLine(); 

                switch (op) {
                    case 1:
                        System.out.print("\nDigite o número da série que queira remover: ");

                        if (scanner.hasNextInt()) {
                            int id = scanner.nextInt();
                            scanner.nextLine(); 

                            if (id >= 1 && id <= lista.size()) {
                                Serie removida = lista.remove(id - 1);
                                limparTerminal();
                                System.out.println("\nSérie " + removida.name + " removida de " + titulo.toLowerCase() + ".");

                            } else {
                                limparTerminal();
                                System.out.println("Número inválido.");

                            }
                            
                        } else {
                            scanner.nextLine();
                            limparTerminal();
                            System.out.println("\nEntrada inválida. Digite um número válido.");

                        }

                        break;

                    case 2:
                        limparTerminal();
                        lista.sort(Comparator.comparing(s -> s.name.toLowerCase()));
                        System.out.println("\nOrdenado por nome (A-Z):");
                        break;

                    case 3:
                        limparTerminal();
                        lista.sort((a, b) -> {
                            Double notaA = a.rating != null && a.rating.average != null ? a.rating.average : 0.0;
                            Double notaB = b.rating != null && b.rating.average != null ? b.rating.average : 0.0;
                            return notaB.compareTo(notaA);
                        });

                        System.out.println("\n--- Ordenado por Nota (Maior > Menor) ---");

                        for (Serie s : lista) {
                            Double nota = s.rating != null && s.rating.average != null ? s.rating.average : null;
                            System.out.println(s.name + " | Nota: " + (nota != null ? nota : "Sem nota"));
                        }

                        break;

                    case 4:
                        limparTerminal();
                        lista.sort(Comparator.comparing(s -> s.status != null ? s.status.toLowerCase() : ""));
                        System.out.println("\n--- Lista por Status ---");
                        String statusAtual = "";

                        for (Serie s : lista) {
                            String statusSerie = traduzirStatus(s.status);

                            if (!statusSerie.equals(statusAtual)) {
                                statusAtual = statusSerie;
                                System.out.println("\n=== " + statusAtual + " ===");
                            }
                            
                            System.out.println(s.name);
                        }

                        break;

                    case 5:
                        limparTerminal();
                        lista.sort(Comparator.comparing(s -> s.premiered != null ? s.premiered : "9999-12-31"));
                        System.out.println("\n--- Ordenado por Data de Estreia (Mais antiga para mais nova) ---");
                        String anoAtual = "";

                        for (Serie s : lista) {
                            String data = s.premiered != null ? s.premiered : "Data Desconhecida";
                            String ano = data.length() >= 4 ? data.substring(0, 4) : "Desconhecido";

                            if (!ano.equals(anoAtual)) {
                                anoAtual = ano;
                                System.out.println("\n=== " + anoAtual + " ===");
                            }
                            System.out.println(s.name);
                        }
                        break;

                    case 6:
                        limparTerminal();
                        lista.sort((a, b) -> {
                            String dataA = a.premiered != null ? a.premiered : "0000-01-01";
                            String dataB = b.premiered != null ? b.premiered : "0000-01-01";
                            return dataB.compareTo(dataA);
                        });

                        String dataAnteriorDesc = "";
                        System.out.println("\n--- Ordenado por Data de Estreia (Mais nova para mais antiga) ---");

                        for (Serie s : lista) {
                            String data = s.premiered != null ? s.premiered : "Data desconhecida";
                            String ano = data.length() >= 4 ? data.substring(0, 4) : "Desconhecido";

                            if (!data.equals(dataAnteriorDesc)) {
                                System.out.println("\n=== " + ano + " ===");
                                dataAnteriorDesc = ano;
                            }
                            System.out.println(s.name);
                        }

                        break;

                    case 7:
                        limparTerminal();
                        System.out.println("Voltando ao menu principal...");
                        break;

                    default:
                        limparTerminal();
                        System.out.println("\nOpção inválida!");
                        break;

                }
            } else {
                scanner.nextLine();
                limparTerminal();
                System.out.println("\nEntrada inválida. Digite um número de 1 a 7.");

            }
        }
    }

    public static String traduzirStatus(String statusIngles) {
    if (statusIngles == null) return "Desconhecido";

    switch (statusIngles.toLowerCase()) {
        case "ended":
            return "Encerrada";
        case "running":
            return "Em exibição";
        case "to be determined":
            return "A ser determinado";
        case "in development":
            return "Em desenvolvimento";
        default:
            return statusIngles;
    }
}

}
