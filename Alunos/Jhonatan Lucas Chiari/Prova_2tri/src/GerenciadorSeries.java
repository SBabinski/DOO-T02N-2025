import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GerenciadorSeries {
    private Scanner scanner;
    private Usuario usuario;
    private TVMazeAPI tvMazeAPI; // declaração da API
    private Persistencia persistencia;

    public GerenciadorSeries(Usuario usuario) {
        this.scanner = new Scanner(System.in);
        this.usuario = usuario;
        this.tvMazeAPI = new TVMazeAPI(); // inicialização da API
        this.persistencia = new Persistencia();
    }

    public void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = obterOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);

        // Salvar dados ao sair
        persistencia.salvarUsuario(usuario);
        System.out.println("\nDados salvos! Até mais!");
    }

    private void exibirMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("            SISTEMA DE SÉRIES TV");
        System.out.println("=".repeat(50));
        System.out.printf("Usuário: %s\n", usuario.getNome());
        System.out.printf("Favoritos: %d | Assistidas: %d | Desejo assistir: %d\n",
                usuario.getFavoritos().size(),
                usuario.getAssistidas().size(),
                usuario.getDesejoAssistir().size());
        System.out.println("-".repeat(50));
        System.out.println("1. Buscar séries");
        System.out.println("2. Ver favoritos");
        System.out.println("3. Ver assistidas");
        System.out.println("4. Ver desejo assistir");
        System.out.println("0. Sair");
        System.out.println("-".repeat(50));
        System.out.print("Escolha uma opção: ");
    }

    private int obterOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                buscarSeries();
                break;
            case 2:
                verListaComOrdenacao("favoritos", usuario.getFavoritos());
                break;
            case 3:
                verListaComOrdenacao("assistidas", usuario.getAssistidas());
                break;
            case 4:
                verListaComOrdenacao("desejo assistir", usuario.getDesejoAssistir());
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void buscarSeries() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println(" BUSCA DE SÉRIES");
        System.out.println("=".repeat(30));
        System.out.print("Digite o nome da série: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("Nome não pode estar vazio!");
            return;
        }

        System.out.println("\n Buscando séries na API TVMaze...");

        List<Serie> series = tvMazeAPI.buscarSeries(nome); // Chamada para a API

        if (series.isEmpty()) {
            System.out.println("\nNenhuma série encontrada com esse nome.");
            return;
        }

        System.out.printf("\n%d série(s) encontrada(s)\n", series.size());
        exibirSeries(series, true);

        System.out.print("\n Escolha uma série (0 para voltar): ");
        try {
            int escolha = Integer.parseInt(scanner.nextLine().trim());
            if (escolha > 0 && escolha <= series.size()) {
                Serie serieSelecionada = series.get(escolha - 1);
                exibirDetalhesSerie(serieSelecionada);
                oferecerAcoes(serieSelecionada);
            }
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida!");
        }
    }

    // método verListaComOrdenacao - parte do menu de ordenação:
    private void verListaComOrdenacao(String nomeLista, List<Serie> lista) {
        if (lista.isEmpty()) {
            System.out.printf("\nSua lista de %s está vazia.\n", nomeLista);
            return;
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("           LISTA: %s (%d séries)\n", nomeLista.toUpperCase(), lista.size());
        System.out.println("=".repeat(50));

        // Menu de ordenação
        System.out.println("Como deseja ordenar a lista?");
        System.out.println("1. Ordem original");
        System.out.println("2. Ordem alfabética (A-Z)");
        System.out.println("3. Por nota (maior para menor)");
        System.out.println("4. Por status");
        System.out.println("5. Por data de estreia (mais antiga para mais recente)"); // ← ALTERADO
        System.out.print("Escolha uma opção: ");

        try {
            int opcaoOrdenacao = Integer.parseInt(scanner.nextLine().trim());
            List<Serie> listaOrdenada = ordenarLista(lista, opcaoOrdenacao);
            exibirSeries(listaOrdenada, false);

            // Opções de gerenciamento da lista
            if (!listaOrdenada.isEmpty()) {
                System.out.print("\nEscolha uma série para gerenciar (0 para voltar): ");
                int escolha = Integer.parseInt(scanner.nextLine().trim());
                if (escolha > 0 && escolha <= listaOrdenada.size()) {
                    Serie serieSelecionada = listaOrdenada.get(escolha - 1);
                    exibirDetalhesSerie(serieSelecionada);
                    oferecerAcoes(serieSelecionada);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida!");
        }
    }

    private List<Serie> ordenarLista(List<Serie> lista, int opcao) {
        List<Serie> listaOrdenada = new ArrayList<>(lista);

        switch (opcao) {
            case 1: // Ordem original
                return listaOrdenada;

            case 2: // Ordem alfabética
                listaOrdenada.sort((s1, s2) -> {
                    String nome1 = s1.getNome() != null ? s1.getNome() : "";
                    String nome2 = s2.getNome() != null ? s2.getNome() : "";
                    return nome1.compareToIgnoreCase(nome2);
                });
                System.out.println("Lista ordenada por ordem alfabética:");
                break;

            case 3: // Por nota (maior para menor)
                listaOrdenada.sort((s1, s2) -> Double.compare(s2.getNota(), s1.getNota()));
                System.out.println("Lista ordenada por nota (maior para menor):");
                break;

            case 4: // Por status
                listaOrdenada.sort((s1, s2) -> {
                    String status1 = s1.getStatus() != null ? s1.getStatus() : "";
                    String status2 = s2.getStatus() != null ? s2.getStatus() : "";
                    return getStatusPrioridade(status1) - getStatusPrioridade(status2);
                });
                System.out.println("Lista ordenada por status:");
                break;

            case 5: // Por data de estreia - ALTERADO MAIS ANTIGA PARA MAIS RECENTE
                listaOrdenada.sort((s1, s2) -> {
                    LocalDate data1 = s1.getDataEstreia();
                    LocalDate data2 = s2.getDataEstreia();

                    // Tratar casos null - séries sem data vão para o final
                    if (data1 == null && data2 == null)
                        return 0;
                    if (data1 == null)
                        return 1; // s1 sem data vai para o final
                    if (data2 == null)
                        return -1; // s2 sem data vai para o final

                    // MAIS ANTIGA PRIMEIRO (data1 compare data2)
                    return data1.compareTo(data2);
                });
                System.out.println("Lista ordenada por data de estreia (mais antiga para mais recente):");
                break;

            default:
                System.out.println("Opção inválida! Exibindo lista na ordem original:");
        }

        return listaOrdenada;
    }

    private int getStatusPrioridade(String status) {
        switch (status.toLowerCase()) {
            case "running":
                return 1;
            case "ended":
                return 2;
            case "to be determined":
                return 3;
            default:
                return 4;
        }
    }

    private void exibirSeries(List<Serie> series, boolean numerada) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println(" SÉRIES ENCONTRADAS");
        System.out.println("=".repeat(80));

        for (int i = 0; i < series.size(); i++) {
            Serie serie = series.get(i);
            if (numerada) {
                System.out.printf("%d. ", (i + 1));
            }
            System.out.printf("TÍTULO: %s\n", serie.getNome());

            // Gêneros traduzidos
            System.out.printf("   GÊNEROS: %s\n", traduzirGeneros(serie.getGeneros()));

            // Nota, Status e Emissora traduzidos
            System.out.printf("   NOTA: %.1f | STATUS: %s | EMISSORA: %s\n",
                    serie.getNota(),
                    traduzirStatus(serie.getStatus()),
                    serie.getEmissora() != null ? serie.getEmissora() : "N/A");

            // Resumo (limitado)
            if (serie.getResumo() != null && !serie.getResumo().isEmpty()) {
                String resumo = serie.getResumo();
                if (resumo.length() > 100) {
                    resumo = resumo.substring(0, 100) + "...";
                }
                System.out.printf("   RESUMO: %s\n", resumo);
            }

            System.out.println("-".repeat(80));
        }
    }

    private void exibirDetalhesSerie(Serie serie) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("DETALHES DA SÉRIE SELECIONADA");
        System.out.println("=".repeat(80));
        System.out.printf("TÍTULO: %s\n\n", serie.getNome());

        System.out.printf("GÊNEROS: %s\n\n", traduzirGeneros(serie.getGeneros()));

        System.out.printf("NOTA: %.1f\n\n", serie.getNota());
        System.out.printf("STATUS: %s\n\n", traduzirStatus(serie.getStatus()));
        System.out.printf("EMISSORA: %s\n\n", serie.getEmissora() != null ? serie.getEmissora() : "N/A");
        System.out.printf("IDIOMA: %s\n\n", traduzirIdioma(serie.getIdioma()));

        // FORMATO DE DATA BRASILEIRO (dd/mm/aaaa)
        DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (serie.getDataEstreia() != null) {
            System.out.printf("ESTREIA: %s\n\n", serie.getDataEstreia().format(formatoBrasileiro));
        } else {
            System.out.println("ESTREIA: N/A\n");
        }

        if (serie.getDataTermino() != null) {
            System.out.printf("TÉRMINO: %s\n\n", serie.getDataTermino().format(formatoBrasileiro));
        } else {
            System.out.println("TÉRMINO: N/A\n");
        }

        if (serie.getResumo() != null) {
            System.out.printf("RESUMO: %s\n", serie.getResumo());
        }

        System.out.println("\n" + "=".repeat(80));
    }

    private void oferecerAcoes(Serie serie) {
        System.out.println("\n AÇÕES DISPONÍVEIS:");
        System.out.println("1. Adicionar aos favoritos");
        System.out.println("2. Marcar como assistida");
        System.out.println("3. Adicionar ao desejo assistir");
        System.out.println("4. Remover dos favoritos");
        System.out.println("5. Remover das assistidas");
        System.out.println("6. Remover do desejo assistir");
        System.out.println("Outro: voltar");
        System.out.print("\nEscolha uma ação: ");

        try {
            int acao = Integer.parseInt(scanner.nextLine().trim());
            processarAcao(serie, acao);
        } catch (NumberFormatException e) {
            System.out.println("Voltando ao menu...");
        }
    }

    private void processarAcao(Serie serie, int acao) {
        switch (acao) {
            case 1:
                usuario.adicionarFavorito(serie);
                System.out.println(" Série adicionada aos favoritos!");
                break;
            case 2:
                usuario.adicionarAssistida(serie);
                System.out.println(" Série marcada como assistida!");
                break;
            case 3:
                usuario.adicionarDesejoAssistir(serie);
                System.out.println(" Série adicionada ao desejo assistir!");
                break;
            case 4:
                if (usuario.removerFavorito(serie)) {
                    System.out.println(" Série removida dos favoritos!");
                } else {
                    System.out.println(" Série não estava nos favoritos!");
                }
                break;
            case 5:
                if (usuario.removerAssistida(serie)) {
                    System.out.println(" Série removida das assistidas!");
                } else {
                    System.out.println(" Série não estava nas assistidas!");
                }
                break;
            case 6:
                if (usuario.removerDesejoAssistir(serie)) {
                    System.out.println(" Série removida do desejo assistir!");
                } else {
                    System.out.println(" Série não estava no desejo assistir!");
                }
                break;
            default:
                System.out.println("Voltando ao menu...");
        }
    }

    // MÉTODOS DE TRADUÇÃO
    private String traduzirStatus(String status) {
        if (status == null)
            return "N/A";
        switch (status.toLowerCase()) {
            case "ended":
                return "Finalizada";
            case "running":
                return "Em exibição";
            case "to be determined":
                return "A ser determinado";
            case "in development":
                return "Em desenvolvimento";
            case "canceled":
                return "Cancelada";
            default:
                return status;
        }
    }

    private String traduzirIdioma(String idioma) {
        if (idioma == null)
            return "N/A";
        switch (idioma.toLowerCase()) {
            case "english":
                return "Inglês";
            case "spanish":
                return "Espanhol";
            case "french":
                return "Francês";
            case "german":
                return "Alemão";
            case "italian":
                return "Italiano";
            case "portuguese":
                return "Português";
            case "japanese":
                return "Japonês";
            case "korean":
                return "Coreano";
            case "chinese":
                return "Chinês";
            default:
                return idioma;
        }
    }

    private String traduzirGeneros(List<String> generos) {
        if (generos == null || generos.isEmpty())
            return "N/A";

        List<String> generosTrauzidos = new ArrayList<>();
        for (String genero : generos) {
            switch (genero.toLowerCase()) {
                case "drama":
                    generosTrauzidos.add("Drama");
                    break;
                case "comedy":
                    generosTrauzidos.add("Comédia");
                    break;
                case "action":
                    generosTrauzidos.add("Ação");
                    break;
                case "adventure":
                    generosTrauzidos.add("Aventura");
                    break;
                case "supernatural":
                    generosTrauzidos.add("Sobrenatural");
                    break;
                case "thriller":
                    generosTrauzidos.add("Suspense");
                    break;
                case "horror":
                    generosTrauzidos.add("Terror");
                    break;
                case "romance":
                    generosTrauzidos.add("Romance");
                    break;
                case "science-fiction":
                    generosTrauzidos.add("Ficção Científica");
                    break;
                case "fantasy":
                    generosTrauzidos.add("Fantasia");
                    break;
                case "crime":
                    generosTrauzidos.add("Crime");
                    break;
                case "mystery":
                    generosTrauzidos.add("Mistério");
                    break;
                case "family":
                    generosTrauzidos.add("Família");
                    break;
                case "children":
                    generosTrauzidos.add("Infantil");
                    break;
                case "animation":
                    generosTrauzidos.add("Animação");
                    break;
                case "documentary":
                    generosTrauzidos.add("Documentário");
                    break;
                case "music":
                    generosTrauzidos.add("Musical");
                    break;
                case "war":
                    generosTrauzidos.add("Guerra");
                    break;
                case "western":
                    generosTrauzidos.add("Faroeste");
                    break;
                case "history":
                    generosTrauzidos.add("História");
                    break;
                default:
                    generosTrauzidos.add(genero);
                    break;
            }
        }
        return String.join(", ", generosTrauzidos);
    }
}