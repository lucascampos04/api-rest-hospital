package com.hospital.apihospital.services.SendEmail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class NotificationLogin {
    private final JavaMailSender mailSender;

    @Value("${support.mail}")
    private String supportMail;
    public NotificationLogin(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotificationLoginInAccount(String email, String nomeUsuario, String horario, String userAgent){
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mail);
            messageHelper.setSubject("Login realizado");
            messageHelper.setText(getLoginNotificationContent(nomeUsuario, horario, userAgent), true);
            messageHelper.setFrom(supportMail);
            messageHelper.setTo(email);
            mailSender.send(mail);
            System.out.println("Email enviado com sucesso para: " + email);
        } catch (Exception e) {
            System.out.println("Erro inesperado ao enviar o e-mail para: " + email);
            e.printStackTrace();
        }
    }

    private String getLoginNotificationContent(String nomeUsuario, String horario, String userAgent) {
        return "<html>"
                + "<body>"
                + "  <div style='background-color: #3498db; color: #ffffff; padding: 10px; text-align: center;'>"
                + "    <h1 style='color: #ffffff;'> Olá " + nomeUsuario + "!</h1>"
                + "    <p>Seu login foi realizado em: " + horario + "</p>"
                + "    <p>Dispositivo utilizado: " + userAgent + "</p>"
                + "    <p>Esse login foi realizado a partir do dispositivo: " + userAgent + "</p>"
                + "    <p>Caso não reconheça esta atividade, <a href='https://lucascampos04.github.io/pag404/' style='color:red'>acesse suas configurações de segurança</a>.</p>"
                + "  </div>"
                + "</body>"
                + "</html>";
    }
}
