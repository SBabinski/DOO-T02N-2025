import java.time.LocalDate;

public class Serie {
    private String nome;
    private String idioma;
    private String genero;
    private double nota;
    private String estado;
    private LocalDate dataDeLancamento;
    private LocalDate dataDeTermino;
    private String emissora;

    public Serie(String nome, String idioma, String genero, double nota, String estado,
                 LocalDate dataDeLancamento, LocalDate dataDeTermino, String emissora) {
        this.nome = nome;
        this.idioma = idioma;
        this.genero = genero;
        this.nota = nota;
        this.estado = estado;
        this.dataDeLancamento = dataDeLancamento;
        this.dataDeTermino = dataDeTermino;
        this.emissora = emissora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getDataDeLancamento() {
        return dataDeLancamento;
    }

    public void setDataDeLancamento(LocalDate dataDeLancamento) {
        this.dataDeLancamento = dataDeLancamento;
    }

    public LocalDate getDataDeTermino() {
        return dataDeTermino;
    }

    public void setDataDeTermino(LocalDate dataDeTermino) {
        this.dataDeTermino = dataDeTermino;
    }

    public String getEmissora() {
        return emissora;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }
}