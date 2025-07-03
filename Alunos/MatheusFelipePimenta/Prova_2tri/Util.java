import java.util.Comparator;

public class Util {
    public static Comparator<Serie> ordemAlfabetica() {
        return Comparator.comparing(Serie::getNome);
    }

    public static Comparator<Serie> porNota() {
        return Comparator.comparingDouble(Serie::getNota).reversed();
    }

    public static Comparator<Serie> porStatus() {
        return Comparator.comparing(Serie::getStatus);
    }

    public static Comparator<Serie> porEstreia() {
        return Comparator.comparing(Serie::getDataEstreia);
    }
}
