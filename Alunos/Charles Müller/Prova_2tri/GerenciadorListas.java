import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Classe responsável pelo gerenciamento das listas de séries
 */
public class GerenciadorListas {

    /**
     * Enum para tipos de ordenação
     */
    public enum TipoOrdenacao {
        ALFABETICA("Ordem Alfabética"),
        NOTA("Nota Geral"),
        ESTADO("Estado"),
        DATA_ESTREIA("Data de Estreia");

        private final String descricao;

        TipoOrdenacao(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Ordena uma lista de séries conforme o tipo especificado
     */
    public List<Serie> ordenarLista(List<Serie> series, TipoOrdenacao tipo) {
        if (series == null || series.isEmpty()) {
            return new ArrayList<>();
        }

        List<Serie> seriesOrdenadas = new ArrayList<>(series);

        switch (tipo) {
            case ALFABETICA:
                seriesOrdenadas.sort(Comparator.comparing(Serie::getNome,
                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
                break;

            case NOTA:
                seriesOrdenadas.sort(Comparator.comparingDouble(Serie::getNotaGeral)
                    .reversed());
                break;

            case ESTADO:
                seriesOrdenadas.sort(Comparator.comparing(Serie::getEstado,
                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
                break;

            case DATA_ESTREIA:
                seriesOrdenadas.sort(Comparator.comparing(Serie::getDataEstreia,
                    Comparator.nullsLast(Comparator.naturalOrder())));
                break;

            default:
                // Mantém ordem original
                break;
        }

        return seriesOrdenadas;
    }

    /**
     * Exibe uma lista de séries formatada
     */
    public void exibirLista(List<Serie> series, String tituloLista, TipoOrdenacao ordenacao) {
        if (series == null || series.isEmpty()) {
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("📋 " + tituloLista);
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("📭 Lista vazia");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return;
        }

        List<Serie> seriesOrdenadas = ordenarLista(series, ordenacao);

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📋 " + tituloLista + " (ordenado por " + ordenacao.getDescricao() + ")");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Total: " + seriesOrdenadas.size() + " séries");
        System.out.println();

        // Cabeçalho da tabela
        System.out.printf("%-4s %-30s | %-6s | %-15s | %-12s%n",
            "#", "Nome", "Nota", "Estado", "Estreia");
        System.out.println("─────┼" + "─".repeat(30) + "┼" + "─".repeat(8) + "┼" +
                          "─".repeat(17) + "┼" + "─".repeat(12));

        // Lista as séries
        for (int i = 0; i < seriesOrdenadas.size(); i++) {
            Serie serie = seriesOrdenadas.get(i);
            System.out.printf("%-4d ", (i + 1));
            serie.exibirResumo();
        }

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    /**
     * Exibe menu de ordenação e retorna a opção escolhida
     */
    public TipoOrdenacao escolherOrdenacao() {
        System.out.println("\n🔧 Como deseja ordenar a lista?");
        System.out.println("1️⃣  Ordem Alfabética");
        System.out.println("2️⃣  Nota Geral (maior para menor)");
        System.out.println("3️⃣  Estado da Série");
        System.out.println("4️⃣  Data de Estreia");
        System.out.print("\nEscolha uma opção (1-4): ");

        return TipoOrdenacao.ALFABETICA; // Padrão será sobrescrito pela interface
    }

    /**
     * Converte número da opção para tipo de ordenação
     */
    public TipoOrdenacao obterTipoOrdenacao(int opcao) {
        switch (opcao) {
            case 1: return TipoOrdenacao.ALFABETICA;
            case 2: return TipoOrdenacao.NOTA;
            case 3: return TipoOrdenacao.ESTADO;
            case 4: return TipoOrdenacao.DATA_ESTREIA;
            default: return TipoOrdenacao.ALFABETICA;
        }
    }

    /**
     * Busca uma série em uma lista pelo índice (1-based)
     */
    public Serie obterSeriePorIndice(List<Serie> series, int indice, TipoOrdenacao ordenacao) {
        if (series == null || series.isEmpty() || indice < 1) {
            return null;
        }

        List<Serie> seriesOrdenadas = ordenarLista(series, ordenacao);

        if (indice > seriesOrdenadas.size()) {
            return null;
        }

        return seriesOrdenadas.get(indice - 1);
    }

    /**
     * Exibe estatísticas de uma lista
     */
    public void exibirEstatisticas(List<Serie> series, String nomeLista) {
        if (series == null || series.isEmpty()) {
            System.out.println("📊 " + nomeLista + ": 0 séries");
            return;
        }

        // Estatísticas básicas
        int total = series.size();
        double notaMedia = series.stream()
            .mapToDouble(Serie::getNotaGeral)
            .filter(nota -> nota > 0)
            .average()
            .orElse(0.0);

        // Contagem por estado
        long concluidas = series.stream()
            .filter(s -> s.getEstado() != null && s.getEstado().equalsIgnoreCase("Ended"))
            .count();

        long emAndamento = series.stream()
            .filter(s -> s.getEstado() != null && s.getEstado().equalsIgnoreCase("Running"))
            .count();

        long canceladas = series.stream()
            .filter(s -> s.getEstado() != null && s.getEstado().equalsIgnoreCase("Cancelled"))
            .count();

        System.out.println("📊 Estatísticas - " + nomeLista + ":");
        System.out.println("   Total: " + total + " séries");
        System.out.println("   Nota média: " + (notaMedia > 0 ? String.format("%.1f/10", notaMedia) : "N/A"));
        System.out.println("   Concluídas: " + concluidas);
        System.out.println("   Em andamento: " + emAndamento);
        System.out.println("   Canceladas: " + canceladas);
    }
}
