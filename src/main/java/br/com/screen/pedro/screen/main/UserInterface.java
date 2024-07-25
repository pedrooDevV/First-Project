package br.com.screen.pedro.screen.main;

import br.com.screen.pedro.screen.model.DadosDaSerie;
import br.com.screen.pedro.screen.model.DadosEpisodio;
import br.com.screen.pedro.screen.model.DadosTemporada;
import br.com.screen.pedro.screen.service.ApiDeConsumo;
import br.com.screen.pedro.screen.service.ConverteOsDados;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private ApiDeConsumo apiDeConsumo = new ApiDeConsumo();
    private ConverteOsDados converteOsDados = new ConverteOsDados();
    private Scanner scanner = new Scanner(System.in);
    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String APY_KEY = "&apikey=38cc1155";

    private final String APY_KEYEP = "&Episode=";

   public void Menu() {
        System.out.println("Digite sua serie: ");
        var serie = scanner.nextLine();
        var json = apiDeConsumo.obterDados(ENDERECO + serie.replace(" ", "+") + APY_KEY);
        DadosDaSerie dados = converteOsDados.obterDados(json, DadosDaSerie.class);
        System.out.println(dados);

        List<DadosTemporada> listaDeTemporadas = new ArrayList<>();


        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = apiDeConsumo.obterDados(ENDERECO + serie.replace(" ", "+") + "&season=" + i + APY_KEY);
            DadosTemporada dadosTemporada = converteOsDados.obterDados(json, DadosTemporada.class);
            listaDeTemporadas.add(dadosTemporada);
        }
        listaDeTemporadas.forEach(System.out::println);

        for (int i = 0; i < dados.totalTemporadas(); i++) {
            List<DadosEpisodio> episodiosTemporada = listaDeTemporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++){
                System.out.println(episodiosTemporada.get(i).titulo());
            }
        }

        listaDeTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

//        List<String> nomes = Arrays.asList("pedro", "Maria", "Joao", "Carlos");
//        nomes.stream().sorted().limit(3).filter(n -> n.startsWith("M")).map(n -> n.toLowerCase())
//                .forEach(System.out::println);


    }
}
