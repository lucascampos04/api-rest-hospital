package com.hospital.apihospital.services.SendEmail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationConsulta {
    private final JavaMailSender mailSender;

    private String supportMail = "camposdlucasoli@gmail.com";

    public NotificationConsulta(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotificationConsultaMarcada(String email, String nomePaciente, String tipoConsulta, Date data) {
        try {
            if (email != null) {
                MimeMessage mail = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mail);
                messageHelper.setSubject("Consulta Marcada");
                messageHelper.setText(getConsultaNotificationContent(nomePaciente, tipoConsulta, data), true);
                messageHelper.setFrom(supportMail);
                messageHelper.setTo(email);
                mailSender.send(mail);
                System.out.println("Email enviado com sucesso para: " + email);
            } else {
                System.out.println("Email nulo. Não foi enviado nenhum e-mail.");
            }
        } catch (MessagingException | MailException e) {
            System.out.println("Erro inesperado ao enviar o e-mail para: " + email);
            e.printStackTrace();
        }
    }

    private String getConsultaNotificationContent(String nomePaciente, String tipoConsulta, Date data) {
        return "<html>"
                + "<body>"
                + "  <div style='background-color: #3498db; color: #ffffff; padding: 10px; text-align: center;'>"
                + "    <h1 style='color: #ffffff;'>Olá " + nomePaciente + "!</h1>"
                + "    <p>Sua consulta foi marcada com sucesso.</p>"
                + "    <p>Tipo de consulta: " + tipoConsulta + "</p>"
                + "    <p>Horário da consulta: " + data + "</p>"
                + "    <p>Confira os detalhes da sua consulta e prepare-se para a visita.</p>"
                + "    <p>Em caso de dúvidas, entre em contato conosco.</p>"
                + "  </div>"
                + "</body>"
                + "</html>";
    }
}
