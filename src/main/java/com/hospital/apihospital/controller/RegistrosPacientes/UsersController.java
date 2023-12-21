package com.hospital.apihospital.controller.RegistrosPacientes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.services.CadastroService.CadastrarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.apihospital.Model.DTO.UsersDTO;
import com.hospital.apihospital.Model.Repository.UsersRepository;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v1/pacientes")
public class UsersController {

    @Autowired
    private CadastrarUserService cadastrarUserService;

    /**
     * Cadastra um novo paciente.
     *
     * @param usersDTO O objeto PacienteDTO contendo informações do paciente a
     *                    ser cadastrado.
     * @param result      O objeto BindingResult para validação.
     * @return Uma resposta com a mensagem de sucesso ou erro.
     */
    @PostMapping("/addpaciente")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody UsersDTO usersDTO, BindingResult result) {
        ResponseEntity<String> response = cadastrarUserService.cadastrarPaciente(usersDTO, result);
        if (response.getStatusCode() == HttpStatus.OK){
            return ResponseEntity.ok().body(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }
}