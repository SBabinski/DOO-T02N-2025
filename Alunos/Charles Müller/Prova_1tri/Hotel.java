import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String nome;
    private List<Cliente> clientes;
    private List<Quarto> quartos;
    private List<Reserva> reservas;

    public Hotel(String nome) {
        this.nome = nome;
        this.clientes = new ArrayList<>();
        this.quartos = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
        System.out.println("Cliente cadastrado com sucesso: " + cliente.getNome());
    }

    public void cadastrarQuarto(Quarto quarto) {
        quartos.add(quarto);
        System.out.println("Quarto cadastrado com sucesso: " + quarto.getNumero());
    }

    public Reserva reservarQuarto(Cliente cliente, Quarto quarto, LocalDate dataInicio, LocalDate dataFim, int numeroHospedes) throws Exception {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }

        if (quarto == null) {
            throw new IllegalArgumentException("Quarto não pode ser nulo.");
        }

        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas.");
        }

        LocalDate hoje = LocalDate.now();
        if (dataInicio.isBefore(hoje)) {
            throw new IllegalArgumentException("A data de início não pode ser no passado.");
        }

        if (dataFim.isBefore(dataInicio) || dataFim.isEqual(dataInicio)) {
            throw new IllegalArgumentException("A data de fim deve ser posterior à data de início.");
        }

        if (numeroHospedes <= 0) {
            throw new IllegalArgumentException("O número de hóspedes deve ser maior que zero.");
        }

        if (numeroHospedes > quarto.getCapacidadeMaxima()) {
            throw new IllegalArgumentException("O número de hóspedes excede a capacidade máxima do quarto (" +
                                            quarto.getCapacidadeMaxima() + " pessoas).");
        }

        if (!isQuartoDisponivel(quarto, dataInicio, dataFim)) {
            throw new Exception("Quarto não disponível para o período selecionado");
        }

        Reserva reserva = new Reserva(cliente, quarto, dataInicio, dataFim, numeroHospedes);
        reservas.add(reserva);
        return reserva;
    }

    public boolean isQuartoDisponivel(Quarto quarto, LocalDate dataInicio, LocalDate dataFim) {
        for (Reserva reserva : reservas) {
            if (reserva.getQuarto().getNumero() == quarto.getNumero() && !reserva.isCheckOutRealizado()) {
                if ((dataInicio.isBefore(reserva.getDataFim()) || dataInicio.isEqual(reserva.getDataFim())) &&
                    (dataFim.isAfter(reserva.getDataInicio()) || dataFim.isEqual(reserva.getDataInicio()))) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Quarto> verificarQuartosDisponiveis(LocalDate dataInicio, LocalDate dataFim) {
        List<Quarto> quartosDisponiveis = new ArrayList<>();

        for (Quarto quarto : quartos) {
            if (isQuartoDisponivel(quarto, dataInicio, dataFim)) {
                quartosDisponiveis.add(quarto);
            }
        }

        return quartosDisponiveis;
    }

    public void realizarCheckIn(int idReserva) throws Exception {
        Reserva reserva = buscarReservaPorId(idReserva);

        if (reserva.isCheckInRealizado()) {
            throw new Exception("Check-in já realizado para esta reserva");
        }

        LocalDate hoje = LocalDate.now();
        if (hoje.isBefore(reserva.getDataInicio())) {
            throw new Exception("Não é possível realizar check-in antes da data de início da reserva (" +
                               reserva.getDataInicio() + ")");
        }

        reserva.realizarCheckIn();
        reserva.getQuarto().setDisponivel(false);
        System.out.println("Check-in realizado com sucesso para a reserva #" + idReserva);
    }

    public double realizarCheckOut(int idReserva) throws Exception {
        Reserva reserva = buscarReservaPorId(idReserva);

        if (!reserva.isCheckInRealizado()) {
            throw new Exception("Não é possível realizar check-out sem antes fazer check-in");
        }

        if (reserva.isCheckOutRealizado()) {
            throw new Exception("Check-out já realizado para esta reserva");
        }

        double valorTotal = reserva.calcularValorTotal();

        reserva.realizarCheckOut();
        reserva.getQuarto().setDisponivel(true);

        System.out.println("Check-out realizado com sucesso para a reserva #" + idReserva);
        return valorTotal;
    }

    private Reserva buscarReservaPorId(int idReserva) throws Exception {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == idReserva) {
                return reserva;
            }
        }
        throw new Exception("Reserva não encontrada com o ID: " + idReserva);
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public List<Quarto> getQuartos() {
        return new ArrayList<>(quartos);
    }

    public List<Reserva> getReservas() {
        return new ArrayList<>(reservas);
    }

    public Cliente buscarClientePorId(int id) throws Exception {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        throw new Exception("Cliente não encontrado com o ID: " + id);
    }

    public Quarto buscarQuartoPorNumero(int numero) throws Exception {
        for (Quarto quarto : quartos) {
            if (quarto.getNumero() == numero) {
                return quarto;
            }
        }
        throw new Exception("Quarto não encontrado com o número: " + numero);
    }

    public List<Reserva> getReservasPendentesCheckIn() {
        List<Reserva> reservasPendentes = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (!reserva.isCheckInRealizado() && !reserva.isCheckOutRealizado()) {
                reservasPendentes.add(reserva);
            }
        }
        return reservasPendentes;
    }

    public List<Reserva> getReservasEmAndamento() {
        List<Reserva> reservasEmAndamento = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.isCheckInRealizado() && !reserva.isCheckOutRealizado()) {
                reservasEmAndamento.add(reserva);
            }
        }
        return reservasEmAndamento;
    }
}
