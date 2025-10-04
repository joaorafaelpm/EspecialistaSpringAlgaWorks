package com.algaworks.algafood_api.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

//    Fazendo a integração do Bean Validation com o Spring, para unificar o padrão de respostas do Bean Validation (ValidationMessage.properties) com o padrão do Spring (messages.properties)
    @Bean
    public LocalValidatorFactoryBean validation (MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();

//        Essa especificação é a que une os dois padrões ValidationMessages.properties e messages.properties em um só, usando o messages.properties como padrão para ambos pacotes de menssagem
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

}
