package com.algaworks.algafood_api.core.email;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Component
//To transformando uma especificação própria do application.properties em uma classe para generalizar o remetente do email e o estado da implementação (por padrão é MOCK/testes)
@ConfigurationProperties("algafood.email")
public class EmailProperties {


    private Sandbox sandbox = new Sandbox();

    @NotNull
    private String remetente;

    private Implementacao impl = Implementacao.MOCK;

    public enum Implementacao {
        SMTP, MOCK , SANDBOX
    }

}
