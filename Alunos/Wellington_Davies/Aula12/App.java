import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private JTextField campoCidade;
    private JTextArea areaResultado;
    private JButton botaoBuscar;

    private static final Color COR_FUNDO = new Color(210, 230, 255);
    private static final Color COR_PAINEL = new Color(235, 245, 255);
    private static final Color COR_BOTAO = new Color(100, 149, 237);
    private static final Color COR_BOTAO_HOVER = new Color(65, 105, 225);
    private static final Color COR_TEXTO = new Color(30, 30, 60);

    public App() {
        setTitle("Consulta de Clima");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 400);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(COR_FUNDO);

        // Título
        JLabel titulo = new JLabel("☁️ Consulta de Clima", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(40, 80, 160));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel superior para cidade e botão centralizado
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        painelTopo.setBackground(COR_FUNDO);

        JLabel labelCidade = new JLabel("Cidade:");
        labelCidade.setFont(new Font("Segoe UI", Font.BOLD, 15));
        labelCidade.setForeground(COR_TEXTO);

        campoCidade = new JTextField(12); 
        campoCidade.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        campoCidade.setBackground(Color.white);
        campoCidade.setForeground(COR_TEXTO);
        campoCidade.setPreferredSize(new Dimension(120, 28)); 

        botaoBuscar = new JButton("Buscar Clima");
        botaoBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoBuscar.setBackground(COR_BOTAO);
        botaoBuscar.setForeground(Color.white);
        botaoBuscar.setFocusPainted(false);
        botaoBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoBuscar.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        botaoBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botaoBuscar.setBackground(COR_BOTAO_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botaoBuscar.setBackground(COR_BOTAO);
            }
        });

        painelTopo.add(labelCidade);
        painelTopo.add(campoCidade);
        painelTopo.add(botaoBuscar);

        painelPrincipal.add(painelTopo, BorderLayout.CENTER);

        // Área de resultado centralizada e expandida
        areaResultado = new JTextArea(8, 30);
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        areaResultado.setBackground(COR_PAINEL);
        areaResultado.setForeground(COR_TEXTO);
        areaResultado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 200, 230), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        painelPrincipal.add(scroll, BorderLayout.SOUTH);

        botaoBuscar.addActionListener(e -> buscarClima());

        setContentPane(painelPrincipal);
        setVisible(true);
    }

    private void buscarClima() {
        String cidade = campoCidade.getText().trim();
        if (cidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome da cidade.");
            return;
        }
        try {
            String url = UrlBuilder.montarUrl(cidade);
            String respostaJson = HttpService.fazerRequisicao(url);
            Clima clima = JsonParserService.parseJson(respostaJson);

            StringBuilder sb = new StringBuilder();
            sb.append("Cidade: ").append(cidade).append("\n");
            sb.append("Temperatura atual: ").append(clima.getTemperaturaAtual()).append("°C\n");
            sb.append("Temperatura máxima: ").append(clima.getTempMax()).append("°C\n");
            sb.append("Temperatura mínima: ").append(clima.getTempMin()).append("°C\n");
            sb.append("Umidade: ").append(clima.getUmidade()).append("%\n");
            sb.append("Condição: ").append(clima.getCondicao()).append("\n");
            sb.append("Precipitação: ").append(clima.getPrecipitacao()).append(" mm\n");
            sb.append("Vento: ").append(clima.getVelocidadeVento()).append(" km/h\n");
            sb.append("Direção do vento: ").append(clima.getDirecaoVento()).append("°\n");

            areaResultado.setText(sb.toString());
        } catch (Exception ex) {
            areaResultado.setText("Erro ao consultar o clima:\n" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
