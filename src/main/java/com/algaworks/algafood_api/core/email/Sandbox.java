package com.algaworks.algafood_api.core.email;


import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
public class Sandbox {

    @NonNull
    private String destinatario ;

}
