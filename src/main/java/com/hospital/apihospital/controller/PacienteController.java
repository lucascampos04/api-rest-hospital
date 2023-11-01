package com.hospital.apihospital.controller;

import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import com.hospital.apihospital.Model.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pr;

    @GetMapping
    public List<CadastrarPaciente> listarPacientes(){
        List<CadastrarPaciente> pacientes = pr.findAll();
        return pacientes;
    }

    @PostMapping("api/addpaciente")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody CadastrarPaciente cadastrar, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(String.valueOf((CadastrarPaciente) result.getAllErrors()));
        }
        CadastrarPaciente cadastrarPaciente = pr.save(cadastrar);
        return ResponseEntity.ok("Paciente inserido com sucesso! CPF: " + cadastrarPaciente.getCpf());
    }

    @DeleteMapping("deletepaciente/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id){
       if (!pr.existsById(id)){
           return ResponseEntity.ok().build();
       }
       pr.deleteById(id);
       return ResponseEntity.notFound().build();
    }


}
