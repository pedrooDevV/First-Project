package br.com.screen.pedro.screen.model;

import br.com.screen.pedro.screen.service.InConverteDados;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") Integer numeroTemporada,
                            @JsonAlias("Episodes") List<DadosEpisodio> episodios) {
}
