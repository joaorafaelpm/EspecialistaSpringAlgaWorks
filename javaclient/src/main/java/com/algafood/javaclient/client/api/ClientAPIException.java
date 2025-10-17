package com.algafood.javaclient.client.api;

import org.springframework.web.client.RestClientResponseException;

import com.algafood.javaclient.client.model.Problem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientAPIException extends RuntimeException {
    
    @Getter
    private Problem problem ;

    public ClientAPIException(String message) {
        super(message);
    }
    
    public ClientAPIException(String message , RestClientResponseException cause) {
        super(message , cause);
        deserializeProblem(cause) ;
    }

    

    private void deserializeProblem(RestClientResponseException cause) {
        try {
            ObjectMapper mapper = new ObjectMapper() ;
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.registerModule(new JavaTimeModule());
            mapper.findAndRegisterModules();
            
            this.problem = mapper.readValue(cause.getResponseBodyAsString(), Problem.class);
        }
        catch (JsonProcessingException e) {
            log.warn("Não foi possível desserializar o corpo da resposta em Problem", e) ;
        }
        

    }

}
