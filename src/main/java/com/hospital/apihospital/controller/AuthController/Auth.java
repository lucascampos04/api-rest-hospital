package com.hospital.apihospital.controller.AuthController;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.services.AuthServices.LoginServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/login")
@RestController
public class Auth {
    private final LoginServices loginServices;
    public Auth(LoginServices loginServices) {
        this.loginServices = loginServices;
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody CadastrarUsers cadastrarUsers, HttpServletRequest request){
        String token = loginServices.login(cadastrarUsers.getEmail(), cadastrarUsers.getPassword(), request);

        if (token != null){
            return ResponseEntity.ok("Token: "+token + " Role : " + cadastrarUsers.getAuthorities());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed in Login");
        }
    }
}
