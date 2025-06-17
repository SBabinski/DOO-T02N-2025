import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private static int contadorId = 1;

    private int id;
    private Cliente cliente;
    private Quarto quarto;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean checkInRealizado;
    private boolean checkOutRealizado;
    private int numeroHospedes;

    public Reserva(Cliente cliente, Quarto quarto, LocalDate dataInicio, LocalDate dataFim, int numeroHospedes) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (quarto == null) {
            throw new IllegalArgumentException("Quarto não pode ser nulo.");
        }

        LocalDate hoje = LocalDate.now();
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("As datas de início e fim não podem ser nulas.");
        }

        if (dataInicio.isBefore(hoje)) {
            throw new IllegalArgumentException("A data de início não pode ser anterior à data atual.");
        }

        if (dataFim.isBefore(dataInicio) || dataFim.isEqual(dataInicio)) {
            throw new IllegalArgumentException("A data de fim deve ser posterior à data de início.");
        }

        if(!quarto.isDisponivel()) {
            throw new IllegalArgumentException("O quarto não está disponível.");
        }

        if (numeroHospedes <= 0) {
            throw new IllegalArgumentException("Número de hóspedes deve ser maior que zero.");
        }

        if (numeroHospedes > quarto.getCapacidadeMaxima()) {
            throw new IllegalArgumentException("Número de hóspedes excede a capacidade máxima do quarto (" + quarto.getCapacidadeMaxima() + " pessoas).");
        }

        this.id = contadorId++;
        this.cliente = cliente;
        this.quarto = quarto;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.numeroHospedes = numeroHospedes;
        this.checkInRealizado = false;
        this.checkOutRealizado = false;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public int getNumeroHospedes() {
        return numeroHospedes;
    }

    public boolean isCheckInRealizado() {
        return checkInRealizado;
    }

    public void realizarCheckIn() {
        this.checkInRealizado = true;
    }

    public boolean isCheckOutRealizado() {
        return checkOutRealizado;
    }

    public void realizarCheckOut() {
        this.checkOutRealizado = true;
    }

    public double calcularValorTotal() {
        long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);
        return dias * quarto.getValorDiaria();
    }

    @Override
    public String toString() {
        return "Reserva #" + id +
                "\nCliente: " + cliente.getNome() +
                "\nQuarto: " + quarto.getNumero() +
                "\nTipo: " + quarto.getTipo() +
                "\nHóspedes: " + numeroHospedes + " pessoa(s)" +
                "\nPeríodo: " + dataInicio + " a " + dataFim +
                "\nValor Total: R$ " + calcularValorTotal() +
                "\nStatus: " + (checkOutRealizado ? "Finalizada" : (checkInRealizado ? "Check-in realizado" : "Reservado"));
    }
}
