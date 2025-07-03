import java.io.File;
import java.util.Scanner;

public class Prova {

    public static void main (String[] args) {
    Scanner scanner = new Scanner(System.in);
    
    Usuario usuario = Salvar.carregar();

    if (usuario == null) {
        System.out.println("Digite seu nome:");
        String nome = scanner.nextLine();

        usuario = new Usuario(nome);

        System.out.println("\nUsuário criado com sucesso.");  
        } else {
            System.out.println("\nBem-vindo de volta, " + usuario.getNome() + "!");
         }


    int op = -1;
    while (op != 0){

        String nomeSerie = "";

        System.out.println("\nMenu:");
        System.out.println("\n[1] Buscar série por nome");
        System.out.println("\n[2] Ver lista de favoritos");
        System.out.println("\n[3] Ver lista de assistidos");
        System.out.println("\n[4] Ver lista de 'para assistir'");
        System.out.println("\n[5] Trocar usuário");
        System.out.println("\n[0] Sair");
        System.out.print("Escolha uma opção: ");

        try {
        op = Integer.parseInt(scanner.nextLine());

        switch (op) {
            case 1:
                Serie serieEncontrada = null;

                while (serieEncontrada == null) {   
                System.out.println("\nDigite o nome da série ou digite 0 para retornar ao menu:");
                nomeSerie = scanner.nextLine();

                    if (nomeSerie.equals("0")) {
                        System.out.println("Voltando ao menu principal...");
                        break;
                    }
                
                serieEncontrada = Funcionar.buscarSeriePorNome(nomeSerie);

                if (serieEncontrada != null) {
                    System.out.println("\nSérie encontrada: " + serieEncontrada);
                    int op2 = -1;

                    while (op2 != 0){
                        System.out.println("\n[1] Adicionar aos favoritos");
                        System.out.println("\n[2] Adicionar aos assistidos");
                        System.out.println("\n[3] Adicionar à lista 'para assistir'");
                        System.out.println("\n[0] Voltar ao menu principal");
                        System.out.print("\nEscolha uma opção: ");
                        op2 = Integer.parseInt(scanner.nextLine());

                        switch (op2){
                            case 1:
                                if (!usuario.getFavoritos().contains(serieEncontrada)){ // ! se nao ta nos fav o if roda adicionando
                                    usuario.getFavoritos().add(serieEncontrada);
                                    System.out.println("Série adicionada aos favoritos!");
                            } else {
                                 System.out.println("Essa série já está nos favoritos.");
                                }

                                break;

                            case 2:
                                usuario.getAssistidos().add(serieEncontrada);
                                System.out.println("\nSérie adicionada aos assistidos: " + serieEncontrada.getName());
                                Salvar.salvar(usuario);
                                break;

                            case 3:
                                usuario.getParaAssistir().add(serieEncontrada);
                                System.out.println("\nSérie adicionada à lista 'para assistir': " + serieEncontrada.getName());
                                Salvar.salvar(usuario);
                                break;

                            case 0:
                                System.out.println("\nVoltando ao menu principal...");
                                break;

                            default:
                                System.out.println("\nOpção inválida. Tente novamente.");
                                break;
                        }
                        
                    }   
                  break;
                } else {
                    System.out.println("Série não encontrada.");
                }
         }
                break;

            case 2:
                System.out.println("\nComo deseja visualizar sua lista de favoritos?");
                Organizar.exibirListaOrdenada("favoritos", usuario.getFavoritos(), scanner);
                
                removerSerie.mostrarListaEPermitirRemocao("favoritos", usuario.getFavoritos(), scanner, usuario);
                break;

                
            case 3:
                System.out.println("\nComo deseja visualizar sua lista de assistidos?");
                Organizar.exibirListaOrdenada("assistidos", usuario.getAssistidos(), scanner);

                removerSerie.mostrarListaEPermitirRemocao("assistidos", usuario.getAssistidos(), scanner, usuario);
                break;  
            
            case 4:
                System.out.println("\nComo deseja visualizar sua lista 'para assistir'?");
                Organizar.exibirListaOrdenada("para assistir", usuario.getParaAssistir(), scanner);

                removerSerie.mostrarListaEPermitirRemocao("para assistir", usuario.getParaAssistir(), scanner, usuario);
                break;

           case 5:
               File file = new File("usuario.json");
               if (file.exists()) {
                file.delete();
                System.out.println("Usuário removido com sucesso. Reinicie o programa para criar um novo.");
                op = 0; 
         } else {
            System.out.println("Nenhum usuário salvo encontrado.");
            }
            break;


            case 0:
            Salvar.salvar(usuario);  
            System.out.println("Saindo..." + usuario.getNome() + "!");
            break;


            default:
            System.out.println("Opção inválida. Tente novamente.");
            break;
            }
            } catch (NumberFormatException e) {
            System.out.println("Erro: digite um número válido.");	
            }
            }
            scanner.close();
             }
}