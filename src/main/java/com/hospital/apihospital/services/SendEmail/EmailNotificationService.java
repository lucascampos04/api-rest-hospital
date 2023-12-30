package com.hospital.apihospital.services.SendEmail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Random;

@Service
public class EmailNotificationService{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${support.mail}")
    private String supportMail;

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
        return "<h1>Olá " + nomeUsuario + ",</h1>"
                + "<p>Sua senha foi alterada com sucesso.</p>"
                + "<p>Se você não realizou esta alteração, entre em contato conosco imediatamente.</p>";
    }

    public void setMailSenderClient(String subject, String email, String content, String nomeUsuario) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mail);
        messageHelper.setSubject(subject);

        String emailContent = content + "<p>Nome do Usuário: <strong>" + nomeUsuario + "</strong></p>";

        messageHelper.setText(emailContent, true);
        messageHelper.setFrom(supportMail);
        messageHelper.setTo(email);
        mailSender.send(mail);
        System.out.println("Email enviado");
    }

    public String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    public String getPasswordEmailContent(String pass,String resetPasswordUrl) {
        return "<p>Olá,</p>"
                + "<p>Sua nova senha é: <strong>" + pass + "</strong></p>"
                + "<p>Por favor, acesse o link a seguir para redefinir sua senha: <a href='" + resetPasswordUrl + "'>" + resetPasswordUrl + "</a></p>";
    }
}
