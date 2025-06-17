import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Calculadora extends JFrame implements ActionListener {
    private JTextField campoNumero1;
    private JTextField campoNumero2;
    private JTextField campoResultado;
    private JButton botaoSoma;
    private JButton botaoSubtracao;
    private JButton botaoMultiplicacao;
    private JButton botaoDivisao;
    private JButton botaoPotencia;
    private JButton botaoLimpar;
    private JButton botaoHistorico;
    
    private java.util.List<String> historico;
    
    private Map<JButton, OperacaoMatematica> mapaOperacoes;
      /**
     * Construtor da calculadora
     */
    public Calculadora() {
        super("Calculadora Avançada");
        
        // Inicializa o histórico de operações
        historico = new java.util.ArrayList<>();
        
        // Inicializa o mapa de operações
        mapaOperacoes = new HashMap<>();
        
        criarInterface();
        inicializarOperacoes();
        configurarEventos();
        configurarJanela();
    }
    
    /**
     * Inicializa as operações matemáticas
     */
    private void inicializarOperacoes() {
        mapaOperacoes.put(botaoSoma, new OperacaoSoma());
        mapaOperacoes.put(botaoSubtracao, new OperacaoSubtracao());
        mapaOperacoes.put(botaoMultiplicacao, new OperacaoMultiplicacao());
        mapaOperacoes.put(botaoDivisao, new OperacaoDivisao());
        mapaOperacoes.put(botaoPotencia, new OperacaoPotencia());
    }
    
    /**
     * Configura os componentes da interface gráfica
     */
    private void criarInterface() {
        // Painéis para organizar os componentes
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel painelNumeros = new JPanel(new GridLayout(2, 2, 5, 5));
        JPanel painelBotoes = new JPanel(new GridLayout(1, 7, 5, 5));
        JPanel painelResultado = new JPanel(new BorderLayout());
        
        // Campos de texto
        campoNumero1 = new JTextField(10);
        campoNumero1.setFont(new Font("Arial", Font.PLAIN, 14));
        
        campoNumero2 = new JTextField(10);
        campoNumero2.setFont(new Font("Arial", Font.PLAIN, 14));
        
        campoResultado = new JTextField(10);
        campoResultado.setFont(new Font("Arial", Font.BOLD, 14));
        campoResultado.setEditable(false);
        campoResultado.setBackground(Color.WHITE);
        
        // Botões
        botaoSoma = new JButton("+");
        botaoSoma.setFont(new Font("Arial", Font.BOLD, 14));
        botaoSoma.setFocusPainted(false);
        
        botaoSubtracao = new JButton("-");
        botaoSubtracao.setFont(new Font("Arial", Font.BOLD, 14));
        botaoSubtracao.setFocusPainted(false);
        
        botaoMultiplicacao = new JButton("×");
        botaoMultiplicacao.setFont(new Font("Arial", Font.BOLD, 14));
        botaoMultiplicacao.setFocusPainted(false);
        
        botaoDivisao = new JButton("÷");
        botaoDivisao.setFont(new Font("Arial", Font.BOLD, 14));
        botaoDivisao.setFocusPainted(false);
        
        botaoPotencia = new JButton("x^y");
        botaoPotencia.setFont(new Font("Arial", Font.BOLD, 14));
        botaoPotencia.setFocusPainted(false);
        
        botaoLimpar = new JButton("Limpar");
        botaoLimpar.setFocusPainted(false);
        
        botaoHistorico = new JButton("Histórico");
        botaoHistorico.setFocusPainted(false);
        
        // Painéis de entrada de números
        painelNumeros.add(new JLabel("Número 1:"));
        painelNumeros.add(campoNumero1);
        painelNumeros.add(new JLabel("Número 2:"));
        painelNumeros.add(campoNumero2);
        
        // Painel de botões
        painelBotoes.add(botaoSoma);
        painelBotoes.add(botaoSubtracao);
        painelBotoes.add(botaoMultiplicacao);
        painelBotoes.add(botaoDivisao);
        painelBotoes.add(botaoPotencia);
        painelBotoes.add(botaoLimpar);
        painelBotoes.add(botaoHistorico);
        
        // Painel de resultado
        painelResultado.add(new JLabel("Resultado:"), BorderLayout.NORTH);
        painelResultado.add(campoResultado, BorderLayout.CENTER);
        
        // Montagem do painel principal
        painelPrincipal.add(painelNumeros, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        painelPrincipal.add(painelResultado, BorderLayout.SOUTH);
        
        // Adiciona o painel principal à janela
        add(painelPrincipal);
    }
    
    /**
     * Configura os listeners de eventos para os botões
     */
    private void configurarEventos() {
        botaoSoma.addActionListener(this);
        botaoSubtracao.addActionListener(this);
        botaoMultiplicacao.addActionListener(this);
        botaoDivisao.addActionListener(this);
        botaoPotencia.addActionListener(this);
        botaoLimpar.addActionListener(this);
        botaoHistorico.addActionListener(this);
        
        // Adiciona suporte a teclas de atalho
        configurarTeclasAtalho();
    }
    
    /**
     * Configura teclas de atalho para as operações
     */
    private void configurarTeclasAtalho() {
        // Mapeia teclas para ações
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();
        
        // Tecla Enter para realizar a última operação selecionada
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "calcular");
        actionMap.put("calcular", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se algum botão estiver com foco, simula clique nele
                if (botaoSoma.hasFocus()) {
                    botaoSoma.doClick();
                } else if (botaoSubtracao.hasFocus()) {
                    botaoSubtracao.doClick();
                } else if (botaoMultiplicacao.hasFocus()) {
                    botaoMultiplicacao.doClick();
                } else if (botaoDivisao.hasFocus()) {
                    botaoDivisao.doClick();
                } else if (botaoPotencia.hasFocus()) {
                    botaoPotencia.doClick();
                } else {
                    // Por padrão, realiza soma se nenhum botão estiver com foco
                    botaoSoma.doClick();
                }
            }
        });
        
        // Tecla Escape para limpar os campos
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "limpar");
        actionMap.put("limpar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        
        // Adiciona atalhos para operações específicas
        inputMap.put(KeyStroke.getKeyStroke("ADD"), "somar");
        actionMap.put("somar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botaoSoma.doClick();
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("SUBTRACT"), "subtrair");
        actionMap.put("subtrair", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botaoSubtracao.doClick();
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("MULTIPLY"), "multiplicar");
        actionMap.put("multiplicar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botaoMultiplicacao.doClick();
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("DIVIDE"), "dividir");
        actionMap.put("dividir", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botaoDivisao.doClick();
            }
        });
    }
    
    /**
     * Configura as propriedades da janela
     */
    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * Trata eventos dos botões da calculadora
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoLimpar) {
            limparCampos();
            return;
        }
        
        if (e.getSource() == botaoHistorico) {
            exibirHistorico();
            return;
        }
        
        try {
            // Tenta realizar a operação matemática
            double num1 = lerNumero(campoNumero1.getText(), "primeiro");
            double num2 = lerNumero(campoNumero2.getText(), "segundo");
            double resultado = 0;
            String simboloOperacao = "";
            
            // Verifica se é uma operação registrada no mapa
            if (mapaOperacoes.containsKey(e.getSource())) {
                OperacaoMatematica operacao = mapaOperacoes.get(e.getSource());
                resultado = operacao.executar(num1, num2);
                simboloOperacao = operacao.getSimbolo();
            }
            
            // Adiciona operação ao histórico
            adicionarAoHistorico(num1, num2, simboloOperacao, resultado);
            
            // Exibe o resultado formatado
            exibirResultado(resultado);
            
        } catch (CalculadoraException ex) {
            // Trata exceções da calculadora
            exibirErro(ex);
        } catch (Exception ex) {
            // Trata outras exceções não previstas
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            campoResultado.setText("ERRO");
        }
    }
    
    /**
     * Lê e valida um número de um campo de texto
     * @param texto O texto a ser convertido em número
     * @param campo Nome do campo para mensagem de erro
     * @return O número convertido
     * @throws CalculadoraException Se o texto não for um número válido
     */
    private double lerNumero(String texto, String campo) throws CalculadoraException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new CalculadoraException("O " + campo + " número não pode estar vazio", 
                    CalculadoraException.ERRO_CAMPO_VAZIO);
        }
        
        try {
            return Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            throw new CalculadoraException("O " + campo + " número não é válido. Digite apenas números", 
                    CalculadoraException.ERRO_ENTRADA_INVALIDA);
        }
    }
    
    /**
     * Exibe o resultado formatado no campo de resultado
     */
    private void exibirResultado(double resultado) {
        // Formata o resultado para evitar exibir decimais desnecessários
        if (resultado == (long) resultado) {
            campoResultado.setText(String.format("%d", (long) resultado));
        } else {
            campoResultado.setText(String.format("%.2f", resultado));
        }
    }
    
    /**
     * Limpa todos os campos da calculadora
     */
    private void limparCampos() {
        campoNumero1.setText("");
        campoNumero2.setText("");
        campoResultado.setText("");
        campoNumero1.requestFocus();
    }
    
    /**
     * Adiciona uma operação ao histórico
     * @param num1 O primeiro número
     * @param num2 O segundo número
     * @param operacao O símbolo da operação
     * @param resultado O resultado da operação
     */
    private void adicionarAoHistorico(double num1, double num2, String operacao, double resultado) {
        String formatoResultado = resultado == (long) resultado 
                ? String.format("%d", (long) resultado)
                : String.format("%.2f", resultado);
                
        String registro = String.format("%.2f %s %.2f = %s", 
                num1, operacao, num2, formatoResultado);
                
        historico.add(registro);
        
        // Limita o histórico a 10 operações
        if (historico.size() > 10) {
            historico.remove(0);
        }
    }
    
    /**
     * Exibe o histórico de operações em uma caixa de diálogo
     */
    private void exibirHistorico() {
        if (historico.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma operação realizada ainda.",
                    "Histórico", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder mensagem = new StringBuilder("<html><body><h3>Histórico de Operações</h3><ul>");
        
        // Adiciona cada operação como um item de lista
        for (String operacao : historico) {
            mensagem.append("<li>").append(operacao).append("</li>");
        }
        
        mensagem.append("</ul></body></html>");
        
        // Usa uma caixa de diálogo customizada para mostrar o histórico
        JOptionPane.showMessageDialog(this, new JLabel(mensagem.toString()),
                "Histórico", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Exibe uma mensagem de erro de acordo com o tipo da exceção
     * @param ex A exceção de calculadora a ser tratada
     */
    private void exibirErro(CalculadoraException ex) {
        String titulo = "Erro";
        int tipoMensagem = JOptionPane.ERROR_MESSAGE;
        
        // Personaliza o ícone e o título com base no tipo de erro
        switch (ex.getTipoErro()) {
            case CalculadoraException.ERRO_DIVISAO_ZERO:
                titulo = "Erro de Divisão";
                break;
                
            case CalculadoraException.ERRO_ENTRADA_INVALIDA:
                titulo = "Erro de Entrada";
                break;
                
            case CalculadoraException.ERRO_CAMPO_VAZIO:
                titulo = "Campo Vazio";
                tipoMensagem = JOptionPane.WARNING_MESSAGE;
                break;
                
            case CalculadoraException.ERRO_OVERFLOW:
                titulo = "Erro de Limite";
                break;
        }
        
        JOptionPane.showMessageDialog(this, ex.getMessage(), titulo, tipoMensagem);
        campoResultado.setText("ERRO");
        
        // Se for erro de campo vazio, coloca o foco no primeiro campo vazio
        if (ex.getTipoErro() == CalculadoraException.ERRO_CAMPO_VAZIO) {
            if (campoNumero1.getText().trim().isEmpty()) {
                campoNumero1.requestFocus();
            } else if (campoNumero2.getText().trim().isEmpty()) {
                campoNumero2.requestFocus();
            }
        }
    }
}
