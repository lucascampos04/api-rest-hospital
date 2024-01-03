package com.hospital.apihospital.services.SendEmail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class MessageCreateAccountInSendOfPassword {
    private final JavaMailSender mailSender;

    @Value("${support.mail}")
    private String supportMail;

    public MessageCreateAccountInSendOfPassword(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordChangeNotification(String email, String nomeUsuario) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mail);
            messageHelper.setSubject("Senha Alterada");
            messageHelper.setText(getPasswordChangeNotificationContent(nomeUsuario), true);
            messageHelper.setFrom(supportMail);
            messageHelper.setTo(email);
            mailSender.send(mail);
            System.out.println("Email enviado com sucesso para: " + email);
        } catch (Exception e) {
            System.out.println("Erro inesperado ao enviar o e-mail para: " + email);
            e.printStackTrace();
        }
    }

    private String getPasswordChangeNotificationContent(String nomeUsuario) {
        return "<html>"
                + "<body>"
                + "  <div style='background-color: #3498db; color: #ffffff; padding: 20px; text-align: center;'>"
                + "    <h1 style='color: #ffffff;'> Olá " + nomeUsuario + "</h1>"
                + "    <p style='font-size: 16px;'> Sua senha foi alterada com sucesso.</p>"
                + "    <p style='font-size: 16px;'> Se você não realizou esta alteração, entre em contato conosco imediatamente.</p>"
                + "  </div>"
                + "</body>"
                + "</html>";
    }
}
