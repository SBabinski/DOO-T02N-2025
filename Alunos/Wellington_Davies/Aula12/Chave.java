public class Chave {

    public static String getChave() {
        String chave = System.getenv("CHAVE_VISUAL_CROSSING");
        if (chave == null || chave.isEmpty()) {
            System.out.println("⚠️ ERRO: Variável de ambiente CHAVE_VISUAL_CROSSING não definida.");
        }
        return chave;
    }
}
