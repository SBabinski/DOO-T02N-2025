package spring_boot_api.tvmaze_api;

import java.util.ArrayList;
import java.util.List;

public class Listas {
	
	    private String nome;
	    private List<ShowsDTO> favoritas = new ArrayList<>();
	    private List<ShowsDTO> assistidas = new ArrayList<>();
	    private List<ShowsDTO> desejoAssistir = new ArrayList<>();
		
	    
	    public String getNome() {
			return nome;
		}
		
	    public void setNome(String nome) {
			this.nome = nome;
		}
		public List<ShowsDTO> getFavoritas() {
			return favoritas;
		}
		public void setFavoritas(List<ShowsDTO> favoritas) {
			this.favoritas = favoritas;
		}
		public List<ShowsDTO> getAssistidas() {
			return assistidas;
		}
		public void setAssistidas(List<ShowsDTO> assistidas) {
			this.assistidas = assistidas;
		}
		public List<ShowsDTO> getDesejoAssistir() {
			return desejoAssistir;
		}
		public void setDesejoAssistir(List<ShowsDTO> desejoAssistir) {
			this.desejoAssistir = desejoAssistir;
		}

}
