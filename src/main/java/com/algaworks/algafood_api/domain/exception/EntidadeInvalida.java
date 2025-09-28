package com.algaworks.algafood_api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntidadeInvalida extends ResponseStatusException {
//  É uma má prática colocar esse conhecimento http em uma classe de domímino (não acessa as urls)
    public EntidadeInvalida(String message) {
        super(HttpStatus.BAD_REQUEST ,message);
    }
    public EntidadeInvalida(HttpStatus status ,String message) {
        super(status , message);
    }
}
