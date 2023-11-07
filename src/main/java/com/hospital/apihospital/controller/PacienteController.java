package com.hospital.apihospital.controller;

import com.hospital.apihospital.Model.DTO.PacienteDTO;
import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import com.hospital.apihospital.Model.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pr;

    @GetMapping
    public List<PacienteDTO> listarPacientes(){
        List<CadastrarPaciente> pacientes = pr.findAll();
        List<PacienteDTO> pacienteDTOs = pacientes.stream()
                .map(PacienteDTO::fromEntity)
                .collect(Collectors.toList());
        return pacienteDTOs;
    }

    @PostMapping("/addpaciente")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody PacienteDTO pacienteDTO, BindingResult result){
        if (result.hasErrors()){
            StringBuilder errorMensagem = new StringBuilder("Erro de validação: ");
            result.getAllErrors().forEach(error -> {
                errorMensagem.append(error.getDefaultMessage()).append("; ");
            });
            return ResponseEntity.badRequest().body(errorMensagem.toString());
        }

        try{
            CadastrarPaciente paciente = new CadastrarPaciente();
            paciente.setNome(pacienteDTO.nome());
            paciente.setCpf(pacienteDTO.cpf());
            paciente.setRg(pacienteDTO.rg());

            CadastrarPaciente pacienteSave = pr.save(paciente);

            return ResponseEntity.ok("Paciente cadastrado com sucesso! ID: " + pacienteSave.getId());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar o paciente: " + e.getMessage());
        }
    }

    @DeleteMapping("deletepaciente/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id){
       if (pr.existsById(id)){
           pr.deleteById(id);
           return ResponseEntity.ok().build();
       } else {
           return ResponseEntity.notFound().build();
       }
    }


}
