package com.hospital.apihospital.services.LoginServices;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import com.hospital.apihospital.services.JwtServices.JwtTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServices {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final JwtTokenService jwtTokenService;

    public LoginServices(PasswordEncoder passwordEncoder, UsersRepository usersRepository, JwtTokenService jwtTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.jwtTokenService = jwtTokenService;
    }

    public String login(String email, String password){
        CadastrarUsers cadastrarUsers = usersRepository.findByEmail(email);

        if (cadastrarUsers != null && passwordEncoder.matches(password, cadastrarUsers.getPassword())){
            String token = jwtTokenService.generateToken(cadastrarUsers.getEmail());
            return token;
        } else {
            return null;
        }
    }
}
