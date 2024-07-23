package br.com.screen.pedro.screen.service;

import br.com.screen.pedro.screen.model.DadosDaSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteOsDados implements InConverteDados{
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obterDados(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (JsonProcessingException e){
          throw new RuntimeException();
        }
    }
}
