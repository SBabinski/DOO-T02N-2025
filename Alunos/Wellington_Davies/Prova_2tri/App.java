import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class App extends JFrame {
    // Definição das cores neutras utilizadas na interface
    private static final Color COR_PRIMARIA = new Color(248, 249, 250);
    private static final Color COR_SECUNDARIA = new Color(240, 242, 245);
    private static final Color COR_BOTAO = new Color(108, 117, 125);
    private static final Color COR_BOTAO_HOVER = new Color(90, 98, 104);
    private static final Color COR_TEXTO = new Color(33, 37, 41);
    private static final Color COR_FUNDO_TEXTO = new Color(255, 255, 255);

    // Componentes da interface
    private JTextPane textPane;
    private JLabel labelImagem;  // JLabel para exibir a imagem
    private ListaDeSeries seriesFavoritas;
    private ListaDeSeries seriesAssistidas;
    private ListaDeSeries desejoAssistir;
    private ListaDeSeries resultadoBusca;

    // Caminhos dos arquivos JSON para persistência de dados
    private String caminhoSaudacao = "saudacao.json";
    private String caminhoFavoritas = "series_favoritas.json";
    private String caminhoAssistidas = "series_assistidas.json";
    private String caminhoDesejo = "series_desejo.json";    

    public App() {
        // Configurações básicas da janela
        setTitle("Catálogo de Séries TV");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);  // Centraliza a janela na tela
        setLayout(new BorderLayout());

        // Criação e posicionamento dos painéis da interface
        JPanel headerPanel = criarHeader();
        add(headerPanel, BorderLayout.NORTH);

        JPanel menuPanel = criarMenuPanel();
        add(menuPanel, BorderLayout.WEST);

        JPanel contentPanel = criarContentPanel();
        add(contentPanel, BorderLayout.CENTER);

        // Inicialização dos dados da aplicação
        inicializarListas();
        carregarDados();
        exibirSaudacao();

        setVisible(true);  // Torna a janela visível
    }

        private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COR_PRIMARIA);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Título principal
        JLabel titulo = new JLabel("Catálogo de Séries TV", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(COR_TEXTO);

        // Subtítulo explicativo
        JLabel subtitulo = new JLabel("Gerencie suas séries favoritas", JLabel.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(108, 117, 125));

        header.add(titulo, BorderLayout.CENTER);
        header.add(subtitulo, BorderLayout.SOUTH);

        return header;
    }

    private JPanel criarMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(COR_SECUNDARIA);
        menuPanel.setBorder(new EmptyBorder(20, 15, 20, 15));
        menuPanel.setPreferredSize(new Dimension(250, 0)); // Título do menu lateral
        JLabel menuTitulo = new JLabel(" Menu Principal");
        menuTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuTitulo.setForeground(COR_TEXTO);
        menuTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(menuTitulo);
        menuPanel.add(Box.createVerticalStrut(20));

        // Definição dos textos dos botões do menu
        String[] textoBotoes = {
            " Buscar Série", 
            " Adicionar Série", 
            " Remover Série", 
            " Exibir Listas", 
            " Ordenar Listas", 
            " Limpar Arquivos", 
            " Salvar e Sair"
        };

        // Criação dos botões e adição ao painel
        JButton[] botoes = new JButton[textoBotoes.length];
        for (int i = 0; i < textoBotoes.length; i++) {
            botoes[i] = criarBotaoEstilizado(textoBotoes[i]);
            menuPanel.add(botoes[i]);
            menuPanel.add(Box.createVerticalStrut(10));  // Espaçamento entre botões
        }

        // Configuração dos eventos de clique para cada botão
        botoes[0].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                buscarSerieSwing();  // Chama função de busca
            }
        });
        botoes[1].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                adicionarSerieSwing();  // Chama função de adicionar série
            }
        });
        botoes[2].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                removerSerieSwing();  // Chama função de remover série
            }
        });
        botoes[3].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                exibirListasSwing();  // Chama função de exibir listas
            }
        });
        botoes[4].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ordenarListasSwing();  // Chama função de ordenar listas
            }
        });
        botoes[5].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                limparArquivosJsonSwing(); // Chama função de limpar arquivos
            }
        });
        botoes[6].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                salvarESair();  // Chama função de salvar e sair
            }
        });

        return menuPanel;
    }

    private JPanel criarContentPanel() {
    JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBackground(COR_FUNDO_TEXTO);
    contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

    // Título da área de conteúdo
    JLabel contentTitulo = new JLabel(" Área de Informações");
    contentTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
    contentTitulo.setForeground(COR_TEXTO);
    contentTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));

    // Painel principal dividido em duas partes (texto + imagem)
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(COR_FUNDO_TEXTO);

    // Área de texto onde serão exibidas as informações das séries
    textPane = new JTextPane();
    textPane.setEditable(false);
    textPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    textPane.setBackground(COR_FUNDO_TEXTO);
    textPane.setForeground(COR_TEXTO);
    textPane.setBorder(new EmptyBorder(15, 15, 15, 15));

    // Scroll para a área de texto
    JScrollPane scrollPane = new JScrollPane(textPane);
    scrollPane.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)
    ));
    scrollPane.setBackground(COR_FUNDO_TEXTO);
    scrollPane.setPreferredSize(new Dimension(400, 0)); // Define largura fixa para o texto

    // Painel para a imagem com dimensões responsivas
    JPanel imagemPanel = new JPanel(new BorderLayout());
    imagemPanel.setBackground(COR_FUNDO_TEXTO);
    imagemPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    imagemPanel.setPreferredSize(new Dimension(280, 0));
    imagemPanel.setMinimumSize(new Dimension(220, 300));  

    // JLabel para exibir a imagem com configurações otimizadas
    labelImagem = new JLabel("Imagem da série aparecerá aqui", JLabel.CENTER);
    labelImagem.setFont(new Font("Segoe UI", Font.ITALIC, 12));
    labelImagem.setForeground(new Color(108, 117, 125));
    labelImagem.setVerticalAlignment(JLabel.CENTER);
    labelImagem.setHorizontalAlignment(JLabel.CENTER);
    labelImagem.setBorder(new EmptyBorder(10, 10, 10, 10)); // Reduzir o padding

    imagemPanel.add(labelImagem, BorderLayout.CENTER);

    // Adiciona os componentes ao painel principal
    mainPanel.add(scrollPane, BorderLayout.CENTER);
    mainPanel.add(imagemPanel, BorderLayout.EAST);

    contentPanel.add(contentTitulo, BorderLayout.NORTH);
    contentPanel.add(mainPanel, BorderLayout.CENTER);

    return contentPanel;
}

    private JButton criarBotaoEstilizado(String texto) {
        JButton btn = new JButton(texto);
        
        // Configurações visuais do botão
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(COR_BOTAO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);   // Remove o foco visual
        btn.setBorderPainted(false);  // Remove a borda padrão
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setPreferredSize(new Dimension(200, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Cursor de mão ao passar por cima

        // Efeito visual quando o mouse passa por cima do botão
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(COR_BOTAO_HOVER);  // Muda cor ao entrar
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(COR_BOTAO);  // Volta cor original ao sair
            }
        });        // Configuração de bordas personalizadas
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        return btn;
    }

    private void inicializarListas() {
        seriesFavoritas = new ListaDeSeries("Favoritas", new ArrayList<>());
        seriesAssistidas = new ListaDeSeries("Assistidas", new ArrayList<>());
        desejoAssistir = new ListaDeSeries("Desejo", new ArrayList<>());
        resultadoBusca = new ListaDeSeries("Resultado da Busca", new ArrayList<>());
    }

    private void carregarDados() {
        seriesFavoritas.carregarDeJson(caminhoFavoritas);
        seriesAssistidas.carregarDeJson(caminhoAssistidas);
        desejoAssistir.carregarDeJson(caminhoDesejo);
    }

    private void exibirSaudacao() {
        String saudacao = Usuario.carregarSaudacao(caminhoSaudacao);        if (saudacao != null) {
            textPane.setText("🎉 " + saudacao + "\n\n Bem-vindo ao seu catálogo pessoal de séries!\n\n" +
                           " Use o menu lateral para navegar pelas funcionalidades:\n" +
                           "• Busque novas séries\n" +
                           "• Organize suas listas\n" +
                           "• Gerencie seus favoritos\n\n" +
                           " Comece clicando em qualquer botão do menu!");
        } else {
            // Saudação padrão para novos usuários
            textPane.setText(" Bem-vindo ao Catálogo de Séries TV!\n\n" +
                           " Este é seu gerenciador pessoal de séries.\n\n" +
                           " Funcionalidades disponíveis:\n" +
                           "• Buscar séries na base do TVMaze\n" +
                           "• Gerenciar listas de favoritos\n" +
                           "• Acompanhar séries assistidas\n" +
                           "• Criar lista de desejos\n\n" +
                           " Use o menu lateral para começar!");
        }
    }

    private void buscarSerieSwing() {
        // Pede o nome da série ao usuário
        String nomeSerie = JOptionPane.showInputDialog(this, "Digite o nome da série:");
        if (nomeSerie == null || nomeSerie.isBlank()) {
            return;  // Se cancelou ou não digitou nada, sai do método
        }
        
        // Preparar o nome para a busca (trocar espaços por +)
        String nomeFormatado = nomeSerie.replace(" ", "+");
        
        try {
            // Buscar séries na API
            String url = UrlBuilder.criarUrl(nomeFormatado);
            String respostaJson = HttpService.fazerRequisicao(url);
            java.util.List<Serie> seriesEncontradas = JsonParserService.parseJsonParaListaDeSeries(respostaJson);
            
            //Verifica se encontrou alguma série
            if (seriesEncontradas.isEmpty()) {
                textPane.setText("Nenhuma série encontrada para: " + nomeSerie);
                return;
            }
            
            //Cria lista de opções para o usuário escolher
            String[] opcoesSeries = new String[seriesEncontradas.size()];
            for (int i = 0; i < seriesEncontradas.size(); i++) {
                Serie serie = seriesEncontradas.get(i);
                String opcao = serie.getNome();
                
                // Adicionar ano se existir
                if (serie.getDataDeLancamento() != null) {
                    int ano = serie.getDataDeLancamento().getYear();
                    opcao = opcao + " (" + ano + ")";
                }
                
                // Adicionar nota
                opcao = opcao + " - Nota: " + serie.getNota();
                
                opcoesSeries[i] = opcao;
            }

            // Usuário escolhe qual série quer salvar
            String serieEscolhida = (String) JOptionPane.showInputDialog(
                this, 
                "Escolha qual série deseja salvar:",
                "Selecionar Série", 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                opcoesSeries, 
                opcoesSeries[0]
            );
            
            if (serieEscolhida == null) {
                textPane.setText("Busca cancelada.");
                return;
            }
            
            //Encontra qual série foi escolhida
            int indiceDaSerieEscolhida = -1;
            for (int i = 0; i < opcoesSeries.length; i++) {
                if (opcoesSeries[i].equals(serieEscolhida)) {
                    indiceDaSerieEscolhida = i;
                    break;
                }
            }
            
            if (indiceDaSerieEscolhida == -1) {
                textPane.setText("Erro: não foi possível identificar a série escolhida.");
                return;
            }
            
            //Pegar a série escolhida da lista
            Serie serieSelecionada = seriesEncontradas.get(indiceDaSerieEscolhida);

            // Verificar se a série já existe na lista de resultados
            boolean jaExiste = false;
            for (Serie s : resultadoBusca.getSeries()) {
                if (s.getNome().equals(serieSelecionada.getNome())) {
                    jaExiste = true;
                    break;
                }
            }
            
            //Salvar a série ou avisar que já existe
            if (jaExiste) {
                textPane.setText("Esta série já está salva na lista de Resultados da Busca:\n\n" + 
                               formatarSerie(serieSelecionada));
                // Exibe a imagem da série
                exibirImagemSerie(serieSelecionada.getImagemUrl());
            } else {
                resultadoBusca.adicionarSerie(serieSelecionada);
                textPane.setText("Série salva com sucesso!\n\n" + 
                               formatarSerie(serieSelecionada) + 
                               "\n\nA série foi adicionada à lista 'Resultados da Busca'.\n" +
                               "Use 'Adicionar Série' para mover para outras listas.");
                // Exibe a imagem da série
                exibirImagemSerie(serieSelecionada.getImagemUrl());
            }
            
        } catch (Exception erro) {
            textPane.setText("Erro ao buscar série: " + erro.getMessage() + 
                            "\n\nVerifique sua conexão com a internet.");
        }
    }

    private void adicionarSerieSwing() {
        // Usuário escolhe a lista de origem
        String[] opcoesOrigem = {"Favoritas", "Assistidas", "Desejo Assistir", "Histórico de Busca"};  
        int origem = JOptionPane.showOptionDialog(this, "Escolha a lista de origem:", "Adicionar Série",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesOrigem, opcoesOrigem[0]);
        if (origem == -1) {
            return;  // Cancelou a operação
        }

        // Determina qual lista foi escolhida como origem
        ListaDeSeries listaOrigem = switch (origem) {
            case 0 -> seriesFavoritas;
            case 1 -> seriesAssistidas;
            case 2 -> desejoAssistir;
            case 3 -> resultadoBusca;
            default -> null;
        };
        
        // Verifica se a lista não está vazia
        if (listaOrigem == null || listaOrigem.getSeries().isEmpty()) {
            textPane.setText("A lista de origem está vazia.");
            return;
        }

        // Cria array com nomes das séries para o usuário escolher
        String[] nomesSeries = new String[listaOrigem.getSeries().size()];
        int idx = 0;
        for (Serie s : listaOrigem.getSeries()) {
            nomesSeries[idx++] = s.getNome();
        }

        // Usuário escolhe qual série mover
        String serieEscolhida = (String) JOptionPane.showInputDialog(this, "Escolha a série para mover:",
                "Selecionar Série", JOptionPane.PLAIN_MESSAGE, null, nomesSeries, nomesSeries[0]);
        if (serieEscolhida == null) {
            return;  // Cancelou a seleção
        }

        // Encontra o objeto Serie correspondente
        Serie serie = null;
        for (Serie s : listaOrigem.getSeries()) {
            if (s.getNome().equalsIgnoreCase(serieEscolhida)) {
                serie = s;
                break;
            }
        }
        if (serie == null) {
            return;
        }

        // Cria opções de destino (exclui a lista de origem)
        ArrayList<String> opcoesDestino = new ArrayList<>();
        if (origem != 0) opcoesDestino.add("Favoritas");
        if (origem != 1) opcoesDestino.add("Assistidas");
        if (origem != 2) opcoesDestino.add("Desejo Assistir");
        if (origem == 3) opcoesDestino.add("Resultados da Busca");

        // Usuário escolhe a lista de destino
        String destinoEscolhido = (String) JOptionPane.showInputDialog(this, "Escolha a lista de destino:",
                "Selecionar Destino", JOptionPane.PLAIN_MESSAGE, null, opcoesDestino.toArray(), opcoesDestino.get(0));
        if (destinoEscolhido == null) {
            return;
        }

        // Determina qual lista foi escolhida como destino
        ListaDeSeries listaDestino = switch (destinoEscolhido) {
            case "Favoritas" -> seriesFavoritas;
            case "Assistidas" -> seriesAssistidas;
            case "Desejo Assistir" -> desejoAssistir;
            case "Resultados da Busca" -> resultadoBusca;
            default -> null;
        };

        if (listaDestino == null) {
            return;
        }
        
        // Verifica se a série já existe na lista de destino
        boolean jaExiste = false;
        for (Serie s : listaDestino.getSeries()) {
            if (s.getNome().equalsIgnoreCase(serie.getNome())) {
                jaExiste = true;
                break;
            }
        }
        
        // Executa a movimentação ou informa duplicação
        if (jaExiste) {
            textPane.setText("A série já está na lista de destino.");
        } else {
            listaDestino.adicionarSerie(serie);     // Adiciona na lista destino
            listaOrigem.getSeries().remove(serie);  // Remove da lista origem
            textPane.setText("Série movida com sucesso!");
        }
    }

    private void removerSerieSwing() {
        // Usuário escolhe de qual lista remover
        String[] opcoes = {"Favoritas", "Assistidas", "Desejo Assistir"};
        int escolha = JOptionPane.showOptionDialog(this, "Escolha a lista:", "Remover Série",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
        if (escolha == -1) {
            return;  // Cancelou a operação
        }

        // Determina qual lista foi escolhida
        ListaDeSeries lista = switch (escolha) {
            case 0 -> seriesFavoritas;
            case 1 -> seriesAssistidas;
            case 2 -> desejoAssistir;
            default -> null;
        };
        
        // Verifica se a lista não está vazia
        if (lista == null || lista.getSeries().isEmpty()) {
            textPane.setText("A lista está vazia.");
            return;
        }

        // Cria array com nomes das séries para escolha
        String[] nomesSeries = new String[lista.getSeries().size()];
        int idx = 0;
        for (Serie s : lista.getSeries()) {
            nomesSeries[idx++] = s.getNome();
        }

        // Usuário escolhe qual série remover
        String serieEscolhida = (String) JOptionPane.showInputDialog(this, "Escolha a série para remover:",
                "Selecionar Série", JOptionPane.PLAIN_MESSAGE, null, nomesSeries, nomesSeries[0]);
        if (serieEscolhida == null) {
            return;  // Cancelou a seleção
        }

        // Remove todas as séries com o nome escolhido
        ArrayList<Serie> paraRemover = new ArrayList<>();
        for (Serie s : lista.getSeries()) {
            if (s.getNome().equalsIgnoreCase(serieEscolhida)) {
                paraRemover.add(s);
            }
        }
        lista.getSeries().removeAll(paraRemover);
        textPane.setText("Série removida (se existia): " + serieEscolhida);
    }

    private void exibirListasSwing() {
        StringBuilder sb = new StringBuilder();
        Serie primeiraSerieComImagem = null;
        
        // Exibe lista de favoritas
        sb.append("=== Lista de séries favoritas ===\n");
        if (seriesFavoritas.getSeries().isEmpty()) {
            sb.append("Nenhuma série favorita encontrada.\n");
        } else {
            for (Serie s : seriesFavoritas.getSeries()) {
                sb.append(formatarSerie(s)).append("\n");
                if (primeiraSerieComImagem == null && s.getImagemUrl() != null && !s.getImagemUrl().equals("Imagem não disponível")) {
                    primeiraSerieComImagem = s;
                }
            }
        }

        // Exibe lista de assistidas
        sb.append("\n=== Lista de séries assistidas ===\n");
        if (seriesAssistidas.getSeries().isEmpty()) {
            sb.append("Nenhuma série assistida encontrada.\n");
        } else {
            for (Serie s : seriesAssistidas.getSeries()) {
                sb.append(formatarSerie(s)).append("\n");
                if (primeiraSerieComImagem == null && s.getImagemUrl() != null && !s.getImagemUrl().equals("Imagem não disponível")) {
                    primeiraSerieComImagem = s;
                }
            }
        }

        // Exibe lista de desejo assistir
        sb.append("\n=== Lista de desejo assistir ===\n");
        if (desejoAssistir.getSeries().isEmpty()) {
            sb.append("Nenhuma série no desejo assistir encontrada.\n");
        } else {
            for (Serie s : desejoAssistir.getSeries()) {
                sb.append(formatarSerie(s)).append("\n");
                if (primeiraSerieComImagem == null && s.getImagemUrl() != null && !s.getImagemUrl().equals("Imagem não disponível")) {
                    primeiraSerieComImagem = s;
                }
            }
        }

        textPane.setText(sb.toString());

        // Exibe a imagem da primeira série encontrada com imagem válida
        if (primeiraSerieComImagem != null) {
            exibirImagemSerie(primeiraSerieComImagem.getImagemUrl());
        } else {
            exibirImagemSerie(null); // Limpa a imagem se não houver nenhuma disponível
        }
    }

    private void ordenarListasSwing() {
        // Usuário escolhe qual lista ordenar
        String[] opcoesLista = {"Favoritas", "Assistidas", "Desejo Assistir"};
        int escolhaLista = JOptionPane.showOptionDialog(this, "Escolha a lista para ordenar:", "Ordenar Listas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesLista, opcoesLista[0]);
        if (escolhaLista == -1) {
            return;
        }

        // Determina qual lista foi escolhida
        ListaDeSeries lista = switch (escolhaLista) {
            case 0 -> seriesFavoritas;
            case 1 -> seriesAssistidas;
            case 2 -> desejoAssistir;
            default -> null;
        };
        if (lista == null) return;

        // Usuário escolhe o critério de ordenação
        String[] criterios = {"Nome", "Nota", "Estado (Running/Ended)", "Data de Estreia"};
        int criterio = JOptionPane.showOptionDialog(this, "Escolha o critério de ordenação:", "Critério",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, criterios, criterios[0]);
        if (criterio == -1) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        switch (criterio) {
            case 0 -> lista.listaOrdenadaPorNome().forEach(s -> sb.append(formatarSerie(s)).append("\n"));
            case 1 -> lista.listaOrdenadaPorNota().forEach(s -> sb.append(formatarSerie(s)).append("\n"));
            case 2 -> {
                // Pergunta se quer "Running" ou "Ended"
                String[] estados = {"Running", "Ended"};
                String estadoEscolhido = (String) JOptionPane.showInputDialog(
                    this,
                    "Escolha o estado da série:",
                    "Filtrar por Estado",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    estados,
                    estados[0]
                );
                if (estadoEscolhido == null) return;
                lista.listarSeriesPorEstado(estadoEscolhido).forEach(s -> sb.append(formatarSerie(s)).append("\n"));
            }
            case 3 -> {
                // Pergunta se quer ordenar por data de estreia ou término
                String[] opcoesData = {"Data de Estreia", "Data de Término"};
                String escolhaData = (String) JOptionPane.showInputDialog(
                    this,
                    "Deseja ordenar por data de estreia ou término?",
                    "Escolha o tipo de data",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opcoesData,
                    opcoesData[0]
                );
                if (escolhaData == null) return;

                if (escolhaData.equals("Data de Estreia")) {
                    // Ordena por data de estreia do mais antigo ao mais recente
                    lista.getSeries().stream()
                        .filter(s -> s.getDataDeLancamento() != null)
                        .sorted(java.util.Comparator.comparing(Serie::getDataDeLancamento))
                        .forEach(s -> sb.append(formatarSerie(s)).append("\n"));
                } else {
                    // Ordena por data de término do mais antigo ao mais recente
                    lista.getSeries().stream()
                        .filter(s -> s.getDataDeTermino() != null)
                        .sorted(java.util.Comparator.comparing(Serie::getDataDeTermino))
                        .forEach(s -> sb.append(formatarSerie(s)).append("\n"));
                }
            }
        }
        textPane.setText(sb.length() > 0 ? sb.toString() : "Nenhuma série encontrada para o critério.");
    }

    private void limparArquivosJsonSwing() {
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja limpar todos os arquivos JSON?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Limpa os arquivos E as listas em memória
            seriesFavoritas.limparJson(caminhoFavoritas);
            seriesAssistidas.limparJson(caminhoAssistidas);
            desejoAssistir.limparJson(caminhoDesejo);
            
            // Limpa também a lista de resultados de busca
            resultadoBusca.getSeries().clear();
            // Exibe mensagem de confirmação
            textPane.setText("Todos os arquivos JSON foram limpos.\nTodas as listas em memória foram esvaziadas.");
        }
    }

    private void salvarESair() {
        try {
            // Salva todas as listas nos respectivos arquivos
            seriesFavoritas.salvarEmJson(caminhoFavoritas);
            seriesAssistidas.salvarEmJson(caminhoAssistidas);
            desejoAssistir.salvarEmJson(caminhoDesejo);
            
            // Confirma o salvamento e encerra
            JOptionPane.showMessageDialog(this, "Listas salvas com sucesso!\nSaindo do programa...");
            System.exit(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar as listas: " + e.getMessage());
        }
    }

    private String formatarSerie(Serie s) {
        // Formata data de lançamento
        String dataLancamento;
        if (s.getDataDeLancamento() != null) {
            dataLancamento = s.getDataDeLancamento().toString();
        } else {
            dataLancamento = "Não disponível";
        }
        
        // Formata data de término
        String dataTermino;
        if (s.getDataDeTermino() != null) {
            dataTermino = s.getDataDeTermino().toString();
        } else {
            dataTermino = "Não disponível";
        }
        
        // Retorna string formatada com todos os dados da série (incluindo imagem)
        return String.format(
            "Nome: %s\nIdioma: %s\nGênero: %s\nNota: %.1f\nEstado: %s\nData de Lançamento: %s\nData de Término: %s\nEmissora: %s\n-------------------------",
            s.getNome(),
            s.getIdioma(),
            s.getGenero(),
            s.getNota(),
            s.getEstado(),
            dataLancamento,
            dataTermino,
            s.getEmissora()  
        );
    }

    private void exibirImagemSerie(String urlImagem) {
        if (urlImagem == null || urlImagem.equals("Imagem não disponível")) {
            // Se não tem imagem, exibe texto padrão
            labelImagem.setIcon(null);
            labelImagem.setText("Imagem não disponível");
            return;
        }

        // Carrega a imagem em uma thread separada para não travar a interface
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { 
                try {
                    // Carrega a imagem da URL
                    java.net.URL url = new java.net.URL(urlImagem);
                    ImageIcon originalIcon = new ImageIcon(url);
                    
                    // Obtém as dimensões originais da imagem
                    Image originalImage = originalIcon.getImage();
                    int originalWidth = originalImage.getWidth(null);
                    int originalHeight = originalImage.getHeight(null);
                    
                    // Obtém as dimensões disponíveis do painel (com margem de segurança)
                    int maxWidth = labelImagem.getParent().getWidth() - 40;  // 20px de cada lado
                    int maxHeight = labelImagem.getParent().getHeight() - 40; // 20px de cada lado
                    
                    // Se o painel ainda não foi renderizado, usa valores padrão
                    if (maxWidth <= 0) maxWidth = 200;
                    if (maxHeight <= 0) maxHeight = 350;
                    
                    // Calcula o fator de escala mantendo a proporção
                    double scaleWidth = (double) maxWidth / originalWidth;
                    double scaleHeight = (double) maxHeight / originalHeight;
                    double scale = Math.min(scaleWidth, scaleHeight); // Usa o menor fator para manter proporção
                    
                    // Calcula as novas dimensões
                    int newWidth = (int) (originalWidth * scale);
                    int newHeight = (int) (originalHeight * scale);
                    
                    // Redimensiona a imagem mantendo a proporção
                    Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);
                    
                    // Define a imagem no JLabel
                    labelImagem.setIcon(resizedIcon);
                    labelImagem.setText(""); // Remove o texto quando há imagem
                    
                    // Revalida o layout para garantir o posicionamento correto
                    labelImagem.revalidate();
                    labelImagem.repaint();
                    
                } catch (Exception e) {
                    // Se der erro ao carregar, exibe mensagem de erro
                    labelImagem.setIcon(null);
                    labelImagem.setText("Erro ao carregar imagem");
                    System.err.println("Erro ao carregar imagem: " + e.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        // Executa a interface gráfica na thread de eventos do Swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App();  // Cria e exibe a janela principal
            }
        });
    }
}