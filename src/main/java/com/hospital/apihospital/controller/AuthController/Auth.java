package com.hospital.apihospital.controller.AuthController;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.services.AuthServices.LoginServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/login")
@RestController
public class Auth {
    private final LoginServices loginServices;
    public Auth(LoginServices loginServices) {
        this.loginServices = loginServices;
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody CadastrarUsers cadastrarUsers, HttpServletRequest request){
        try {
            String token1 = loginServices.login(cadastrarUsers.getEmail(), cadastrarUsers.getPassword(), request);
            if (token1 != null){
                Object responseObject = new Object() {
                    public final String token = token1;
                    public final String role = cadastrarUsers.getAuthorities().toString();
                };
                return ResponseEntity.ok(responseObject.toString());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed in Login");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed Login: " + e.getMessage());
        }
    }
}
