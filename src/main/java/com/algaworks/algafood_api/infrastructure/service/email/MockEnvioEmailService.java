package com.algaworks.algafood_api.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MockEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(Menssagem menssagem) {
        String corpo = processarTemplate(menssagem);
        log.info("[MOCK] - E-mail será enviado para o :{} \n Com o assunto: {} \n Com o corpo: {}" , menssagem.getDestinatarios() , menssagem.getAssunto(), corpo );
    }

}
