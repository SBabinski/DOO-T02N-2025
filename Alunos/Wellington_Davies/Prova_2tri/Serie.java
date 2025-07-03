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
    private String imagemUrl;
    
    // Construtor padrão
    public Serie(String nome, String idioma, String genero, double nota, String estado, 
                 LocalDate dataDeLancamento, LocalDate dataDeTermino, String emissora, String imagemUrl) {
        this.nome = nome;
        this.idioma = idioma;
        this.genero = genero;
        this.nota = nota;
        this.estado = estado;
        this.dataDeLancamento = dataDeLancamento;
        this.dataDeTermino = dataDeTermino;
        this.emissora = emissora;
        this.imagemUrl = imagemUrl;  
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

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public void apresentarse() {
        System.out.println("=== Informações da Série ===");
        System.out.println("Nome: " + this.nome);
        System.out.println("Idioma: " + this.idioma);
        System.out.println("Gênero: " + this.genero);
        System.out.println("Nota: " + this.nota);
        System.out.println("Estado: " + this.estado);
        System.out.println("Data de Lançamento: " + (this.dataDeLancamento != null ? this.dataDeLancamento : "Não disponível"));
        System.out.println("Data de Término: " + (this.dataDeTermino != null ? this.dataDeTermino : "Não disponível"));
        System.out.println("Emissora: " + this.emissora);
        //System.out.println("Imagem: " + this.imagemUrl);
        System.out.println("============================");
    }

    @Override
    public String toString() {
        return String.format("Serie{nome='%s', idioma='%s', genero='%s', nota=%.1f, estado='%s', emissora='%s'}", 
                           nome, idioma, genero, nota, estado, emissora);
    }
}