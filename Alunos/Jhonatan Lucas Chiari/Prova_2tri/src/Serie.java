import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Serie {
    private int id;
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String status;
    private LocalDate dataEstreia;
    private LocalDate dataTermino;
    private String emissora;
    private String resumo;

    // Construtores
    public Serie() {}

    public Serie(int id, String nome, String idioma, List<String> generos, 
                 double nota, String status, String dataEstreia, String dataTermino, 
                 String emissora, String resumo) {
        this.id = id;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.emissora = emissora;
        this.resumo = resumo;
        
        // Parse das datas
        this.dataEstreia = parseData(dataEstreia);
        this.dataTermino = parseData(dataTermino);
    }

    private LocalDate parseData(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(data, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public List<String> getGeneros() { return generos; }
    public void setGeneros(List<String> generos) { this.generos = generos; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDataEstreia() { return dataEstreia; }
    public void setDataEstreia(LocalDate dataEstreia) { this.dataEstreia = dataEstreia; }
    public void setDataEstreia(String dataEstreia) { this.dataEstreia = parseData(dataEstreia); }

    public LocalDate getDataTermino() { return dataTermino; }
    public void setDataTermino(LocalDate dataTermino) { this.dataTermino = dataTermino; }
    public void setDataTermino(String dataTermino) { this.dataTermino = parseData(dataTermino); }

    public String getEmissora() { return emissora; }
    public void setEmissora(String emissora) { this.emissora = emissora; }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(nome).append(" ===\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Idioma: ").append(idioma != null ? idioma : "N/A").append("\n");
        sb.append("Gêneros: ").append(generos != null ? String.join(", ", generos) : "N/A").append("\n");
        sb.append("Nota: ").append(nota > 0 ? nota : "N/A").append("\n");
        sb.append("Status: ").append(status != null ? status : "N/A").append("\n");
        sb.append("Data de Estreia: ").append(dataEstreia != null ? dataEstreia.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A").append("\n");
        sb.append("Data de Término: ").append(dataTermino != null ? dataTermino.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A").append("\n");
        sb.append("Emissora: ").append(emissora != null ? emissora : "N/A").append("\n");
        
        return sb.toString();
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
        return Integer.hashCode(id);
    }
}