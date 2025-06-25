import javax.swing.*;

public class SerieApp {
    // usuário atual
    private Usuario usuario;
    // gerenciador das listas de séries do usuário
    private SerieManager serieManager;

    // inicia a aplicação
    public void inicializar() {
        // tenta carregar os dados do usuário salvos anteriormente
        usuario = Json.carregarUsuario();

        if (usuario == null) {
            // se não encontrou usuário salvo, pede o nome ao usuário
            String nome = JOptionPane.showInputDialog(null, "Bem-vindo! Qual seu nome ou apelido?");
            usuario = new Usuario(nome);

            // salva o novo usuário criado
            Json.salvarUsuario(usuario);
        }
        // Tenta carregar as séries salvas anteriormente
        serieManager = Json.carregarSeries();
        if (serieManager == null) {
            // se não tiver séries salvas cria uma nova lista vazia
            serieManager = new SerieManager();
        } else {
            // remove duplicatas das listas usando distinct
            serieManager.setFavoritos(
                serieManager.getFavoritos().stream().distinct().toList()
            );
            serieManager.setAssistidas(
                serieManager.getAssistidas().stream().distinct().toList()
            );
            serieManager.setDesejo(
                serieManager.getDesejo().stream().distinct().toList()
            );
        }
        // abre a janela principal da aplicação, passando usuário e séries
        new JanelaPrincipal(usuario, serieManager).setVisible(true);
    }
}
