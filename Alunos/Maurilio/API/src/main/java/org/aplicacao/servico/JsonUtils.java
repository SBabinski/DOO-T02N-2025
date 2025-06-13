package org.aplicacao.servico;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aplicacao.dto.ProcuraFilmeDto;
import org.aplicacao.dto.Usuario;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void salvarLista(List<ProcuraFilmeDto> lista, String nomeArquivo) throws IOException{
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(nomeArquivo), lista);
    }

    public static List<ProcuraFilmeDto> carregarLista(String nomeArquivo) throws IOException{
        File file = new File(nomeArquivo);
        if (file.exists()){
            return List.of(mapper.readValue(file, ProcuraFilmeDto[].class));
        } else {
            return new java.util.ArrayList<>();
        }
    }

    public static void salvarUsuario(Usuario usuario, String nomeArquivo) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(nomeArquivo), usuario);
    }

    public static Usuario carregarUsuario(String nomeArquivo) throws IOException{
        File file = new File(nomeArquivo);
        if (file.exists()) {
            return mapper.readValue(file, Usuario.class);
        } else {
            return null;
        }
    }
}
