package br.com.screen.pedro.screen.main;

import br.com.screen.pedro.screen.model.DadosDaSerie;
import br.com.screen.pedro.screen.model.DadosEpisodio;
import br.com.screen.pedro.screen.model.DadosTemporada;
import br.com.screen.pedro.screen.model.Episodio;
import br.com.screen.pedro.screen.service.ApiDeConsumo;
import br.com.screen.pedro.screen.service.ConverteOsDados;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        listaDeTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = listaDeTemporadas.stream().flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(p -> System.out.println("Primeiro filtro N/A " + p))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(p -> System.out.println("Ordenação " +p))
//                .limit(5).
//                map(e -> e.titulo().toUpperCase()).
//                forEach(System.out::println);

        List<Episodio> episodios = listaDeTemporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);
        System.out.println("Digite o Trecho");
        var trechoTitulo = scanner.nextLine();
        Optional<Episodio> trechoEpisodio = episodios.stream().
                filter(t -> t.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase())).findFirst();

        if(trechoEpisodio.isPresent()){
            System.out.println("Episodio encontrado");
            System.out.println("Temporada " + trechoEpisodio.get().getTemporada());
        } else {
            System.out.println("Referência não encontrada");
        }

        Map<Integer, Double> avaliacaoPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacaoPorTemporada);

        DoubleSummaryStatistics est  = episodios.stream()
                 .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
                System.out.println("Média: " + est.getAverage());
                System.out.println("Melhor episódio: " + est.getMax());
                System.out.println("Pior episódio: " + est.getMin());
                System.out.println("Quantidade: " + est.getCount());

//        System.out.println("Digite apartir de que data você quer ver seus apisodios: ");
//        var data = scanner.nextInt();
//        scanner.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(data, 1, 1);
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataDeLancamento() != null && e.getDataDeLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episodio: " + e.getTitulo() +
//                                " Data de lançamento: " + e.getDataDeLancamento().format(dateTimeFormatter)
//                ));
    }
}
