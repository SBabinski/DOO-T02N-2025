import java.time.LocalDate;
import java.util.*;

class Operacoes {
    static List<RegistroHospede> listaHospedes = new ArrayList<>();
    static List<UnidadeEstadia> listaQuartos = new ArrayList<>();
    static List<RegistroReserva> historicoReservas = new ArrayList<>();

    static void registrarHospede(Scanner sc) {
        System.out.print("Nome completo: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        listaHospedes.add(new RegistroHospede(nome, cpf));
        System.out.println("Hóspede adicionado com sucesso.");
    }

    static void adicionarQuarto(Scanner sc) {
        System.out.print("Número do quarto: ");
        int numero = sc.nextInt();
        System.out.print("Tipo (1 - Simples | 2 - Executiva): ");
        int tipo = sc.nextInt();
        sc.nextLine(); // limpar o \n

        UnidadeEstadia quarto = (tipo == 1) ? new SuiteSimples(numero) : new SuiteExecutiva(numero);
        listaQuartos.add(quarto);
        System.out.println("Quarto registrado.");
    }

    static void registrarReserva(Scanner sc) {
        RegistroHospede hospede = selecionarHospede(sc);
        UnidadeEstadia quarto = selecionarQuarto(sc);

        if (!quarto.estaLivre()) {
            System.out.println("Quarto indisponível.");
            return;
        }

        System.out.print("Data de entrada (AAAA-MM-DD): ");
        LocalDate entrada = LocalDate.parse(sc.nextLine());
        System.out.print("Data de saída (AAAA-MM-DD): ");
        LocalDate saida = LocalDate.parse(sc.nextLine());

        RegistroReserva reserva = new RegistroReserva(hospede, quarto, entrada, saida);
        historicoReservas.add(reserva);
        quarto.ocupado = true;
        System.out.println("Reserva confirmada. Valor: R$" + reserva.calcularTotal());
    }

    static void realizarCheckIn(Scanner sc) {
        RegistroReserva r = escolherReserva(sc);
        if (r != null) {
            r.unidade.ocupado = true;
            System.out.println("Check-in realizado.");
        }
    }

    static void realizarCheckOut(Scanner sc) {
        RegistroReserva r = escolherReserva(sc);
        if (r != null) {
            r.unidade.ocupado = false;
            System.out.println("Check-out concluído. Total: R$" + r.calcularTotal());
        }
    }

    static void listarQuartos() {
        listaQuartos.forEach(System.out::println);
    }

    static RegistroHospede selecionarHospede(Scanner sc) {
        for (int i = 0; i < listaHospedes.size(); i++) {
            System.out.println((i + 1) + ". " + listaHospedes.get(i));
        }
        System.out.print("Escolha o hóspede: ");
        return listaHospedes.get(sc.nextInt() - 1);
    }

    static UnidadeEstadia selecionarQuarto(Scanner sc) {
        for (int i = 0; i < listaQuartos.size(); i++) {
            System.out.println((i + 1) + ". " + listaQuartos.get(i));
        }
        System.out.print("Escolha o quarto: ");
        return listaQuartos.get(sc.nextInt() - 1);
    }

    static RegistroReserva escolherReserva(Scanner sc) {
        for (int i = 0; i < historicoReservas.size(); i++) {
            System.out.println((i + 1) + ". " + historicoReservas.get(i));
        }
        System.out.print("Escolha a reserva: ");
        return historicoReservas.get(sc.nextInt() - 1);
    }
}