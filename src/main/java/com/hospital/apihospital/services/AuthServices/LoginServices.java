package com.hospital.apihospital.services.AuthServices;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import com.hospital.apihospital.services.JwtServices.JwtTokenService;
import com.hospital.apihospital.services.SendEmail.NotificationLogin;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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

    public ResponseEntity<Map<String, Object>> login(String email, String password, HttpServletRequest request){
        CadastrarUsers cadastrarUsers = usersRepository.findByEmail(email);

        if (cadastrarUsers != null && passwordEncoder.matches(password, cadastrarUsers.getPassword())){
            String userAgent = request.getHeader("User-Agent");
            String token = jwtTokenService.generateToken(cadastrarUsers.getEmail());

            // Data formatada dd/mm/yyyy
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String horario = now.format(format);

            notificationLogin.sendNotificationLoginInAccount(cadastrarUsers.getEmail(), cadastrarUsers.getNome(), horario,userAgent);
            System.out.println("Role : " + cadastrarUsers.getRole());
            System.out.println("Nome " + cadastrarUsers.getNome());

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("token", token);
            responseMap.put("role", cadastrarUsers.getRole());
            responseMap.put("nome", cadastrarUsers.getNome());
            responseMap.put("cpf", cadastrarUsers.getCpf());

            return ResponseEntity.ok().body(responseMap);
        } else {
            return null;
        }
    }
}
