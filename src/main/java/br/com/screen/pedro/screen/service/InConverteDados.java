package br.com.screen.pedro.screen.service;

public interface InConverteDados {
    <T> T obterDados(String json, Class<T> tClass);
}
