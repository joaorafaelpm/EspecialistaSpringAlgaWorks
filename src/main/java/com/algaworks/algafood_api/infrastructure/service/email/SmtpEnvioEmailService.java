package com.algaworks.algafood_api.infrastructure.service.email;

import com.algaworks.algafood_api.core.email.EmailProperties;
import com.algaworks.algafood_api.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private EmailProperties emailProperties;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void enviar(Menssagem menssagem) {
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
            helper.setTo(menssagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(menssagem.getAssunto());
            helper.setText(corpo, true);
            return mimeMessage;
        }
        catch (Exception e) {
            throw new EmailException("Não foi possível criar mensagem de e-email." , e);
        }
    }


    protected String processarTemplate (Menssagem menssagem) {
        try {
            Template template = freemarkerConfig.getTemplate(menssagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, menssagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do e-mail." , e);
        }
    }
}
