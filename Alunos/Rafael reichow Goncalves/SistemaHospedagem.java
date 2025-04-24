import java.util.*;

public class SistemaHospedagem {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Sistema de Hospedagem ---");
            System.out.println("1. Novo Hóspede");
            System.out.println("2. Novo Quarto");
            System.out.println("3. Criar Reserva");
            System.out.println("4. Check-In");
            System.out.println("5. Check-Out");
            System.out.println("6. Consultar Quartos");
            System.out.println("0. Finalizar");
            System.out.print("Escolha: ");
            opcao = entrada.nextInt();
            entrada.nextLine(); 

            switch (opcao) {
                case 1 -> Operacoes.registrarHospede(entrada);
                case 2 -> Operacoes.adicionarQuarto(entrada);
                case 3 -> Operacoes.registrarReserva(entrada);
                case 4 -> Operacoes.realizarCheckIn(entrada);
                case 5 -> Operacoes.realizarCheckOut(entrada);
                case 6 -> Operacoes.listarQuartos();
            }
        } while (opcao != 0);

        System.out.println("Encerrando sistema.");
    }
}