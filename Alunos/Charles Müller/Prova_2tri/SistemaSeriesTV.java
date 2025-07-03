import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal do sistema de acompanhamento de séries
 */
public class SistemaSeriesTV {
    private Usuario usuario;
    private TVMazeAPI api;
    private PersistenciaJSON persistencia;
    private GerenciadorListas gerenciador;
    private Scanner scanner;

    public SistemaSeriesTV() {
        this.api = new TVMazeAPI();
        this.persistencia = new PersistenciaJSON();
        this.gerenciador = new GerenciadorListas();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Método principal para iniciar o sistema
     */
    public static void main(String[] args) {
        SistemaSeriesTV sistema = new SistemaSeriesTV();
        sistema.iniciar();
    }

    /**
     * Inicia o sistema
     */
    public void iniciar() {
        try {
            exibirBanner();
            carregarDados();

            if (usuario == null) {
                configurarUsuario();
            } else {
                System.out.println("🎉 Bem-vindo de volta, " + usuario.getNome() + "!");
                usuario.exibirEstatisticas();
            }

            menuPrincipal();

        } catch (Exception e) {
            System.err.println("❌ Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    /**
     * Exibe o banner do sistema
     */
    private void exibirBanner() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    📺 SISTEMA DE SÉRIES TV 📺                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║           Gerencie suas séries favoritas com estilo!        ║");
        System.out.println("║                     Powered by TVMaze API                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Carrega os dados salvos
     */
    private void carregarDados() {
        try {
            if (persistencia.existeDados()) {
                usuario = persistencia.carregarUsuario();
                System.out.println("📂 Dados carregados com sucesso!");
            }
        } catch (IOException e) {
            System.err.println("⚠️ Erro ao carregar dados: " + e.getMessage());
            System.out.println("Iniciando com dados em branco...");
        }
    }

    /**
     * Configura um novo usuário
     */
    private void configurarUsuario() {
        System.out.println("👋 Bem-vindo ao Sistema de Séries TV!");
        System.out.print("Digite seu nome ou apelido: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            nome = "Usuário";
        }

        usuario = new Usuario(nome);
        salvarDados();

        System.out.println("✅ Usuário " + nome + " criado com sucesso!");
    }

    /**
     * Salva os dados do usuário
     */
    private void salvarDados() {
        try {
            persistencia.salvarUsuario(usuario);
        } catch (IOException e) {
            System.err.println("⚠️ Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Menu principal do sistema
     */
    private void menuPrincipal() {
        while (true) {
            try {
                exibirMenuPrincipal();
                int opcao = lerOpcaoInt();

                switch (opcao) {
                    case 1:
                        buscarSeries();
                        break;
                    case 2:
                        gerenciarListas();
                        break;
                    case 3:
                        exibirListas();
                        break;
                    case 4:
                        configurarUsuario();
                        break;
                    case 5:
                        usuario.exibirEstatisticas();
                        break;
                    case 0:
                        encerrarSistema();
                        return;
                    default:
                        System.out.println("❌ Opção inválida! Tente novamente.");
                }

                pausar();

            } catch (Exception e) {
                System.err.println("❌ Erro: " + e.getMessage());
                pausar();
            }
        }
    }

    /**
     * Exibe o menu principal
     */
    private void exibirMenuPrincipal() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("🏠 MENU PRINCIPAL - Usuário: " + usuario.getNome());
        System.out.println("═".repeat(60));
        System.out.println("1️⃣  🔍 Buscar Séries");
        System.out.println("2️⃣  📝 Gerenciar Listas");
        System.out.println("3️⃣  📋 Exibir Listas");
        System.out.println("4️⃣  👤 Alterar Usuário");
        System.out.println("5️⃣  📊 Estatísticas");
        System.out.println("0️⃣  🚪 Sair");
        System.out.println("═".repeat(60));
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Busca séries na API
     */
    private void buscarSeries() {
        System.out.println("\n🔍 BUSCAR SÉRIES");
        System.out.println("─".repeat(40));
        System.out.print("Digite o nome da série: ");
        String nomeSerie = scanner.nextLine().trim();

        if (nomeSerie.isEmpty()) {
            System.out.println("❌ Nome não pode estar vazio!");
            return;
        }

        try {
            System.out.println("🔄 Buscando séries...");
            List<Serie> resultados = api.buscarSeries(nomeSerie);

            if (resultados.isEmpty()) {
                System.out.println("😞 Nenhuma série encontrada com o nome: " + nomeSerie);
                return;
            }

            exibirResultadosBusca(resultados);

        } catch (APIException e) {
            System.err.println("❌ Erro na busca: " + e.getMessage());
        }
    }

    /**
     * Exibe os resultados da busca
     */
    private void exibirResultadosBusca(List<Serie> series) {
        System.out.println("\n📺 RESULTADOS DA BUSCA");
        System.out.println("─".repeat(50));
        System.out.println("Encontradas " + series.size() + " séries:");
        System.out.println();

        // Cabeçalho da tabela
        System.out.printf("%-4s %-30s | %-6s | %-15s | %-12s%n",
            "#", "Nome", "Nota", "Estado", "Estreia");
        System.out.println("─────┼" + "─".repeat(30) + "┼" + "─".repeat(8) + "┼" +
                          "─".repeat(17) + "┼" + "─".repeat(12));

        for (int i = 0; i < series.size(); i++) {
            System.out.printf("%-4d ", (i + 1));
            series.get(i).exibirResumo();
        }

        System.out.println("\n🔧 Opções:");
        System.out.println("Digite o número da série para ver detalhes e gerenciar");
        System.out.println("Digite 0 para voltar ao menu principal");
        System.out.print("Escolha: ");

        int escolha = lerOpcaoInt();

        if (escolha > 0 && escolha <= series.size()) {
            Serie serieSelecionada = series.get(escolha - 1);
            exibirDetalhesSerie(serieSelecionada);
        }
    }

    /**
     * Exibe detalhes de uma série e opções de gerenciamento
     */
    private void exibirDetalhesSerie(Serie serie) {
        System.out.println("\n");
        serie.exibirDetalhes();

        System.out.println("\n🔧 GERENCIAR SÉRIE");
        System.out.println("─".repeat(30));
        System.out.println("1️⃣  ⭐ Adicionar/Remover dos Favoritos");
        System.out.println("2️⃣  ✅ Adicionar/Remover das Assistidas");
        System.out.println("3️⃣  📝 Adicionar/Remover da Lista 'Para Assistir'");
        System.out.println("0️⃣  🔙 Voltar");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcaoInt();

        switch (opcao) {
            case 1:
                gerenciarFavoritos(serie);
                break;
            case 2:
                gerenciarAssistidas(serie);
                break;
            case 3:
                gerenciarParaAssistir(serie);
                break;
        }
    }

    /**
     * Gerencia favoritos
     */
    private void gerenciarFavoritos(Serie serie) {
        if (usuario.isFavorito(serie)) {
            System.out.print("❓ Série já está nos favoritos. Deseja remover? (s/n): ");
            if (confirmarAcao()) {
                usuario.removerFavorito(serie);
                System.out.println("💔 Série removida dos favoritos!");
                salvarDados();
            }
        } else {
            System.out.print("❓ Deseja adicionar série aos favoritos? (s/n): ");
            if (confirmarAcao()) {
                usuario.adicionarFavorito(serie);
                System.out.println("⭐ Série adicionada aos favoritos!");
                salvarDados();
            }
        }
    }

    /**
     * Gerencia assistidas
     */
    private void gerenciarAssistidas(Serie serie) {
        if (usuario.isAssistida(serie)) {
            System.out.print("❓ Série já está nas assistidas. Deseja remover? (s/n): ");
            if (confirmarAcao()) {
                usuario.removerAssistida(serie);
                System.out.println("❌ Série removida das assistidas!");
                salvarDados();
            }
        } else {
            System.out.print("❓ Deseja marcar série como assistida? (s/n): ");
            if (confirmarAcao()) {
                usuario.adicionarAssistida(serie);
                System.out.println("✅ Série marcada como assistida!");
                salvarDados();
            }
        }
    }

    /**
     * Gerencia para assistir
     */
    private void gerenciarParaAssistir(Serie serie) {
        if (usuario.isParaAssistir(serie)) {
            System.out.print("❓ Série já está na lista 'Para Assistir'. Deseja remover? (s/n): ");
            if (confirmarAcao()) {
                usuario.removerParaAssistir(serie);
                System.out.println("❌ Série removida da lista 'Para Assistir'!");
                salvarDados();
            }
        } else {
            System.out.print("❓ Deseja adicionar à lista 'Para Assistir'? (s/n): ");
            if (confirmarAcao()) {
                usuario.adicionarParaAssistir(serie);
                System.out.println("📝 Série adicionada à lista 'Para Assistir'!");
                salvarDados();
            }
        }
    }

    /**
     * Menu para gerenciar listas
     */
    private void gerenciarListas() {
        System.out.println("\n📝 GERENCIAR LISTAS");
        System.out.println("─".repeat(40));
        System.out.println("1️⃣  ⭐ Gerenciar Favoritos");
        System.out.println("2️⃣  ✅ Gerenciar Assistidas");
        System.out.println("3️⃣  📝 Gerenciar Para Assistir");
        System.out.println("0️⃣  🔙 Voltar");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcaoInt();

        switch (opcao) {
            case 1:
                gerenciarListaEspecifica(usuario.getFavoritos(), "Favoritos");
                break;
            case 2:
                gerenciarListaEspecifica(usuario.getAssistidas(), "Assistidas");
                break;
            case 3:
                gerenciarListaEspecifica(usuario.getParaAssistir(), "Para Assistir");
                break;
        }
    }

    /**
     * Gerencia uma lista específica
     */
    private void gerenciarListaEspecifica(List<Serie> lista, String nomeLista) {
        if (lista.isEmpty()) {
            System.out.println("📭 Lista '" + nomeLista + "' está vazia!");
            return;
        }

        GerenciadorListas.TipoOrdenacao ordenacao = escolherOrdenacao();
        gerenciador.exibirLista(lista, nomeLista, ordenacao);

        System.out.println("\n🔧 Opções:");
        System.out.println("Digite o número da série para ver detalhes");
        System.out.println("Digite 0 para voltar");
        System.out.print("Escolha: ");

        int escolha = lerOpcaoInt();

        if (escolha > 0) {
            Serie serie = gerenciador.obterSeriePorIndice(lista, escolha, ordenacao);
            if (serie != null) {
                exibirDetalhesSerie(serie);
            } else {
                System.out.println("❌ Número inválido!");
            }
        }
    }

    /**
     * Menu para exibir listas
     */
    private void exibirListas() {
        System.out.println("\n📋 EXIBIR LISTAS");
        System.out.println("─".repeat(40));
        System.out.println("1️⃣  ⭐ Favoritos");
        System.out.println("2️⃣  ✅ Assistidas");
        System.out.println("3️⃣  📝 Para Assistir");
        System.out.println("4️⃣  📊 Todas as Estatísticas");
        System.out.println("0️⃣  🔙 Voltar");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcaoInt();

        switch (opcao) {
            case 1:
                exibirListaComOrdenacao(usuario.getFavoritos(), "Favoritos");
                break;
            case 2:
                exibirListaComOrdenacao(usuario.getAssistidas(), "Assistidas");
                break;
            case 3:
                exibirListaComOrdenacao(usuario.getParaAssistir(), "Para Assistir");
                break;
            case 4:
                exibirTodasEstatisticas();
                break;
        }
    }

    /**
     * Exibe uma lista com opção de ordenação
     */
    private void exibirListaComOrdenacao(List<Serie> lista, String nomeLista) {
        GerenciadorListas.TipoOrdenacao ordenacao = escolherOrdenacao();
        gerenciador.exibirLista(lista, nomeLista, ordenacao);
        gerenciador.exibirEstatisticas(lista, nomeLista);
    }

    /**
     * Exibe todas as estatísticas
     */
    private void exibirTodasEstatisticas() {
        System.out.println("\n📊 ESTATÍSTICAS COMPLETAS");
        System.out.println("═".repeat(50));

        usuario.exibirEstatisticas();
        System.out.println();

        gerenciador.exibirEstatisticas(usuario.getFavoritos(), "Favoritos");
        gerenciador.exibirEstatisticas(usuario.getAssistidas(), "Assistidas");
        gerenciador.exibirEstatisticas(usuario.getParaAssistir(), "Para Assistir");
    }

    /**
     * Permite escolher tipo de ordenação
     */
    private GerenciadorListas.TipoOrdenacao escolherOrdenacao() {
        System.out.println("\n🔧 Como deseja ordenar a lista?");
        System.out.println("1️⃣  📝 Ordem Alfabética");
        System.out.println("2️⃣  ⭐ Nota Geral (maior para menor)");
        System.out.println("3️⃣  📊 Estado da Série");
        System.out.println("4️⃣  📅 Data de Estreia");
        System.out.print("Escolha (1-4): ");

        int opcao = lerOpcaoInt();
        return gerenciador.obterTipoOrdenacao(opcao);
    }

    /**
     * Confirma uma ação do usuário
     */
    private boolean confirmarAcao() {
        String resposta = scanner.nextLine().trim().toLowerCase();
        return resposta.equals("s") || resposta.equals("sim") || resposta.equals("y") || resposta.equals("yes");
    }

    /**
     * Lê uma opção inteira do usuário com tratamento de erro
     */
    private int lerOpcaoInt() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer
            return opcao;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Limpa o buffer
            System.out.println("❌ Por favor, digite um número válido!");
            return -1;
        }
    }

    /**
     * Pausa o sistema aguardando input do usuário
     */
    private void pausar() {
        System.out.println("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Encerra o sistema
     */
    private void encerrarSistema() {
        try {
            salvarDados();
            System.out.println("\n💾 Dados salvos com sucesso!");
            System.out.println("👋 Obrigado por usar o Sistema de Séries TV!");
            System.out.println("🎬 Até a próxima!");
        } catch (Exception e) {
            System.err.println("⚠️ Erro ao salvar dados: " + e.getMessage());
        }
    }
}
