
import java.time.LocalDate;

public class Noticia {
    private int id;
    private String titulo;
    private String introducao;
    private String link;
    private String tipo;
    private String data_publicacao;

    private boolean lida = false;
    private boolean favorita = false;
    private boolean paraLerDepois = false;

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getIntroducao() { return introducao; }
    public String getLink() { return link; }
    public String getTipo() { return tipo; }
    public String getData_publicacao() { return data_publicacao; }

    public boolean isLida() { return lida; }
    public boolean isFavorita() { return favorita; }
    public boolean isParaLerDepois() { return paraLerDepois; }

    public void setLida(boolean lida) { this.lida = lida; }
    public void setFavorita(boolean favorita) { this.favorita = favorita; }
    public void setParaLerDepois(boolean paraLerDepois) { this.paraLerDepois = paraLerDepois; }
}
