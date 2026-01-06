package com.algaworks.algafood_api.core.security.authorizationserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties("algafood.auth")
public class AlgaFoodSecurityProperties {

    private String providerUrl;

    public String getProviderUrl() {
        return providerUrl;
    }
}
