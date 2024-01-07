package com.hospital.apihospital.controller.AuthController;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.services.AuthServices.LoginServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/login")
@RestController
public class Auth {
    private final LoginServices loginServices;
    public Auth(LoginServices loginServices) {
        this.loginServices = loginServices;
    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> auth(@RequestBody CadastrarUsers cadastrarUsers, HttpServletRequest request){
        return loginServices.login(cadastrarUsers.getEmail(), cadastrarUsers.getPassword(), request);
    }

}
