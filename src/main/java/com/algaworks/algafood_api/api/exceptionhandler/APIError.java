package com.algaworks.algafood_api.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

//Classe genérica de Erros para passar ao ExceptionHandler dentro do padrão RFC 7807
//Eu incluo na minha menssagem de erro somente o que for passado dentro de exception handler
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class APIError {
//    Os elementos dessa classe foram criados seguindo o padrão do RFC 7807
    private Integer status;
    private String type;
    private String title ;
    private String detail;

    private String userMessage;
    private LocalDateTime timestamp;

    private List<Field> fields;


    @Getter
    @Builder
    public static class Field {
        private String name;
        private String userMessage;
    }
}


