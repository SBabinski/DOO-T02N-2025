import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Persistencia {
    
    private Gson criarGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }
    
    public void salvarUsuario(Usuario usuario) {
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(usuario.getNome() + ".json"), 
                StandardCharsets.UTF_8)) {
            
            Gson gson = criarGson();
            gson.toJson(usuario, writer);
            writer.flush(); // Força a escrita
            
            // DEBUG
            System.out.println("Dados salvos com sucesso!");
            System.out.println("Favoritos: " + usuario.getFavoritos().size());
            System.out.println("Assistidas: " + usuario.getAssistidas().size());
            System.out.println("Desejo assistir: " + usuario.getDesejoAssistir().size());
            
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Usuario carregarUsuario() {
        // Procurar por arquivos .json na pasta atual
        File pasta = new File(".");
        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".json"));
        
        if (arquivos == null || arquivos.length == 0) {
            return null; // Nenhum arquivo encontrado
        }
        
        // Pegar o primeiro arquivo encontrado
        File arquivo = arquivos[0];
        
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(arquivo), 
                StandardCharsets.UTF_8)) {
            
            Gson gson = criarGson();
            Usuario usuario = gson.fromJson(reader, Usuario.class);
            
            // Verificar se o usuario foi carregado corretamente
            if (usuario == null) {
                System.out.println("Erro: Arquivo JSON inválido ou corrompido.");
                return null;
            }
            
            // Verificar se as listas foram inicializadas
            if (usuario.getFavoritos() == null || usuario.getAssistidas() == null || usuario.getDesejoAssistir() == null) {
                System.out.println("Erro: Dados incompletos no arquivo JSON.");
                return null;
            }
            
            // DEBUG
            System.out.println("Carregando - Favoritos: " + usuario.getFavoritos().size());
            System.out.println("Carregando - Assistidas: " + usuario.getAssistidas().size());
            System.out.println("Carregando - Desejo assistir: " + usuario.getDesejoAssistir().size());
            System.out.println("Usuário carregado com sucesso!");
            return usuario;
            
        } catch (Exception e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
            return null;
        }
    }
    
    public Usuario carregarUsuario(String nome) {
        File arquivo = new File(nome + ".json");
        
        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado, criando usuário novo.");
            return new Usuario(nome);
        }
        
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(arquivo), 
                StandardCharsets.UTF_8)) {
            
            Gson gson = criarGson();
            Usuario usuario = gson.fromJson(reader, Usuario.class);
            
            // Verificar se o usuario foi carregado corretamente
            if (usuario == null) {
                System.out.println("Erro: Arquivo JSON inválido. Criando usuário novo.");
                return new Usuario(nome);
            }
            
            // Verificar se as listas foram inicializadas
            if (usuario.getFavoritos() == null || usuario.getAssistidas() == null || usuario.getDesejoAssistir() == null) {
                System.out.println("Erro: Dados incompletos. Criando usuário novo.");
                return new Usuario(nome);
            }
            
            // DEBUG
            System.out.println("Carregando - Favoritos: " + usuario.getFavoritos().size());
            System.out.println("Carregando - Assistidas: " + usuario.getAssistidas().size());
            System.out.println("Carregando - Desejo assistir: " + usuario.getDesejoAssistir().size());
            System.out.println("Usuário carregado com sucesso!");
            return usuario;
            
        } catch (Exception e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
            System.out.println("Criando usuário novo.");
            return new Usuario(nome);
        }
    }
    
    // Classe interna para adaptar LocalDate
    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.format(formatter));
            }
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return LocalDate.parse(in.nextString(), formatter);
        }
    }
}