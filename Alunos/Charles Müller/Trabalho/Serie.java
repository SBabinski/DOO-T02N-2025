import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

/**
 * Classe que representa uma série de TV
 */
public class Serie {
    private int id;
    private String nome;
    private String idioma;
    private List<String> generos;
    private double notaGeral;
    private String estado;
    private LocalDate dataEstreia;
    private LocalDate dataTermino;
    private String emissora;
    private String resumo;

    // Construtor
    public Serie(int id, String nome, String idioma, List<String> generos,
                 double notaGeral, String estado, LocalDate dataEstreia,
                 LocalDate dataTermino, String emissora, String resumo) {
        this.id = id;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.notaGeral = notaGeral;
        this.estado = estado;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = emissora;
        this.resumo = resumo;
    }

    // Construtor padrão
    public Serie() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public List<String> getGeneros() { return generos; }
    public void setGeneros(List<String> generos) { this.generos = generos; }

    public double getNotaGeral() { return notaGeral; }
    public void setNotaGeral(double notaGeral) { this.notaGeral = notaGeral; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getDataEstreia() { return dataEstreia; }
    public void setDataEstreia(LocalDate dataEstreia) { this.dataEstreia = dataEstreia; }

    public LocalDate getDataTermino() { return dataTermino; }
    public void setDataTermino(LocalDate dataTermino) { this.dataTermino = dataTermino; }

    public String getEmissora() { return emissora; }
    public void setEmissora(String emissora) { this.emissora = emissora; }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    /**
     * Converte string de data no formato da API para LocalDate
     */
    public static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Formata a data para exibição
     */
    public String formatDate(LocalDate date) {
        if (date == null) {
            return "Não informado";
        }
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Formata os gêneros para exibição
     */
    public String formatGeneros() {
        if (generos == null || generos.isEmpty()) {
            return "Não informado";
        }
        return String.join(", ", generos);
    }

    /**
     * Exibe as informações detalhadas da série
     */
    public void exibirDetalhes() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📺 " + nome);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🌍 Idioma: " + (idioma != null ? idioma : "Não informado"));
        System.out.println("🎭 Gêneros: " + formatGeneros());
        System.out.println("⭐ Nota: " + (notaGeral > 0 ? String.format("%.1f/10", notaGeral) : "Não avaliado"));
        System.out.println("📊 Estado: " + (estado != null ? estado : "Não informado"));
        System.out.println("📅 Estreia: " + formatDate(dataEstreia));
        System.out.println("📅 Término: " + formatDate(dataTermino));
        System.out.println("📺 Emissora: " + (emissora != null ? emissora : "Não informado"));

        if (resumo != null && !resumo.trim().isEmpty()) {
            System.out.println("📝 Resumo: " + resumo.replaceAll("<[^>]*>", ""));
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    /**
     * Exibe informações resumidas da série
     */
    public void exibirResumo() {
        System.out.printf("📺 %-30s | ⭐ %-4s | 📊 %-15s | 📅 %s%n",
            nome.length() > 30 ? nome.substring(0, 27) + "..." : nome,
            notaGeral > 0 ? String.format("%.1f", notaGeral) : "N/A",
            estado != null ? estado : "N/A",
            formatDate(dataEstreia)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Serie serie = (Serie) obj;
        return id == serie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nome + " (" + (dataEstreia != null ? dataEstreia.getYear() : "?") + ")";
    }
}
