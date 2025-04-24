import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class RegistroReserva {
    RegistroHospede hospede;
    UnidadeEstadia unidade;
    LocalDate dataEntrada;
    LocalDate dataSaida;

    RegistroReserva(RegistroHospede hospede, UnidadeEstadia unidade, LocalDate entrada, LocalDate saida) {
        this.hospede = hospede;
        this.unidade = unidade;
        this.dataEntrada = entrada;
        this.dataSaida = saida;
    }

    double calcularTotal() {
        long dias = ChronoUnit.DAYS.between(dataEntrada, dataSaida);
        return dias * unidade.diaria;
    }

    public String toString() {
        return hospede + " → " + unidade + " | De " + dataEntrada + " até " + dataSaida;
    }
}