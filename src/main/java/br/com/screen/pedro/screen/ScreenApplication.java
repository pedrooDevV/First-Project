package br.com.screen.pedro.screen;

import br.com.screen.pedro.screen.main.UserInterface;
import br.com.screen.pedro.screen.model.DadosTemporada;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserInterface userMenu = new UserInterface();
        userMenu.Menu();
    }
}
