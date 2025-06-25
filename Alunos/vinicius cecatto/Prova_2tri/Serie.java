package prova2tri;

import java.util.List;

public class Serie {
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String status;
    private String dataEstreia;
    private String dataFim;
    private String emissora;

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

    public String getDataEstreia() { return dataEstreia; }
    public void setDataEstreia(String dataEstreia) { this.dataEstreia = dataEstreia; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public String getEmissora() { return emissora; }
    public void setEmissora(String emissora) { this.emissora = emissora; }

    @Override
    public String toString() {
        String notaStr = (nota == 0.0) ? "Não disponível" : String.format("%.1f", nota);
        String generosStr = (generos == null || generos.isEmpty()) ? "Não informado" : String.join(", ", generos);
        String fimStr = (dataFim == null || dataFim.isBlank()) ? "Ainda em exibição" : dataFim;
        String emissoraStr = (emissora == null || emissora.isBlank()) ? "Não informada" : emissora;

        return "----------------------------------------\n" +
                "Nome: " + nome + "\n" +
                "Idioma: " + idioma + "\n" +
                "Gêneros: " + generosStr + "\n" +
                "Nota: " + notaStr + "\n" +
                "Estado: " + status + "\n" +
                "Estreia: " + dataEstreia + "\n" +
                "Fim: " + fimStr + "\n" +
                "Emissora: " + emissoraStr + "\n" +
                "----------------------------------------";
    }
}
