package service;

import com.google.gson.Gson;
import model.Serie;
import model.User;
import model.sortStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SeriesService {
    static Scanner scan = new Scanner(System.in);
    private User user;

    private static void menu() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║         MENU PRINCIPAL         ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║  1. Buscar Séries              ║");
        System.out.println("║  2. Gerenciar favoritos        ║");
        System.out.println("║  3. Gerenciar assistidas       ║");
        System.out.println("║  4. Gerenciar desejadas        ║");
        System.out.println("║  0. Sair                       ║");
        System.out.println("╚════════════════════════════════╝\n");
    }

    private void executar() {
        while (true) {
            menu();
            int opc = scanInt();

            switch (opc) {
                case 1:
                    callSearchSeriesByName().forEach(System.out::println);
                    break;
                case 2:
                    listMenu("Favoritos", user.getFavoriteSeries());
                    break;
                case 3:
                    listMenu("Séries assistidas", user.getWatchedSeries());
                    break;
                case 4:
                    listMenu("Séries desejadas", user.getWatchLaterSeries());
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    System.exit(0);
                default:
                    System.out.println("╔════════════════════════════════╗");
                    System.out.println("║         NÚMERO INVÁLIDO        ║");
                    System.out.println("╚════════════════════════════════╝\n");
            }
        }
    }

    public static List<Serie> callSearchSeriesByName() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Nome da série: ");
        String name = scan.nextLine();
        return ApiService.searchSeriesByName(name);
    }

    public static List<Serie> listWithIndexes(List<Serie> series) {
        for (int i = 0; i < series.size(); i++) {
            System.out.println("[" + (i + 1) + "]" + " " + series.get(i));
        }

        System.out.println();
        return series;
    }

    private void addToList(String listName, List<Serie> userList) {
        List<Serie> seriesList = listWithIndexes(callSearchSeriesByName());
        System.out.println("Escolha o número corespondente a série, para adicionar a lista de " + listName);
        int opcSeries = scanInt();

        if (opcSeries <= 0 || opcSeries > seriesList.size()) {
            return;
        }

        Serie serie = seriesList.get(opcSeries - 1);
        userList.add(serie);
        saveToJson();
        System.out.println(serie.getShow().getName() + ", adicionado a lista de " + listName + "!\n");
    }

    private void removeFromList(String listName, List<Serie> userList) {
        if (isEmptyList(userList)) return;
        List<Serie> seriesList = listWithIndexes(userList);
        System.out.println("Escolha o número corespondente a série que deseja remover: ");
        int opcSeriesRemove = scanInt();

        if (opcSeriesRemove <= 0 || opcSeriesRemove > seriesList.size()) {
            return;
        }

        Serie serieToRemove = seriesList.get(opcSeriesRemove - 1);
        boolean isRemove = seriesList.remove(serieToRemove);
        saveToJson();
        if (isRemove) System.out.println(serieToRemove.getShow().getName() + ", removido dos " + listName + "!\n");
    }

    private void listMenu(String listName, List<Serie> userList) {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("\t\t" + listName);
        System.out.println("╚════════════════════════════════╝");
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║  1. Adicionar série            ║");
        System.out.println("║  2. Remover série              ║");
        System.out.println("║  3. Listar séries              ║");
        System.out.println("║  4. Ordenar lista              ║");
        System.out.println("║  0. Voltar                     ║");
        System.out.println("╚════════════════════════════════╝\n");

        int opc = scanInt();
        if (opc <= 0 || opc > 4) return;

        switch (opc) {
            case 1:
                addToList(listName, userList);
                break;
            case 2:
                removeFromList(listName, userList);
                break;
            case 3:
                if (isEmptyList(userList)) return;
                userList.forEach(System.out::println);
                break;
            case 4:
                if (isEmptyList(userList)) return;
                orderingMenu(userList);
                break;
            default:
                break;
        }
    }

    private void orderingMenu(List<Serie> seriesList) {
        if (isEmptyList(seriesList)) return;
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║  1. Ordem alfabética           ║");
        System.out.println("║  2. Nota geral                 ║");
        System.out.println("║  3. Estado da série            ║");
        System.out.println("║  4. Data de estreia            ║");
        System.out.println("║  0. Voltar                     ║");
        System.out.println("╚════════════════════════════════╝\n");

        int opc = scanInt();
        if (opc <= 0 || opc > 4) return;

        switch (opc) {
            case 1:
                seriesList.sort((s1, s2) -> s1.getShow().getName().compareTo(s2.getShow().getName()));
                saveToJson();
                break;
            case 2:
                seriesList.sort((o1, o2) -> Double.compare(o1.getShow().getRating().getAverage(), o2.getShow().getRating().getAverage()));
                saveToJson();
                break;
            case 3:
                seriesList.sort((s1, s2) -> sortStatus.valueOf(s1.getShow().getStatus()).compareTo(sortStatus.valueOf(s2.getShow().getStatus())));
                saveToJson();
                break;
            case 4:
                seriesList.sort((s1, s2) -> s1.getShow().getPremiered().compareTo(s2.getShow().getPremiered()));
                saveToJson();
                break;
            default:
                break;
        }
    }

    public void jsonInitialization() {
        Path path = Paths.get("user.json");
        Gson gson = new Gson();

        if (!Files.exists(path)) {
            System.out.println("Bem-vindo ao SeriesApp!, insira um nome de usuário: ");
            String userName = scan.nextLine();
            user = new User(userName);
            String jsonUserSave = gson.toJson(user);

            try {
                Files.writeString(path, jsonUserSave);
                executar();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                String jsonUser = Files.readString(path);
                user = gson.fromJson(jsonUser, User.class);
                executar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveToJson() {
        Path path = Paths.get("user.json");
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);

        try {
            Files.writeString(path, jsonUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int scanInt() {
        while (true) {
            try {
                int opc = scan.nextInt();
                scan.nextLine();
                return opc;
            } catch (InputMismatchException e) {
                System.out.println("╔════════════════════════════════╗");
                System.out.println("║         NÚMERO INVÁLIDO        ║");
                System.out.println("╚════════════════════════════════╝\n");
                scan.nextLine();
            }
        }
    }

    public static <T> boolean isEmptyList(List<T> list) {
        if (list.isEmpty()) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("║          LISTA VAZIA!          ║");
            System.out.println("╚════════════════════════════════╝\n");
            return true;
        }
        return false;
    }
}
