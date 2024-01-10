package com.hospital.apihospital.services.SendEmail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;

@Service
public class NotificationConsultaSuccess {
    private final JavaMailSender mailSender;

    @Value("${support.mail}")
    private String supportMail;

    public NotificationConsultaSuccess(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setMailSenderClient(String subject, String email, String content, String nomeUsuario) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mail);
        messageHelper.setSubject(subject);

        String emailContent = content + "<p>Olá : <strong>" + nomeUsuario + "</strong></p>";

        messageHelper.setText(emailContent, true);
        messageHelper.setFrom(supportMail);
        messageHelper.setTo(email);
        mailSender.send(mail);
        System.out.println("Email enviado");
    }

    public String getConsultaSuccess(Long id, Date data, String login){
        return "<html>"
                + "<head>"
                + "  <style>"
                + "    body { font-family: 'Arial', sans-serif; }"
                + "    h4 { color: #3498db; }"
                + "    p { font-size: 16px; }"
                + "    strong { color: #27ae60; }"
                + "    a { color: #3498db; text-decoration: none; }"
                + "    a:hover { text-decoration: underline; }"
                + "  </style>"
                + "</head>"
                + "<body>"
                + "  <h4>Consulta marcada com sucesso!!</h4>"
                + "  <p><strong>" + id + "</strong></p>"
                + "  <p>Data : " + data + "</p>"
                + "  <p>Para mais informaçõs : <a href='" + login + "'>" + login + "</a></p>"
                + "</body>"
                + "</html>";
    }
}
