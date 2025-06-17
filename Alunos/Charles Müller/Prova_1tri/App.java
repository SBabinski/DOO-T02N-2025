import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static Hotel hotel;
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        hotel = new Hotel("Hotel Java");

        inicializarDadosTeste();

        boolean executando = true;
        while (executando) {
            exibirMenuPrincipal();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuQuartos();
                    break;
                case 3:
                    menuReservas();
                    break;
                case 4:
                    realizarCheckIn();
                    break;
                case 5:
                    realizarCheckOut();
                    break;
                case 6:
                    verificarDisponibilidade();
                    break;
                case 0:
                    executando = false;
                    System.out.println("Sistema encerrado. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void inicializarDadosTeste() {
        Cliente cliente1 = new Cliente(1, "João Silva", "123.456.789-00", "joao@email.com", "(47) 99999-1111");
        Cliente cliente2 = new Cliente(2, "Maria Santos", "987.654.321-00", "maria@email.com", "(47) 99999-2222");
        hotel.cadastrarCliente(cliente1);
        hotel.cadastrarCliente(cliente2);

        Quarto quartoSimples1 = new QuartoSimples(101, 150.0);
        Quarto quartoSimples2 = new QuartoSimples(102, 150.0);
        Quarto quartoDuplo1 = new QuartoDuplo(201, 300.0);
        Quarto quartoDuplo2 = new QuartoDuplo(202, 300.0);

        hotel.cadastrarQuarto(quartoSimples1);
        hotel.cadastrarQuarto(quartoSimples2);
        hotel.cadastrarQuarto(quartoDuplo1);
        hotel.cadastrarQuarto(quartoDuplo2);
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n===== SISTEMA DE GERENCIAMENTO DE HOTEL =====");
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Quartos");
        System.out.println("3. Gerenciar Reservas");
        System.out.println("4. Realizar Check-in");
        System.out.println("5. Realizar Check-out");
        System.out.println("6. Verificar Disponibilidade de Quartos");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    private static void menuClientes() {
        boolean submenu = true;
        while (submenu) {
            System.out.println("\n===== GERENCIAMENTO DE CLIENTES =====");
            System.out.println("1. Cadastrar Novo Cliente");
            System.out.println("2. Listar Todos os Clientes");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 0:
                    submenu = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n--- CADASTRO DE CLIENTE ---");

        // Validação do ID
        int id;
        while (true) {
            try {
                System.out.print("ID: ");
                id = Integer.parseInt(scanner.nextLine());

                if (id <= 0) {
                    System.out.println("ERRO: ID deve ser um número positivo.");
                    continue;
                }

                // Verificar se já existe cliente com este ID
                for (Cliente c : hotel.getClientes()) {
                    if (c.getId() == id) {
                        System.out.println("ERRO: Já existe um cliente cadastrado com este ID.");
                        return;
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Digite um número válido.");
            }
        }

        // Validação do nome
        String nome;
        while (true) {
            System.out.print("Nome: ");
            nome = scanner.nextLine();
            if (nome == null || nome.trim().isEmpty()) {
                System.out.println("ERRO: Nome não pode ser vazio.");
                continue;
            }
            break;
        }

        // Validação do CPF
        String cpf;
        while (true) {
            System.out.print("CPF (formato XXX.XXX.XXX-XX): ");
            cpf = scanner.nextLine();
            if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                System.out.println("ERRO: Formato de CPF inválido.");
                continue;
            }
            break;
        }

        // Validação do email
        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            if (email == null || !email.matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                System.out.println("ERRO: Email inválido.");
                continue;
            }
            break;
        }

        // Validação do telefone
        String telefone;
        while (true) {
            System.out.print("Telefone (formato (XX) XXXXX-XXXX): ");
            telefone = scanner.nextLine();
            if (telefone == null || !telefone.matches("\\(\\d{2}\\) \\d{4,5}-\\d{4}")) {
                System.out.println("ERRO: Formato de telefone inválido.");
                continue;
            }
            break;
        }

        try {
            Cliente cliente = new Cliente(id, nome, cpf, email, telefone);
            hotel.cadastrarCliente(cliente);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("ERRO ao cadastrar cliente: " + e.getMessage());
        }
    }

    private static void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        List<Cliente> clientes = hotel.getClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }


    private static void menuQuartos() {
        boolean submenu = true;
        while (submenu) {
            System.out.println("\n===== GERENCIAMENTO DE QUARTOS =====");
            System.out.println("1. Cadastrar Novo Quarto Simples");
            System.out.println("2. Cadastrar Novo Quarto Duplo");
            System.out.println("3. Listar Todos os Quartos");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    cadastrarQuartoSimples();
                    break;
                case 2:
                    cadastrarQuartoDuplo();
                    break;
                case 3:
                    listarQuartos();
                    break;
                case 0:
                    submenu = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void cadastrarQuartoSimples() {
        System.out.println("\n--- CADASTRO DE QUARTO SIMPLES ---");

        int numero;
        while (true) {
            try {
                System.out.print("Número: ");
                numero = Integer.parseInt(scanner.nextLine());

                if (numero <= 0) {
                    System.out.println("ERRO: O número do quarto deve ser positivo.");
                    continue;
                }

                for (Quarto q : hotel.getQuartos()) {
                    if (q.getNumero() == numero) {
                        System.out.println("ERRO: Já existe um quarto cadastrado com este número.");
                        return;
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Digite um número válido.");
            }
        }

        double valorDiaria;
        while (true) {
            try {
                System.out.print("Valor da diária (R$): ");
                valorDiaria = Double.parseDouble(scanner.nextLine());

                if (valorDiaria <= 0) {
                    System.out.println("ERRO: O valor da diária deve ser maior que zero.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Digite um valor numérico válido.");
            }
        }

        try {
            Quarto quarto = new QuartoSimples(numero, valorDiaria);
            hotel.cadastrarQuarto(quarto);
            System.out.println("Quarto simples cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("ERRO ao cadastrar quarto: " + e.getMessage());
        }
    }

    private static void cadastrarQuartoDuplo() {
        System.out.println("\n--- CADASTRO DE QUARTO DUPLO ---");
    int numero;
    while (true) {
        try {
            System.out.print("Número: ");
            numero = Integer.parseInt(scanner.nextLine());

            if (numero <= 0) {
                System.out.println("ERRO: O número do quarto deve ser positivo.");
                continue;
            }

            for (Quarto q : hotel.getQuartos()) {
                if (q.getNumero() == numero) {
                    System.out.println("ERRO: Já existe um quarto cadastrado com este número.");
                    return;
                }
            }
            break;
        } catch (NumberFormatException e) {
            System.out.println("ERRO: Digite um número válido.");
        }
    }

    double valorDiaria;
    while (true) {
        try {
            System.out.print("Valor da diária (R$): ");
            valorDiaria = Double.parseDouble(scanner.nextLine());

            if (valorDiaria <= 0) {
                System.out.println("ERRO: O valor da diária deve ser maior que zero.");
                continue;
            }
            break;
        } catch (NumberFormatException e) {
            System.out.println("ERRO: Digite um valor numérico válido.");
        }
    }

    try {
        Quarto quarto = new QuartoDuplo(numero, valorDiaria);
        hotel.cadastrarQuarto(quarto);
        System.out.println("Quarto duplo cadastrado com sucesso!");
    } catch (IllegalArgumentException e) {
        System.out.println("ERRO ao cadastrar quarto: " + e.getMessage());
    }
}

    private static void listarQuartos() {
        System.out.println("\n--- LISTA DE QUARTOS ---");
        List<Quarto> quartos = hotel.getQuartos();
        if (quartos.isEmpty()) {
            System.out.println("Nenhum quarto cadastrado.");
            return;
        }

        for (Quarto quarto : quartos) {
            System.out.println(quarto);
        }
    }


    private static void menuReservas() {
        boolean submenu = true;
        while (submenu) {
            System.out.println("\n===== GERENCIAMENTO DE RESERVAS =====");
            System.out.println("1. Fazer Nova Reserva");
            System.out.println("2. Listar Todas as Reservas");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    fazerReserva();
                    break;
                case 2:
                    listarReservas();
                    break;
                case 0:
                    submenu = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void fazerReserva() {
        try {
            System.out.println("\n--- FAZER RESERVA ---");

            System.out.println("\nClientes disponíveis:");
            listarClientes();

            Cliente cliente = null;
            while (cliente == null) {
                try {
                    System.out.print("Digite o ID do cliente: ");
                    int idCliente = Integer.parseInt(scanner.nextLine());
                    cliente = hotel.buscarClientePorId(idCliente);
                } catch (NumberFormatException e) {
                    System.out.println("ERRO: Digite um ID válido.");
                } catch (Exception e) {
                    System.out.println("ERRO: " + e.getMessage());
                }
            }

            LocalDate dataInicio = null;
            LocalDate dataFim = null;
            LocalDate hoje = LocalDate.now();

            while (dataInicio == null) {
                try {
                    System.out.print("\nData de início (dd/MM/yyyy): ");
                    dataInicio = LocalDate.parse(scanner.nextLine(), formatter);

                    if (dataInicio.isBefore(hoje)) {
                        System.out.println("ERRO: A data de início não pode ser no passado.");
                        dataInicio = null;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("ERRO: Formato de data inválido. Use o formato dd/MM/yyyy.");
                }
            }

            while (dataFim == null) {
                try {
                    System.out.print("Data de fim (dd/MM/yyyy): ");
                    dataFim = LocalDate.parse(scanner.nextLine(), formatter);

                    if (dataFim.isBefore(dataInicio) || dataFim.isEqual(dataInicio)) {
                        System.out.println("ERRO: A data de fim deve ser posterior à data de início.");
                        dataFim = null;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("ERRO: Formato de data inválido. Use o formato dd/MM/yyyy.");
                }
            }

            List<Quarto> quartosDisponiveis = hotel.verificarQuartosDisponiveis(dataInicio, dataFim);
            System.out.println("\nQuartos disponíveis para o período:");
            if (quartosDisponiveis.isEmpty()) {
                System.out.println("Não há quartos disponíveis para o período selecionado.");
                return;
            }

            for (Quarto quarto : quartosDisponiveis) {
                System.out.println(quarto);
            }

            Quarto quartoSelecionado = null;
            while (quartoSelecionado == null) {
                try {
                    System.out.print("Digite o número do quarto desejado: ");
                    int numeroQuarto = Integer.parseInt(scanner.nextLine());

                    for (Quarto quarto : quartosDisponiveis) {
                        if (quarto.getNumero() == numeroQuarto) {
                            quartoSelecionado = quarto;
                            break;
                        }
                    }

                    if (quartoSelecionado == null) {
                        System.out.println("ERRO: Quarto não disponível ou inexistente na lista.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ERRO: Digite um número válido.");
                }
            }

            int numeroHospedes = 0;
            while (numeroHospedes <= 0 || numeroHospedes > quartoSelecionado.getCapacidadeMaxima()) {
                try {
                    System.out.print("Número de hóspedes (máximo " + quartoSelecionado.getCapacidadeMaxima() + "): ");
                    numeroHospedes = Integer.parseInt(scanner.nextLine());

                    if (numeroHospedes <= 0) {
                        System.out.println("ERRO: O número de hóspedes deve ser maior que zero.");
                    } else if (numeroHospedes > quartoSelecionado.getCapacidadeMaxima()) {
                        System.out.println("ERRO: O número excede a capacidade máxima do quarto.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ERRO: Digite um número válido.");
                }
            }

            Reserva reserva = hotel.reservarQuarto(cliente, quartoSelecionado, dataInicio, dataFim, numeroHospedes);
            System.out.println("\nReserva realizada com sucesso:");
            System.out.println(reserva);

        } catch (Exception e) {
            System.out.println("Erro ao fazer reserva: " + e.getMessage());
        }
    }

    private static void listarReservas() {
        System.out.println("\n--- LISTA DE RESERVAS ---");
        List<Reserva> reservas = hotel.getReservas();
        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }

        for (Reserva reserva : reservas) {
            System.out.println(reserva);
            System.out.println("------------------------------");
        }
    }


    private static void realizarCheckIn() {
        try {
            System.out.println("\n--- REALIZAR CHECK-IN ---");
            System.out.println("Reservas pendentes de check-in:");

            List<Reserva> reservasPendentes = hotel.getReservasPendentesCheckIn();
            if (reservasPendentes.isEmpty()) {
                System.out.println("Não há reservas pendentes de check-in.");
                return;
            }

            for (Reserva reserva : reservasPendentes) {
                System.out.println(reserva);
                System.out.println("------------------------------");
            }

            System.out.print("Digite o ID da reserva para check-in: ");
            int idReserva = Integer.parseInt(scanner.nextLine());
            hotel.realizarCheckIn(idReserva);
            System.out.println("Check-in realizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao realizar check-in: " + e.getMessage());
        }
    }

    private static void realizarCheckOut() {
        try {
            System.out.println("\n--- REALIZAR CHECK-OUT ---");
            System.out.println("Reservas com check-in realizado:");

            List<Reserva> reservasEmAndamento = hotel.getReservasEmAndamento();
            if (reservasEmAndamento.isEmpty()) {
                System.out.println("Não há hóspedes para realizar check-out.");
                return;
            }

            for (Reserva reserva : reservasEmAndamento) {
                System.out.println(reserva);
                System.out.println("------------------------------");
            }

            System.out.print("Digite o ID da reserva para check-out: ");
            int idReserva = Integer.parseInt(scanner.nextLine());
            double valorTotal = hotel.realizarCheckOut(idReserva);
            System.out.println("Check-out realizado com sucesso!");
            System.out.println("Valor total a pagar: R$ " + valorTotal);

        } catch (Exception e) {
            System.out.println("Erro ao realizar check-out: " + e.getMessage());
        }
    }


    private static void verificarDisponibilidade() {
        try {
            System.out.println("\n--- VERIFICAR DISPONIBILIDADE DE QUARTOS ---");
            System.out.print("Data de início (dd/MM/yyyy): ");
            LocalDate dataInicio = LocalDate.parse(scanner.nextLine(), formatter);
            System.out.print("Data de fim (dd/MM/yyyy): ");
            LocalDate dataFim = LocalDate.parse(scanner.nextLine(), formatter);

            List<Quarto> quartosDisponiveis = hotel.verificarQuartosDisponiveis(dataInicio, dataFim);

            System.out.println("\nQuartos disponíveis entre " + dataInicio.format(formatter) + " e " + dataFim.format(formatter) + ":");
            if (quartosDisponiveis.isEmpty()) {
                System.out.println("Não há quartos disponíveis para o período selecionado.");
                return;
            }

            for (Quarto quarto : quartosDisponiveis) {
                System.out.println(quarto);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido. Use o formato dd/MM/yyyy.");
        } catch (Exception e) {
            System.out.println("Erro ao verificar disponibilidade: " + e.getMessage());
        }
    }
}
