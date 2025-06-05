import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        System.out.println(contagemPalavras);
    }
}
