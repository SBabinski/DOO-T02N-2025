import javax.swing.*;

/**
 * Launcher principal que permite escolher entre interface console ou gráfica
 * Versão corrigida
 *
 * @author Charles Müller
 * @version 1.1
 */
public class WeatherAppLauncher_Fixed {

    public static void main(String[] args) {
        // Verificar argumentos da linha de comando
        if (args.length > 0) {
            String mode = args[0].toLowerCase();

            switch (mode) {
                case "--console":
                case "-c":
                    runConsoleMode();
                    return;
                case "--gui":
                case "-g":
                    runGUIMode();
                    return;
                case "--help":
                case "-h":
                    showHelp();
                    return;
                default:
                    System.out.println("Modo inválido: " + args[0]);
                    showHelp();
                    return;
            }
        }

        // Se não há argumentos, perguntar ao usuário
        chooseInterface();
    }

    /**
     * Permite ao usuário escolher a interface
     */
    private static void chooseInterface() {
        // Tentar usar interface gráfica para escolha
        try {
            // UIManager configuração removida por incompatibilidade

            String[] options = {"🖥️ Interface Gráfica", "💻 Interface Console", "❌ Cancelar"};

            int choice = JOptionPane.showOptionDialog(
                null,
                "Escolha o modo de execução do Weather App:",
                "Weather App - Launcher",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            switch (choice) {
                case 0: // Interface Gráfica
                    runGUIMode();
                    break;
                case 1: // Interface Console
                    runConsoleMode();
                    break;
                default: // Cancelar ou fechar
                    System.out.println("Execução cancelada pelo usuário.");
                    System.exit(0);
            }

        } catch (Exception e) {
            // Se falhar a interface gráfica, usar console para escolha
            System.out.println("Erro ao inicializar interface de escolha: " + e.getMessage());
            chooseInterfaceConsole();
        }
    }

    /**
     * Escolha via console se a interface gráfica falhar
     */
    private static void chooseInterfaceConsole() {
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            System.out.println("\n=== WEATHER APP - LAUNCHER ===");
            System.out.println("Escolha o modo de execução:");
            System.out.println("1. Interface Gráfica");
            System.out.println("2. Interface Console");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opção (1-2): ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        runGUIMode();
                        break;
                    case 2:
                        runConsoleMode();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        chooseInterfaceConsole();
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida!");
                scanner.nextLine(); // Limpar buffer
                chooseInterfaceConsole();
            }
        }
    }

    /**
     * Executa o modo console
     */
    private static void runConsoleMode() {
        System.out.println("Iniciando Weather App em modo console...\n");

        try {
            WeatherApp.main(new String[0]);
        } catch (Exception e) {
            System.err.println("Erro ao executar modo console: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Executa o modo interface gráfica
     */
    private static void runGUIMode() {
        System.out.println("Iniciando Weather App em modo gráfico...");

        try {
            SwingUtilities.invokeLater(() -> {
                try {
                    WeatherAppGUI_Fixed app = new WeatherAppGUI_Fixed();
                    app.setVisible(true);
                } catch (Exception e) {
                    System.err.println("Erro ao inicializar interface gráfica: " + e.getMessage());
                    e.printStackTrace();

                    // Fallback para console
                    JOptionPane.showMessageDialog(
                        null,
                        "Erro ao inicializar interface gráfica.\nExecutando em modo console...",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                    runConsoleMode();
                }
            });
        } catch (Exception e) {
            System.err.println("Erro ao executar modo gráfico: " + e.getMessage());
            e.printStackTrace();

            // Fallback para console
            System.out.println("Executando em modo console como alternativa...");
            runConsoleMode();
        }
    }

    /**
     * Mostra ajuda da linha de comando
     */
    private static void showHelp() {
        System.out.println("\n=== WEATHER APP - AJUDA ===");
        System.out.println("Uso: java WeatherAppLauncher_Fixed [opção]");
        System.out.println("\nOpções:");
        System.out.println("  --gui, -g       Executar em modo interface gráfica");
        System.out.println("  --console, -c   Executar em modo console");
        System.out.println("  --help, -h      Mostrar esta ajuda");
        System.out.println("\nSe nenhuma opção for fornecida, será exibido um");
        System.out.println("diálogo para escolher o modo de execução.");
        System.out.println("\nExemplos:");
        System.out.println("  java WeatherAppLauncher_Fixed --gui");
        System.out.println("  java WeatherAppLauncher_Fixed -c");
        System.out.println("  java WeatherAppLauncher_Fixed");
        System.out.println();
    }
}
