import javax.swing.*;
import java.awt.*;
import java.util.List;

// interface gráfica
public class JanelaPrincipal extends JFrame {
    private SerieManager serieManager; // gerenciador de séries

    // lista que exibe resultados da busca de séries
    private DefaultListModel<Serie> searchModel = new DefaultListModel<>();
    private JList<Serie> searchList = new JList<>(searchModel);

    // painel exibir detalhes da série selecionada
    private JPanel detalhesPanel = new JPanel(new BorderLayout());

    // recebe o usuário e o gerenciador de séries
    public JanelaPrincipal(Usuario usuario, SerieManager serieManager) {
        super("Séries de TV - Bem-vindo, " + usuario.getNome());
        this.serieManager = serieManager;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600); 
        setLocationRelativeTo(null); 
        initComponents(); // inicializa os componentes visuais
    }

    // inicializa todos os componentes da interface 
    private void initComponents() {
        // painel esquerdo: busca e lista séries
        JPanel painelEsquerda = new JPanel();
        painelEsquerda.setLayout(new BorderLayout());
        painelEsquerda.setPreferredSize(new Dimension(340, 600));

        // painel esquerdo: busca na parte de cima
        JPanel painelBusca = new JPanel(new BorderLayout(3, 3));
        JLabel lblBusca = new JLabel("Buscar Série:");
        JTextField campoBusca = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        painelBusca.add(lblBusca, BorderLayout.WEST);
        painelBusca.add(campoBusca, BorderLayout.CENTER);
        painelBusca.add(btnBuscar, BorderLayout.EAST);
        painelBusca.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // séries encontradas na busca
        searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollList = new JScrollPane(searchList);

        painelEsquerda.add(painelBusca, BorderLayout.NORTH);
        painelEsquerda.add(scrollList, BorderLayout.CENTER);

        // botões das listas do usuário (Favoritos, Assistidas, Desejo)
        JPanel painelTopoDireita = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        JButton btnFavoritos = new JButton("★");
        btnFavoritos.setToolTipText("Favoritos");
        btnFavoritos.addActionListener(e -> mostrarLista("Favoritos", serieManager.getFavoritos()));
        JButton btnAssistidas = new JButton("✔");
        btnAssistidas.setToolTipText("Assistidas");
        btnAssistidas.addActionListener(e -> mostrarLista("Assistidas", serieManager.getAssistidas()));
        JButton btnDesejo = new JButton("♥");
        btnDesejo.setToolTipText("Desejo");
        btnDesejo.addActionListener(e -> mostrarLista("Desejo", serieManager.getDesejo()));
        painelTopoDireita.add(btnFavoritos);
        painelTopoDireita.add(btnAssistidas);
        painelTopoDireita.add(btnDesejo);

        // painel direito: detalhes da série
        detalhesPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        // painel direito: topo= botões, centro= detalhes
        JPanel painelDireita = new JPanel(new BorderLayout());
        painelDireita.add(painelTopoDireita, BorderLayout.NORTH);
        painelDireita.add(detalhesPanel, BorderLayout.CENTER);

        // divide a tela entre o painel esquerdo e a direito
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelEsquerda, painelDireita);
        splitPane.setDividerLocation(340);
        splitPane.setResizeWeight(0);

        add(splitPane, BorderLayout.CENTER);

        // executa a busca quando o botão é clicado
        btnBuscar.addActionListener(e -> {
            String nome = campoBusca.getText().trim();
            if (nome.isEmpty())
                return;
            try {
                searchModel.clear(); // limpa resultados anteriores
                List<Serie> resultado = TvMazeApi.buscarSeriesPorNome(nome); // busca séries pela API
                for (Serie s : resultado)
                    searchModel.addElement(s); // add séries encontradas na lista
                detalhesPanel.removeAll(); // limpa detalhes
                detalhesPanel.revalidate();
                detalhesPanel.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar: " + ex.getMessage());
            }
        });

        // permite buscar pressionando Enter
        campoBusca.addActionListener(e -> btnBuscar.doClick());

        // ao clicar numa série da lista, mostra os detalhes ao lado
        searchList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Serie s = searchList.getSelectedValue();
                if (s != null) {
                    mostrarDetalhesSerieLateral(s);
                } else {
                    detalhesPanel.removeAll();
                    detalhesPanel.revalidate();
                    detalhesPanel.repaint();
                }
            }
        });
    }

    // exibe lista (Favoritos, Assistidas, Desejo) em outra janela 
    private void mostrarLista(String titulo, List<Serie> lista) {
        DefaultListModel<Serie> model = new DefaultListModel<>();
        for (Serie s : lista)
            model.addElement(s);

        JList<Serie> jList = new JList<>(model);
        JScrollPane scroll = new JScrollPane(jList);

        // opcoes disponíveis para a lista
        Object[] opcoes = {
                "Remover",
                "Ordenar por Nome",
                "Ordenar por Nota geral",
                "Ordenar por Estado",
                "Ordenar por Estreia",
                "Ver Detalhes",
                "Fechar"
        };

        while (true) {
            int result = JOptionPane.showOptionDialog(
                    this, scroll, titulo, JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[6]);
            if (result == 6 || result == JOptionPane.CLOSED_OPTION)
                break;
            Serie s = jList.getSelectedValue();
            if (result == 0 && s != null) { // remover
                lista.remove(s);
                model.removeElement(s);
                Json.salvarSeries(serieManager);
            } else if (result >= 1 && result <= 4) { // ordenações
                List<Serie> ordenada = switch (result) {
                    case 1 -> serieManager.ordenarPorNome(lista);
                    case 2 -> serieManager.ordenarPorNota(lista);
                    case 3 -> serieManager.ordenarPorEstado(lista);
                    case 4 -> serieManager.ordenarPorEstreia(lista);
                    default -> lista;
                };
                model.clear();
                for (Serie serie : ordenada)
                    model.addElement(serie);
            } else if (result == 5 && s != null) { // detalhes
                mostrarDetalhesSerieDialog(s);
            }
        }
    }

    // mostra os detalhes completos da série selecionada do lado direito da tela
    private void mostrarDetalhesSerieLateral(Serie s) {
        detalhesPanel.removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        // exibe a imagem da série se tiver
        if (s.getImagemUrl() != null && !s.getImagemUrl().isEmpty()) {
            try {
                java.net.URL url = new java.net.URL(s.getImagemUrl());
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(150, 210, Image.SCALE_SMOOTH);
                JLabel lblImagem = new JLabel(new ImageIcon(scaledImg));
                lblImagem.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 18));
                panel.add(lblImagem);
            } catch (Exception ex) {
                panel.add(new JLabel());
            }
        } else {
            panel.add(new JLabel());
        }

        // exibe informações da série
        Box box = Box.createVerticalBox();
        JLabel lblNome = new JLabel("<html><h2>" + s.getNome() + "</h2></html>");
        box.add(lblNome);
        box.add(Box.createVerticalStrut(8));
        box.add(new JLabel("Idioma: " + s.getIdioma()));
        box.add(new JLabel("Gêneros: " + String.join(", ", s.getGeneros())));
        box.add(new JLabel("Nota geral: " + s.getNotaGeral()));
        box.add(new JLabel("Estado: " + s.getEstado()));
        box.add(new JLabel("Estreia: " + s.getDataEstreia()));

        // indica se a série já terminou ou está em andamento
        boolean emAndamento = (s.getDataTermino() == null || s.getDataTermino().isEmpty()
                || s.getEstado().equalsIgnoreCase("Running"));
        String terminoText = "Término: ";
        if (emAndamento) {
            terminoText += "Série em andamento";
        } else {
            terminoText += s.getDataTermino();
        }
        box.add(new JLabel(terminoText));

        box.add(new JLabel("Emissora: " + s.getEmissora()));

        box.add(Box.createVerticalStrut(12));

        // botões para add série nas listas (Favoritos, Assistidas, Desejo)
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JButton btnFav = new JButton("★");
        btnFav.setToolTipText("Favoritos");
        btnFav.addActionListener(ev -> {
            serieManager.adicionarFavorito(s);
            Json.salvarSeries(serieManager);
            JOptionPane.showMessageDialog(this, "Adicionado aos favoritos!");
        });
        JButton btnAss = new JButton("✔");
        btnAss.setToolTipText("Assistidas");
        btnAss.addActionListener(ev -> {
            serieManager.adicionarAssistida(s);
            Json.salvarSeries(serieManager);
            JOptionPane.showMessageDialog(this, "Adicionado como assistida!");
        });
        JButton btnDesejo = new JButton("♥");
        btnDesejo.setToolTipText("Desejo");
        btnDesejo.addActionListener(ev -> {
            serieManager.adicionarDesejo(s);
            Json.salvarSeries(serieManager);
            JOptionPane.showMessageDialog(this, "Adicionado ao desejo!");
        });
        botoes.add(btnFav);
        botoes.add(btnAss);
        botoes.add(btnDesejo);
        botoes.setOpaque(false);
        box.add(botoes);

        panel.add(box);

        detalhesPanel.add(panel, BorderLayout.CENTER);
        detalhesPanel.revalidate();
        detalhesPanel.repaint();
    }

    // mostra os detalhes da série em uma janela separada
    private void mostrarDetalhesSerieDialog(Serie s) {
        JPanel panel = new JPanel(new BorderLayout());

        boolean emAndamento = (s.getDataTermino() == null || s.getDataTermino().isEmpty()
                || s.getEstado().equalsIgnoreCase("Running"));
        String terminoText = emAndamento ? "Série em andamento" : s.getDataTermino();

        JLabel lblTexto = new JLabel("<html>" +
                "<b>Nome:</b> " + s.getNome() + "<br>" +
                "<b>Idioma:</b> " + s.getIdioma() + "<br>" +
                "<b>Gêneros:</b> " + String.join(", ", s.getGeneros()) + "<br>" +
                "<b>Nota geral:</b> " + s.getNotaGeral() + "<br>" +
                "<b>Estado:</b> " + s.getEstado() + "<br>" +
                "<b>Data de estreia:</b> " + s.getDataEstreia() + "<br>" +
                "<b>Data de término:</b> " + terminoText + "<br>" +
                "<b>Emissora:</b> " + s.getEmissora() + "<br>" +
                "</html>");
        panel.add(lblTexto, BorderLayout.CENTER);

        // exibe imagem grande da série se tiver
        if (s.getImagemUrl() != null && !s.getImagemUrl().isEmpty()) {
            try {
                java.net.URL url = new java.net.URL(s.getImagemUrl());
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(180, 260, Image.SCALE_SMOOTH);
                JLabel lblImagem = new JLabel(new ImageIcon(scaledImg));
                panel.add(lblImagem, BorderLayout.WEST);
            } catch (Exception ex) {
                // Ignora erros
            }
        }

        JOptionPane.showMessageDialog(this, panel, "Detalhes da Série", JOptionPane.PLAIN_MESSAGE);
    }
}