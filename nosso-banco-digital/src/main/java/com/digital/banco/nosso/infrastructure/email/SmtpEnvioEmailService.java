package com.digital.banco.nosso.infrastructure.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.digital.banco.nosso.core.email.EmailProperties;
import com.digital.banco.nosso.domain.service.EnvioEmailService;

public class SmtpEnvioEmailService implements EnvioEmailService{

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(emailProperties.getRemetente());
			helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			helper.setSubject(mensagem.getAssunto());
			helper.setText(mensagem.getCorpo(), true);
			
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possivel enviar e-mail.", e);
		} 
	}

	
}
