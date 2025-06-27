package spring_boot_api.tvmaze_api;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ListasRepository {

	private static final String ARQUIVO = "Listas.json";

	private final ObjectMapper mapper = new ObjectMapper();

	public Listas carregar() {
		try {
			File file = new File(ARQUIVO);
			if (!file.exists()) {
				return new Listas();
			}
			return mapper.readValue(file, Listas.class);
		} catch (IOException e) {
			System.err.println("Erro ao carregar usuário: " + e.getMessage());
			return new Listas();
		}
	}

	public void salvar(Listas usuario) {
		
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO), usuario);
		}

		catch (IOException e) {
			System.err.println("Erro ao salvar usuário: " + e.getMessage());
		}
	}
}