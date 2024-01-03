package com.hospital.apihospital.services.AuthServices;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import com.hospital.apihospital.services.JwtServices.JwtTokenService;
import com.hospital.apihospital.services.SendEmail.NotificationLogin;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LoginServices {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final JwtTokenService jwtTokenService;
    private final NotificationLogin notificationLogin;

    public LoginServices(PasswordEncoder passwordEncoder, UsersRepository usersRepository, JwtTokenService jwtTokenService, NotificationLogin notificationLogin) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.jwtTokenService = jwtTokenService;
        this.notificationLogin = notificationLogin;
    }

    public String login(String email, String password, HttpServletRequest request){
        CadastrarUsers cadastrarUsers = usersRepository.findByEmail(email);

        if (cadastrarUsers != null && passwordEncoder.matches(password, cadastrarUsers.getPassword())){
            String userAgent = request.getHeader("User-Agent");
            String token = jwtTokenService.generateToken(cadastrarUsers.getEmail());

            // Data formatada dd/mm/yyyy
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String horario = now.format(format);

            notificationLogin.sendNotificationLoginInAccount(cadastrarUsers.getEmail(), cadastrarUsers.getNome(), horario,userAgent);
            return token;
        } else {
            return null;
        }
    }
}
