package com.algaworks.algafood_api.infrastructure.service.email;

import com.algaworks.algafood_api.core.email.EmailProperties;
import com.algaworks.algafood_api.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


public class SandboxEnvioEmailService extends SmtpEnvioEmailService{

    @Autowired
    private EmailProperties emailProperties;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void enviar(EnvioEmailService.Menssagem menssagem) {
        try {
            MimeMessage mimeMessage = generateMimeMessage(menssagem);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar e-email." , e);
        }
    }

    protected MimeMessage generateMimeMessage(Menssagem menssagem){
        try {
            String corpo = processarTemplate(menssagem);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(emailProperties.getRemetente());
            helper.setSubject(menssagem.getAssunto());
            helper.setText(corpo , true);
            return mimeMessage;
        }
        catch (Exception e) {
            throw new EmailException("Não foi possível criar mensagem de e-email." , e);
        }
    }

}
