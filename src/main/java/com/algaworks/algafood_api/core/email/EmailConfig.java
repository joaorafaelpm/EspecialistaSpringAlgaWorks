package com.algaworks.algafood_api.core.email;

import com.algaworks.algafood_api.domain.service.EnvioEmailService;
import com.algaworks.algafood_api.infrastructure.service.email.MockEnvioEmailService;
import com.algaworks.algafood_api.infrastructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafood_api.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

//    Como a gente pode estar em fase de teste ou não, a gente valida aqui. Em todos os casos que a implementação for para teste, dentro de emailProperties estará anotado como fake
//    O método será decidido a partir disso, então basta alterar o application.properties para o método desejado e manter o padrão de testes por precaução
    @Bean
    public EnvioEmailService envioEmailService() {
        switch (emailProperties.getImpl()) {
            case MOCK:
                return new MockEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEnvioEmailService();
            default:
                return null;
        }
    }
}
