import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hotel {
    static Scanner sc = new Scanner(System.in);
    private List<Quarto> listquartos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public void cadastrarCliente() {
        System.out.println("Insira seu nome:");
        String nome = sc.next();
        sc.nextLine();

        System.out.println("Insira sua idade:");
        int idade = sc.nextInt();
        sc.nextLine();

        Cliente cliente = new Cliente(nome, idade);
        clientes.add(cliente);

        System.out.println("Clientes cadastrados:");
        for (Cliente c : clientes) {
            System.out.println(c.getNome());
        }
    }

    public void cadastrarQuarto() {
        System.out.println("Insira o número do seu Quarto");
        int num = sc.nextInt();
        sc.nextLine();

        System.out.println("Qual o tipo de Quarto?");
        System.out.println("[1]-Quarto Simples" + "    " + "[2]-Quarto Duplo");
        int opc = sc.nextInt();
        sc.nextLine();

        if (opc == 1) {
            QuartoSimples quartoSimples = new QuartoSimples(num);
            listquartos.add(quartoSimples);
        }
        if (opc == 2) {
            QuartoDuplo quartoDuplo = new QuartoDuplo(num);
            listquartos.add(quartoDuplo);
        }
    }

    public void reservaQuarto() {
        System.out.println("---Insira o tipo de quarto que você deseja!---");
        System.out.println("[1]-Quarto Simples" + "    " + "[2]-Quarto Duplo");
        int opc = sc.nextInt();
        sc.nextLine();

        System.out.println("Lista dos Quartos Disponíveis");
        for (Quarto quarto : listquartos) {
            if (quarto.getTipo() == opc && quarto.isDisponibilidade()) {
                System.out.println("Quarto " + quarto.getNum() + " (Tipo " + opc + ")");
            }
        }

        System.out.println("Insira o número do Quarto que você quer");
        int num = sc.nextInt();
        sc.nextLine();

        if (opc == 1) {
            System.out.println("Insira o nome do 1º cliente:");
            String nome1 = sc.nextLine();
            System.out.println("Insira a idade do 1º cliente:");
            int idade1 = sc.nextInt();
            sc.nextLine();
            System.out.println("Insira o nome do 2º cliente:");
            String nome2 = sc.nextLine();
            System.out.println("Insira a idade do 2º cliente:");
            int idade2 = sc.nextInt();
            sc.nextLine();

            for (Quarto quarto : listquartos) {
                if (quarto.getNum() == num && quarto.getTipo() == 1 && quarto.isDisponibilidade()) {
                    boolean cliente1Encontrado = false, cliente2Encontrado = false;
                    for (Cliente cliente : quarto.getListquartocliente()) {
                        if (cliente.getNome().equalsIgnoreCase(nome1)) {
                            cliente1Encontrado = true;
                        }
                        if (cliente.getNome().equalsIgnoreCase(nome2)) {
                            cliente2Encontrado = true;
                        }
                    }
                    if (cliente1Encontrado && cliente2Encontrado) {
                        quarto.adicionarClienteQuarto(new Cliente(nome1, idade1));
                        quarto.adicionarClienteQuarto(new Cliente(nome2, idade2));
                        quarto.setDisponibilidade(false);
                        System.out.println("Quarto " + num + " reservado para " + nome1 + " e " + nome2);
                    } else {
                        System.out.println("[ERRO] - Um ou ambos os clientes não foram encontrados.");
                    }
                }
            }
        }
    }

    public void checkInCliente() {
        System.out.println("Insira o nome do cliente para o check-in:");
        String nome = sc.nextLine();
        System.out.println("Insira o número do quarto para o check-in:");
        int num = sc.nextInt();
        sc.nextLine();

        Cliente cliente = null;
        for (Cliente c : clientes) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                cliente = c;
                break;
            }
        }

        if (cliente != null) {
            for (Quarto quarto : listquartos) {
                if (quarto.getNum() == num && quarto.isDisponibilidade()) {
                    quarto.adicionarClienteQuarto(cliente);
                    quarto.setDisponibilidade(false);
                    System.out.println("Check-in realizado com sucesso!");
                }
            }
        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    public void checkOutCliente() {
        System.out.println("Insira o nome do cliente para o check-out:");
        String nome = sc.nextLine();
        System.out.println("Insira o número do quarto para o check-out:");
        int num = sc.nextInt();
        sc.nextLine();

        for (Quarto quarto : listquartos) {
            if (quarto.getNum() == num) {
                Cliente remover = null;
                for (Cliente c : quarto.getListquartocliente()) {
                    if (c.getNome().equalsIgnoreCase(nome)) {
                        remover = c;
                        break;
                    }
                }
                if (remover != null) {
                    quarto.removerCliente(remover);
                    if (quarto.getListquartocliente().isEmpty()) {
                        quarto.setDisponibilidade(true);
                    }
                    System.out.println("Check-out realizado com sucesso!");
                }
            }
        }
    }

    public double calcularDiarias(Quarto quarto, int dias) {
        double valorDiaria = quarto.getValor();
        return valorDiaria * dias;
    }

    public boolean verificarDisponibilidade(Quarto quarto, String data) {
        return quarto.isDisponibilidade();
    }
}
