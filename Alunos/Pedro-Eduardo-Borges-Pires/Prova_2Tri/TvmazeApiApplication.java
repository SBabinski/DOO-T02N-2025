package spring_boot_api.tvmaze_api;
import org.springframework.boot.CommandLineRunner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TvmazeApiApplication implements CommandLineRunner{
	
	@Autowired
    private Console console;

	public static void main(String[] args) {
		SpringApplication.run(TvmazeApiApplication.class, args);
	}
	
	public void run(String... args) {
        console.iniciar(); 
    }

}
