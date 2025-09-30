package com.algaworks.algafood_api.api.exceptionhandler;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

//Classe genérica de Erros para passar ao ExceptionHandler

@Getter
@Builder
public class APIError {
    private LocalDateTime dataHora ;
    private String message ;
}
