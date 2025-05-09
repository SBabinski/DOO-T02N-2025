import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        Hotel hotel = new Hotel();


        while (true) {
            System.out.println("[1]-Cadastrar Cliente" + "                     " + "[2]-Cadastrar Quarto");
            System.out.println("[3]-Reservar Quarto" + "                       " + "[4] - Realizar Check-in");
            System.out.println("[5] - Realizar Check-out" + "                  " + "[0] - Sair");


            int opc = sc.nextInt();
            sc.nextLine();


            switch (opc) {
                case 1:
                    hotel.cadastrarCliente();
                    break;
                case 2:
                    hotel.cadastrarQuarto();
                    break;
                case 3:
                    hotel.reservaQuarto();
                    break;
                case 4:
                    hotel.checkInCliente();
                    break;
                case 5:
                    hotel.checkOutCliente();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }

    }
}