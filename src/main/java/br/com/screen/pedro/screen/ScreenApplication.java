package br.com.screen.pedro.screen;

import br.com.screen.pedro.screen.model.DadosDaSerie;
import br.com.screen.pedro.screen.service.ApiDeConsumo;
import br.com.screen.pedro.screen.service.ConverteOsDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		var apiDeConsumo = new ApiDeConsumo();
		var json = apiDeConsumo.obterDados("http://www.omdbapi.com/?i=gilmore+girls&apikey=38cc1155");
		System.out.println(json);
		ConverteOsDados converte = new ConverteOsDados();
		DadosDaSerie dados = converte.obterDados(json, DadosDaSerie.class);
		System.out.println(dados);
	}
}
