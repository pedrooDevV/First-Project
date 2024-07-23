package br.com.screen.pedro.screen.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosDaSerie(@JsonAlias("Title") String titulo,
                           @JsonAlias("totalSeasons") Integer totalTemporadas,
                           @JsonAlias("imdbRating") double Avaliacao) {


}
